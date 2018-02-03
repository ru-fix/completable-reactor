package ru.fix.completable.reactor.graph.internal

import ru.fix.completable.reactor.graph.Vertex

class VxAccessor {

    companion object {

        private val accessor = Vertex::class.java.getDeclaredField("vx").let {
            it.isAccessible = true
            it::get
        }

        fun vx(vertex: Vertex): GlVertex = accessor(vertex) as GlVertex
    }
}
