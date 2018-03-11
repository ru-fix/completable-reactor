package ru.fix.completable.reactor.graph

import ru.fix.completable.reactor.graph.internal.GlCoordinatesBuilder
import ru.fix.completable.reactor.graph.internal.GlPayloadImpl
import java.util.concurrent.CompletableFuture

open class KGraph<Payload> {
    protected fun <HandlerResult> handler(handler: Payload.() -> CompletableFuture<HandlerResult>):
            KMergerBuilder<Payload, HandlerResult> {

        return KMergerBuilder()
    }

    protected fun router(router: (payload: Payload) -> Enum<*>): Vertex {
        TODO()
    }

    protected fun <SubgraphPayload> subgraph(
            subgraphPayloadType: Class<SubgraphPayload>,
            subgraph: Payload.() -> SubgraphPayload): KMergerBuilder<Payload, SubgraphPayload> {

        TODO()
    }

    protected fun payload(): PayloadTransitionBuilder<Payload> {
        TODO()
//        return GlPayloadImpl<>();
    }

    protected fun coordinates(): CoordinatesBuilder {
        return GlCoordinatesBuilder()
    }
}

class KMergerBuilder<Payload, HandlerResult> {
    fun withMerger(merger: Payload.(handlerResult: HandlerResult) -> Enum<*>): Vertex {
//        if (vx.merger != null) {
//            throw IllegalStateException("withMerger method used twice on same vertex")
//        }
//        vx.merger = merger as Merger<Any?, Any?>
//        return vx.vertex
        TODO()
    }

    fun withoutMerger(): Vertex {
        //return vx.vertex
        TODO()
    }
}

