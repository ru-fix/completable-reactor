package ru.fix.completable.reactor.runtime.gl

@FunctionalInterface
interface SyncHandler<Payload, HandlerResult> {
    fun handle(payload: Payload): HandlerResult
}
