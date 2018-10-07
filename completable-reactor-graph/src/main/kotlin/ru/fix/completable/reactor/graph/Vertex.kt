package ru.fix.completable.reactor.graph

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
        val transition = GlTransition()
        transition.isOnAny = true

        checkForIncompatibleTransitions(vx, transition)

        return GlTransitionBuilder(
                this,
                transition,
                vx.transitions
        )
    }

    fun onElse(): TransitionBuilder {
        val transition = GlTransition()
        transition.isOnElse = true

        checkForIncompatibleTransitions(vx, transition)

        return GlTransitionBuilder(
                this,
                transition,
                vx.transitions
        )
    }

    /**
     * -check that vertex is transition onElse or onAny has merger or router
     * -check that vertex doesn't have two transitions onAny and onElse
     */
    private fun checkForIncompatibleTransitions(vx: GlVertex, newTransition: GlTransition) {
        if(vx.merger == null && vx.router == null) {
            val name = if (newTransition.isOnAny) "onAny()" else "onElse()"
            throw IllegalArgumentException("" +
                    "Vertex ${vx.name} is used as source of $name transition but does not have merger or router.")
        }


        var hasIncompatibleTransition = false
        vx.transitions.forEach { transition ->
            if (transition.isOnAny && newTransition.isOnElse) {
               hasIncompatibleTransition = true
            } else if (transition.isOnElse && newTransition.isOnAny) {
                hasIncompatibleTransition = true
            }
        }
        if (hasIncompatibleTransition)  {
            throw IllegalArgumentException(
                    "Vertex ${vx.name} is used together incompatible transitions onElse and onAny.")
        }
    }
}
