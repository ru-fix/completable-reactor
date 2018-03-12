package ru.fix.completable.reactor.graph.kotlin.internal

import ru.fix.completable.reactor.graph.Merger
import ru.fix.completable.reactor.graph.Vertex
import ru.fix.completable.reactor.graph.internal.GlVertex
import ru.fix.completable.reactor.graph.kotlin.MergerBuilder

internal class GlMergerBuilder<Payload, HandlerResult>(private val vx: GlVertex)
    : MergerBuilder<Payload, HandlerResult> {


    override fun withMerger(merger: Payload.(handlerResult: HandlerResult) -> Enum<*>): Vertex {
        if (vx.merger != null) {
            throw IllegalStateException("withMerger method used twice on same vertex")
        }
        vx.merger = Merger<Payload, HandlerResult> { payload, handlerResult -> merger(payload, handlerResult) }
                as Merger<Any?, Any?>

        return vx.vertex
    }

    override fun withoutMerger(): Vertex {
        return vx.vertex
    }
}