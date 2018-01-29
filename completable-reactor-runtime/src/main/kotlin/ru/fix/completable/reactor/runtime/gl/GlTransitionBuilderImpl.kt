package ru.fix.completable.reactor.runtime.gl

import ru.fix.completable.reactor.runtime.internal.gl.ConfigContext

class GlTransitionBuilderImpl(
        val vertex: Vertex,
        val transition: GlTransition) : GlTransitionBuilder {

    override fun complete(): Vertex {
        transition.isComplete = true
        return vertex
    }

    override fun handleBy(vertex: Vertex): Vertex {
        if(vertex.name == null){
            vertex.name = ConfigContext.get().resolveVertexName(vertex)
        }
        transition.handleBy = vertex
        return vertex
    }

    override fun mergeBy(vertex: Vertex): Vertex {
        if(vertex.name == null){
            vertex.name = ConfigContext.get().resolveVertexName(vertex)
        }
        transition.mergeBy = vertex
        return vertex
    }
}
