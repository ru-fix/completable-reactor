package ru.fix.completable.reactor.graph.internal

import ru.fix.completable.reactor.graph.TransitionBuilder
import ru.fix.completable.reactor.graph.Vertex

class GlTransitionBuilder(
        val vx: GlVertex,
        val transition: GlTransition) : TransitionBuilder {

    override fun complete(): Vertex {
        transition.isComplete = true
        return vx.vertex
    }

    override fun handleBy(vertex: Vertex): Vertex {
        val targetGlVertex = VxAccessor.vx(vertex)

        if (targetGlVertex.name == null) {
            targetGlVertex.name = BuilderContext.get().resolveVertexName(vertex)

            transition.handleBy = targetGlVertex
        }

        return vertex
    }

    override fun mergeBy(vertex: Vertex): Vertex {
        val targetGlVertex = VxAccessor.vx(vertex)

        if (targetGlVertex.name == null) {
            targetGlVertex.name = BuilderContext.get().resolveVertexName(vertex)
        }

        if (targetGlVertex.merger == null) {
            throw IllegalArgumentException(
                    """
                        MergeBy transition is targeting vertex ${targetGlVertex.name} that does not have merger.
                        MergeBy can only transition to vertexes with mergers.
                        You can build such vertexes in two manners:
                            handler(...).withMerger(...)
                            subgraph(...).withMerger(...)
                        Other vertices that built without mergers can not be targeted by mergeBy transition.
                        """.trimIndent())
        }

        transition.mergeBy = targetGlVertex

        return vertex
    }
}
