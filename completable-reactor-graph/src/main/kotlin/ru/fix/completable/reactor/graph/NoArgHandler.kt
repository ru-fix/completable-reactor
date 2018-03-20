package ru.fix.completable.reactor.graph

import java.util.concurrent.CompletableFuture

@FunctionalInterface
interface NoArgHandler<HandlerResult> {
    fun handle(): CompletableFuture<HandlerResult>
}
