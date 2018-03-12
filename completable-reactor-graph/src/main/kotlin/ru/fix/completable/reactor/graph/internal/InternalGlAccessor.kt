package ru.fix.completable.reactor.graph.internal

import ru.fix.completable.reactor.graph.Graphable
import ru.fix.completable.reactor.graph.Vertex

class InternalGlAccessor {

    companion object {

        private val vxAccessor = Vertex::class.java.getDeclaredField("vx").let {
            it.isAccessible = true
            it::get
        }

        @JvmStatic
        fun vx(vertex: Vertex): GlVertex = vxAccessor(vertex) as GlVertex

        @JvmStatic
        fun graph(graph: Graphable): GlGraph {

            var type: Class<*>? = graph.javaClass

            while (type != null) {
                val field = type.declaredFields.find { it.name == "graph" }

                if (field != null && field.type.isAssignableFrom(GlGraph::class.java)) {
                    field.isAccessible = true
                    return field.get(graph) as GlGraph
                }

                type = type.superclass
            }

            throw IllegalArgumentException("Can not find field `graph` within Graphable class ${graph.javaClass}")
        }
    }
}
