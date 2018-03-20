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

        graphBuilderValidator.validateHandler(vx)

        vx.handler = Handler<Payload, HandlerResult> { pld -> handler(pld) } as Handler<Any?, Any?>
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

        graphBuilderValidator.validateRouter(vx)

        vx.router = Router<Payload> { pld -> router(pld) } as Router<Any?>
        return vertex
    }

    protected fun <SubgraphPayload: Any> subgraph(
            subgraphPayloadType: KClass<SubgraphPayload>,
            subgraph: Payload.() -> SubgraphPayload): MergerBuilder<Payload, SubgraphPayload> {

        val vertex = Vertex()
        val vx = InternalGlAccessor.vx(vertex)

        graphBuilderValidator.validateSubgraph(vx)

        vx.subgraphPayloadType = subgraphPayloadType.java
        vx.subgraphPayloadBuilder = Subgraph<Payload, SubgraphPayload> {pld -> subgraph(pld) } as Subgraph<Any?, Any?>
        return GlMergerBuilder(vertex)
    }
}