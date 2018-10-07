package ru.fix.completable.reactor.graph

import ru.fix.completable.reactor.graph.internal.GlEmptyMerger
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

        if(vx.merger != null && !vx.isRoutable){
            throw IllegalArgumentException("" +
                    "Vertex ${vx.name} have non routable merger or implicit non routable empty merger." +
                    " That vertex could participate only in .onAny() transition.")
        }

        if(vx.router != null && !vx.isRoutable){
            throw IllegalArgumentException("" +
                    "Vertex ${vx.name} have non routable mutator." +
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
        addEmptyMergerIfNotExists()

        val transition = GlTransition()
        transition.isOnAny = true

        checkForIncompatibleTransitions(transition)

        return GlTransitionBuilder(
                this,
                transition,
                vx.transitions
        )
    }

    fun onElse(): TransitionBuilder {
        addEmptyMergerIfNotExists()

        val transition = GlTransition()
        transition.isOnElse = true

        checkForIncompatibleTransitions(transition)

        return GlTransitionBuilder(
                this,
                transition,
                vx.transitions
        )
    }

    /**
     * When user doesn't provide any merger but vertex participate in onAny transition
     * then vertex is effectively generates default empty merger
     */
    private fun addEmptyMergerIfNotExists() {
        if((vx.handler != null || vx.subgraphPayloadBuilder != null) && vx.merger == null){
            vx.merger = GlEmptyMerger()
        }
    }

    /**
     * -
     * -check that vertex is transition onElse or onAny has merger or router
     * -check that vertex doesn't have two transitions onAny and onElse
     */
    private fun checkForIncompatibleTransitions(newTransition: GlTransition) {
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
