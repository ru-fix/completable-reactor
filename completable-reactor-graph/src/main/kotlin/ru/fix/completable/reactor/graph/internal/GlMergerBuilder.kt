package ru.fix.completable.reactor.graph.internal

import ru.fix.completable.reactor.graph.Merger
import ru.fix.completable.reactor.graph.MergerBuilder
import ru.fix.completable.reactor.graph.RoutingMerger
import ru.fix.completable.reactor.graph.Vertex

internal class GlMergerBuilder<Payload, HandlerResult>(private val vertex: Vertex) :
        MergerBuilder<Payload, HandlerResult> {

    private val vx = InternalGlAccessor.vx(vertex)

    override fun withMerger(merger: Merger<Payload, HandlerResult>): Vertex {
        return withRoutingMerger(object: RoutingMerger<Payload, HandlerResult>{
            override fun merge(payload: Payload, handlerResult: HandlerResult): Enum<*> {
                merger.merge(payload, handlerResult)
                return GlEmptyMerger.EmptyMergerStatusEnum.EMPTY_MERGER_STATUS
            }
        })
    }

    override fun withRoutingMerger(merger: RoutingMerger<Payload, HandlerResult>): Vertex {
        if (vx.merger != null) {
            throw IllegalStateException("withMerger method used twice on same vertex")
        }
        vx.merger = merger as RoutingMerger<Any?, Any?>
        vx.isTransitionableMerger = true

        return vertex
    }

    override fun withEmptyMerger(): Vertex {
        if (vx.merger != null) {
            throw IllegalStateException("withMerger method used twice on same vertex")
        }
        vx.merger = GlEmptyMerger()
        return vertex
    }

    override fun withoutMerger(): Vertex {
        return vertex
    }
}
