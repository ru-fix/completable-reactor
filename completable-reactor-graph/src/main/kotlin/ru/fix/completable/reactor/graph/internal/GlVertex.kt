package ru.fix.completable.reactor.graph.internal

import ru.fix.completable.reactor.graph.*
import java.util.*

class GlVertex(
        @JvmField
        val vertex: Vertex) {

    @JvmField
    var name: String? = null

    @JvmField
    var handler: Handler<Any?, Any?>? = null

    @JvmField
    var merger: Merger<Any?, Any?>? = null

    @JvmField
    var isEmptyMerger: Boolean = false

    @JvmField
    var router: Router<Any?>? = null

    @JvmField
    var subgraphPayloadType: Class<*>? = null

    @JvmField
    var subgraphPayloadBuilder: Subgraph<Any?, Any?>? = null

    @JvmField
    val transitions: MutableList<GlTransition> = ArrayList()

    override fun toString(): String {
        val type = when {
            handler != null -> "handler"
            router != null -> "router"
            subgraphPayloadBuilder != null -> "subgraph"
            else -> "unknown: $name"
        }
        return "$type: $name"
    }
}