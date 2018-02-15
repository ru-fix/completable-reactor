package ru.fix.completable.reactor.graph

import ru.fix.completable.reactor.graph.internal.BuilderContext
import ru.fix.completable.reactor.graph.internal.GlTransition
import ru.fix.completable.reactor.graph.internal.GlTransitionBuilder
import ru.fix.completable.reactor.graph.internal.GlVertex

/**
 * Graph node.
 */
open class Vertex {

    //field name is used via reflection
    private var vx = GlVertex(this)

    init {
        if (this.javaClass != Vertex::class.java) {
            // user derived class that extends Vertex
            BuilderContext.get().setVertex(vx)
        }

        val currentGraph = BuilderContext.get().getGraph() ?: throw IllegalStateException(
                """
                Vertex created not inside class that extends Graph.
                Or vertex created in different thread from Graph class.
                Builder context is not set.
                """.trimIndent())

        currentGraph.vertices.add(vx)
    }

    fun on(mergeStatus: Enum<*>): TransitionBuilder {
        val transition = GlTransition()
        transition.mergeStatuses = hashSetOf(mergeStatus)


        return GlTransitionBuilder(
                this.vx,
                transition,
                vx.transitions
        )
    }

    fun onAny(): TransitionBuilder {
        val transition = GlTransition()
        transition.isOnAny = true


        return GlTransitionBuilder(
                this.vx,
                transition,
                vx.transitions
        )
    }

    /**
     * Create new instance of Vertex with same handler, merger, router or subgraph.
     * Position on the graph and transitions are not copied, they should be defined by using
     * transition api separately for each vertices.
     */
    fun clone(): Vertex {
        if (vx.handler == null || vx.router == null || vx.subgraphPayloadBuilder == null) {
            throw IllegalArgumentException("" +
                    "Vertex ${vx.name} could not be cloned." +
                    " It does not have neither handler, router or subgraph.")
        }


        return Vertex().also {
            it.vx.handler = this.vx.handler
            it.vx.merger = this.vx.merger
            it.vx.router = this.vx.router
            it.vx.subgraphPayloadBuilder = this.vx.subgraphPayloadBuilder
            it.vx.subgraphPayloadType = this.vx.subgraphPayloadType
        }
    }
}
