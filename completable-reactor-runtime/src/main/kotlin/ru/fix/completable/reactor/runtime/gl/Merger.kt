package ru.fix.completable.reactor.runtime.gl

@FunctionalInterface
interface Merger<Payload, HandlerResult> {
    fun merge(payload: Payload, handlerResult: HandlerResult): Enum<*>
}
