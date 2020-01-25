package ru.fix.completable.reactor.runtime

import ru.fix.aggregating.profiler.Identity

object Metrics {
    const val PENDING_REQUEST = "pendingRequest"

    const val REACTOR_EXECUTION = "completable_reactor"

    const val REACTOR_VERTEX_EXECUTION = "completable_reactor_vertices"
}

object Tags {
    const val MERGE = "merger"

    const val HANDLE = "handler"

    const val PAYLOAD = "payload_execution"

    const val EXECUTION = "execution"
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
                    tags(payload, Tags.PAYLOAD)
            )

    @JvmStatic
    fun executionIdentity(payload: String?): Identity {
        return Identity(
                Metrics.REACTOR_EXECUTION,
                tags(payload, Tags.EXECUTION)
        )
    }

    private fun tags(payload: String?, operation: String) =
            mapOf(
                    "payload" to payload,
                    "operation" to operation
            )
}