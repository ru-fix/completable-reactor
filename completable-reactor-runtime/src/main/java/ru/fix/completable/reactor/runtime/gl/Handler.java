package ru.fix.completable.reactor.runtime.gl;

import java.util.concurrent.CompletableFuture;

@FunctionalInterface
public interface Handler<Payload, HandlerResult>{
    CompletableFuture<HandlerResult> handle(Payload payload);
}
