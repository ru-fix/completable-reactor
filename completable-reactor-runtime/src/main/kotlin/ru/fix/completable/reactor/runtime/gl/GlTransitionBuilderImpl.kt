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
        if (vertex.name == null) {
            vertex.name = ConfigContext.get().resolveVertexName(vertex)
        }
        transition.handleBy = vertex
        return vertex
    }

    override fun mergeBy(vertex: Vertex): Vertex {
        if (vertex.name == null) {
            vertex.name = ConfigContext.get().resolveVertexName(vertex)
        }

        if (vertex.merger == null) {
            throw IllegalArgumentException("""
                MergeBy transition is targeting vertex ${vertex.name} that does not have merger.
                MergeBy can only transition to vertexes with mergers.
                You can build such vertexes in two manners:
                    handler(...).withMerger(...)
                    subgraph(...).withMerger(...)
                Other vertices that built without mergers can not be targeted by mergeBy transition.
            """.trimIndent())
        }

        transition.mergeBy = vertex
        return vertex
    }
}
