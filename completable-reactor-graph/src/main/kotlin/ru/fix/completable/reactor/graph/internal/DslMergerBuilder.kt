package ru.fix.completable.reactor.graph.internal

import ru.fix.completable.reactor.graph.Merger
import ru.fix.completable.reactor.graph.MergerBuilder
import ru.fix.completable.reactor.graph.RoutingMerger
import ru.fix.completable.reactor.graph.Vertex
import ru.fix.completable.reactor.graph.runtime.RuntimeVertex

class DslMergerBuilder<Payload, HandlerResult>(private val vertex: Vertex) :
        MergerBuilder<Payload, HandlerResult> {

    private val vx = InternalDslAccessor.vx(vertex)

    override fun withRoutingMerger(merger: RoutingMerger<Payload, HandlerResult>): Vertex {
        if (vx.merger != null) {
            throw IllegalStateException("withMerger/withEmptyMerger/withRoutingMerger method used twice on same vertex")
        }
        vx.merger = merger as RoutingMerger<Any?, Any?>

        vx.type = when {
            vx.handler != null -> RuntimeVertex.Type.HandlerWithRoutingMerger
            vx.subgraphPayloadBuilder != null -> RuntimeVertex.Type.SubgraphWithRoutingMerger
            else -> throw IllegalStateException()
        }
        return vertex
    }

    override fun withMerger(merger: Merger<Payload, HandlerResult>): Vertex {
        if (vx.merger != null) {
            throw IllegalStateException("withMerger/withEmptyMerger/withRoutingMerger method used twice on same vertex")
        }

        vx.merger = object : RoutingMerger<Payload, HandlerResult> {
            override fun merge(payload: Payload, handlerResult: HandlerResult): Enum<*> {
                merger.merge(payload, handlerResult)
                return RuntimeEmptyMerger.EmptyMergerStatusEnum.EMPTY_MERGER_STATUS
            }
        } as RoutingMerger<Any?, Any?>

        vx.type = when {
            vx.handler != null -> RuntimeVertex.Type.HandlerWithMerger
            vx.subgraphPayloadBuilder != null -> RuntimeVertex.Type.SubgraphWithMerger
            else -> throw IllegalStateException()
        }
        return vertex
    }

    override fun withoutMerger(): Vertex {
        vx.type = when {
            vx.handler != null -> RuntimeVertex.Type.HandlerWithoutMerger
            vx.subgraphPayloadBuilder != null -> RuntimeVertex.Type.SubgraphWithoutMerger
            else -> throw IllegalStateException()
        }
        return vertex
    }
}
