package ru.fix.completable.reactor.graph;

@FunctionalInterface
public interface Merger<Payload, HandlerResult> {
    Object merge(Payload payload, HandlerResult handlerResult);
}
