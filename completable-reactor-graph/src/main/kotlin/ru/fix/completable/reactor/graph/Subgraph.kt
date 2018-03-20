package ru.fix.completable.reactor.graph

@FunctionalInterface
interface Subgraph<Payload, SubgraphPayload> {
    fun subgraph(payload: Payload): SubgraphPayload
}
