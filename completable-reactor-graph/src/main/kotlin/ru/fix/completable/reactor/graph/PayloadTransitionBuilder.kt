package ru.fix.completable.reactor.graph

interface PayloadTransitionBuilder<Payload> {
    fun handleBy(vertex: Vertex): PayloadTransitionBuilder<Payload>
}
