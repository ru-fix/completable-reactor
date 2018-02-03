package ru.fix.completable.reactor.graph;

import java.util.concurrent.CompletableFuture;

@FunctionalInterface
public interface Handler<Payload, HandlerResult> {
    CompletableFuture<HandlerResult> handle(Payload payload);
}
