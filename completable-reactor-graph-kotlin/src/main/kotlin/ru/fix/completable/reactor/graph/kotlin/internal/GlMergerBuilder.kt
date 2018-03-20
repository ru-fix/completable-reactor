package ru.fix.completable.reactor.graph.kotlin.internal

import ru.fix.completable.reactor.graph.RoutingMerger
import ru.fix.completable.reactor.graph.Vertex
import ru.fix.completable.reactor.graph.internal.GlEmptyMerger
import ru.fix.completable.reactor.graph.internal.InternalGlAccessor
import ru.fix.completable.reactor.graph.kotlin.MergerBuilder

internal class GlMergerBuilder<Payload, HandlerResult>(private val vertex: Vertex)
    : MergerBuilder<Payload, HandlerResult> {

    private val vx = InternalGlAccessor.vx(vertex)

    override fun withRoutingMerger(merger: Payload.(handlerResult: HandlerResult) -> Enum<*>): Vertex {
        if (vx.merger != null) {
            throw IllegalStateException("withMerger method used twice on same vertex")
        }
        vx.merger = object : RoutingMerger<Payload, HandlerResult> {
            override fun merge(payload: Payload, handlerResult: HandlerResult): Enum<*> {
                return merger(payload, handlerResult)
            }

        } as RoutingMerger<Any?, Any?>
        vx.isTransitionableMerger = true

        return vertex
    }

    override fun withMerger(merger: Payload.(handlerResult: HandlerResult) -> Unit): Vertex {
        return withRoutingMerger { handlerResult ->
            merger(this, handlerResult)
            GlEmptyMerger.EmptyMergerStatusEnum.EMPTY_MERGER_STATUS
        }
    }

    override fun withoutMerger(): Vertex {
        return vertex
    }

    override fun withEmptyMerger(): Vertex {
        if (vx.merger != null) {
            throw IllegalStateException("withMerger method used twice on same vertex")
        }
        vx.merger = GlEmptyMerger()

        return vertex
    }
}