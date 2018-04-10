package ru.fix.completable.reactor.graph

@FunctionalInterface
interface Merger<Payload, HandlerResult> {
    fun merge(payload: Payload, handlerResult: HandlerResult)
}
