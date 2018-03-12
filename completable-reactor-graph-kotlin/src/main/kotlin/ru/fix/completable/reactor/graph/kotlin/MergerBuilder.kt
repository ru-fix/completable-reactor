package ru.fix.completable.reactor.graph.kotlin

import ru.fix.completable.reactor.graph.Vertex

interface MergerBuilder<Payload, HandlerResult> {

    fun withMerger(merger: Payload.(handlerResult: HandlerResult) -> Enum<*>): Vertex

    fun withoutMerger(): Vertex
}