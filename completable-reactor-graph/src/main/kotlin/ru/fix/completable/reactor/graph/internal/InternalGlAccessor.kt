package ru.fix.completable.reactor.graph.internal

import ru.fix.completable.reactor.graph.Graphable
import ru.fix.completable.reactor.graph.Vertex
import ru.fix.completable.reactor.graph.runtime.RuntimeGraph
import ru.fix.completable.reactor.graph.runtime.RuntimeVertex

class InternalGlAccessor {

    companion object {

        private val vxAccessor = Vertex::class.java.getDeclaredField("vx").let {
            it.isAccessible = true
            it::get
        }

        @JvmStatic
        fun vx(vertex: Vertex): RuntimeVertex = vxAccessor(vertex) as RuntimeVertex

        @JvmStatic
        fun graph(graph: Graphable): RuntimeGraph {

            var type: Class<*>? = graph.javaClass

            while (type != null) {
                val field = type.declaredFields.find { it.name == "graph" }

                if (field != null && field.type.isAssignableFrom(RuntimeGraph::class.java)) {
                    field.isAccessible = true
                    return field.get(graph) as RuntimeGraph
                }

                type = type.superclass
            }

            throw IllegalArgumentException("Can not find field `graph` within Graphable class ${graph.javaClass}")
        }
    }
}
