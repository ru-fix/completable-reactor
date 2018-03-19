package ru.fix.completable.reactor.graph;

@FunctionalInterface
public interface RoutingMerger<Payload, HandlerResult> {
    Enum merge(Payload payload, HandlerResult handlerResult);
}
//TODO replace Enum with Any?
//TODO move GL graph API to separate package
//TODO create scala API proposal
