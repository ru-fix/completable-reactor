package ru.fix.completable.reactor.graph.internal

import ru.fix.completable.reactor.graph.Graph
import ru.fix.completable.reactor.graph.Vertex

class InternalGlAccessor {

    companion object {

        private val vxAccessor = Vertex::class.java.getDeclaredField("vx").let {
            it.isAccessible = true
            it::get
        }

        private val graphAccessor = Graph::class.java.getDeclaredField("graph").let {
            it.isAccessible = true
            it::get
        }

        @JvmStatic
        fun vx(vertex: Vertex): GlVertex = vxAccessor(vertex) as GlVertex

        @JvmStatic
        fun graph(graph: Graph<*>): GlGraph = graphAccessor(graph) as GlGraph
    }
}
