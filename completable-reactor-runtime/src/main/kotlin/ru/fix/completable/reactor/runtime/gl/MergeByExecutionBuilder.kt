package ru.fix.completable.reactor.runtime.gl

import mu.KotlinLogging
import ru.fix.commons.profiler.ProfiledCall
import ru.fix.completable.reactor.runtime.ProfilerNames
import ru.fix.completable.reactor.runtime.gl.ExecutionBuilder.Companion.INVALID_HANDLE_PAYLOAD_CONTEXT
import ru.fix.completable.reactor.runtime.gl.ExecutionBuilder.Companion.INVALID_MERGE_PAYLOAD_CONTEXT
import ru.fix.completable.reactor.runtime.gl.ExecutionBuilder.HandlePayloadContext
import ru.fix.completable.reactor.runtime.gl.ExecutionBuilder.ProcessingVertex
import ru.fix.completable.reactor.runtime.gl.ExecutionBuilder.MergePayloadContext
import ru.fix.completable.reactor.runtime.internal.CRReactorGraph
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.stream.Collectors

private val log = KotlinLogging.logger {}

class MergeByExecutionBuilder<PayloadType>(
        val processingVertices: IdentityHashMap<Vertex, ProcessingVertex>,
        val executionResultFuture: CompletableFuture<PayloadType>,
        val builder: ExecutionBuilder) {

    /**
     * Join incoming merge flows and Handler/Subgraph handing future with single merging invocation
     */
    fun joinIncomingMergeByFlowsToSingleMergingInvocation() {

        for ((vx, pvx) in processingVertices) {

            if ((vx.handler != null || vx.subgraph != null) && vx.merger == null) {
                /**
                 * Skip handler/subgraph without merger
                 */
                if (pvx.outgoingTransitions.isEmpty()) {
                    throw IllegalStateException("""
                        Invalid graph configuration:
                        Vertex ${vx.name} does not have merger, but has outgoing transitions
                        """)
                }
                continue
            }

            /**
             * mergeBy transitions with single handler result transition
             */
            val incomingFlows = ArrayList<CompletableFuture<*>>()
            pvx.incomingMergingFlows
                    .asIterable()
                    .map { it.feature }
                    .forEach { incomingFlows.add(it) }

            /**
             * Handler, Subgraph and Router wait for Handling future to complte before execute merging action.
             * In case of router Handling future does not carry result, only the fact that all incoming handleBy
             * transitions is completed successfully.
             */
            incomingFlows.add(pvx.handlingFeature)

            CompletableFuture.allOf(*incomingFlows.toTypedArray())
                    .thenRunAsync {

                        /**
                         * Handling result could be INVALID_HANDLE_PAYLOAD_CONTEXT in case of exception
                         * Could carry NULL result in case of router
                         */
                        var handlePayloadContext: HandlePayloadContext? = null


                        /**
                         * This is Handler, Subgraph or Router
                         */
                        handlePayloadContext = Optional.of(pvx.handlingFeature)
                                .map { feature ->
                                    try {
                                        if (!feature.isDone) {
                                            val resultException = RuntimeException(
                                                    """
                                            Illegal graph execution state.
                                            Handling feature is not completed.
                                            Vertex: ${vx.name}
                                            """)
                                            log.error(resultException) {}
                                            executionResultFuture.completeExceptionally(resultException)

                                            return@map INVALID_HANDLE_PAYLOAD_CONTEXT
                                        } else {
                                            return@map feature.get()
                                        }
                                    } catch (exc: Exception) {
                                        val resultException = RuntimeException(
                                                """
                                        Failed to get vertex handling feature result for vertex: ${vx.name}
                                        """,
                                                exc)

                                        log.error(resultException) {}
                                        executionResultFuture.completeExceptionally(resultException)

                                        return@map INVALID_HANDLE_PAYLOAD_CONTEXT;
                                    }
                                }
                                .orElse(ExecutionBuilder.INVALID_HANDLE_PAYLOAD_CONTEXT)

                        if (handlePayloadContext === ExecutionBuilder.INVALID_HANDLE_PAYLOAD_CONTEXT) {
                            /**
                             * Failed to get handler/subgraph result.
                             * Merging will not be applied to payload.
                             * All outgoing flows from merge point will be marked as terminal.
                             * executionResult completed by exception
                             */
                            pvx.mergingFeature.complete(MergePayloadContext(isTerminal = true))
                            return@thenRunAsync

                        } else if (handlePayloadContext.isTerminal) {
                            /**
                             * Handling context was marked as terminal during flow by terminal transition.
                             * Merging will not be applied to payload.
                             * All outgoing flows from merge point will be marked as terminal.
                             */
                            pvx.mergingFeature.complete(MergePayloadContext(isTerminal = true))
                            return@thenRunAsync

                        } else if (handlePayloadContext.isDeadTransition) {
                            /**
                             * Vertex was disabled during flow by dead transition.
                             * Merging will not be applied to payload.
                             * All outgoing flows from merge point will be marked as dead.
                             */
                            pvx.mergingFeature.complete(MergePayloadContext(isDeadTransition = true))
                            return@thenRunAsync
                        }


                        /**
                         * Incoming merge flows (mergeBy transitions), could be empty.
                         */
                        val incomingMergingFlows = pvx.incomingMergingFlows.stream()
                                .map { feature ->
                                    try {
                                        if (!feature.feature.isDone) {

                                            val resultException = RuntimeException(
                                                    """
                                Illegal graph execution state.
                                Incoming merge feature is not complete.
                                Vertex: ${vx.name}
                                """)
                                            log.error(resultException) {}
                                            executionResultFuture.completeExceptionally(resultException)
                                            return@map INVALID_MERGE_PAYLOAD_CONTEXT
                                        } else {
                                            return@map feature.feature.get()
                                        }
                                    } catch (exc: Exception) {
                                        val resultException = RuntimeException(
                                                """
                            Failed to get incoming merge flow feature result for vertex: ${vx.name}
                            """,
                                                exc)

                                        log.error(resultException) {}
                                        executionResultFuture.completeExceptionally(resultException)
                                        return@map INVALID_MERGE_PAYLOAD_CONTEXT
                                    }
                                }.collect(Collectors.toList())


                        if (incomingMergingFlows.stream()
                                        .anyMatch { context -> context === INVALID_MERGE_PAYLOAD_CONTEXT }) {
                            /**
                             * Exception during merging
                             * Mark as terminal all outgoing flows from merge point
                             */
                            pvx.mergingFeature.complete(MergePayloadContext(isTerminal = true))

                        } else if (incomingMergingFlows.stream().anyMatch(MergePayloadContext::isTerminal)) {
                            /**
                             * Terminal state reached.
                             * Mark as terminal all outgoing flows from merge point
                             */
                            pvx.mergingFeature.complete(MergePayloadContext(isTerminal = true))

                        } else {

                            val activeIncomingMergingFlows = incomingMergingFlows
                                    .stream()
                                    .filter { context -> !context.isDeadTransition }
                                    .collect(Collectors.toList())

                            if (vx.router != null) {
                                /**
                                 * Router
                                 */
                                if (incomingMergingFlows.isEmpty()) {



                                    /**
                                     * Check that there are at least one incoming transition that marked as dead
                                     */
                                    if (incomingMergingFlows.stream().anyMatch(ExecutionBuilder.MergePayloadContext::isDeadTransition)) {
                                        /**
                                         * Detached MergePoint marked as Dead, because there are no active incoming
                                         * flows and there is at least one incoming dead transition
                                         * Mark as dead all outgoing flows from merge point
                                         */
                                        vertex.getmergingFeature().complete(
                                                new MergePayloadContext ()
                                                        .setDeadTransition(true));
                                    } else {
                                        throw new IllegalStateException (String.format(
                                                "There is no incoming merge flows for detached merge point %s." +
                                                        " At least dead incoming transition expected.",
                                                vertex.getProcessingItem().getDebugName()));
                                    }

                                } else {

                                    /**
                                     * Illegal graph state.
                                     * Router should not have incoming merging features.
                                     * Mark as terminal all outgoing flows from merge point
                                     * Complete graph with exception
                                     */
                                    val incomingMergingFlowsIntoRouterExc = Exception(
                                            """
                                            There are incoming merging flows into router ${vx.name}
                                            Router should not have any incoming merging features.
                                            """)

                                    executionResultFuture.completeExceptionally(incomingMergingFlowsIntoRouterExc)
                                    pvx.mergingFeature.complete(MergePayloadContext(isTerminal = true))
                                }
                            } else {
                                /**
                                 * Handler or Subgraph
                                 */
                                if (incomingMergingFlows.isEmpty()) {
                                    /**
                                     * No incoming merge flows, only one flow from handling feature
                                     */
                                    merge(vertex,
                                            handlePayloadContext.getProcessorResult(),
                                            handlePayloadContext.getPayload(),
                                            executionResultFuture);
                                } else {
                                    /**
                                     * Incoming merge flows exists. But some of them can be marked as dead.
                                     */
                                    if (activeIncomingMergingFlows.isEmpty()) {
                                        /**
                                         * There is no active incoming merge flow for given merge point.
                                         * Mark merge point as dead.
                                         */
                                        pvx.mergingFeature.complete(MergePayloadContext(isDeadTransition = true))

                                    } else {

                                        if (activeIncomingMergingFlows.size > 1) {
                                            /**
                                             * Illegal graph state. Too many active incoming flows.
                                             * Mark as terminal all outgoing flows from merge point
                                             * Complete graph with exception
                                             */
                                            val tooManyActiveIncomingFlowsExc = Exception(
                                                    """
                                                    There is more than one active incoming flow for merge point
                                                    for vertex ${vx.name}
                                                    Reactor can not determinate from which of transitions
                                                    take payload.
                                                    Possible loss of computation results.
                                                    Possible concurrent modifications of payload.
                                                    """)

                                            executionResultFuture.completeExceptionally(tooManyActiveIncomingFlowsExc)
                                            pvx.mergingFeature.complete(MergePayloadContext(isTerminal = true))

                                        } else {

                                            merge(vertex,
                                                    handlePayloadContext.getProcessorResult(),
                                                    activeIncomingMergingFlows.get(0).getPayload(),
                                                    executionResultFuture)
                                        }
                                    }
                                }

                            }
                        }
                    }
                    .exceptionally { throwable ->
                        log.error(throwable) { "Joining incoming merge flows failed for veretex ${vx.name}." }
                        return@exceptionally null
                    }

        }
    }


    /**
     * @param processingVertex
     * @param processorResult       empty in case of detached merge point
     * @param payload
     * @param executionResultFuture
     * @param <PayloadType>
     */
    private <PayloadType> void merge(ProcessingVertex processingVertex,
    Object processorResult,
    Object payload,
    CompletableFuture<PayloadType> executionResultFuture)
    {


//-->
        if (vx.router != null) {
            /**
             * Router does not participate in mergeBy transitions.
             * All incomingHandlingFlows is processed with incomingMergingFlows in mergeres section.
             */
            continue
        }


        /**
         * In case of detached merge point processor should not have incoming handling transition.
         */
        if (processorInfo.getProcessingItemType() == CRReactorGraph.ProcessingItemType.MERGE_POINT) {
            throw new IllegalStateException (String.format(
                    "Processor %s is of type %s and should not have any incoming handling transition",
                    processingVertex.getProcessingItem().getDebugName(),
                    processorInfo.getProcessingItemType()));
        }
//<--


        CRReactorGraph.ProcessingItemInfo processorInfo = processingVertex . getProcessingItemInfo ();

        Supplier<Enum> mergerInvocation;

        switch(processorInfo.getProcessingItemType()) {
            case PROCESSOR :
            if (processorInfo.getDescription().getMerger() == null) {
                /**
                 * This Processor does not have merger
                 */
                processingVertex.getmergingFeature().complete(new MergePayloadContext ().setDeadTransition(true));
                return;
            } else {
                mergerInvocation = () -> (Enum) processorInfo.getDescription().getMerger().merge(
                payload,
                processorResult);
            }
            break;
            case SUBGRAPH :
            if (processorInfo.getSubgraphDescription().getMerger() == null) {
                /**
                 * This Subgraph does not have merger
                 */
                processingVertex.getmergingFeature().complete(new MergePayloadContext ().setDeadTransition(true));
                return;
            } else {
                mergerInvocation = () -> (Enum) processorInfo.getSubgraphDescription().getMerger().merge(
                payload,
                processorResult);
            }
            break;
            case MERGE_POINT :
            mergerInvocation = () -> (Enum) processorInfo.getDetachedMergePointDescription()
            .getMerger()
                .merge(payload);
            break;

            default:
            throw new IllegalArgumentException (String.format("Unknown processor type: %s",
                    processorInfo.getProcessingItemType()));
        }


        try {
            ProfiledCall mergeCall = profiler . profiledCall (
                    ProfilerNames.PROCESSOR_MERGE + processingVertex.getProcessingItem().getProfilingName())
                    .start();
            boolean isTraceablePayload = tracer . isTraceable (payload);
            Object mergeTracingMarker = isTraceablePayload ?
            tracer.beforeMerge(processingVertex.getProcessingItem().getIdentity(), payload, processorResult) :
            null;

            Enum mergeStatus = mergerInvocation . get ();

            mergeCall.stop();

            if (isTraceablePayload) {
                tracer.afterMerger(mergeTracingMarker, processingVertex.getProcessingItem().getIdentity(), payload);
            }

            /**
             * Select outgoing transitions that matches mergeStatus
             */
            List<CRReactorGraph.Transition> activeTransitions = processingVertex . getoutgoingTransitions ().stream()
                    .filter(transition -> transition.isOnAny() || transition.getMergeStatuses().contains(mergeStatus))
            .collect(Collectors.toList());

            if (activeTransitions.size() <= 0) {
                throw new IllegalStateException (String.format("Merger function returned %s.%s status." +
                        " But merge point of processor %s does not have matching transition for this status." +
                        " Expected status from merger function one of: %s",
                        mergeStatus.getDeclaringClass(), mergeStatus,
                        processingVertex.getProcessingItem().getDebugName(),
                        processingVertex.getoutgoingTransitions().stream()
                                .map(CRReactorGraph.Transition::getDebugDescription)
                                .collect(Collectors.joining(",", "{", "}"))));
            }

            /**
             * check if this merge point have terminal transitions that matches merge status
             */
            if (activeTransitions.stream().anyMatch(CRReactorGraph.Transition::isComplete)) {

                /**
                 * Handle terminal transition by completing execution result
                 */
                if (!executionResultFuture.complete((PayloadType) payload)) {

                    Object previousResult = null;
                    try {
                        if (executionResultFuture.isDone()) {
                            previousResult = executionResultFuture.get();
                        } else {
                            log.error("Illegal graph execution state." +
                                    " Completion failed for new result," +
                                    " but execution result from previous terminal step is not complete.");
                        }
                    } catch (Exception exc) {
                        log.error("Failed to get completed execution result from previous terminal step.", exc);
                    }

                    log.error("Processing chain was completed by at least two different terminal steps." +
                            " Already completed with result {}." +
                            " New completion result {} in merge point for processor {}",
                            debugSerializer.dumpObject(previousResult),
                            debugSerializer.dumpObject(payload),
                            processingVertex.getProcessingItem().getDebugName());
                }

                /**
                 * Terminal state reached. Execution result completed.
                 * Throw poison pill - terminal context. All following merge points should be deactivated.
                 */
                processingVertex.getmergingFeature().complete(new MergePayloadContext ()
                        .setPayload(null)
                        .setMergeResult(mergeStatus)
                        .setTerminal(true));
            } else {
                /**
                 * There is no terminal state reached after merging.
                 */
                processingVertex.getmergingFeature().complete(new MergePayloadContext ()
                        .setPayload(payload)
                        .setMergeResult(mergeStatus));
            }

        } catch (Exception exc) {
            log.error("Failed to merge payload {} {} by processing item {} for result {}",
                    payload.getClass(),
                    debugSerializer.dumpObject(payload),
                    processingVertex.getProcessingItem().getDebugName(),
                    debugSerializer.dumpObject(processorResult),
                    exc);

            executionResultFuture.completeExceptionally(exc);

            processingVertex.getmergingFeature().complete(new MergePayloadContext ().setDeadTransition(true));
        }
    }

    private fun invokeRouterHandlingMethod(pvx: ProcessingVertex, payload: Any?): CompletableFuture<Any?> {

        return try {
            CompletableFuture.supplyAsync { pvx.vertex.router.route(payload) }
        } catch (exc: Exception) {
            val result = CompletableFuture<Any?>()
            result.completeExceptionally(
                    IllegalArgumentException("""
                            Exception during router invocation for vertex ${pvx.vertex.name}.
                            Payload: ${builder.debugSerializer.dumpObject(payload)}
                            """,
                            exc))
            result
        }
    }
}