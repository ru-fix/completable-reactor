package ru.fix.completable.reactor.graph.internal

import ru.fix.completable.reactor.graph.Graphable
import ru.fix.completable.reactor.graph.Vertex

class BuilderContext {

    private var vertex: GlVertex? = null

    private var fieldNameResolver: FieldNameResolver? = null

    private var graph: GlGraph? = null

    fun setGraph(graph: Graphable, glGraph: GlGraph) {
        this.graph = glGraph
        this.fieldNameResolver = FieldNameResolver(graph, listOf(Vertex::class.java))
    }

    fun getGraph() = graph


    fun setVertex(vertex: GlVertex) {
        this.vertex = vertex
    }

    fun extractVertexOrDefault(defaultVertexSupplier: ()->GlVertex): GlVertex {
        if (this.vertex != null) {
            val result = this.vertex
            this.vertex = null

            return result!!
        } else {
            return defaultVertexSupplier()
        }
    }


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

    fun resolveVertexName(vertex: ru.fix.completable.reactor.graph.Vertex): String? {
        return fieldNameResolver?.resolveFieldName(vertex)
    }
}
