package ru.fix.completable.reactor.graph

interface MergerBuilder<Payload, HandlerResult> {

    fun withMerger(merger: Merger<Payload, HandlerResult>): Vertex

    //TODO: add javadoc for all vertices
    fun withRoutingMerger(merger: RoutingMerger<Payload, HandlerResult>): Vertex

    //TODO: specialize Vertices: router, merger, so handleBy and mergeBy will not conflict for router
    fun withEmptyMerger(): Vertex

    fun withoutMerger(): Vertex
}
