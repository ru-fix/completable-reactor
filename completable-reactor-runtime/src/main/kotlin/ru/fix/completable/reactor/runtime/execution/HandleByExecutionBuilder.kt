package ru.fix.completable.reactor.runtime.execution

import mu.KotlinLogging
import ru.fix.completable.reactor.graph.runtime.RuntimeVertex
import ru.fix.completable.reactor.runtime.ProfilerNames
import ru.fix.completable.reactor.runtime.execution.ExecutionBuilder.Companion.INVALID_TRANSITION_PAYLOAD_CONTEXT
import ru.fix.completable.reactor.runtime.execution.ExecutionBuilder.HandlePayloadContext
import ru.fix.completable.reactor.runtime.execution.ExecutionBuilder.ProcessingVertex
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.stream.Collectors

private val log = KotlinLogging.logger {}

class HandleByExecutionBuilder<PayloadType>(
        val processingVertices: IdentityHashMap<RuntimeVertex, ProcessingVertex>,
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
                        """.trimIndent())
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
                                        """.trimIndent())

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
                                        """.trimIndent())
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

                        /**
                         * If there is a single active incoming flow - use transition payload context from this flow.
                         * If there are many active incoming flows - select any of them:
                         * we are using single payload instance for whole graph execution, no matter from which of
                         * active transition we will select it.
                         */
                        handle(pvx, activeIncomingFlows[0], executionResultFuture)
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
                    """.trimIndent())
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
                            """.trimIndent(),
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
                            """.trimIndent(),
                            exc))
        }

        if (result == null) {
            result = CompletableFuture()
            result.completeExceptionally(
                    NullPointerException(
                            """
                            Handler returned NULL for vertex ${pvx.vertex.name}.
                            Payload: ${builder.debugSerializer.dumpObject(payload)}
                            """.trimIndent()))
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
        val handlingResult: CompletableFuture<Any?>?

        try {
            handlingResult = invokeHandlingMethod(pvx, payload)

        } catch (handlingException: Exception) {
            val exc = RuntimeException(
                    """
                    Failed handling by veretx ${vx.name} for payload ${builder.debugSerializer.dumpObject(payload)}.
                    Handling method raised exception: $handlingException.
                    """.trimIndent(),
                    handlingException)

            log.error(exc) {}
            executionResultFuture.completeExceptionally(exc)
            pvx.handlingFeature.complete(ExecutionBuilder.HandlePayloadContext(isTerminal = true))
            handleCall.stop()
            return
        }

        if (handlingResult == null) {
            val exc = RuntimeException(
                    """
                    Failed handling by vertex ${vx.name} for payload ${builder.debugSerializer.dumpObject(payload)}.
                    Handling method returned NULL.
                    Instance of CompletableFuture expected.
                    """.trimIndent())

            log.error(exc) {}
            executionResultFuture.completeExceptionally(exc)
            pvx.handlingFeature.complete(ExecutionBuilder.HandlePayloadContext(isTerminal = true))
            handleCall.stop()
            return
        }

        handlingResult.handleAsync { result, resultThrowable ->

            var throwable = resultThrowable

            handleCall.stop()

            if (isTraceablePayload) {
                builder.tracer.afterHandle(handleTracingMarker, handleTracingIdentity, result, throwable)
            }

            if (throwable != null) {
                val exc = RuntimeException(
                        """
                        Failed handling by vertex ${vx.name} for payload ${builder.debugSerializer.dumpObject(payload)}
                        """.trimIndent(),
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