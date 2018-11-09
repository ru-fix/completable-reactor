package ru.fix.completable.reactor.graph

import ru.fix.completable.reactor.graph.internal.RuntimeEmptyMerger
import ru.fix.completable.reactor.graph.runtime.RuntimeTransition
import ru.fix.completable.reactor.graph.internal.DslTransitionBuilder
import ru.fix.completable.reactor.graph.runtime.RuntimeVertex

/**
 * Graph node.
 */
class Vertex {

    // Field accessed via reflection by field name
    private var vx = RuntimeVertex(this)

    fun on(mergeStatus: Enum<*>): TransitionBuilder {
        if(vx.merger == null){
            throw IllegalArgumentException("" +
                    "Vertex ${vx.name} is used as source of on() transition but does not have merger")
        }

        if(vx.merger != null && !vx.isRoutable){
            throw IllegalArgumentException("" +
                    "Vertex ${vx.name} have non routable merger or implicit non routable empty merger." +
                    " That vertex could participate only in .onAny() transition.")
        }

        val transition = RuntimeTransition()
        transition.mergeStatuses = hashSetOf(mergeStatus)

        return DslTransitionBuilder(
                this,
                transition,
                vx.transitions
        )
    }

    fun onAny(): TransitionBuilder {
        addEmptyMergerIfNotExists()

        val transition = RuntimeTransition()
        transition.isOnAny = true

        checkForIncompatibleOnAnyOnElseTransitions(transition)

        return DslTransitionBuilder(
                this,
                transition,
                vx.transitions
        )
    }

    fun onElse(): TransitionBuilder {
        val transition = RuntimeTransition()
        transition.isOnElse = true

        checkForIncompatibleOnAnyOnElseTransitions(transition)

        return DslTransitionBuilder(
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
        when{
            vx.handler != null && vx.merger == null ->{
                vx.merger = RuntimeEmptyMerger()
                vx.type = RuntimeVertex.Type.HandlerWithEmptyMerger
            }
            vx.subgraphPayloadBuilder != null && vx.merger == null -> {
                vx.merger = RuntimeEmptyMerger()
                vx.type = RuntimeVertex.Type.SubgraphWithEmptyMerger
            }
        }
    }

    /**
     * -
     * -check that vertex is transition onElse or onAny has merger or router
     * -check that vertex doesn't have two transitions onAny and onElse
     */
    private fun checkForIncompatibleOnAnyOnElseTransitions(newTransition: RuntimeTransition) {
        if(vx.merger == null) {
            val name = if (newTransition.isOnAny) "onAny()" else "onElse()"
            throw IllegalArgumentException("" +
                    "Vertex ${vx.name} is used as source of $name transition but does not have merger.")
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
                    "Vertex ${vx.name} is used two incompatible transitions onElse and onAny is same time.")
        }
    }
}
