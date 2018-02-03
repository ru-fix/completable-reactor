package ru.fix.completable.reactor.graph

interface TransitionBuilder {

    fun complete(): ru.fix.completable.reactor.graph.Vertex

    fun handleBy(vertex: ru.fix.completable.reactor.graph.Vertex): ru.fix.completable.reactor.graph.Vertex

    fun mergeBy(vertex: ru.fix.completable.reactor.graph.Vertex): ru.fix.completable.reactor.graph.Vertex
}