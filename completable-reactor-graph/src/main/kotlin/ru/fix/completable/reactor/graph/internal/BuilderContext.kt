package ru.fix.completable.reactor.graph.internal

import ru.fix.completable.reactor.graph.Graph
import ru.fix.completable.reactor.graph.Vertex

class BuilderContext {

    private var vertex: GlVertex? = null

    private var fieldNameResolver: FieldNameResolver? = null

    var graph: Graph<*>? = null
        set(value) {
            field = value
            if (value != null) {
                this.fieldNameResolver = FieldNameResolver(value, listOf(Vertex::class.java))
            } else {
                this.fieldNameResolver = null
            }
        }

    fun setVertex(vertex: GlVertex) {
        this.vertex = vertex
    }

    fun extractVertexOrDefault(defaultVertex: GlVertex): GlVertex {
        if (this.vertex != null) {
            val result = this.vertex
            this.vertex = null

            return result!!
        } else {
            return defaultVertex
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
