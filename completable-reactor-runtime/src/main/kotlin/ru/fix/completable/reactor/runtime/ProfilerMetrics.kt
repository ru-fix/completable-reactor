package ru.fix.completable.reactor.runtime

import ru.fix.aggregating.profiler.Identity

object Metrics {
    const val PENDING_REQUEST = "pending_request"

    const val COMPLETABLE_REACTOR = "completable_reactor"

    const val COMPLETABLE_REACTOR_GRAPH = "completable_reactor_graph"
}

object Tags {
    const val MERGE = "merge"

    const val HANDLE = "handle"

    const val SUBMIT = "submit"

    const val EXECUTION = "execution"
}

object ProfilerIdentity {

    fun mergeIdentity(payload: String?, vertexName: String?) =
            Identity(
                    Metrics.COMPLETABLE_REACTOR_GRAPH,
                    tags(payload, Tags.MERGE, vertexName)
            )

    fun handleIdentity(payload: String?, vertexName: String?) =
            Identity(
                    Metrics.COMPLETABLE_REACTOR_GRAPH,
                    tags(payload, Tags.HANDLE, vertexName)
            )

    private fun tags(payload: String?, operation: String, vertexName: String?) =
            mapOf(
                    "payload" to payload,
                    "operation" to operation,
                    "vertex" to vertexName
            )

    @JvmStatic
    fun submitIdentity(payload: String?) =
            Identity(
                    Metrics.COMPLETABLE_REACTOR,
                    tags(payload, Tags.SUBMIT)
            )

    @JvmStatic
    fun executionIdentity(payload: String?) =
            Identity(
                    Metrics.COMPLETABLE_REACTOR,
                    tags(payload, Tags.EXECUTION)
            )

    private fun tags(payload: String?, operation: String) =
            mapOf(
                    "payload" to payload,
                    "method" to operation
            )
}