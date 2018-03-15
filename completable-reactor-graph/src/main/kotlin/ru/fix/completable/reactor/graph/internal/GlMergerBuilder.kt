package ru.fix.completable.reactor.graph.internal

import ru.fix.completable.reactor.graph.Merger
import ru.fix.completable.reactor.graph.MergerBuilder
import ru.fix.completable.reactor.graph.Vertex

internal class GlMergerBuilder<Payload, HandlerResult>(private val vx: GlVertex) :
        MergerBuilder<Payload,
                HandlerResult> {

    override fun withMerger(merger: Merger<Payload, HandlerResult>): Vertex {
        if (vx.merger != null) {
            throw IllegalStateException("withMerger method used twice on same vertex")
        }
        vx.merger = merger as Merger<Any?, Any?>
        return vx.vertex
    }

    override fun withEmptyMerger(): Vertex {
        if (vx.merger != null) {
            throw IllegalStateException("withMerger method used twice on same vertex")
        }
        vx.merger = GlEmptyMerger()
        vx.isEmptyMerger = true
        return vx.vertex
    }

    override fun withoutMerger(): Vertex {
        return vx.vertex
    }
}
