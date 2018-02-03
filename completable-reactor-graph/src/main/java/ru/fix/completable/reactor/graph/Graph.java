package ru.fix.completable.reactor.graph;

import ru.fix.completable.reactor.graph.internal.*;

import java.util.concurrent.CompletableFuture;

public abstract class Graph<Payload> {

    private GlGraph graph = new GlGraph();

    {
        // Deliberately leaking reference to ThreadLocal context.
        // Graph construction is happening in single thread
        BuilderContext.get().setGraph(this);
    }

    protected PayloadTransitionBuilder<Payload> payload() {
        return new GlPayloadImpl<>(graph);
    }

    protected CoordinatesBuilder coordinates() {
        return new GlCoordinatesBuilder();
    }

    protected <HandlerResult> MergerBuilder<Payload, HandlerResult> handler(
            NoArgHandler<HandlerResult> handler) {

        return handler(payload -> handler.handle());
    }

    protected <HandlerResult> MergerBuilder<Payload, HandlerResult> handler(Handler<Payload, HandlerResult> handler) {

        GlVertex vx = BuilderContext.get().extractVertexOrDefault(new GlVertex(new Vertex()));
        requireNull(vx.handler, "handler method used twice on same vertex");
        requireNull(vx.merger, "handler method used after merger initialization for given vertex");
        requireNull(vx.router, "handler method used after router initialization for given vertex");
        requireNull(vx.subgraph, "handler method used after subgraph initialization for given vertex");

        vx.handler = handler;
        return new GlMergerBuilder<>(vx);
    }

    protected Vertex router(Router<Payload> router) {
        GlVertex vx = BuilderContext.Companion.get().extractVertexOrDefault(new GlVertex(new Vertex()));
        requireNull(vx.handler, "router method used after handler initialization for given vertex");
        requireNull(vx.merger, "router method used after merger initialization for given vertex");
        requireNull(vx.router, "router method used twice on same vertex");
        requireNull(vx.subgraph, "router method used after subgraph initialization for given vertex");

        vx.router = router;
        return vx.vertex;
    }

    protected <SubgraphPayload> MergerBuilder<Payload, SubgraphPayload> subgraph(
            Class<SubgraphPayload> subgraphPayloadClass,
            Subgraph<Payload, SubgraphPayload> subgraph) {

        GlVertex vx = BuilderContext.Companion.get().extractVertexOrDefault(new GlVertex(new Vertex()));
        requireNull(vx.handler, "subgraph method used after handler initialization for given vertex");
        requireNull(vx.merger, "subgraph method used after merger initialization for given vertex");
        requireNull(vx.router, "subgraph method used after router initialization for given vertex");
        requireNull(vx.subgraph, "subgrah method used twice on same vertex");

        vx.subgraph = subgraphPayloadClass;
        vx.subgraphPayloadBuilder = subgraph;
        return new GlMergerBuilder<>(vx);
    }

    protected <HandlerResult> MergerBuilder<Payload, HandlerResult> handlerSync(
            SyncHandler<Payload, HandlerResult> handlerSync) {
        return handler(payload -> CompletableFuture.supplyAsync(() -> handlerSync.handle(payload)));
    }

    protected <HandlerResult> MergerBuilder<Payload, HandlerResult> handlerSync(
            NoArgSyncHandler<HandlerResult> handlerSync) {
        return handler(payload -> CompletableFuture.supplyAsync(handlerSync::handle));
    }

    private static void requireNull(Object value, String message) {
        if (value != null) {
            throw new IllegalStateException(message);
        }
    }

}
