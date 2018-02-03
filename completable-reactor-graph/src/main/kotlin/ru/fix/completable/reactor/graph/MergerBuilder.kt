package ru.fix.completable.reactor.graph

interface MergerBuilder<Payload, HandlerResult> {

    fun withMerger(merger: Merger<Payload, HandlerResult>): Vertex

    fun withoutMerger(): Vertex
}
