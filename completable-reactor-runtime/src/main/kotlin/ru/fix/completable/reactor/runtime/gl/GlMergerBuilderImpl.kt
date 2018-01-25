package ru.fix.completable.reactor.runtime.gl

internal class GlMergerBuilderImpl<Payload, HandlerResult>(private val vertex: Vertex) : GlMergerBuilder<Payload, HandlerResult> {

    override fun withMerger(merger: Merger<Payload, HandlerResult>): Vertex {
        if (vertex.merger != null) {
            throw IllegalStateException("withMerger method used twice on same vertex")
        }
        vertex.merger = merger
        return vertex
    }

    override fun withoutMerger(): Vertex {
        return vertex
    }
}
