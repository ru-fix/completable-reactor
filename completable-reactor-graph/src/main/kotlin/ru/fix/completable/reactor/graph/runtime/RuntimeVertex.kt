package ru.fix.completable.reactor.graph.runtime

import ru.fix.completable.reactor.graph.*
import java.util.*

class RuntimeVertex(val sourceVertex: Vertex) {

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

    /**
     * Merger or EmptyMerger or Mutator are non routable, they could participate only in onAny() transition
     * RoutingMerger and Router are routable, they could participate in on(state) transition
     */
    @JvmField
    var isRoutable: Boolean = false

    @JvmField
    var router: Router<Any?>? = null

    @JvmField
    var subgraphPayloadType: Class<*>? = null

    @JvmField
    var subgraphPayloadBuilder: Subgraph<Any?, Any?>? = null

    @JvmField
    val transitions: MutableList<RuntimeTransition> = ArrayList()

    val isMergerable: Boolean
        get() = merger != null || router != null

    enum class Type {
        HandlerWithMerger,
        HandlerWithRoutingMerger,
        HandlerWithoutMerger,
        Router,
        Mutator,
        Subgraph
    }

    var type: Type? = null

    override fun toString() = "$type: $name"
}