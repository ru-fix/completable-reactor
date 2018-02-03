package ru.fix.completable.reactor.runtime.execution


import mu.KotlinLogging
import ru.fix.commons.profiler.Profiler
import ru.fix.completable.reactor.graph.internal.GlGraph
import ru.fix.completable.reactor.graph.internal.GlTransition
import ru.fix.completable.reactor.graph.internal.GlVertex
import ru.fix.completable.reactor.runtime.cloning.ThreadsafeCopyMaker
import ru.fix.completable.reactor.runtime.debug.DebugSerializer
import ru.fix.completable.reactor.runtime.immutability.ImmutabilityChecker
import ru.fix.completable.reactor.runtime.immutability.ImmutabilityControlLevel
import ru.fix.completable.reactor.runtime.tracing.Tracer
import java.util.*
import java.util.concurrent.CompletableFuture

typealias SubgraphRunner = (Any?) -> CompletableFuture<Any?>

private val log = KotlinLogging.logger {}

class ExecutionBuilder(
        val profiler: Profiler,
        val immutabilityChecker: ImmutabilityChecker,
        val threadsafeCopyMaker: ThreadsafeCopyMaker,
        val subgraphRunner: SubgraphRunner,
        val debugSerializer: DebugSerializer,
        val tracer: Tracer) {

    /**
     * If this flag is enabled then internal processing graph state will be attached to Execution result.
     * This allows easy access to execution state during debug.
     * One of drawbacks of this future is that GC will be prevented form removing internal state objects
     * until all reference to execution result dies.
     * <p>
     * By default this flag is disabled.
     */
    var debugProcessingVertexGraphState: Boolean = false

    /**
     * Immutability check ensures that there is no payload modification during handling.
     */
    @Volatile
    var immutabilityControlLevel = ImmutabilityControlLevel.NO_CONTROL


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

//            if (vx.router != null) {
//                /**
//                 * //TODO check, probably this conversion will ot be used in CR2 model
//                 * In case of Router, transition from start point is being converted
//                 * to a {@link MergePayloadContext}
//                 */
//                pvx.incomingMergingFlows.add(TransitionFuture(
//                        startPointTransitionFuture.thenApplyAsync { transitionPayloadContext ->
//                            MergePayloadContext(
//                                    isDeadTransition = transitionPayloadContext.isDeadTransition,
//                                    isTerminal = transitionPayloadContext.isTerminal,
//                                    payload = transitionPayloadContext.payload,
//                                    mergeResult = null)
//                        }))
//            } else {
//                pvx.incomingHandlingFlows.add(TransitionFuture(startPointTransitionFuture))
//            }

            pvx.incomingHandlingFlows.add(TransitionFuture(startPointTransitionFuture))
        }


        /**
         * Populate outgoing flows
         */
        processingVertices.values.asIterable().filter { it.isMergerable }.forEach { mergerablePvx ->

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
                                                """
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
                                                        """
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
                    .asIterable()
                    .flatMap { it.incomingHandlingFlows }
                    .map { it.feature }
                    .forEach { future ->
                        future.complete(TransitionPayloadContext(isDeadTransition = true))
                    }

            processingVertices.values
                    .asIterable()
                    .flatMap { it.incomingHandlingFlows }
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
                        .asIterable()
                        .flatMap { listOf(it.handlingFeature, it.mergingFeature) }
                        .toTypedArray()
        )

        return ReactorGraphExecution(
                submitFuture,
                executionResultFuture,
                chainExecutionFuture,
                if (debugProcessingVertexGraphState) {
                    processingVertices.values
                } else {
                    null
                })
    }


}
