package ru.fix.completable.reactor.graph.internal

import ru.fix.completable.reactor.graph.PayloadTransitionBuilder
import ru.fix.completable.reactor.graph.Vertex
import ru.fix.completable.reactor.graph.runtime.RuntimeGraph

class DslPayloadImpl<Payload>(val graph: RuntimeGraph) : PayloadTransitionBuilder<Payload> {

    override fun handleBy(vertex: Vertex): PayloadTransitionBuilder<Payload> {
        val vx = InternalDslAccessor.vx(vertex)

        graph.startPoint.add(vx)
        if (vx.name == null) {
            vx.name = BuilderContext.get().resolveVertexName(vertex)
        }
        return this
    }
}