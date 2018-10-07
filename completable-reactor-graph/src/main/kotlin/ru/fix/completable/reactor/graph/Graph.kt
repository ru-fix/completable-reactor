package ru.fix.completable.reactor.graph

import ru.fix.completable.reactor.graph.internal.*
import ru.fix.completable.reactor.graph.runtime.GlGraph
import java.util.concurrent.CompletableFuture

/**
 * Base class for Completable Reactor Graphs.
 *
 * Provides DSL builder methods to create Graph.
 *
 * ```
 * class MyGraph extends Graph<PayloadType> {
 *      Vertex vx1 = handler(...).withMerger(...);
 *      Vertex vx2 = router(...);
 *      ...
 *      {
 *          payload().handleBy(vx1);
 *          vx1.onAny().handleBy(vx2);
 *          vx2.onAny().complete();
 *
 *          coordinates()
 *              .vx(vx1, ...);
 *      }
 * }
 * ```
 */
abstract class Graph<Payload> : Graphable {

    // Field accessed via reflection by field name
    private val graph = GlGraph()

    init {
        // Deliberately leaking reference to ThreadLocal context.
        // Graph construction is happening in single thread
        BuilderContext.get().setGraph(this, graph)
    }

    private val graphBuilderValidator = GraphBuilderValidator()

    /**
     * Specifies StartPoint of the graph. Can be used to create transitions from StartPoint to Vertices.
     */
    protected fun payload(): PayloadTransitionBuilder<Payload> {
        return GlPayloadImpl(graph)
    }

    /**
     * Specifies position of graph nodes for visualization purpose.
     */
    protected fun coordinates(): CoordinatesBuilder {
        return GlCoordinatesBuilder()
    }

    /**
     * Creates vertex based on async method invocation.
     */
    protected fun <HandlerResult> handler(
            handler: NoArgHandler<HandlerResult>): MergerBuilder<Payload, HandlerResult> {

        return handler(object: Handler<Payload, HandlerResult>{
            override fun handle(payload: Payload): CompletableFuture<HandlerResult> {
                return handler.handle()
            }
        })
    }

    /**
     * Creates vertex based on async function.
     * Handler extract required data from payload.
     * Asynchronously invokes functions and passing data from payload as arguments.
     * Then waits the function to complete.
     * Then Handler pass function result and payload to to Merger if one was specified during graph construction.
     */
    protected fun <HandlerResult> handler(handler: Handler<Payload, HandlerResult>):
            MergerBuilder<Payload, HandlerResult> {

        val vertex = Vertex()
        val vx = InternalGlAccessor.vx(vertex)
        graph.vertices.add(vx)

        graphBuilderValidator.validateHandler(vx)

        vx.handler = handler as Handler<Any?, Any?>
        return GlMergerBuilder(vertex)
    }

    /**
     * Creates vertex based on sync method invocation.
     */
    protected fun router(router: Router<Payload>): Vertex {
        val vertex = Vertex()
        val vx = InternalGlAccessor.vx(vertex)
        graph.vertices.add(vx)

        graphBuilderValidator.validateRouter(vx)

        vx.router = router as Router<Any?>
        vx.isRoutable = true
        return vertex
    }

    /**
     * Creates vertex based on sync method invocation.
     */
    protected fun mutator(mutator: Mutator<Payload>): Vertex {
        return router(object : Router<Payload> {
            override fun route(payload: Payload): Enum<*> {
                mutator.mutate(payload)
                return GlEmptyMerger.EmptyMergerStatusEnum.EMPTY_MERGER_STATUS
            }
        })
    }

    /**
     * Creates vertex based on subgraph invocation.
     */
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
