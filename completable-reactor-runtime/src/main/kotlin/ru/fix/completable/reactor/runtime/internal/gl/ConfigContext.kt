package ru.fix.completable.reactor.runtime.internal.gl

import ru.fix.completable.reactor.runtime.gl.GraphConfig
import ru.fix.completable.reactor.runtime.gl.Vertex

class ConfigContext {

    private var vertex: Vertex? = null

    private var configFieldNameResolver: ConfigFieldNameResolver? = null

    var graphConfig: GraphConfig<*>? = null
        set(value) {
            field = value
            if (value != null) {
                this.configFieldNameResolver = ConfigFieldNameResolver(value, listOf(Vertex::class.java))
            } else {
                this.configFieldNameResolver = null
            }
        }


    fun setVertex(vertex: Vertex) {
        this.vertex = vertex
    }

    fun extractVertexOrDefault(defaultVertex: Vertex): Vertex {
        if (this.vertex != null) {
            val result = this.vertex
            this.vertex = null

            return result!!
        } else {
            return defaultVertex
        }
    }


    companion object {
        private val configContext = ThreadLocal<ConfigContext>()

        @JvmStatic
        fun get(): ConfigContext {
            var context: ConfigContext? = ConfigContext.configContext.get()
            if (context == null) {
                context = ConfigContext()
                configContext.set(context)
            }
            return context
        }
    }

    fun resolveVertexName(vertex: Vertex): String? {
        return configFieldNameResolver?.resolveFieldName(vertex)
    }
}
