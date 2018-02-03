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
    var router: Router<Any?>? = null

    @JvmField
    var subgraphPayloadType: Class<*>? = null

    @JvmField
    var subgraphPayloadBuilder: Subgraph<Any?, Any?>? = null

    @JvmField
    val transitions: MutableList<GlTransition> = ArrayList()

}