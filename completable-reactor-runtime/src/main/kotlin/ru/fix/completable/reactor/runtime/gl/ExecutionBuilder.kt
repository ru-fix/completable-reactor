package ru.fix.completable.reactor.runtime.gl


import mu.KotlinLogging
import ru.fix.commons.profiler.ProfiledCall;
import ru.fix.commons.profiler.Profiler;
import ru.fix.completable.reactor.runtime.ProfilerNames;
import ru.fix.completable.reactor.runtime.ReactorGraph;
import ru.fix.completable.reactor.runtime.cloning.ThreadsafeCopyMaker;
import ru.fix.completable.reactor.runtime.debug.DebugSerializer;
import ru.fix.completable.reactor.runtime.execution.ReactorGraphExecution
import ru.fix.completable.reactor.runtime.immutability.ImmutabilityChecker;
import ru.fix.completable.reactor.runtime.immutability.ImmutabilityControlLevel;
import ru.fix.completable.reactor.runtime.internal.CRProcessingItem;
import ru.fix.completable.reactor.runtime.internal.CRReactorGraph;
import ru.fix.completable.reactor.runtime.internal.gl.GlReactorGraph
import ru.fix.completable.reactor.runtime.tracing.Tracer;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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


    class MergePayloadContext(
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


    class TransitionPayloadContext(
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

    class HandlePayloadContext(

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
            var processorResult: Any? = null
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
    class ProcessingVertex(val vertex: Vertex) {

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
    fun <PayloadType> build(reactorGraph: ReactorGraph<PayloadType>): ReactorGraphExecution<PayloadType> {

        val glReactorGraph = reactorGraph as GlReactorGraph<PayloadType>

        /**
         * Internal representation of processing graph based on processing vertices.
         * //TODO: check, maybe we do not need map and simple list is enough fo rCR2
         */
        val processingVertices = IdentityHashMap<Vertex, ProcessingVertex>()


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
        glReactorGraph.vertices.forEach { vx ->
            val pvx = ProcessingVertex(vertex = vx)

            /**
             * Populate Routers
             */
            if (vx.router != null) {
                /**
                 * Router does not uses {@code {@link ProcessingVertex#handlingFeature}
                 * Insure that it never invoked during execution.
                 */
                pvx.handlingFeature.completeExceptionally(IllegalStateException(
                        "Processing vertex of router ${vx.name} should not use handlingFeature."))
            }

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
        for (vx in glReactorGraph.startPoint) {
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
        executionResultFuture.thenRunAsync(() -> {
            processingVertices.entrySet().stream()
                    .map(Map.Entry::getValue)
                    .map(ProcessingVertex::getincomingHandlingFlows)
                    .flatMap(Collection::stream)
                    .map(TransitionFuture::getFuture)
                    .forEach(future -> future.complete(new TransitionPayloadContext().setDeadTransition(true)));

            processingVertices.entrySet().stream()
                    .map(Map.Entry::getValue)
                    .map(ProcessingVertex::getincomingHandlingFlows)
                    .flatMap(Collection::stream)
                    .map(TransitionFuture::getFuture)
                    .forEach(future -> future.complete(new TransitionPayloadContext().setDeadTransition(true)));
        }).exceptionally(throwable -> {
            log.error("Marking transitions as dead is failed.", throwable);
            return null;
        });

        /**
         * Collect chain execution processor handling futures
         * Processors futures holds handle invocation result or dead context.
         * Exception is an detached merge point which handlingFeature is not used
         * Then all processors futures completes chainExecutionFuture completes too.
         */
        CompletableFuture<Void> chainExecutionFuture = CompletableFuture . allOf (
                processingVertices.entrySet().stream()
                        .map(Map.Entry::getValue)
                        //detached merge point does not have processor future
                        .filter(vertex -> vertex.getProcessingItemInfo().getProcessingItemType() !=
        CRReactorGraph.ProcessingItemType.MERGE_POINT)
        .map(ProcessingVertex::gethandlingFeature)
                .toArray(CompletableFuture[]::new)
        );

        return ReactorGraphExecution.< PayloadType > builder ()
                .resultFuture(executionResultFuture)
                .submitFuture(submitFuture)
                .chainExecutionFuture(chainExecutionFuture)
                .debugProcessingVertexGraphState(debugProcessingVertexGraphState ? processingVertices . values () : null)
        .build();
    }



}
