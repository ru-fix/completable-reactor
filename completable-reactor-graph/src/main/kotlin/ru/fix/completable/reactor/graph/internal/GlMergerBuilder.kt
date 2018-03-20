package ru.fix.completable.reactor.graph.internal

import ru.fix.completable.reactor.graph.Merger
import ru.fix.completable.reactor.graph.MergerBuilder
import ru.fix.completable.reactor.graph.RoutingMerger
import ru.fix.completable.reactor.graph.Vertex

internal class GlMergerBuilder<Payload, HandlerResult>(private val vx: GlVertex) :
        MergerBuilder<Payload,
                HandlerResult> {

    override fun withMerger(merger: Merger<Payload, HandlerResult>): Vertex {
        vx.isNonTransitionMerger = true
        return withRoutingMerger{ payload, handlerResult ->
            merger.merge(payload, handlerResult)
            GlEmptyMerger.EmptyMergerStatusEnum.EMPTY_MERGER_STATUS
        }
    }

    override fun withRoutingMerger(merger: RoutingMerger<Payload, HandlerResult>?): Vertex {
        if (vx.merger != null) {
            throw IllegalStateException("withMerger method used twice on same vertex")
        }
        vx.merger = merger as RoutingMerger<Any?, Any?>

        return vx.vertex
    }

    override fun withEmptyMerger(): Vertex {
        if (vx.merger != null) {
            throw IllegalStateException("withMerger method used twice on same vertex")
        }
        vx.merger = GlEmptyMerger()
        vx.isNonTransitionMerger = true
        return vx.vertex
    }

    override fun withoutMerger(): Vertex {
        return vx.vertex
    }
}
