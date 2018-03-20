package ru.fix.completable.reactor.graph;

import ru.fix.completable.reactor.graph.internal.*;
import ru.fix.completable.reactor.graph.runtime.GlGraph;
import ru.fix.completable.reactor.graph.runtime.GlVertex;

public abstract class Graph<Payload> implements Graphable {

    // Field accessed via reflection by field name
    private GlGraph graph = new GlGraph();

    private GraphBuilderValidator graphBuilderValidator = new GraphBuilderValidator();

    protected final PayloadTransitionBuilder<Payload> payload() {
        return new GlPayloadImpl<>(graph);
    }

    protected final CoordinatesBuilder coordinates() {
        return new GlCoordinatesBuilder();
    }

    protected <HandlerResult> MergerBuilder<Payload, HandlerResult> handler(
            NoArgHandler<HandlerResult> handler) {
        return handler(payload -> handler.handle());
    }

    protected <HandlerResult> MergerBuilder<Payload, HandlerResult> handler(Handler<Payload, HandlerResult> handler) {
        Vertex vertex = new Vertex();
        GlVertex vx = InternalGlAccessor.vx(vertex);

        graphBuilderValidator.validateHandler(vx);

        vx.handler = (Handler<Object, Object>) handler;
        return new GlMergerBuilder<>(vertex);
    }

    protected Vertex router(Router<Payload> router) {
        Vertex vertex = new Vertex();
        GlVertex vx = InternalGlAccessor.vx(vertex);

        graphBuilderValidator.validateRouter(vx);

        vx.router = (Router<Object>) router;
        return vertex;
    }

    protected Vertex mutator(Mutator<Payload> mutator) {
        return router(payload -> {
            mutator.mutate(payload);
            return GlEmptyMerger.EmptyMergerStatusEnum.EMPTY_MERGER_STATUS;
        });
    }

    protected <SubgraphPayload> MergerBuilder<Payload, SubgraphPayload> subgraph(
            Class<SubgraphPayload> subgraphPayloadType,
            Subgraph<Payload, SubgraphPayload> subgraph) {

        Vertex vertex = new Vertex();
        GlVertex vx = InternalGlAccessor.vx(vertex);

        graphBuilderValidator.validateSubgraph(vx);

        vx.subgraphPayloadType = subgraphPayloadType;
        vx.subgraphPayloadBuilder = (Subgraph<Object, Object>) subgraph;
        return new GlMergerBuilder<>(vertex);
    }


}
