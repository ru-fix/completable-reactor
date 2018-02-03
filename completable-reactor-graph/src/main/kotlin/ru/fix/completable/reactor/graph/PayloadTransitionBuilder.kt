package ru.fix.completable.reactor.graph

interface PayloadTransitionBuilder<Payload> {
    fun handleBy(vertex: ru.fix.completable.reactor.graph.Vertex): PayloadTransitionBuilder<Payload>
}
