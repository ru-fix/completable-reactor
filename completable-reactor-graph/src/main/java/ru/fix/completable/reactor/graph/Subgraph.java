package ru.fix.completable.reactor.graph;

@FunctionalInterface
public interface Subgraph<Payload, SubgraphPayload> {
    SubgraphPayload subgraph(Payload payload);
}
