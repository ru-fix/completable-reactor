package ru.fix.completable.reactor.runtime.gl

import mu.KotlinLogging
import ru.fix.completable.reactor.runtime.ProfilerNames
import ru.fix.completable.reactor.runtime.gl.ExecutionBuilder.Companion.INVALID_HANDLE_PAYLOAD_CONTEXT
import ru.fix.completable.reactor.runtime.gl.ExecutionBuilder.Companion.INVALID_MERGE_PAYLOAD_CONTEXT
import ru.fix.completable.reactor.runtime.gl.ExecutionBuilder.HandlePayloadContext
import ru.fix.completable.reactor.runtime.gl.ExecutionBuilder.ProcessingVertex
import ru.fix.completable.reactor.runtime.gl.ExecutionBuilder.MergePayloadContext
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

                            /**
                             * Handler, Router or Subgraph compteled handlingFeature successfully.
                             */
                            if (incomingMergingFlows.isEmpty()) {
                                /**
                                 * No incoming merge flows, only one flow from handling feature
                                 */
                                merge(
                                        pvx,
                                        handlePayloadContext.handlingResult,
                                        handlePayloadContext.payload,
                                        executionResultFuture)
                            } else {
                                /**
                                 * Incoming merge flows exists. And all of them should be marked as dead.
                                 */
                                if (incomingMergingFlows.any { it.isDeadTransition }) {
                                    /**
                                     * There is exist at least one incoming merge flow for given
                                     * merger that ьarked as dead.
                                     */
                                    pvx.mergingFeature.complete(MergePayloadContext(isDeadTransition = true))

                                } else {
                                    /**
                                     * Illegal case, no such transitions should exist.
                                     */
                                    throw IllegalStateException(
                                            "Incoming merging features with unknown states: ${incomingMergingFlows}")
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
     * @param pvx
     * @param handlingResult       empty in case of detached merge point
     * @param payload
     * @param executionResultFuture
     * @param <PayloadType>
     */
    private fun <PayloadType> merge(
            pvx: ProcessingVertex,
            handlingResult: Any?,
            payload: Any?,
            executionResultFuture: CompletableFuture<PayloadType>) {


        var mergerInvocation: () -> Enum<*>

        when {
            pvx.vertex.merger != null -> mergerInvocation = { pvx.vertex.merger.merge(payload, handlingResult) }
            pvx.vertex.router != null -> {
                if (handlingResult != null) {
                    throw IllegalStateException("""
                        Invalid runtime behavior.
                        Router received non null handling result.
                        Vertex: ${pvx.vertex.name}
                        """.trimIndent())
                }
                mergerInvocation = { pvx.vertex.router.route(payload) }
            }
            else -> throw IllegalArgumentException("""
                Vertex does not have merger or router.
                And tries to participate in merging invocation.
                Vertex: ${pvx.vertex.name}
                """)
        }

        try {

            val mergeCall = builder.profiler.profiledCall(
                    ProfilerNames.PROCESSOR_MERGE + pvx.vertex.name)
                    .start()

            val isTraceablePayload = builder.tracer.isTraceable(payload)
            val mergeTracingMarker =
                    if (isTraceablePayload) {
                        builder.tracer.beforeMerge(pvx.vertex.name, payload, handlingResult)
                    } else {
                        null
                    }

            val mergeStatus = mergerInvocation()

            mergeCall.stop()

            if (isTraceablePayload) {
                builder.tracer.afterMerger(mergeTracingMarker, pvx.vertex.name, payload)
            }

            /**
             * Select outgoing transitions that matches mergeStatus
             */
            val activeTransitions = pvx.outgoingTransitions
                    .stream()
                    .filter { transition -> transition.isOnAny || transition.mergeStatuses.contains(mergeStatus) }
                    .collect(Collectors.toList())

            if (activeTransitions.isEmpty()) {
                throw IllegalStateException("""
                    Merging function returned status: $mergeStatus
                    But outgoing transition of vertex ${pvx.vertex.name}
                        does not have matching transition for this status.
                    Expected status for merging function (merge or route) one of:
                    ${pvx.outgoingTransitions.joinToString { it.toString() }}
                    """.trimIndent())
            }

            /**
             * check if this merge point have terminal transitions that matches merge status
             */
            if (activeTransitions.any { it.isComplete }) {

                /**
                 * Handle terminal transition by completing execution result
                 */
                if (!executionResultFuture.complete(payload as PayloadType)) {

                    var previousResult: Any? = null
                    try {
                        if (executionResultFuture.isDone) {
                            previousResult = executionResultFuture.get()
                        } else {
                            log.error {
                                """
                                Illegal graph execution state.
                                Completion failed for new result,
                                    but execution result from previous terminal step is not complete.
                                """.trimIndent()
                            }
                        }
                    } catch (exc: Exception) {
                        log.error(exc) { "Failed to get completed execution result from previous terminal step." }
                    }

                    log.error {
                        """
                        Processing chain was completed by at least two different terminal steps.
                        Already completed with result ${builder.debugSerializer.dumpObject(previousResult)}
                        New completion result: ${builder.debugSerializer.dumpObject(payload)}
                        in merger/router for vertex ${pvx.vertex.name}
                        """
                    }
                }

                /**
                 * Terminal state reached. Execution result completed.
                 * Throw poison pill - terminal context. All following merge points should be deactivated.
                 */
                pvx.mergingFeature.complete(MergePayloadContext(
                        payload = null,
                        mergeResult = mergeStatus,
                        isTerminal = true))

            } else {
                /**
                 * There is no terminal state reached after merging.
                 */
                pvx.mergingFeature.complete(MergePayloadContext(
                        payload = payload,
                        mergeResult = mergeStatus))
            }

        } catch (exc: Exception) {
            log.error(exc) {
                """
                Failed to merge payload ${builder.debugSerializer.dumpObject(payload)}
                by vertex ${pvx.vertex.name}
                with result ${builder.debugSerializer.dumpObject(handlingResult)}
                """
            }

            executionResultFuture.completeExceptionally(exc)

            pvx.mergingFeature.complete(MergePayloadContext(isDeadTransition = true))

        }
    }

}