package ru.fix.completable.reactor.graph.kotlin

import ru.fix.completable.reactor.graph.*
import ru.fix.completable.reactor.graph.internal.*
import ru.fix.completable.reactor.graph.kotlin.internal.GlMergerBuilder
import java.util.concurrent.CompletableFuture
import kotlin.reflect.KClass
import kotlinx.coroutines.experimental.future.future

open class Graph<Payload> : Graphable {

    // Field name used via reflection
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

        val vx = BuilderContext.get().extractVertexOrDefault { InternalGlAccessor.vx(Vertex()) }

        graphBuilderValidator.validateHandler(vx)

        vx.handler = Handler<Payload, HandlerResult> { pld -> handler(pld) } as Handler<Any?, Any?>
        return GlMergerBuilder(vx)
    }

    protected fun <HandlerResult> suspendHandler(handler: suspend Payload.() -> HandlerResult):
            MergerBuilder<Payload, HandlerResult> {

        return handler {
            future {
                handler()
            }
        }
    }

    protected fun router(router: Payload.() -> Enum<*>): Vertex {
        val vx = BuilderContext.get().extractVertexOrDefault { InternalGlAccessor.vx(Vertex()) }

        graphBuilderValidator.validateRouter(vx)

        vx.router = Router<Payload> { pld -> router(pld) } as Router<Any?>
        return vx.vertex
    }

    protected fun <SubgraphPayload: Any> subgraph(
            subgraphPayloadType: KClass<SubgraphPayload>,
            subgraph: Payload.() -> SubgraphPayload): MergerBuilder<Payload, SubgraphPayload> {

        val vx = BuilderContext.get().extractVertexOrDefault { InternalGlAccessor.vx(Vertex()) }

        graphBuilderValidator.validateSubgraph(vx)

        vx.subgraphPayloadType = subgraphPayloadType.java
        vx.subgraphPayloadBuilder = Subgraph<Payload, SubgraphPayload> {pld -> subgraph(pld) } as Subgraph<Any?, Any?>
        return GlMergerBuilder(vx)
    }
}