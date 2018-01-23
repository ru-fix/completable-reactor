package ru.fix.completable.reactor.runtime.gl

internal class GlCoordinatesImpl : GlCoordinates {
    override fun payload(x: Int, y: Int): GlCoordinates {
        return this
    }

    override fun handler(vertex: Vertex, x: Int, y: Int): GlCoordinates {
        return this
    }

    override fun merger(vertex: Vertex, x: Int, y: Int): GlCoordinates {
        return this
    }

    override fun router(vertex: Vertex, x: Int, y: Int): GlCoordinates {
        return this
    }

    override fun complete(vertex: Vertex, x: Int, y: Int): GlCoordinates {
        return this
    }

    override fun subgraph(vertex: Vertex, x: Int, y: Int): GlCoordinates {
        return this
    }
}
