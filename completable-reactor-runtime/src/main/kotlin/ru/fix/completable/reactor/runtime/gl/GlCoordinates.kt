package ru.fix.completable.reactor.runtime.gl

interface GlCoordinates {
    /**
     * Payload start point coordinates
     */
    fun payload(x: Int, y: Int): GlCoordinates

    fun handler(vertex: Vertex, x: Int, y: Int): GlCoordinates

    fun merger(vertex: Vertex, x: Int, y: Int): GlCoordinates

    fun router(vertex: Vertex, x: Int, y: Int): GlCoordinates

    fun subgraph(vertex: Vertex, x: Int, y: Int): GlCoordinates

    fun complete(vertex: Vertex, x: Int, y: Int): GlCoordinates
}