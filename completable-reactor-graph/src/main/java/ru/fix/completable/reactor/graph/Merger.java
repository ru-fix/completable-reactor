package ru.fix.completable.reactor.graph;

@FunctionalInterface
public interface Merger<Payload, HandlerResult> {
    Enum merge(Payload payload, HandlerResult handlerResult);
}
