package ru.fix.completable.reactor.graph

interface CoordinatesBuilder {
    /**
     * Payload start point coordinates
     */
    fun payload(x: Int, y: Int): CoordinatesBuilder

    fun handler(vertex: Vertex, x: Int, y: Int): CoordinatesBuilder

    fun merger(vertex: Vertex, x: Int, y: Int): CoordinatesBuilder

    fun router(vertex: Vertex, x: Int, y: Int): CoordinatesBuilder

    fun subgraph(vertex: Vertex, x: Int, y: Int): CoordinatesBuilder

    fun vx(vertex: Vertex, x: Int, y: Int): CoordinatesBuilder

    fun vx(vertex: Vertex, x: Int, y: Int, x2: Int, y2: Int): CoordinatesBuilder

    fun complete(vertex: Vertex, x: Int, y: Int): CoordinatesBuilder
}