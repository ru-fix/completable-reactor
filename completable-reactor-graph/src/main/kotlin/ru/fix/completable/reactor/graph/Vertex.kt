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

    fun orElse(): TransitionBuilder {
        //TODO: правильная валидация!!!
        if(vx.merger == null && vx.router == null){
            throw IllegalArgumentException("" +
                    "Vertex ${vx.name} is used as source of orElse() transition but does not have merger or router.")
        }

        val transition = GlTransition()
        transition.isOrElse = true

        return GlTransitionBuilder(
                this,
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

        BuilderContext.get().getGraph()?.let { graph ->
            if (vx.name == null) {
                vx.name = BuilderContext.get().resolveVertexName(this)
            }
        }

        if (vx.handler == null && vx.router == null && vx.subgraphPayloadBuilder == null) {
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
            it.vx.isTransitionableMerger = this.vx.isTransitionableMerger

            BuilderContext.get().getGraph()?.vertices?.add(it.vx) ?: throw IllegalStateException("" +
                    "Vertex created not inside class that extends Graph. " +
                    "Or vertex created in different thread from Graph class. " +
                    "Builder context is not set.")
        }
    }
}
