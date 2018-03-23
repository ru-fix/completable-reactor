package ru.fix.completable.reactor.runtime.execution

import mu.KotlinLogging
import ru.fix.completable.reactor.graph.internal.GlVertex
import ru.fix.completable.reactor.runtime.ProfilerNames
import ru.fix.completable.reactor.runtime.execution.ExecutionBuilder.Companion.INVALID_TRANSITION_PAYLOAD_CONTEXT
import ru.fix.completable.reactor.runtime.execution.ExecutionBuilder.HandlePayloadContext
import ru.fix.completable.reactor.runtime.execution.ExecutionBuilder.ProcessingVertex
import ru.fix.completable.reactor.runtime.immutability.ImmutabilityChecker
import ru.fix.completable.reactor.runtime.immutability.ImmutabilityControlLevel
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.stream.Collectors

private val log = KotlinLogging.logger {}

class HandleByExecutionBuilder<PayloadType>(
        val processingVertices: IdentityHashMap<GlVertex, ProcessingVertex>,
        val executionResultFuture: CompletableFuture<PayloadType>,
        val builder: ExecutionBuilder) {

    /**
     * Join incoming handling flows to single handling invocation
     * All incoming handling transitions should complete.
     * One of them should carry payload.
     * Other transitions should complete with isDead flag.
     */
    fun joinIncomingHandleByFlowsToSingleHandlingInvocation() {
        //TODO FIX!! allow detached processor to read data from payload and only after that execute merge point
        // down the flow, so merge point and detached processor would not run concurrently


        for ((vx, pvx) in processingVertices) {

            if (pvx.incomingHandlingFlows.size <= 0) {
                throw IllegalArgumentException("""
                        Invalid graph configuration.
                        Vertex ${vx.name} does not have incoming handling flows.
                        Probably missing `.handleBy(${vx.name})` transition that targets this vertex in configuration.
                        """)
            }

            /**
             * First we should wait for all incoming handleBy transition to complete
             */
            CompletableFuture.allOf(
                    *pvx.incomingHandlingFlows.asSequence().map { it.feature }.toList().toTypedArray()

            ).thenRunAsync {

                val incomingFlows: List<ExecutionBuilder.TransitionPayloadContext> = pvx
                        .incomingHandlingFlows
                        .stream()
                        .map { future ->
                            try {
                                /**
                                 * Future should be already complete.
                                 */
                                if (!future.feature.isDone()) {
                                    val resultException = Exception("""
                                        Illegal graph execution state.
                                        Future is not completed. Vertex: ${vx.name}
                                        """)

                                    log.error(resultException) {}
                                    executionResultFuture.completeExceptionally(resultException)

                                    INVALID_TRANSITION_PAYLOAD_CONTEXT
                                } else {
                                    future.feature.get()
                                }
                            } catch (exc: Exception) {
                                val resultException = Exception("""
                                        Failed to get incoming processor flow future result
                                        for vertex: ${vx.name}
                                        """)
                                log.error(resultException) {}
                                executionResultFuture.completeExceptionally(resultException)

                                INVALID_TRANSITION_PAYLOAD_CONTEXT
                            }
                        }
                        .collect(Collectors.toList())

                if (incomingFlows.stream().anyMatch { context -> context === INVALID_TRANSITION_PAYLOAD_CONTEXT }) {
                    /**
                     * Invalid graph execution state
                     * Mark as terminal all outgoing flows from vertex
                     */
                    pvx.handlingFeature.complete(ExecutionBuilder.HandlePayloadContext(isTerminal = true))

                } else if (incomingFlows.stream().anyMatch(ExecutionBuilder.TransitionPayloadContext::isTerminal)) {
                    /**
                     * Terminal state reached.
                     * Mark as terminal all outgoing flows from vertex
                     */
                    pvx.handlingFeature.complete(ExecutionBuilder.HandlePayloadContext(isTerminal = true))

                } else {
                    val activeIncomingFlows: List<ExecutionBuilder.TransitionPayloadContext> = incomingFlows
                            .stream()
                            .filter { context -> !context.isDeadTransition }
                            .collect(Collectors.toList())

                    if (activeIncomingFlows.isEmpty()) {
                        /**
                         * There is no active incoming flow for given vertex.
                         * Vertex will not be invoked.
                         * All outgoing flows from vertex will be marked as dead.
                         */
                        pvx.handlingFeature.complete(ExecutionBuilder.HandlePayloadContext(isDeadTransition = true))

                    } else {
                        if (activeIncomingFlows.size > 1) {

                            /**
                             * Illegal graph state. Too many active incoming flows.
                             * Mark as terminal all outgoing flows
                             * Complete graph with exception
                             */
                            val tooManyActiveIncomingFlowsExc = Exception("""
                                    There is more than one active incoming flow for vertex ${vx.name}.
                                    Reactor can not determinate from which of transitions to take payload.
                                    Possible loss of computation results.
                                    Possible concurrent modifications of payload.
                                    """)

                            executionResultFuture.completeExceptionally(tooManyActiveIncomingFlowsExc)
                            pvx.handlingFeature.complete(ExecutionBuilder.HandlePayloadContext(isTerminal = true))

                        } else {

                            handle(pvx, activeIncomingFlows[0], executionResultFuture)
                        }
                    }
                }
            }
                    .exceptionally { throwable ->
                        log.error("Join incoming handleBy flows failed for vertex ${vx.name}.", throwable)
                        null
                    }

        }//end of handleBy flows
    }

    private fun invokeHandlingMethod(
            pvx: ProcessingVertex,
            payload: Any?): CompletableFuture<Any?> {

        return when {
        // Handler
            pvx.vertex.handler != null -> invokeHandlerHandlingMethod(pvx, payload)

        // Subgraph
            pvx.vertex.subgraphPayloadBuilder != null -> invokeSubgraphHandlingMethod(pvx, payload)

        // Router route method is invoked in MergeBy section in a way Merger merge method works.
        // To pass execution down to mergerFeature for Router we use fake empty handling
            pvx.vertex.router != null -> CompletableFuture.completedFuture(null)

            else -> throw IllegalStateException(
                    """
                    Vertex ${pvx.vertex.name} is not handler or subgraph
                     but engine tries to execute handling method.
                    """)
        }
    }

    private fun invokeSubgraphHandlingMethod(pvx: ProcessingVertex, payload: Any?): CompletableFuture<Any?> {
        val subgraphPayload = pvx.vertex.subgraphPayloadBuilder!!.subgraph(payload)

        return try {
            builder.subgraphRunner(subgraphPayload)
        } catch (exc: Exception) {
            val result = CompletableFuture<Any?>()
            result.completeExceptionally(
                    IllegalArgumentException(
                            """
                            Exception during subgraph launching for vertex ${pvx.vertex.name}.
                            Payload: ${builder.debugSerializer.dumpObject(payload)}
                            """,
                            exc))
            result
        }
    }

    private fun invokeHandlerHandlingMethod(pvx: ProcessingVertex, payload: Any?): CompletableFuture<Any?> {

        var result: CompletableFuture<Any?>?

        try {
            result = pvx.vertex.handler!!.handle(payload)

        } catch (exc: Exception) {
            result = CompletableFuture()
            result.completeExceptionally(
                    IllegalArgumentException(
                            """
                            Exception during handler invocation for vertex ${pvx.vertex.name}.
                            Payload: ${builder.debugSerializer.dumpObject(payload)}
                            """,
                            exc))
        }

        if (result == null) {
            result = CompletableFuture()
            result.completeExceptionally(
                    NullPointerException(
                            """
                            Handler returned NULL for vertex ${pvx.vertex.name}.
                            Payload: ${builder.debugSerializer.dumpObject(payload)}
                            """))
        }

        return result
    }

    private fun <PayloadType> handle(
            pvx: ProcessingVertex,
            payloadContext: ExecutionBuilder.TransitionPayloadContext,
            executionResultFuture: CompletableFuture<PayloadType>) {

        val vx = pvx.vertex
        val payload = payloadContext.payload

        val handleCall = builder.profiler.profiledCall("" +
                ProfilerNames.HANDLE +
                ".${payload?.javaClass?.simpleName}" +
                ".${vx.name}")
                .start()

        val isTraceablePayload = builder.tracer.isTraceable(payload)
        val handleTracingMarker =
                if (isTraceablePayload) {
                    builder.tracer.beforeHandle(vx.name, payload)
                } else {
                    null
                }
        val handleTracingIdentity =
                if (isTraceablePayload) {
                    vx.name
                } else {
                    null
                }
        var handlingResult: CompletableFuture<Any?>? = null
        var payloadSnapshot: ImmutabilityChecker.Snapshot? = null
        val controlLevel = builder.immutabilityControlLevel

        try {
            if (controlLevel != ImmutabilityControlLevel.NO_CONTROL) {
                /**
                 * Invoke handling with immutability check.
                 */
                payloadSnapshot = builder.immutabilityChecker.takeSnapshot(payload)

                handlingResult = invokeHandlingMethod(pvx, payload)

            } else {
                /**
                 * Invoke handling without immutability check.
                 */
                payloadSnapshot = null

                handlingResult = invokeHandlingMethod(pvx, payload)
            }
        } catch (handlingException: Exception) {
            val exc = RuntimeException(
                    """
                    Failed handling by veretx ${vx.name} for payload ${builder.debugSerializer.dumpObject(payload)}.
                    Handling method raised exception: $handlingException.
                    """,
                    handlingException)

            log.error(exc) {}
            executionResultFuture.completeExceptionally(exc)
            pvx.handlingFeature.complete(ExecutionBuilder.HandlePayloadContext(isTerminal = true))
            return
        }

        if (handlingResult == null) {
            val exc = RuntimeException(
                    """
                    Failed handling by vertex ${vx.name} for payload ${builder.debugSerializer.dumpObject(payload)}.
                    Handling method returned NULL.
                    Instance of CompletableFuture expected.
                    """)

            log.error(exc) {}
            executionResultFuture.completeExceptionally(exc)
            pvx.handlingFeature.complete(ExecutionBuilder.HandlePayloadContext(isTerminal = true))
            return
        }

        handlingResult.handleAsync { result, resultThrowable ->

            var throwable = resultThrowable

            handleCall.stop()

            if (isTraceablePayload) {
                builder.tracer.afterHandle(handleTracingMarker, handleTracingIdentity, result, throwable)
            }

            if (controlLevel != ImmutabilityControlLevel.NO_CONTROL) {

                val diff = builder.immutabilityChecker.diff(payloadSnapshot, payload)
                if (diff.isPresent) {
                    val message = """"
                        Concurrent modification detected for payload:
                        ${builder.debugSerializer.dumpObject(payload)}
                        Diff:
                        ${diff.get()}
                        """

                    when (controlLevel) {
                        ImmutabilityControlLevel.LOG_ERROR -> log.error { message }
                        ImmutabilityControlLevel.LOG_WARN -> log.warn { message }
                        ImmutabilityControlLevel.EXCEPTION -> {
                            val immutabilityException = RuntimeException(message)
                            log.error(immutabilityException) { message }

                            if (throwable != null) {
                                log.error(throwable) {
                                    """
                                    Overwriting execution exception $throwable
                                    by immutability check exception $immutabilityException.
                                    """
                                }
                            }
                            throwable = immutabilityException
                        }
                    }
                }
            }

            if (throwable != null) {
                val exc = RuntimeException(
                        """
                        Failed handling by vertex ${vx.name} for payload ${builder.debugSerializer.dumpObject(payload)}
                        """,
                        throwable)

                log.error(exc) {}
                executionResultFuture.completeExceptionally(exc)

                pvx.handlingFeature.complete(HandlePayloadContext(isTerminal = true))
            } else {
                pvx.handlingFeature.complete(HandlePayloadContext(payload = payload, handlingResult = result))
            }

            null

        }.exceptionally { exc ->
            log.error("Failed to execute afterHandle block for vertex ${pvx.vertex.name}")
            null
        }
    }

}