package ru.fix.completable.reactor.runtime.gl;

@FunctionalInterface
public interface NoArgSyncHandler<HandlerResult> {
    HandlerResult handle();
}
