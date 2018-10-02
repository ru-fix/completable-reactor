package ru.fix.completable.reactor.graph

import ru.fix.completable.reactor.graph.internal.BuilderContext
import ru.fix.completable.reactor.graph.internal.GlTransition
import ru.fix.completable.reactor.graph.internal.GlTransitionBuilder
import ru.fix.completable.reactor.graph.runtime.GlVertex

/**
 * Graph node.
 */
class Vertex {

    // Field accessed via reflection by field name
    private var vx = GlVertex()

    fun on(mergeStatus: Enum<*>): TransitionBuilder {
        if(vx.merger == null && vx.router == null){
            throw IllegalArgumentException("" +
                    "Vertex ${vx.name} is used as source of on() transition but does not have merger or router.")
        }

        if(vx.merger != null && !vx.isTransitionableMerger){
            throw IllegalArgumentException("" +
                    "Vertex ${vx.name} have non transition merger." +
                    " That vertex could participate only in .onAny() transition.")
        }

        val transition = GlTransition()
        transition.mergeStatuses = hashSetOf(mergeStatus)


        return GlTransitionBuilder(
                this,
                transition,
                vx.transitions
        )
    }

    fun onAny(): TransitionBuilder {
        if(vx.merger == null && vx.router == null){
            throw IllegalArgumentException("" +
                    "Vertex ${vx.name} is used as source of onAny() transition but does not have merger or router.")
        }

        val transition = GlTransition()
        transition.isOnAny = true

        return GlTransitionBuilder(
                this,
                transition,
                vx.transitions
        )
    }
}
