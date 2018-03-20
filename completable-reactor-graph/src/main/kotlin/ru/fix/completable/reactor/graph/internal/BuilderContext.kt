package ru.fix.completable.reactor.graph.internal

import ru.fix.completable.reactor.graph.Graphable
import ru.fix.completable.reactor.graph.Vertex
import ru.fix.completable.reactor.graph.runtime.GlGraph

class BuilderContext {

    private var fieldNameResolver: FieldNameResolver? = null

    private var graph: GlGraph? = null

    fun setGraph(graph: Graphable, glGraph: GlGraph) {
        this.graph = glGraph
        this.fieldNameResolver = FieldNameResolver(graph, listOf(Vertex::class.java))
    }

    fun getGraph() = graph

    companion object {
        private val builderContext = ThreadLocal<BuilderContext>()

        @JvmStatic
        fun get(): BuilderContext {
            var context: BuilderContext? = builderContext.get()
            if (context == null) {
                context = BuilderContext()
                builderContext.set(context)
            }
            return context
        }
    }

    fun resolveVertexName(vertex: Vertex): String? {
        return fieldNameResolver?.resolveFieldName(vertex)
    }
}
