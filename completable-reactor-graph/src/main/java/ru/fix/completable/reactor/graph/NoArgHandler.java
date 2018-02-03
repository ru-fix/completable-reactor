package ru.fix.completable.reactor.graph;

import java.util.concurrent.CompletableFuture;

@FunctionalInterface
public interface NoArgHandler<HandlerResult> {
    CompletableFuture<HandlerResult> handle();
}
