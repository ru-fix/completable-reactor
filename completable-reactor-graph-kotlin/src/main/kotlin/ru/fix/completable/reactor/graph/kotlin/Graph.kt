package ru.fix.completable.reactor.graph.kotlin

import ru.fix.completable.reactor.graph.*
import ru.fix.completable.reactor.graph.internal.*
import ru.fix.completable.reactor.graph.kotlin.internal.GlMergerBuilder
import java.util.concurrent.CompletableFuture
import kotlin.reflect.KClass
import kotlinx.coroutines.experimental.future.future
import ru.fix.completable.reactor.graph.runtime.GlGraph

open class Graph<Payload> : Graphable {

    // Field accessed via reflection by field name
    private val graph = GlGraph()

    init {
        // Deliberately leaking reference to ThreadLocal context.
        // Graph construction is happening in single thread
        BuilderContext.get().setGraph(this, graph)
    }

    private val graphBuilderValidator = GraphBuilderValidator()

    protected fun payload(): PayloadTransitionBuilder<Payload> {
        return GlPayloadImpl(graph)
    }

    protected fun coordinates(): CoordinatesBuilder {
        return GlCoordinatesBuilder()
    }


    protected fun <HandlerResult> handler(handler: Payload.() -> CompletableFuture<HandlerResult>):
            MergerBuilder<Payload, HandlerResult> {

        val vertex = Vertex()
        val vx = InternalGlAccessor.vx(vertex)
        graph.vertices.add(vx)

        graphBuilderValidator.validateHandler(vx)

        vx.handler = object : Handler<Payload, HandlerResult> {
            override fun handle(payload: Payload): CompletableFuture<HandlerResult> {
                return handler(payload)
            }
        } as Handler<Any?, Any?>

        return GlMergerBuilder(vertex)
    }

    protected fun <HandlerResult> suspendHandler(handler: suspend Payload.() -> HandlerResult):
            MergerBuilder<Payload, HandlerResult> {

        return handler {
            future {
                handler()
            }
        }
    }

    protected fun mutator(mutator: Payload.() -> Unit): Vertex {
        return router {
            mutator(this)
            GlEmptyMerger.EmptyMergerStatusEnum.EMPTY_MERGER_STATUS
        }
    }

    protected fun router(router: Payload.() -> Enum<*>): Vertex {
        val vertex = Vertex()
        val vx = InternalGlAccessor.vx(vertex)
        graph.vertices.add(vx)

        graphBuilderValidator.validateRouter(vx)

        vx.router = object : Router<Payload> {
            override fun route(payload: Payload): Enum<*> {
                return router(payload)
            }
        } as Router<Any?>

        return vertex
    }

    protected fun <SubgraphPayload : Any> subgraph(
            subgraphPayloadType: KClass<SubgraphPayload>,
            subgraph: Payload.() -> SubgraphPayload): MergerBuilder<Payload, SubgraphPayload> {

        val vertex = Vertex()
        val vx = InternalGlAccessor.vx(vertex)
        graph.vertices.add(vx)

        graphBuilderValidator.validateSubgraph(vx)

        vx.subgraphPayloadType = subgraphPayloadType.java
        vx.subgraphPayloadBuilder = object : Subgraph<Payload, SubgraphPayload> {
            override fun subgraph(payload: Payload): SubgraphPayload {
                return subgraph(payload)
            }
        } as Subgraph<Any?, Any?>

        return GlMergerBuilder(vertex)
    }
}