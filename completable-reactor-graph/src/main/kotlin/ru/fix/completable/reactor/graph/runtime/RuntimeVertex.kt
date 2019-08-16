package ru.fix.completable.reactor.graph.runtime

import ru.fix.completable.reactor.graph.*
import java.util.*

class RuntimeVertex(val sourceVertex: Vertex) {

    companion object {
        private val ROUTABLE_TYPES = setOf(
                Type.HandlerWithRoutingMerger,
                Type.SubgraphWithRoutingMerger,
                Type.Router)

        private val MERGERABLE_TYPES = setOf(
                Type.HandlerWithMerger,
                Type.HandlerWithEmptyMerger,
                Type.HandlerWithRoutingMerger,
                Type.SubgraphWithMerger,
                Type.SubgraphWithEmptyMerger,
                Type.SubgraphWithRoutingMerger,
                Type.Router,
                Type.Mutator)
    }

    /**
     * Name is assigned when vertex participates in incoming transition.
     * If Name is NULL this means that there were no incoming transition to vertex.
     * That could happen if vertex was initialized, but never used in any incoming transitions.
     */
    @JvmField
    var name: String? = null

    @JvmField
    var handler: Handler<Any?, Any?>? = null

    @JvmField
    var merger: RoutingMerger<Any?, Any?>? = null

    @JvmField
    var isImplicitMerger: Boolean = false

    @JvmField
    var subgraphPayloadType: Class<*>? = null


    @JvmField
    var subgraphPayloadBuilder: Subgraph<Any?, Any?>? = null

    @JvmField
    val transitions: MutableList<RuntimeTransition> = ArrayList()


    /**
     * Merger or EmptyMerger or Mutator are non routable, they could participate only in onAny() transition
     * RoutingMerger and Router are routable, they could participate in on(state) transition
     */
    val isRoutable: Boolean
        get() = type in ROUTABLE_TYPES

    /**
     * Vertices that have mergers and could have outgoing transitions
     * Router, Mutator, Subgraph or Handlers with any type of Mergers
     */
    val isMergerable: Boolean
        get() = type in MERGERABLE_TYPES

    enum class Type {
        HandlerWithMerger,
        HandlerWithRoutingMerger,
        HandlerWithoutMerger,
        HandlerWithEmptyMerger,
        SubgraphWithMerger,
        SubgraphWithRoutingMerger,
        SubgraphWithoutMerger,
        SubgraphWithEmptyMerger,
        Router,
        Mutator,
    }

    var type: Type? = null

    override fun toString() = "$type: $name"
}