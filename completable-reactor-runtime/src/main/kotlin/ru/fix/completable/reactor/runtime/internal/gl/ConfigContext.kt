package ru.fix.completable.reactor.runtime.internal.gl

import ru.fix.completable.reactor.runtime.gl.Vertex

class ConfigContext {

    private var vertex: Vertex? = null

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
}
