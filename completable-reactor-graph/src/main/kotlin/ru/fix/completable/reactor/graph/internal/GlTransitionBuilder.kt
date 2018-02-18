package ru.fix.completable.reactor.graph.internal

import ru.fix.completable.reactor.graph.TransitionBuilder
import ru.fix.completable.reactor.graph.Vertex

class GlTransitionBuilder(
        val vx: GlVertex,
        val transition: GlTransition,
        val transitionReceiver: MutableList<GlTransition>) : TransitionBuilder {

    override fun complete(): Vertex {
        transition.isComplete = true
        mergeTransition(vx, transition, transitionReceiver)
        return vx.vertex
    }

    override fun handleBy(vertex: Vertex): Vertex {
        val targetGlVertex = InternalGlAccessor.vx(vertex)

        if (targetGlVertex.name == null) {
            targetGlVertex.name = BuilderContext.get().resolveVertexName(vertex)
        }

        transition.handleBy = targetGlVertex
        mergeTransition(vx, transition, transitionReceiver)

        return vx.vertex
    }

    override fun mergeBy(vertex: Vertex): Vertex {
        val targetGlVertex = InternalGlAccessor.vx(vertex)

        if (targetGlVertex.name == null) {
            targetGlVertex.name = BuilderContext.get().resolveVertexName(vertex)
        }

        if (targetGlVertex.merger == null) {

            val vertexTypeMessage =
                    if (targetGlVertex.router != null) {
                        "Vertex ${targetGlVertex.name} is of type Router." +
                                " Routers allowed to participate only in handleBy transitions."
                    } else if (targetGlVertex.handler != null) {
                        "Vertex ${targetGlVertex.name} is of type Handler without Merger." +
                                " It can participate only in handleBy transitions."
                    } else if (targetGlVertex.subgraphPayloadBuilder != null) {
                        "Vertex ${targetGlVertex.name} is of type Subgrpah without Merger." +
                                " It can participate only in handleBy transitions."
                    } else {
                        ""
                    }


            throw IllegalArgumentException(
                    """
                        MergeBy transition is targeting vertex ${targetGlVertex.name} that does not have merger.
                        $vertexTypeMessage
                        MergeBy can only transition to vertexes with mergers.
                        You can build such vertexes in two manners:
                            handler(...).withMerger(...)
                            subgraph(...).withMerger(...)
                        Other vertices that built without mergers can not be targeted by mergeBy transition.
                        You probably have to use HandleBy transition instead.
                        """.trimIndent())
        }

        transition.mergeBy = targetGlVertex
        mergeTransition(vx, transition, transitionReceiver)

        return vx.vertex
    }

    /**
     * Check if transition between mergePoint and given processor already exist.
     * Check if new transition is redundant.
     * Try to merge new transition into existing one.
     * If transition does not exist add new transition to mergePoint.
     */
    fun mergeTransition(
            sourceVertex: GlVertex,
            newTransition: GlTransition,
            sourceVertexTransitions: MutableList<GlTransition>) {

        if (transition.isComplete) {
            val existingCompleteTransitions = sourceVertexTransitions.filter { it.isComplete }
            if (existingCompleteTransitions.isNotEmpty()) {
                existingCompleteTransitions.first().let {
                    it.mergeStatuses = it.mergeStatuses + transition.mergeStatuses
                }

            } else {
                sourceVertexTransitions.add(newTransition)
            }

        } else {
            // not complete transition

            val targetAccessor: (GlTransition) -> GlVertex?

            when {
                newTransition.handleBy != null -> targetAccessor = { transition: GlTransition -> transition.handleBy }

                newTransition.mergeBy != null -> targetAccessor = { transition: GlTransition -> transition.mergeBy }

                else -> throw IllegalArgumentException("Transition does not have neither handleBy neither mergeBy target")
            }

            val newTransitionTarget = targetAccessor(newTransition)!!

            val existingTransitionToSameTarget = sourceVertexTransitions
                    .asSequence()
                    .filter { targetAccessor(it) == newTransitionTarget }
                    .toList()

            if (existingTransitionToSameTarget.isNotEmpty()) {
                if (existingTransitionToSameTarget.size > 1) {
                    throw IllegalArgumentException("" +
                            "More that one transition exit" +
                            " from ${sourceVertex.name} to ${newTransitionTarget.name}." +
                            " Transition is redundant."
                    )
                }

                val existingTransition = existingTransitionToSameTarget.first()
                if (existingTransition.isOnAny) {
                    throw IllegalArgumentException("" +
                            "Unconditional transition from ${sourceVertex.name}" +
                            " to ${newTransitionTarget.name} already exist." +
                            " Transition is redundant.")
                }

                if (existingTransition.mergeStatuses.containsAll(newTransition.mergeStatuses)) {
                    throw IllegalArgumentException("" +
                            "Transition that suite given condition already exist" +
                            " between ${sourceVertex.name} and ${newTransitionTarget.name}." +
                            " Transition is redundant.")
                }

                existingTransition.mergeStatuses = existingTransition.mergeStatuses.union(newTransition.mergeStatuses)


            } else {
                sourceVertexTransitions.add(newTransition)
            }
        }
    }
}
