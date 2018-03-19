package ru.fix.completable.reactor.graph.kotlin.internal

import ru.fix.completable.reactor.graph.Merger
import ru.fix.completable.reactor.graph.RoutingMerger
import ru.fix.completable.reactor.graph.Vertex
import ru.fix.completable.reactor.graph.internal.GlEmptyMerger
import ru.fix.completable.reactor.graph.internal.GlVertex
import ru.fix.completable.reactor.graph.kotlin.MergerBuilder

internal class GlMergerBuilder<Payload, HandlerResult>(private val vx: GlVertex)
    : MergerBuilder<Payload, HandlerResult> {

    override fun withRoutingMerger(merger: Payload.(handlerResult: HandlerResult) -> Enum<*>): Vertex {
        if (vx.merger != null) {
            throw IllegalStateException("withMerger method used twice on same vertex")
        }
        vx.merger = RoutingMerger<Payload, HandlerResult> { payload, handlerResult -> merger(payload, handlerResult) }
                as RoutingMerger<Any?, Any?>

        return vx.vertex
    }

    override fun withMerger(merger: Payload.(handlerResult: HandlerResult) -> Unit): Vertex {
        return withRoutingMerger { handlerResult ->
            merger(this, handlerResult)
            GlEmptyMerger.EmptyMergerStatusEnum.EMPTY_MERGER_STATUS
        }
    }

    override fun withoutMerger(): Vertex {
        return vx.vertex
    }

    override fun withEmptyMerger(): Vertex{
        if (vx.merger != null) {
            throw IllegalStateException("withMerger method used twice on same vertex")
        }
        vx.merger = GlEmptyMerger()
        vx.isEmptyMerger = true

        return vx.vertex
    }
}