package ru.fix.completable.reactor.graph

interface CoordinatesBuilder {
    /**
     * Payload start point coordinates
     */
    fun pd(x: Int, y: Int): CoordinatesBuilder

    /**
     * Vertex coordinates
     */
    fun vx(vertex: Vertex, x: Int, y: Int): CoordinatesBuilder

    /**
     * Vertex coordinates
     */
    fun vx(vertex: Vertex, x: Int, y: Int, x2: Int, y2: Int): CoordinatesBuilder

    /**
     * Complete coordinates
     */
    fun ct(vertex: Vertex, x: Int, y: Int): CoordinatesBuilder
}