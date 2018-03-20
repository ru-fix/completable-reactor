package ru.fix.completable.reactor.graph

interface TransitionBuilder {

    fun complete(): Vertex

    fun handleBy(vertex: Vertex): Vertex

    fun mergeBy(vertex: Vertex): Vertex
}