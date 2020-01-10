package ru.fix.completable.reactor.graph

import ru.fix.completable.reactor.graph.internal.*
import ru.fix.completable.reactor.graph.runtime.RuntimeGraph
import ru.fix.completable.reactor.graph.runtime.RuntimeVertex
import java.util.concurrent.CompletableFuture
//TODO docs
/**
 * Base class for Completable Reactor Graphs.
 *
 * Provides DSL builder methods to create Graph.
 *
 * ```
 * class MyGraph extends JGraph<> {
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
abstract class JGraph<
        Submit: ru.fix.completable.reactor.graph.Submit<Request,Response>,
        Request, 
        Response,
        Context> : Graphable {

    // Field accessed via reflection by field name
    private val graph = RuntimeGraph()

    init {
        // Deliberately leaking reference to ThreadLocal context.
        // Graph construction is happening in single thread
        BuilderContext.get().setGraph(this, graph)
    }

    private val graphBuilderValidator = GraphBuilderValidator()

    /**
     * Specifies StartPoint of the graph. Can be used to create transitions from StartPoint to Vertices.
     */
    protected fun payload(): PayloadTransitionBuilder<Payload<Request, Response, Context>> {
        return DslPayloadImpl(graph)
    }

    /**
     * Specifies position of graph nodes for visualization purpose.
     */
    protected fun coordinates(): CoordinatesBuilder {
        return DslCoordinatesBuilder()
    }

    /**
     * Creates vertex based on async method invocation.
     */
    protected fun <HandlerResult> handler(
            handler: NoArgHandler<HandlerResult>): MergerBuilder<Payload<Request, Response, Context>, HandlerResult> {

        return handler(object : Handler<Payload<Request, Response, Context>, HandlerResult> {
            override fun handle(payload: Payload<Request, Response, Context>): CompletableFuture<HandlerResult> {
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
    protected fun <HandlerResult> handler(handler: Handler<Payload<Request, Response, Context>, HandlerResult>):
            MergerBuilder<Payload<Request, Response, Context>, HandlerResult> {

        val vertex = Vertex()
        val vx = InternalDslAccessor.vx(vertex)
        graph.vertices.add(vx)

        graphBuilderValidator.validateHandler(vx)

        vx.handler = handler as Handler<Any?, Any?>
        return DslMergerBuilder(vertex)
    }

    /**
     * Creates vertex based on sync method invocation.
     */
    protected fun router(router: Router<Payload<Request, Response, Context>>): Vertex {
        val vertex = Vertex()
        val vx = InternalDslAccessor.vx(vertex)
        graph.vertices.add(vx)

        graphBuilderValidator.validateRouter(vx)

        vx.handler = RuntimeEmptyHandler()
        vx.merger = RuntimeRouterMerger(router as Router<Any?>)

        vx.type = RuntimeVertex.Type.Router
        return vertex
    }

    /**
     * Creates vertex based on sync method invocation.
     */
    protected fun mutator(mutator: Mutator<Payload<Request, Response, Context>>): Vertex {
        val vertex = Vertex()
        val vx = InternalDslAccessor.vx(vertex)
        graph.vertices.add(vx)

        graphBuilderValidator.validateMutator(vx)

        vx.handler = RuntimeEmptyHandler()
        vx.merger = RuntimeMutatorMerger(mutator as Mutator<Any?>)

        vx.type = RuntimeVertex.Type.Mutator
        return vertex
    }

    /**
     * Creates vertex based on subgraph invocation.
     */
    protected fun <SubgraphRequest, SubgraphResponse> subgraph(
            subgraphSubmitType: Class<out ru.fix.completable.reactor.graph.Submit<SubgraphRequest, SubgraphResponse>>,
            subgraph: Subgraph<Payload<Request, Response, Context>, SubgraphRequest>):
            MergerBuilder<Payload<Request, Response, Context>, SubgraphResponse> {

        val vertex = Vertex()
        val vx = InternalDslAccessor.vx(vertex)
        graph.vertices.add(vx)

        graphBuilderValidator.validateSubgraph(vx)

        vx.subgraphSubmitType = subgraphSubmitType
        vx.subgraphRequestBuilder = subgraph as Subgraph<Any?, Any?>
        return DslMergerBuilder(vertex)
    }
}
