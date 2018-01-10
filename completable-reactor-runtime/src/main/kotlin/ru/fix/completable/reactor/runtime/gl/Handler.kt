package ru.fix.completable.reactor.runtime.gl

import java.util.concurrent.CompletableFuture

@FunctionalInterface
interface Handler<Payload, HandlerResult> {
    fun handle(payload: Payload): CompletableFuture<HandlerResult>
}
