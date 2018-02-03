package ru.fix.completable.reactor.graph.internal

import ru.fix.completable.reactor.graph.PayloadTransitionBuilder
import ru.fix.completable.reactor.graph.Vertex

class GlPayloadImpl<Payload>(val graph: GlGraph) : PayloadTransitionBuilder<Payload> {

    override fun handleBy(vertex: Vertex): PayloadTransitionBuilder<Payload> {
        val vx = VxAccessor.vx(vertex)

        graph.startPoint.add(vx)
        if (vx.name == null) {
            vx.name = BuilderContext.get().resolveVertexName(vertex)
        }
        return this
    }
}