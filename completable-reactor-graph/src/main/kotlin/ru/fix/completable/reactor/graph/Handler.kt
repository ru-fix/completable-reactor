package ru.fix.completable.reactor.graph

import java.util.concurrent.CompletableFuture

@FunctionalInterface
interface Handler<Payload, HandlerResult> {
    fun handle(payload: Payload): CompletableFuture<HandlerResult>
}
