package ru.fix.completable.reactor.runtime

import ru.fix.aggregating.profiler.Identity

object Metrics {
    const val PENDING_REQUEST = "pending_request"

    const val REACTOR_EXECUTION = "completable_reactor"

    const val REACTOR_VERTEX_EXECUTION = "completable_reactor_vertices"
}

object Tags {
    const val MERGE = "merge"

    const val HANDLE = "handle"

    const val REACHED_TERMINAL_STEP = "payload_reached_terminal_step"

    const val FULLY_COMPLETED = "execution_graph_fully_completed"
}

object ProfilerIdentity {

    fun mergeIdentity(payload: String?, vertexName: String?) =
            Identity(
                    Metrics.REACTOR_VERTEX_EXECUTION,
                    tags(payload, Tags.MERGE, vertexName)
            )

    fun handlerIdentity(payload: String?, vertexName: String?) =
            Identity(
                    Metrics.REACTOR_VERTEX_EXECUTION,
                    tags(payload, Tags.HANDLE, vertexName)
            )

    private fun tags(payload: String?, operation: String, vertexName: String?) =
            mapOf(
                    "payload" to payload,
                    "operation" to operation,
                    "vertex" to vertexName
            )

    @JvmStatic
    fun payloadIdentity(payload: String?) =
            Identity(
                    Metrics.REACTOR_EXECUTION,
                    tags(payload, Tags.REACHED_TERMINAL_STEP)
            )

    @JvmStatic
    fun executionIdentity(payload: String?) =
            Identity(
                    Metrics.REACTOR_EXECUTION,
                    tags(payload, Tags.FULLY_COMPLETED)
            )

    private fun tags(payload: String?, operation: String) =
            mapOf(
                    "payload" to payload,
                    "execution_cycle" to operation
            )
}