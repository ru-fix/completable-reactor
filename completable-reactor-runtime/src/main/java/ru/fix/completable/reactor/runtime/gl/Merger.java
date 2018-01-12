package ru.fix.completable.reactor.runtime.gl;

@FunctionalInterface
public interface Merger<Payload, HandlerResult> {
    Enum merge(Payload payload, HandlerResult handlerResult);
}
