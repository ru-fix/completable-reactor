package ru.fix.completable.reactor.runtime.execution


import mu.KotlinLogging
import ru.fix.aggregating.profiler.Profiler
import ru.fix.completable.reactor.graph.internal.GlTransition
import ru.fix.completable.reactor.graph.runtime.GlGraph
import ru.fix.completable.reactor.graph.runtime.GlVertex
import ru.fix.completable.reactor.runtime.debug.DebugSerializer
import ru.fix.completable.reactor.runtime.tracing.Tracer
import java.util.*
import java.util.concurrent.CompletableFuture
import kotlin.collections.ArrayList

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
     * Each Processing Vertex (pvx) is mapped to Vertex (vx).
     * Tree of vertexes (vx) represents graph model and it is immutable.
     * Tree of Processing Vertexes (pvx) represents execution graph. Runtime build execution graph each time payload
     * submitted to ReactorGraph.
     *
     * <img src="./doc-files/ProcessingVertex.png" alt="">
     */
    class ProcessingVertex(val vertex: GlVertex) {

        val incomingHandlingFlows = ArrayList<TransitionFuture<TransitionPayloadContext>>()

        val incomingMergingFlows = ArrayList<TransitionFuture<MergePayloadContext>>()

        //TODO: rename to Future
        val handlingFeature = CompletableFuture<HandlePayloadContext>()

        val mergingFeature = CompletableFuture<MergePayloadContext>()

        /**
         * Holds information about outgoing transitions from this merge point.
         * If during merging process we will receive merge status that match any of terminal transitions
         * then we should complete mergingFeature with Terminal MergePayloadContext
         */
        val outgoingTransitions = ArrayList<GlTransition>()

        /**
         * This is Merger or Router
         */
        val isMergerable: Boolean
            get() = vertex.merger != null || vertex.router != null
    }


    /**
     * @param reactorGraph
     * @param <PayloadType>
     * @return
     */
    fun <PayloadType> build(glGraph: GlGraph): ReactorGraphExecution<PayloadType> {


        /**
         * Internal representation of processing graph based on processing vertices.
         * //TODO: check, maybe we do not need map and simple list is enough fo rCR2
         */
        val processingVertices = IdentityHashMap<GlVertex, ProcessingVertex>()


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
        glGraph.vertices.forEach { vx ->
            val pvx = ProcessingVertex(vertex = vx)

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
        for (vx in glGraph.startPoint) {
            val pvx = processingVertices[vx]!!
            pvx.incomingHandlingFlows.add(TransitionFuture(startPointTransitionFuture))
        }


        /**
         * Populate outgoing flows
         */
        processingVertices.values.asSequence().filter { it.isMergerable }.forEach { mergerablePvx ->

            /**
             * Completion of Mergerable feature triggers completion of outgoing transitions.
             */

            val mergingFeature = mergerablePvx.mergingFeature

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
                        .flatMap { listOf(it.handlingFeature, it.mergingFeature).asSequence() }
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
            transition: GlTransition,
            mergerablePvx: ProcessingVertex,
            context: ExecutionBuilder.MergePayloadContext
    ): Boolean {
        return transition.isOnElse &&
                mergerablePvx.outgoingTransitions.all { !it.mergeStatuses.contains(context.mergeResult) }
    }


    private fun dumpFutureState(future: CompletableFuture<*>): String {
        return "{isDone=${future.isDone},isExc=${future.isCompletedExceptionally}}"
    }

    fun <PayloadType> dumpExecutionState(execution: ReactorGraphExecution<PayloadType>): String {
        try {

            //TODO make pretty dump, current version hard to read
            return (execution.debugProcessingVertexGraphState as Collection<ProcessingVertex>).asSequence()
                    .joinToString(",\n", "\n", "\n") {
                        "${it.vertex.name}={\n" +
                                "handlingFuture=${dumpFutureState(it.handlingFeature)},\n" +
                                "mergingFuture=${dumpFutureState(it.mergingFeature)},\n" +
                                "incomingHandlingFLows={${it.incomingHandlingFlows.joinToString { dumpFutureState(it.feature) }}},\n" +
                                "incomingMergingFLows={${it.incomingMergingFlows.joinToString { dumpFutureState(it.feature) }}}\n" +
                                "}"
                    }
        } catch (exc: Exception) {
            log.error(exc) { "Failed to dump state." }
            return "(failed to dump state)"
        }
    }

}
