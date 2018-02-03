package ru.fix.completable.reactor.graph;

@FunctionalInterface
public interface SyncHandler<Payload, HandlerResult> {
    HandlerResult handle(Payload payload);
}
