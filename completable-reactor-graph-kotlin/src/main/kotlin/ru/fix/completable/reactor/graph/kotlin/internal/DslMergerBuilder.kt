package ru.fix.completable.reactor.graph.kotlin.internal

import ru.fix.completable.reactor.graph.RoutingMerger
import ru.fix.completable.reactor.graph.Vertex
import ru.fix.completable.reactor.graph.internal.RuntimeEmptyMerger
import ru.fix.completable.reactor.graph.internal.InternalDslAccessor
import ru.fix.completable.reactor.graph.kotlin.MergerBuilder

internal class DslMergerBuilder<Payload, HandlerResult>(private val vertex: Vertex)
    : MergerBuilder<Payload, HandlerResult> {

    private val vx = InternalDslAccessor.vx(vertex)

    override fun withRoutingMerger(merger: Payload.(handlerResult: HandlerResult) -> Enum<*>): Vertex {
        if (vx.merger != null) {
            throw IllegalStateException("with*Merger method used twice on same vertex")
        }
        vx.merger = object : RoutingMerger<Payload, HandlerResult> {
            override fun merge(payload: Payload, handlerResult: HandlerResult): Enum<*> {
                return merger(payload, handlerResult)
            }

        } as RoutingMerger<Any?, Any?>
        vx.isRoutable = true

        return vertex
    }

    override fun withMerger(merger: Payload.(handlerResult: HandlerResult) -> Unit): Vertex {
        if (vx.merger != null) {
            throw IllegalStateException("with*Merger method used twice on same " +
                    "vertex")
        }
        vx.merger = object : RoutingMerger<Payload, HandlerResult> {
            override fun merge(payload: Payload, handlerResult: HandlerResult): Enum<*> {
                merger(payload, handlerResult)
                return RuntimeEmptyMerger.EmptyMergerStatusEnum.EMPTY_MERGER_STATUS
            }

        } as RoutingMerger<Any?, Any?>

        return vertex
    }

    override fun withoutMerger(): Vertex {
        return vertex
    }

}