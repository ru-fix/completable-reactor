package ru.fix.completable.reactor.graph.internal

import ru.fix.completable.reactor.graph.CoordinatesBuilder
import ru.fix.completable.reactor.graph.Vertex

internal class GlCoordinatesBuilder : CoordinatesBuilder {
    override fun payload(x: Int, y: Int): CoordinatesBuilder {
        return this
    }

    override fun handler(vertex: Vertex, x: Int, y: Int): CoordinatesBuilder {
        return this
    }

    override fun merger(vertex: Vertex, x: Int, y: Int): CoordinatesBuilder {
        return this
    }

    override fun router(vertex: Vertex, x: Int, y: Int): CoordinatesBuilder {
        return this
    }

    override fun complete(vertex: Vertex, x: Int, y: Int): CoordinatesBuilder {
        return this
    }

    override fun subgraph(vertex: Vertex, x: Int, y: Int): CoordinatesBuilder {
        return this
    }
}
