package ru.fix.completable.reactor.graph.internal

import ru.fix.completable.reactor.graph.Handler
import java.util.concurrent.CompletableFuture

class RuntimeEmptyHandler : Handler<Any?, Any?> {

    override fun handle(payload: Any?): CompletableFuture<Any?> {
        return CompletableFuture.completedFuture(null)
    }
}