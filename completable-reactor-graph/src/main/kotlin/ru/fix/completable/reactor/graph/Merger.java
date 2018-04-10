package ru.fix.completable.reactor.graph;
// Interface written in Java due to void returning value
// Kotlin Unit type as returning value treated on Java side as Object
@FunctionalInterface
public interface Merger<Payload, HandlerResult> {
    void merge(Payload payload, HandlerResult handlerResult);
}
