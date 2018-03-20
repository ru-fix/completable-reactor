package ru.fix.completable.reactor.graph

import ru.fix.completable.reactor.graph.internal.*
import ru.fix.completable.reactor.graph.runtime.GlGraph
import java.util.concurrent.CompletableFuture

abstract class Graph<Payload> : Graphable {

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

    protected fun <HandlerResult> handler(
            handler: NoArgHandler<HandlerResult>): MergerBuilder<Payload, HandlerResult> {

        return handler(object: Handler<Payload, HandlerResult>{
            override fun handle(payload: Payload): CompletableFuture<HandlerResult> {
                return handler.handle()
            }
        })
    }

    protected fun <HandlerResult> handler(handler: Handler<Payload, HandlerResult>):
            MergerBuilder<Payload, HandlerResult> {

        val vertex = Vertex()
        val vx = InternalGlAccessor.vx(vertex)
        graph.vertices.add(vx)

        graphBuilderValidator.validateHandler(vx)

        vx.handler = handler as Handler<Any?, Any?>
        return GlMergerBuilder(vertex)
    }

    protected fun router(router: Router<Payload>): Vertex {
        val vertex = Vertex()
        val vx = InternalGlAccessor.vx(vertex)
        graph.vertices.add(vx)

        graphBuilderValidator.validateRouter(vx)

        vx.router = router as Router<Any?>
        return vertex
    }

    protected fun mutator(mutator: Mutator<Payload>): Vertex {
        return router(object : Router<Payload> {
            override fun route(payload: Payload): Enum<*> {
                mutator.mutate(payload)
                return GlEmptyMerger.EmptyMergerStatusEnum.EMPTY_MERGER_STATUS
            }
        })
    }

    protected fun <SubgraphPayload> subgraph(
            subgraphPayloadType: Class<SubgraphPayload>,
            subgraph: Subgraph<Payload, SubgraphPayload>): MergerBuilder<Payload, SubgraphPayload> {

        val vertex = Vertex()
        val vx = InternalGlAccessor.vx(vertex)
        graph.vertices.add(vx)

        graphBuilderValidator.validateSubgraph(vx)

        vx.subgraphPayloadType = subgraphPayloadType
        vx.subgraphPayloadBuilder = subgraph as Subgraph<Any?, Any?>
        return GlMergerBuilder(vertex)
    }
}
