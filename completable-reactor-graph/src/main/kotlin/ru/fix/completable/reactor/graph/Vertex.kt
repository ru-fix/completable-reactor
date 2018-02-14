package ru.fix.completable.reactor.graph

import ru.fix.completable.reactor.graph.internal.BuilderContext
import ru.fix.completable.reactor.graph.internal.GlTransition
import ru.fix.completable.reactor.graph.internal.GlTransitionBuilder
import ru.fix.completable.reactor.graph.internal.GlVertex
import java.util.*

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

    fun on(vararg mergeStatuses: Enum<*>): TransitionBuilder {
        val transition = GlTransition()
        transition.mergeStatuses = HashSet(Arrays.asList(*mergeStatuses))


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
}
