package ru.fix.completable.reactor.graph;

@FunctionalInterface
public interface NoArgSyncHandler<HandlerResult> {
    HandlerResult handle();
}
