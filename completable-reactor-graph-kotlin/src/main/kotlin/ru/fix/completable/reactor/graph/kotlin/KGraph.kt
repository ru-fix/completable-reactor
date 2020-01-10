package ru.fix.completable.reactor.graph.kotlin

import kotlinx.coroutines.*
import kotlinx.coroutines.future.future
import org.slf4j.LoggerFactory
import ru.fix.completable.reactor.graph.*
import ru.fix.completable.reactor.graph.internal.*
import ru.fix.completable.reactor.graph.kotlin.internal.DslMergerBuilder
import ru.fix.completable.reactor.graph.runtime.RuntimeGraph
import ru.fix.completable.reactor.graph.runtime.RuntimeVertex
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ForkJoinPool
import kotlin.coroutines.CoroutineContext
import kotlin.reflect.KClass
//TODO docs how to use
open class KGraph<
        Submit : ru.fix.completable.reactor.graph.Submit<Request, Response>,
        Request,
        Response,
        Context> : Graphable {

    companion object {
        private val log = LoggerFactory.getLogger(KGraph::class.java)
        //TODO: add ability to use different pool for completable reactor, update default value accordingly.
        /**
         * By default, Completable Reactor uses Common pool for execution.
         * suspendHandler works as a bridge to Kotlin coroutines.
         * This bridge will use [defaultCoroutineScope] scope to execute coroutines.
         * You can redefine defaultCoroutineScope globally.
         */
        var defaultCoroutineScope: CoroutineScope = object : CoroutineScope {

            override val coroutineContext: CoroutineContext
                get() = ForkJoinPool.commonPool().asCoroutineDispatcher() +
                        SupervisorJob() +
                        CoroutineExceptionHandler { context, thr ->
                            log.error(context.toString(), thr)
                        }
        }
    }

    // Field accessed via reflection by field name
    private val graph = RuntimeGraph()

    init {
        // Deliberately leaking reference to ThreadLocal context.
        // Graph construction is happening in single thread
        BuilderContext.get().setGraph(this, graph)
    }

    private val graphBuilderValidator = GraphBuilderValidator()

    protected fun payload(): PayloadTransitionBuilder<Payload<Request,Response,Context>> {
        return DslPayloadImpl(graph)
    }

    protected fun coordinates(): CoordinatesBuilder {
        return DslCoordinatesBuilder()
    }


    protected fun <HandlerResult> handler(handler: Payload<Request, Response, Context>.() -> CompletableFuture<HandlerResult>):
            MergerBuilder<Payload<Request, Response, Context>, HandlerResult> {

        val vertex = Vertex()
        val vx = InternalDslAccessor.vx(vertex)
        graph.vertices.add(vx)

        graphBuilderValidator.validateHandler(vx)

        vx.handler = object : Handler<Payload<Request, Response, Context>, HandlerResult> {
            override fun handle(payload: Payload<Request, Response, Context>): CompletableFuture<HandlerResult> {
                return handler(payload)
            }
        } as Handler<Any?, Any?>

        return DslMergerBuilder(vertex)
    }

    /**
     * Builds new handler based on coroutine.
     * Coroutine context is inherited from a [coroutineScope].
     * By default [KGraph.defaultCoroutineScope] will be used.
     * Default [KGraph.defaultCoroutineScope] could be redefined by user globally.
     */
    protected fun <HandlerResult> suspendHandler(
            coroutineScope: CoroutineScope? = null,
            handler: suspend Payload<Request, Response, Context>.() -> HandlerResult):
            MergerBuilder<Payload<Request,Response,Context>, HandlerResult> {

        return handler {
            (coroutineScope ?: defaultCoroutineScope).future {
                handler()
            }
        }
    }

    protected fun mutator(mutator: Payload<Request,Response,Context>.() -> Unit): Vertex {
        val vertex = Vertex()
        val vx = InternalDslAccessor.vx(vertex)
        graph.vertices.add(vx)

        graphBuilderValidator.validateMutator(vx)

        vx.handler = RuntimeEmptyHandler()
        @Suppress("UNCHECKED_CAST")
        vx.merger = RuntimeMutatorMerger(
                object : Mutator<Payload<Request,Response,Context>> {
                    override fun mutate(payload: Payload<Request,Response,Context>) {
                        mutator(payload)
                    }
                } as Mutator<Any?>)
        vx.type = RuntimeVertex.Type.Mutator

        return vertex
    }

    protected fun router(router: Payload<Request,Response,Context>.() -> Enum<*>): Vertex {
        val vertex = Vertex()
        val vx = InternalDslAccessor.vx(vertex)
        graph.vertices.add(vx)

        graphBuilderValidator.validateRouter(vx)

        vx.handler = RuntimeEmptyHandler()
        vx.merger = RuntimeRouterMerger(
                object : Router<Payload<Request,Response,Context>> {
                    override fun route(payload: Payload<Request,Response,Context>): Enum<*> {
                        return router(payload)
                    }
                } as Router<Any?>
        )
        vx.type = RuntimeVertex.Type.Router

        return vertex
    }

    protected fun <SubgraphRequest : Any, SubgraphResponse : Any> subgraph(
            subgraphSubmitType: KClass<out ru.fix.completable.reactor.graph.Submit<SubgraphRequest, SubgraphResponse>>,
            subgraph: Payload<Request,Response,Context>.() -> SubgraphRequest):
            MergerBuilder<Payload<Request,Response,Context>, SubgraphResponse> {

        val vertex = Vertex()
        val vx = InternalDslAccessor.vx(vertex)
        graph.vertices.add(vx)

        graphBuilderValidator.validateSubgraph(vx)

        vx.subgraphSubmitType = subgraphSubmitType.java
        vx.subgraphRequestBuilder = object : Subgraph<Payload<Request,Response,Context>, SubgraphRequest> {
            override fun subgraph(payload: Payload<Request,Response,Context>): SubgraphRequest {
                return subgraph(payload)
            }
        } as Subgraph<Any?, Any?>

        return DslMergerBuilder(vertex)
    }
}