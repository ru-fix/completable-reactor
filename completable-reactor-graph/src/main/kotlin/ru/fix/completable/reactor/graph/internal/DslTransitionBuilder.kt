package ru.fix.completable.reactor.graph.internal

import ru.fix.completable.reactor.graph.TransitionBuilder
import ru.fix.completable.reactor.graph.Vertex
import ru.fix.completable.reactor.graph.runtime.RuntimeTransition
import ru.fix.completable.reactor.graph.runtime.RuntimeVertex

class DslTransitionBuilder(
        val vertex: Vertex,
        val transition: RuntimeTransition,
        val transitionReceiver: MutableList<RuntimeTransition>) : TransitionBuilder {

    private val vx = InternalDslAccessor.vx(vertex)

    override fun complete(): Vertex {
        transition.isComplete = true
        mergeTransition(vx, transition, transitionReceiver)
        return this.vertex
    }

    override fun handleBy(vertex: Vertex): Vertex {
        val targetGlVertex = InternalDslAccessor.vx(vertex)

        if (targetGlVertex.name == null) {
            targetGlVertex.name = BuilderContext.get().resolveVertexName(vertex)
        }

        transition.handleBy = targetGlVertex
        mergeTransition(vx, transition, transitionReceiver)

        return this.vertex
    }

    override fun mergeBy(vertex: Vertex): Vertex {
        val targetGlVertex = InternalDslAccessor.vx(vertex)

        if (targetGlVertex.name == null) {
            targetGlVertex.name = BuilderContext.get().resolveVertexName(vertex)
        }

        when (targetGlVertex.type) {
            RuntimeVertex.Type.Router,
            RuntimeVertex.Type.Mutator -> throw IllegalArgumentException(
                    """
                    MergeBy transition is targeting vertex ${targetGlVertex.name}.
                    Vertex ${targetGlVertex.name} is of type Router or Mutator.
                    Routers and Mutators are allowed to participate only in handleBy transitions.
                    Maybe you want to use HandleBy transition instead.
                    """.trimIndent())

            RuntimeVertex.Type.SubgraphWithEmptyMerger,
            RuntimeVertex.Type.SubgraphWithMerger,
            RuntimeVertex.Type.SubgraphWithRoutingMerger,
            RuntimeVertex.Type.HandlerWithEmptyMerger,
            RuntimeVertex.Type.HandlerWithMerger,
            RuntimeVertex.Type.HandlerWithRoutingMerger -> {
                /* OK */
            }

            /*
                MergeBy transition is targeting vertex of type Handler or Subgraph without Merger.
                We will implicitly create empty merger fot that vertex
                TODO: add implicit empty merger creation into JavaSourceParser, onAny case already exist in parser
             */
            RuntimeVertex.Type.HandlerWithoutMerger -> {
                targetGlVertex.merger = RuntimeEmptyMerger()
                targetGlVertex.type = RuntimeVertex.Type.HandlerWithEmptyMerger
            }
            RuntimeVertex.Type.SubgraphWithoutMerger -> {
                targetGlVertex.merger = RuntimeEmptyMerger()
                targetGlVertex.type = RuntimeVertex.Type.SubgraphWithEmptyMerger
            }
        }

        transition.mergeBy = targetGlVertex
        mergeTransition(vx, transition, transitionReceiver)

        return this.vertex
    }

    /**
     * Check if transition between mergePoint and given processor already exist.
     * Check if new transition is redundant.
     * Try to merge new transition into existing one.
     * If transition does not exist add new transition to mergePoint.
     */
    fun mergeTransition(
            sourceVertex: RuntimeVertex,
            newTransition: RuntimeTransition,
            sourceVertexTransitions: MutableList<RuntimeTransition>) {

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

            val targetAccessor: (RuntimeTransition) -> RuntimeVertex?

            when {
                newTransition.handleBy != null -> targetAccessor = { transition: RuntimeTransition -> transition.handleBy }

                newTransition.mergeBy != null -> targetAccessor = { transition: RuntimeTransition -> transition.mergeBy }

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
