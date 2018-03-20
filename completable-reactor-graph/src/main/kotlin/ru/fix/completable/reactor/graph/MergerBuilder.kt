package ru.fix.completable.reactor.graph

interface MergerBuilder<Payload, HandlerResult> {

    fun withMerger(merger: Merger<Payload, HandlerResult>): Vertex

    fun withRoutingMerger(merger: RoutingMerger<Payload, HandlerResult>): Vertex

    fun withEmptyMerger(): Vertex

    fun withoutMerger(): Vertex
}
