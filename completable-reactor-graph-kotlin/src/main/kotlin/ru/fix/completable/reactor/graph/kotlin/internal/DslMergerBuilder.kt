package ru.fix.completable.reactor.graph.kotlin.internal

import ru.fix.completable.reactor.graph.Merger
import ru.fix.completable.reactor.graph.RoutingMerger
import ru.fix.completable.reactor.graph.Vertex
import ru.fix.completable.reactor.graph.kotlin.MergerBuilder

internal class DslMergerBuilder<Payload, HandlerResult>(vertex: Vertex) :
        MergerBuilder<Payload, HandlerResult> {

    private val baseMergerBuilder = ru.fix.completable.reactor.graph.internal.DslMergerBuilder<Payload, HandlerResult>(vertex)

    override fun withRoutingMerger(merger: Payload.(handlerResult: HandlerResult) -> Enum<*>): Vertex {
        return baseMergerBuilder.withRoutingMerger(
                object : RoutingMerger<Payload, HandlerResult> {
                    override fun merge(payload: Payload, handlerResult: HandlerResult): Enum<*> {
                        return merger(payload, handlerResult)
                    }
                }
        )
    }

    override fun withMerger(merger: Payload.(handlerResult: HandlerResult) -> Unit): Vertex {
        return baseMergerBuilder.withMerger(
                object : Merger<Payload, HandlerResult> {
                    override fun merge(payload: Payload, handlerResult: HandlerResult) {
                        merger(payload, handlerResult)
                    }
                }
        )
    }

    override fun withoutMerger(): Vertex {
        return baseMergerBuilder.withoutMerger()
    }
}