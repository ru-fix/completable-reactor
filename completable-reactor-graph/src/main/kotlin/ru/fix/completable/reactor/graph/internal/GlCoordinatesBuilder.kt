package ru.fix.completable.reactor.graph.internal

import ru.fix.completable.reactor.graph.CoordinatesBuilder
import ru.fix.completable.reactor.graph.Vertex

class GlCoordinatesBuilder : CoordinatesBuilder {
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

    override fun vx(vertex: Vertex, x: Int, y: Int): CoordinatesBuilder {
        return this
    }

    override fun vx(vertex: Vertex, x: Int, y: Int, x2: Int, y2: Int): CoordinatesBuilder {
        return this
    }

    override fun pd(x: Int, y: Int): CoordinatesBuilder {
        return this
    }

    override fun ct(vertex: Vertex, x: Int, y: Int): CoordinatesBuilder {
        return this
    }
}
