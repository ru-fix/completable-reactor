package ru.fix.completable.reactor.runtime.execution

import ru.fix.completable.reactor.graph.runtime.RuntimeTransition
import ru.fix.completable.reactor.graph.runtime.RuntimeVertex
import ru.fix.completable.reactor.runtime.debug.DebugSerializer
import java.util.concurrent.CompletableFuture

/**
 * Each Processing Vertex (pvx) is mapped to Vertex (vx).
 * Tree of vertexes (vx) represents graph model and it is immutable.
 * Tree of Processing Vertexes (pvx) represents execution graph. Runtime build execution graph each time payload
 * submitted to ReactorGraph.
 *
 */
class ProcessingVertex(val vertex: RuntimeVertex,
                       private val subgraphRunner: SubgraphRunner,
                       private val debugSerializer: DebugSerializer) {

    val incomingHandlingFlows = ArrayList<ExecutionBuilder.TransitionFuture<ExecutionBuilder.TransitionPayloadContext>>()

    val incomingMergingFlows = ArrayList<ExecutionBuilder.TransitionFuture<ExecutionBuilder.MergePayloadContext>>()

    val handlingFuture = CompletableFuture<ExecutionBuilder.HandlePayloadContext>()

    val mergingFuture = CompletableFuture<ExecutionBuilder.MergePayloadContext>()

    /**
     * Holds information about outgoing transitions from this merge point.
     * If during merging process we will receive merge status that match any of terminal transitions
     * then we should complete mergingFuture with Terminal MergePayloadContext
     */
    val outgoingTransitions = ArrayList<RuntimeTransition>()

    /**
     * This is Merger or Router
     */
    val isMergerable: Boolean
        get() = vertex.merger != null || vertex.router != null


    fun invokeHandlingMethod(payload: Any?): CompletableFuture<Any?> {

        return when {
            // Handler
            this.vertex.handler != null -> invokeHandlerHandlingMethod(payload)

            // Subgraph
            this.vertex.subgraphPayloadBuilder != null -> invokeSubgraphHandlingMethod(payload)

            // Router route method is invoked in MergeBy section in a way Merger merge method works.
            // To pass execution down to mergerFeature for Router we use fake empty handling
            this.vertex.router != null -> CompletableFuture.completedFuture(null)

            else -> CompletableFuture<Any?>().apply {
                completeExceptionally(IllegalStateException(
                        """
                        Vertex ${vertex.name} is not handler or subgraph
                        but engine tries to execute handling method.
                        """.trimIndent()))
            }
        }
    }

    private fun invokeSubgraphHandlingMethod(payload: Any?): CompletableFuture<Any?> {
        val subgraphPayload = this.vertex.subgraphPayloadBuilder!!.subgraph(payload)

        if (subgraphPayload == null) {
            return CompletableFuture<Any?>().apply {
                completeExceptionally(
                        java.lang.IllegalArgumentException(
                                """
                                Subgraph payload builder of ${vertex.name} returned NULL instead of subgraph payload.
                                """.trimIndent()
                        )
                )
            }
        }

        return try {
            subgraphRunner(subgraphPayload)

        } catch (exc: Exception) {
            val result = CompletableFuture<Any?>()
            result.completeExceptionally(
                    IllegalArgumentException(
                            """
                            Exception during subgraph launching for vertex ${this.vertex.name}.
                            Payload: ${debugSerializer.dumpObject(payload)}
                            """.trimIndent(),
                            exc))
            result
        }
    }

    private fun invokeHandlerHandlingMethod(payload: Any?): CompletableFuture<Any?> {

        var result: CompletableFuture<Any?>?

        try {
            result = this.vertex.handler!!.handle(payload)

        } catch (exc: Exception) {
            result = CompletableFuture()
            result.completeExceptionally(
                    IllegalArgumentException(
                            """
                            Exception during handler invocation for vertex ${this.vertex.name}.
                            Payload: ${debugSerializer.dumpObject(payload)}
                            """.trimIndent(),
                            exc))
        }

        if (result == null) {
            result = CompletableFuture()
            result.completeExceptionally(
                    NullPointerException(
                            """
                            Handler returned NULL instead of future for vertex ${this.vertex.name}.
                            Payload: ${debugSerializer.dumpObject(payload)}
                            """.trimIndent()))
        }

        return result
    }

    fun invokeMergingMethod(payload: Any?, handlingResult: Any?): Enum<*>? {
        return when {
            vertex.merger != null -> {
                vertex.merger!!.merge(payload, handlingResult)
            }

            vertex.router != null -> {
                if (handlingResult != null) {
                    throw IllegalStateException("""
                        Invalid runtime behavior.
                        Router received non null handling result.
                        Vertex: ${vertex.name}
                        """.trimIndent())
                }
                vertex.router!!.route(payload)
            }

            else -> throw IllegalArgumentException(
                    """
                    Vertex does not have merger or router.
                    And tries to participate in merging invocation.
                    Vertex: ${vertex.name}
                    """.trimIndent())
        }
    }
}