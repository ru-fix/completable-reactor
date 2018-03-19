package ru.fix.completable.reactor.graph;

import ru.fix.completable.reactor.graph.internal.*;

import java.util.concurrent.CompletableFuture;

public abstract class Graph<Payload> implements Graphable {

    // Field name used via reflection
    private GlGraph graph = new GlGraph();

    {
        // Deliberately leaking reference to ThreadLocal context.
        // Graph construction is happening in single thread
        BuilderContext.get().setGraph(this, graph);
    }

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
        GlVertex vx = BuilderContext.get().extractVertexOrDefault(() -> InternalGlAccessor.vx(new Vertex()));

        graphBuilderValidator.validateHandler(vx);

        vx.handler = (Handler<Object, Object>) handler;
        return new GlMergerBuilder<>(vx);
    }

    protected Vertex router(Router<Payload> router) {
        GlVertex vx = BuilderContext.Companion.get().extractVertexOrDefault(() -> InternalGlAccessor.vx(new Vertex()));

        graphBuilderValidator.validateRouter(vx);

        vx.router = (Router<Object>) router;
        return vx.vertex;
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

        GlVertex vx = BuilderContext.Companion.get().extractVertexOrDefault(() -> InternalGlAccessor.vx(new Vertex()));

        graphBuilderValidator.validateSubgraph(vx);

        vx.subgraphPayloadType = subgraphPayloadType;
        vx.subgraphPayloadBuilder = (Subgraph<Object, Object>) subgraph;
        return new GlMergerBuilder<>(vx);
    }


}
