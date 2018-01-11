package ru.fix.completable.reactor.runtime.gl;

@FunctionalInterface
public interface SyncHandler<Payload, HandlerResult> {
    HandlerResult handle(Payload payload);
}
