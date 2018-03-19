package ru.fix.completable.reactor.graph

interface CoordinatesBuilder {
    /**
     * Payload start point coordinates
     */
    fun payload(x: Int, y: Int): CoordinatesBuilder

    fun handler(vertex: ru.fix.completable.reactor.graph.Vertex, x: Int, y: Int): CoordinatesBuilder

    fun merger(vertex: ru.fix.completable.reactor.graph.Vertex, x: Int, y: Int): CoordinatesBuilder

    fun router(vertex: ru.fix.completable.reactor.graph.Vertex, x: Int, y: Int): CoordinatesBuilder

    fun subgraph(vertex: ru.fix.completable.reactor.graph.Vertex, x: Int, y: Int): CoordinatesBuilder

    fun vx(vertex: ru.fix.completable.reactor.graph.Vertex, x: Int, y: Int): CoordinatesBuilder

    fun vx(vertex: ru.fix.completable.reactor.graph.Vertex, x: Int, y: Int, x2: Int, y2: Int): CoordinatesBuilder

    fun complete(vertex: ru.fix.completable.reactor.graph.Vertex, x: Int, y: Int): CoordinatesBuilder
}