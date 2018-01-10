package ru.fix.completable.reactor.runtime.gl

internal class GlTransitionBuilderImpl(val vertex: Vertex, val transition: GlTransition) : GlTransitionBuilder {

    override fun complete(): Vertex {
        transition.setComplete(true)
        return vertex
    }

    override fun handleBy(vertex: Vertex): Vertex {
        transition.setHandleBy(vertex)
        return vertex
    }

    override fun mergeBy(vertex: Vertex): Vertex {
        transition.setMergeBy(vertex)
        return vertex
    }
}
