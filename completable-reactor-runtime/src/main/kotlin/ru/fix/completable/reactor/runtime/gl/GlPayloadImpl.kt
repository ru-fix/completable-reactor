package ru.fix.completable.reactor.runtime.gl

import ru.fix.completable.reactor.runtime.internal.gl.GlReactorGraph

class GlPayloadImpl<Payload> (val graph: GlReactorGraph<Payload>) : GlPayload<Payload> {

    override fun handleBy(vertex: Vertex): GlPayload<Payload> {
        graph.startPoint.add(vertex)
        return this
    }
}