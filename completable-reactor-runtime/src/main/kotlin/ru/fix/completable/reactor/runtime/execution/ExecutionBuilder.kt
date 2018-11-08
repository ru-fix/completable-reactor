package ru.fix.completable.reactor.runtime.execution


import mu.KotlinLogging
import ru.fix.aggregating.profiler.Profiler
import ru.fix.completable.reactor.graph.runtime.RuntimeTransition
import ru.fix.completable.reactor.graph.runtime.RuntimeGraph
import ru.fix.completable.reactor.graph.runtime.RuntimeVertex
import ru.fix.completable.reactor.runtime.debug.DebugSerializer
import ru.fix.completable.reactor.runtime.tracing.Tracer
import java.util.*
import java.util.concurrent.CompletableFuture

typealias SubgraphRunner = (Any?) -> CompletableFuture<Any?>

private val log = KotlinLogging.logger {}

class ExecutionBuilder(
        val profiler: Profiler,
        val subgraphRunner: SubgraphRunner,
        val debugSerializer: DebugSerializer,
        val tracer: Tracer) {


    data class MergePayloadContext(
            var payload: Any? = null,
            /**
             * Transition marked as dead when merge status does not match transition condition
             */
            var isDeadTransition: Boolean = false,
            /**
             * Terminal graph state reached.
             * No further merging (or payload modification) is allowed.
             * All transitions except copy-transition for detached processors is allowed.
             */
            var isTerminal: Boolean = false,

            var mergeResult: Any? = null
    )


    data class TransitionPayloadContext(
            var payload: Any? = null,
            /**
             * Transition marked as dead when merge status does not match transition condition
             */
            var isDeadTransition: Boolean = false,
            /**
             * Terminal graph state reached.
             * No further merging (or payload modification) is allowed.
             * All transitions except copy-transition for detached processors is allowed.
             */
            var isTerminal: Boolean = false
    )

    data class HandlePayloadContext(

            var payload: Any? = null,
            /**
             * Transition marked as dead when merge status does not match transition condition
             */
            var isDeadTransition: Boolean = false,
            /**
             * Terminal graph state reached.
             * No further merging (or payload modification) is allowed.
             * All transitions except copy-transition for detached processors is allowed.
             */
            var isTerminal: Boolean = false,
            var handlingResult: Any? = null
    )

    companion object {
        val INVALID_MERGE_PAYLOAD_CONTEXT = MergePayloadContext()
        val INVALID_TRANSITION_PAYLOAD_CONTEXT = TransitionPayloadContext()
        val INVALID_HANDLE_PAYLOAD_CONTEXT = HandlePayloadContext()
    }


    /**
     * Represent deferred computation result of a transition
     * //TODO replace with type alias
     */
    class TransitionFuture<PayloadContextType>(
            val feature: CompletableFuture<PayloadContextType>
    )


    /**
     * @param reactorGraph
     * @param <PayloadType>
     * @return
     */
    fun <PayloadType> build(runtimeGraph: RuntimeGraph): ReactorGraphExecution<PayloadType> {


        /**
         * Internal representation of processing graph based on processing vertices.
         * //TODO: check, maybe we do not need map and simple list is enough fo rCR2
         */
        val processingVertices = IdentityHashMap<RuntimeVertex, ProcessingVertex>()


        val submitFuture = CompletableFuture<PayloadType>()

        /**
         * Will be completed on payload submission to processor chain
         */
        val startPointTransitionFuture = submitFuture.thenApplyAsync { payload ->
            TransitionPayloadContext(payload = payload)
        }

        /**
         * Will be completed with payload when terminal graph state would be reached.
         */
        val executionResultFuture = CompletableFuture<PayloadType>()

        /**
         * Init Processing Vertices based on graph model vertices
         *
         */
        runtimeGraph.vertices.forEach { vx ->
            val pvx = ProcessingVertex(
                    vertex = vx,
                    subgraphRunner = subgraphRunner,
                    debugSerializer = debugSerializer)

            /**
             * Populate MergePoints transitions
             */
            if (vx.transitions.isNotEmpty()) {
                //TODO: replace copy with delegation since outgoingTransitions is immutable
                pvx.outgoingTransitions.addAll(vx.transitions)
            }

            processingVertices[vx] = pvx
        }

        /**
         * Populate start point transition
         */
        for (vx in runtimeGraph.startPoint) {
            val pvx = processingVertices[vx]!!
            pvx.incomingHandlingFlows.add(TransitionFuture(startPointTransitionFuture))
        }


        /**
         * Populate outgoing flows
         */
        processingVertices.values.asSequence().filter { it.vertex.isMergerable }.forEach { mergerablePvx ->

            /**
             * Completion of Mergerable feature triggers completion of outgoing transitions.
             */

            val mergingFeature = mergerablePvx.mergingFuture

            for (transition in mergerablePvx.outgoingTransitions) {

                /**
                 * Terminal merge point vertex handled synchronously with merging process
                 * Skip that kind of transition during pre-processing.
                 */
                if (!transition.isComplete) {
                    /**
                     * Populate outgoing handleBy flows
                     * activates when Mergerable feature is completed
                     */
                    if (transition.handleBy != null) {
                        val target = transition.handleBy

                        processingVertices[target]!!.incomingHandlingFlows.add(TransitionFuture(
                                mergingFeature.thenApplyAsync { context ->

                                    if (context.isTerminal) {
                                        TransitionPayloadContext(isTerminal = true)

                                    } else if (context.isDeadTransition) {
                                        TransitionPayloadContext(isDeadTransition = true)

                                    } else if (transition.isOnAny
                                            || transition.mergeStatuses.contains(context.mergeResult)) {

                                        TransitionPayloadContext(payload = context.payload)

                                    } else if (isOnElse(transition, mergerablePvx, context)) {
                                        TransitionPayloadContext(payload = context.payload)

                                    } else {
                                        TransitionPayloadContext(
                                                payload = context.payload,
                                                isDeadTransition = true)
                                    }

                                }.exceptionally { exc ->
                                    log.error(exc) {
                                        """
                                        Failed to activate handleBy transition.
                                        Mark transition as dead.
                                        Transition from ${mergerablePvx.vertex.name} to ${target?.name}.
                                        Transition: $transition
                                        """.trimIndent()
                                    }

                                    TransitionPayloadContext(isDeadTransition = true)
                                }
                        ))

                    }

                    /**
                     * Populate outgoing mergeBy flows
                     */
                    if (transition.mergeBy != null) {
                        val target = transition.mergeBy

                        processingVertices[target]!!.incomingMergingFlows.add(TransitionFuture(
                                mergingFeature.thenApplyAsync { context ->
                                    if (context.isTerminal) {
                                        MergePayloadContext(isTerminal = true)

                                    } else if (context.isDeadTransition) {
                                        MergePayloadContext(isDeadTransition = true)

                                    } else if (transition.isOnAny ||
                                            transition.mergeStatuses.contains(context.mergeResult)) {

                                        MergePayloadContext(
                                                payload = context.payload,
                                                mergeResult = context.mergeResult)

                                    } else if (isOnElse(transition, mergerablePvx, context)) {
                                      MergePayloadContext(
                                              payload = context.payload,
                                              mergeResult = context.mergeResult)
                                    } else {
                                        MergePayloadContext(isDeadTransition = true)
                                    }
                                }.exceptionally { exc ->
                                    log.error(exc) {
                                        """
                                        Failed to activate mergeBy transition.
                                        Mark transition as dead.
                                        Transition from ${mergerablePvx.vertex.name} to ${target?.name}.
                                        Transition: $transition
                                        """.trimIndent()
                                    }

                                    MergePayloadContext(isDeadTransition = true)
                                }
                        ))

                    }
                }
            }
        }

        HandleByExecutionBuilder(
                processingVertices,
                executionResultFuture,
                this
        ).joinIncomingHandleByFlowsToSingleHandlingInvocation()


        MergeByExecutionBuilder(
                processingVertices,
                executionResultFuture,
                this
        ).joinIncomingMergeByFlowsToSingleMergingInvocation()


        /**
         * Handle terminal vertices.
         * When execution reaches 'complete' vertex all transitions should be marked dead and complete.
         */
        executionResultFuture.thenRunAsync {
            processingVertices.values
                    .asSequence()
                    .flatMap { it.incomingHandlingFlows.asSequence() }
                    .map { it.feature }
                    .forEach { future ->
                        future.complete(TransitionPayloadContext(isDeadTransition = true))
                    }

            processingVertices.values
                    .asSequence()
                    .flatMap { it.incomingHandlingFlows.asSequence() }
                    .map { it.feature }
                    .forEach { future ->
                        future.complete(TransitionPayloadContext(isDeadTransition = true))
                    }
        }.exceptionally { throwable ->
            log.error(throwable) {
                "Marking transitions as dead " +
                        "after execution result feature is completed is failed."
            }
            null
        }

        /**
         * Collect chain execution handling futures.
         * Handling futures holds handle invocation result or dead or terminated context.
         * Handlers without mergers could complete much later than main flow.
         * We have to keep chainExecutionFuture in pending state until all vertices are completed.
         * Then all handling and merging futures completes, then chainExecutionFuture completes too.
         * Joint executionResultFuture to list of pending features too.
         */

        val chainExecutionFuture = CompletableFuture.allOf(
                executionResultFuture,
                * processingVertices.values
                        .asSequence()
                        .flatMap { listOf(it.handlingFuture, it.mergingFuture).asSequence() }
                        .toList()
                        .toTypedArray()
        )

        return ReactorGraphExecution(
                submitFuture,
                executionResultFuture,
                chainExecutionFuture,
                processingVertices.values)
    }

    /**
     * Check if transition is marked as `onElse`
     * and merge results are not satisfied by another existing transitions
     */
    private fun isOnElse(
            transition: RuntimeTransition,
            mergerablePvx: ProcessingVertex,
            context: ExecutionBuilder.MergePayloadContext
    ): Boolean {
        return transition.isOnElse &&
                mergerablePvx.outgoingTransitions.asSequence()
                        .filter { !it.isOnElse }
                        .all { !it.mergeStatuses.contains(context.mergeResult) }
    }

    fun <PayloadType> dumpExecutionState(execution: ReactorGraphExecution<PayloadType>): String {
        try {

            val possiblyExecuting = mutableListOf<ProcessingVertex>()
            val completed = mutableListOf<ProcessingVertex>()
            val others = mutableListOf<ProcessingVertex>()

            val state = execution.debugProcessingVertexGraphState as Collection<ProcessingVertex>

            state.forEach { vx ->
                when {
                    vx.handlingFuture.isDone
                            && vx.mergingFuture.isDone -> completed.add(vx)

                    vx.incomingHandlingFlows.all { it.feature.isDone }
                            && vx.incomingMergingFlows.all { it.feature.isDone } -> possiblyExecuting.add(vx)

                    else ->
                        others.add(vx)
                }
            }

            fun dump(future: CompletableFuture<*>) =
                    "\"(done:${future.isDone}, excptn:${future.isCompletedExceptionally})\""


            fun dump(vertices: Collection<ProcessingVertex>): String {
                return vertices.joinToString(",\n", "\n", "\n") { vx ->
                    "\"${vx.vertex.name}\":{\n" +
                            "\"handlingFuture\":${dump(vx.handlingFuture)},\n" +
                            "\"mergingFuture\":${dump(vx.mergingFuture)},\n" +
                            "\"incomingHandlingFlows\":[${vx.incomingHandlingFlows.joinToString { dump(it.feature) }}],\n" +
                            "\"incomingMergingFlows\":[${vx.incomingMergingFlows.joinToString { dump(it.feature) }}]\n" +
                            "}"
                }
            }

            return "{\n" +
                    "\"possiblyExecuting\": {${dump(possiblyExecuting)}},\n" +
                    "\"completed\": {${dump(completed)}},\n" +
                    "\"others\": {${dump(others)}}\n" +
                    "}"

        } catch (exc: Exception) {
            log.error(exc) { "Failed to dump state." }
            return "(failed to dump state)"
        }
    }

}
