package ru.fix.completable.reactor.graph;

@FunctionalInterface
public interface Merger<Payload, HandlerResult> {
    void merge(Payload payload, HandlerResult handlerResult);
}
