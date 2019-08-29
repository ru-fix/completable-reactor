package ru.fix.completable.reactor.graph

import java.util.concurrent.CompletableFuture

interface Execution<ResponseT>{
    /**
     * Will be completed with payload when terminal graph state would be reached.
     */
    val resultFuture: CompletableFuture<ResponseT>
    /**
     * Completes when all processes within given execution of single request is complete
     */
    val executionFuture: CompletableFuture<Void>
}