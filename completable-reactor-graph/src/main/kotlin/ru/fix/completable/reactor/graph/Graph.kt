package ru.fix.completable.reactor.graph

import ru.fix.completable.reactor.graph.internal.*
import ru.fix.completable.reactor.graph.runtime.GlGraph

abstract class Graph<Payload> : Graphable {

    // Field accessed via reflection by field name
    private val graph = GlGraph()

    private val graphBuilderValidator = GraphBuilderValidator()

    protected fun payload(): PayloadTransitionBuilder<Payload> {
        return GlPayloadImpl(graph)
    }

    protected fun coordinates(): CoordinatesBuilder {
        return GlCoordinatesBuilder()
    }

    protected fun <HandlerResult> handler(
            handler: NoArgHandler<HandlerResult>): MergerBuilder<Payload, HandlerResult> {
        return handler(Handler{ handler.handle() })
    }

    protected fun <HandlerResult> handler(handler: Handler<Payload, HandlerResult>):
            MergerBuilder<Payload, HandlerResult> {

        val vertex = Vertex()
        val vx = InternalGlAccessor.vx(vertex)

        graphBuilderValidator.validateHandler(vx)

        vx.handler = handler as Handler<Any?, Any?>
        return GlMergerBuilder(vertex)
    }

    protected fun router(router: Router<Payload>): Vertex {
        val vertex = Vertex()
        val vx = InternalGlAccessor.vx(vertex)

        graphBuilderValidator.validateRouter(vx)

        vx.router = router as Router<Any?>
        return vertex
    }

    protected fun mutator(mutator: Mutator<Payload>): Vertex {
        return router(Router { payload ->
            mutator.mutate(payload)
            GlEmptyMerger.EmptyMergerStatusEnum.EMPTY_MERGER_STATUS
        })
    }

    protected fun <SubgraphPayload> subgraph(
            subgraphPayloadType: Class<SubgraphPayload>,
            subgraph: Subgraph<Payload, SubgraphPayload>): MergerBuilder<Payload, SubgraphPayload> {

        val vertex = Vertex()
        val vx = InternalGlAccessor.vx(vertex)

        graphBuilderValidator.validateSubgraph(vx)

        vx.subgraphPayloadType = subgraphPayloadType
        vx.subgraphPayloadBuilder = subgraph as Subgraph<Any?, Any?>
        return GlMergerBuilder(vertex)
    }
}
