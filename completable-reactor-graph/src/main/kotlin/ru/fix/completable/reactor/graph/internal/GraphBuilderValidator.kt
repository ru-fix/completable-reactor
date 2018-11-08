package ru.fix.completable.reactor.graph.internal

import ru.fix.completable.reactor.graph.runtime.RuntimeVertex

class GraphBuilderValidator {

    fun validateHandler(vx: RuntimeVertex) {
        requireNull(vx.handler, "handler method used twice on same vertex")
        requireNull(vx.merger, "handler method used after merger initialization for given vertex")
        requireNull(vx.router, "handler method used after router initialization for given vertex")
        requireNull(vx.subgraphPayloadBuilder, "handler method used after subgraph initialization for given vertex")
    }

    fun validateRouter(vx: RuntimeVertex) {
        requireNull(vx.handler, "router method used after handler initialization for given vertex")
        requireNull(vx.merger, "router method used after merger initialization for given vertex")
        requireNull(vx.router, "router method used twice on same vertex")
        requireNull(vx.subgraphPayloadBuilder, "router method used after subgraph initialization for given vertex")
    }

    fun validateMutator(vx: RuntimeVertex) {
        requireNull(vx.handler, "mutator method used after handler initialization for given vertex")
        requireNull(vx.merger, "mutator method used after merger initialization for given vertex")
        requireNull(vx.router, "mutator method used twice on same vertex")
        requireNull(vx.subgraphPayloadBuilder, "mutator method used after subgraph initialization for given vertex")
    }

    fun validateSubgraph(vx: RuntimeVertex){
        requireNull(vx.handler, "subgraph method used after handler initialization for given vertex")
        requireNull(vx.merger, "subgraph method used after merger initialization for given vertex")
        requireNull(vx.router, "subgraph method used after router initialization for given vertex")
        requireNull(vx.subgraphPayloadBuilder, "subgrah method used twice on same vertex")
    }

    private fun requireNull(value: Any?, message: String) {
        if (value != null) {
            throw IllegalStateException(message)
        }
    }
}