package ru.fix.completable.reactor.graph

interface CoordinatesBuilder {

    //TODO remove
    fun payload(x: Int, y: Int): CoordinatesBuilder
    /**
     * Payload start point coordinates
     */
    fun pd(x: Int, y: Int): CoordinatesBuilder

    //TODO remove
    fun handler(vertex: Vertex, x: Int, y: Int): CoordinatesBuilder

    //TODO remove
    fun merger(vertex: Vertex, x: Int, y: Int): CoordinatesBuilder

    //TODO remove
    fun router(vertex: Vertex, x: Int, y: Int): CoordinatesBuilder

    //TODO remove
    fun subgraph(vertex: Vertex, x: Int, y: Int): CoordinatesBuilder

    /**
     * Vertex coordinates
     */
    fun vx(vertex: Vertex, x: Int, y: Int): CoordinatesBuilder

    /**
     * Vertex coordinates
     */
    fun vx(vertex: Vertex, x: Int, y: Int, x2: Int, y2: Int): CoordinatesBuilder

    //TODO remove
    fun complete(vertex: Vertex, x: Int, y: Int): CoordinatesBuilder

    /**
     * Complete coordinates
     */
    fun ct(vertex: Vertex, x: Int, y: Int): CoordinatesBuilder
}