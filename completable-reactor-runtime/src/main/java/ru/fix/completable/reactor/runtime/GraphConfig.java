package ru.fix.completable.reactor.runtime;


import ru.fix.completable.reactor.runtime.gl.*;

public class GraphConfig<Payload> {

    public GlPayload<Payload> payload() {
        throw new UnsupportedOperationException();
    }

    public GlCoordinates coordinates() {
        throw new UnsupportedOperationException();
    }

    public ReactorGraph<Payload> buildGraph() {
        throw new UnsupportedOperationException();
    }

    public <HandlerResult> GlMergerBuilder<Payload, HandlerResult> handler(
            Handler<Payload, HandlerResult> handler) {
        throw new UnsupportedOperationException();
    }

    public <SubgraphPayload> GlMergerBuilder<Payload, SubgraphPayload> subgraph(
            Class<SubgraphPayload> subgraphPayloadClass) {
        throw new UnsupportedOperationException();
    }

    public <HandlerResult> GlMergerBuilder<Payload, HandlerResult> handlerSync(
            SyncHandler<Payload, HandlerResult> handler) {
        throw new UnsupportedOperationException();
    }

    public <HandlerResult> GlMergerBuilder<Payload, HandlerResult> handler(
            String title,
            Handler<Payload, HandlerResult> handler) {
        throw new UnsupportedOperationException();
    }

    public <HandlerResult> GlMergerBuilder<Payload, HandlerResult> handler(
            String title,
            String doc,
            Handler<Payload, HandlerResult> handler) {
        throw new UnsupportedOperationException();
    }

    public <HandlerResult> GlMergerBuilder<Payload, HandlerResult> handler(
            String title,
            String[] doc,
            Handler<Payload, HandlerResult> handler) {
        throw new UnsupportedOperationException();
    }


    public Vertex router(Router<Payload> router) {
        throw new UnsupportedOperationException();
    }

    public Vertex router(String title, Router<Payload> router) {
        throw new UnsupportedOperationException();
    }

    public Vertex router(String title, String[] docs, Router<Payload> router) {
        throw new UnsupportedOperationException();
    }
}
