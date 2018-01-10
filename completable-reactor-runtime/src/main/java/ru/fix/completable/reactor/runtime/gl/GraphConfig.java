package ru.fix.completable.reactor.runtime.gl;


import ru.fix.completable.reactor.runtime.ReactorGraph;
import ru.fix.completable.reactor.runtime.internal.gl.ConfigContext;
import ru.fix.completable.reactor.runtime.internal.gl.GlReactorGraph;

import java.util.concurrent.CompletableFuture;

public abstract class GraphConfig<Payload> {

    GlReactorGraph<Payload> graph = new GlReactorGraph<>();

    public GlPayload<Payload> payload() {
        throw new UnsupportedOperationException();
    }

    public GlCoordinates coordinates() {
        return new GlCoordinatesImpl();
    }

    public ReactorGraph<Payload> buildGraph() {
        GlReactorGraph<Payload> result = this.graph;
        this.graph = new GlReactorGraph<>();
        return result;
    }

    public <HandlerResult> GlMergerBuilder<Payload, HandlerResult> handler(
            Handler<Payload, HandlerResult> handler) {

        Vertex vertex = ConfigContext.Companion.get().extractVertexOrDefault(new Vertex());
        requireNull(vertex.handler, "handler method used twice on same vertex");
        requireNull(vertex.merger, "handler method used after merger initialization for given vertex");
        requireNull(vertex.router, "handler method used after router initialization for given vertex");
        requireNull(vertex.subgraph, "handler method used after subgraph initialization for given vertex");

        vertex.handler = handler;
        return new GlMergerBuilderImpl<>(vertex);
    }

    public Vertex router(Router<Payload> router) {
        Vertex vertex = ConfigContext.Companion.get().extractVertexOrDefault(new Vertex());
        requireNull(vertex.handler, "router method used after handler initialization for given vertex");
        requireNull(vertex.merger, "router method used after merger initialization for given vertex");
        requireNull(vertex.router, "router method used twice on same vertex");
        requireNull(vertex.subgraph, "router method used after subgraph initialization for given vertex");

        vertex.router = router;
        return vertex;
    }

    public <SubgraphPayload> GlMergerBuilder<Payload, SubgraphPayload> subgraph(
            Class<SubgraphPayload> subgraphPayloadClass) {

        Vertex vertex = ConfigContext.Companion.get().extractVertexOrDefault(new Vertex());
        requireNull(vertex.handler, "subgraph method used after handler initialization for given vertex");
        requireNull(vertex.merger, "subgraph method used after merger initialization for given vertex");
        requireNull(vertex.router, "subgraph method used after router initialization for given vertex");
        requireNull(vertex.subgraph, "subgrah method used twice on same vertex");

        vertex.subgraph = subgraphPayloadClass;
        return new GlMergerBuilderImpl<>(vertex);
    }

    public <HandlerResult> GlMergerBuilder<Payload, HandlerResult> handlerSync(
            SyncHandler<Payload, HandlerResult> handlerSync) {
        return handler(payload -> CompletableFuture.supplyAsync(() -> handlerSync.handle(payload)));
    }

    public <HandlerResult> GlMergerBuilder<Payload, HandlerResult> handlerSync(
            String title,
            SyncHandler<Payload, HandlerResult> handlerSync) {
        return handler(payload -> CompletableFuture.supplyAsync(() -> handlerSync.handle(payload)));
    }

    public <HandlerResult> GlMergerBuilder<Payload, HandlerResult> handlerSync(
            String title,
            String doc,
            SyncHandler<Payload, HandlerResult> handlerSync) {
        return handler(payload -> CompletableFuture.supplyAsync(() -> handlerSync.handle(payload)));
    }

    public <HandlerResult> GlMergerBuilder<Payload, HandlerResult> handlerSync(
            String title,
            String[] docs,
            SyncHandler<Payload, HandlerResult> handlerSync) {
        return handler(payload -> CompletableFuture.supplyAsync(() -> handlerSync.handle(payload)));
    }

    public <HandlerResult> GlMergerBuilder<Payload, HandlerResult> handler(
            String title,
            Handler<Payload, HandlerResult> handler) {
        return handler(handler);
    }

    public <HandlerResult> GlMergerBuilder<Payload, HandlerResult> handler(
            String title,
            String doc,
            Handler<Payload, HandlerResult> handler) {
        return handler(handler);
    }

    public <HandlerResult> GlMergerBuilder<Payload, HandlerResult> handler(
            String title,
            String[] doc,
            Handler<Payload, HandlerResult> handler) {
        return handler(handler);
    }

    public Vertex router(String title, Router<Payload> router) {
        return router(router);
    }

    public Vertex router(String title, String doc, Router<Payload> router) {
        return router(router);
    }

    public Vertex router(String title, String[] docs, Router<Payload> router) {
        return router(router);
    }

    public interface DependencyInjector {
        <T> T inject(String name, Class<T> type);
    }

    private static DependencyInjector dependencyInjector;

    public static void registerDependecnyInjector(DependencyInjector dependencyInjector) {
        GraphConfig.dependencyInjector = dependencyInjector;
    }

    static void requireNull(Object value, String message) {
        if(value != null){
            throw new IllegalStateException(message);
        }
    }
}
