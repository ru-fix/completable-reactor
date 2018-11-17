package ru.fix.completable.reactor.graph.kotlin

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.future.future
import ru.fix.completable.reactor.graph.*
import ru.fix.completable.reactor.graph.internal.*
import ru.fix.completable.reactor.graph.kotlin.internal.DslMergerBuilder
import ru.fix.completable.reactor.graph.runtime.RuntimeGraph
import ru.fix.completable.reactor.graph.runtime.RuntimeVertex
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ForkJoinPool
import kotlin.reflect.KClass

open class Graph<Payload> : Graphable {

    companion object {
        //TODO: add ability to use different pool for completable reactor, update default value accordingly.
        /**
         * By default, Completable Reactor uses Common pool for execution.
         * suspendHandler works as a bridge to Kotlin coroutines.
         * This bridge will use [defaultCoroutineScope] scope to execute coroutines.
         * You can redefine defaultCoroutineScope globally.
         */
        var defaultCoroutineScope: CoroutineScope = CoroutineScope(ForkJoinPool.commonPool().asCoroutineDispatcher())
    }

    // Field accessed via reflection by field name
    private val graph = RuntimeGraph()

    init {
        // Deliberately leaking reference to ThreadLocal context.
        // Graph construction is happening in single thread
        BuilderContext.get().setGraph(this, graph)
    }

    private val graphBuilderValidator = GraphBuilderValidator()

    protected fun payload(): PayloadTransitionBuilder<Payload> {
        return DslPayloadImpl(graph)
    }

    protected fun coordinates(): CoordinatesBuilder {
        return DslCoordinatesBuilder()
    }


    protected fun <HandlerResult> handler(handler: Payload.() -> CompletableFuture<HandlerResult>):
            MergerBuilder<Payload, HandlerResult> {

        val vertex = Vertex()
        val vx = InternalDslAccessor.vx(vertex)
        graph.vertices.add(vx)

        graphBuilderValidator.validateHandler(vx)

        @Suppress("UNCHECKED_CAST")
        vx.handler = object : Handler<Payload, HandlerResult> {
            override fun handle(payload: Payload): CompletableFuture<HandlerResult> {
                return handler(payload)
            }
        } as Handler<Any?, Any?>

        return DslMergerBuilder(vertex)
    }

    /**
     * Builds new handler based on coroutine.
     * Coroutine context is inherited from a [coroutineScope].
     * By default [Graph.defaultCoroutineScope] will be used.
     * Default [Graph.defaultCoroutineScope] could be redefined by user globally.
     */
    protected fun <HandlerResult> suspendHandler(coroutineScope: CoroutineScope? = null, handler: suspend Payload.() -> HandlerResult):
            MergerBuilder<Payload, HandlerResult> {

        return handler {
            (coroutineScope ?: defaultCoroutineScope).future {
                handler()
            }
        }
    }

    protected fun mutator(mutator: Payload.() -> Unit): Vertex {
        val vertex = Vertex()
        val vx = InternalDslAccessor.vx(vertex)
        graph.vertices.add(vx)

        graphBuilderValidator.validateMutator(vx)

        vx.handler = RuntimeEmptyHandler()
        @Suppress("UNCHECKED_CAST")
        vx.merger = RuntimeMutatorMerger(
                object : Mutator<Payload> {
                    override fun mutate(payload: Payload) {
                        mutator(payload)
                    }
                } as Mutator<Any?>)
        vx.type = RuntimeVertex.Type.Mutator

        return vertex
    }

    protected fun router(router: Payload.() -> Enum<*>): Vertex {
        val vertex = Vertex()
        val vx = InternalDslAccessor.vx(vertex)
        graph.vertices.add(vx)

        graphBuilderValidator.validateRouter(vx)

        vx.handler = RuntimeEmptyHandler()
        @Suppress("UNCHECKED_CAST")
        vx.merger = RuntimeRouterMerger(
                object : Router<Payload> {
                    override fun route(payload: Payload): Enum<*> {
                        return router(payload)
                    }
                } as Router<Any?>
        )
        vx.type = RuntimeVertex.Type.Router

        return vertex
    }

    protected fun <SubgraphPayload : Any> subgraph(
            subgraphPayloadType: KClass<SubgraphPayload>,
            subgraph: Payload.() -> SubgraphPayload): MergerBuilder<Payload, SubgraphPayload> {

        val vertex = Vertex()
        val vx = InternalDslAccessor.vx(vertex)
        graph.vertices.add(vx)

        graphBuilderValidator.validateSubgraph(vx)

        vx.subgraphPayloadType = subgraphPayloadType.java
        @Suppress("UNCHECKED_CAST")
        vx.subgraphPayloadBuilder = object : Subgraph<Payload, SubgraphPayload> {
            override fun subgraph(payload: Payload): SubgraphPayload {
                return subgraph(payload)
            }
        } as Subgraph<Any?, Any?>

        return DslMergerBuilder(vertex)
    }
}