package ru.fix.completable.reactor.graph
//TODO: docs
@FunctionalInterface
interface Subgraph<Payload, SubgraphRequest> {
    fun subgraph(payload: Payload): SubgraphRequest
}
