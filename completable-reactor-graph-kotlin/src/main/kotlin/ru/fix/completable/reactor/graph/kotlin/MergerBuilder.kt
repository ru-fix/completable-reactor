package ru.fix.completable.reactor.graph.kotlin

import ru.fix.completable.reactor.graph.Vertex

interface MergerBuilder<Payload, HandlerResult> {

    //TODO emptyMerger, or merger that could return any object, or do not return anything at all

    fun withMerger(merger: Payload.(handlerResult: HandlerResult) -> Enum<*>): Vertex

    fun withoutMerger(): Vertex
}