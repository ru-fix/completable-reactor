package ru.fix.completable.reactor.runtime.gl;

import java.util.concurrent.CompletableFuture;

@FunctionalInterface
public interface NoArgHandler<HandlerResult> {
    CompletableFuture<HandlerResult> handle();
}
