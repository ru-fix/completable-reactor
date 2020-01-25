package ru.fix.completable.reactor.graph

@FunctionalInterface
interface Merger<Payload, HandlerResult> {
    fun merge(payload: Payload, handlerResult: HandlerResult)
}

@FunctionalInterface
interface ExceptionallyMerger<Payload, HandlerResult, Exception: kotlin.Exception> {
    fun merge(payload: Payload, handlerResult: HandlerResult)
}
