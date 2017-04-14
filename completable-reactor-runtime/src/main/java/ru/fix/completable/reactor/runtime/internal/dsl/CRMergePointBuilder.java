package ru.fix.completable.reactor.runtime.internal.dsl;

import lombok.val;
import ru.fix.completable.reactor.runtime.dsl.*;
import ru.fix.completable.reactor.runtime.internal.CRReactorGraph;
import ru.fix.completable.reactor.runtime.internal.ReactorReflector;

/**
 * @author Kamil Asfandiyarov
 */
public class CRMergePointBuilder<PayloadType> implements MergePointBuilder<PayloadType> {

    final BuilderContext<PayloadType> builderContext;
    final CRReactorGraph<PayloadType> graph;
    final CRReactorGraph.MergePoint mergePoint;

    CRMergePointBuilder(BuilderContext<PayloadType> builderContext, CRReactorGraph.MergePoint graphMergePoint) {
        this.builderContext = builderContext;
        this.graph = builderContext.graph;
        this.mergePoint = graphMergePoint;
    }

    static <PayloadType> CRMergePointBuilder<PayloadType> startBuildingMergePoint(
            BuilderContext<PayloadType> builderContext,
            Processor<? super PayloadType> processor) {

        val graph = builderContext.graph;

        CRProcessor crProcessor = (CRProcessor) processor;
        graph.ensureProcessingItemRegistered(crProcessor);

        if (crProcessor.getProcessorDescription().merger == null) {
            throw new IllegalArgumentException(String.format(
                    "Merge point added to processor %s does not have merger.\n" +
                            "To participate in transitions processor merge point should have merger.\n" +
                            "Use 'withMerger' clause to attach merger to processor.",
                    crProcessor.getDebugName()));
        }

        if (graph.getMergePoints().stream()
                .filter(graphMergePoint -> graphMergePoint.getType() == CRReactorGraph.MergePoint.Type.PROCESSOR)
                .anyMatch(graphMergePoint -> graphMergePoint.getProcessor().equals(processor))) {

            throw new IllegalArgumentException(String.format("Processor merge point for processor %s already registered.", ((CRProcessor)
                    processor).getDebugName()));
        }

        CRReactorGraph.MergePoint graphMergePoint = new CRReactorGraph.MergePoint()
                .setType(CRReactorGraph.MergePoint.Type.PROCESSOR)
                .setProcessor(crProcessor);
        graph.getMergePoints().add(graphMergePoint);

        return new CRMergePointBuilder<>(builderContext, graphMergePoint);
    }

    static <PayloadType> CRMergePointBuilder<PayloadType> startBuildingMergePoint(
            BuilderContext<PayloadType> builderContext,
            Subgraph<? super PayloadType> subgraph) {

        val graph = builderContext.graph;

        CRSubgraph crSubgraph = (CRSubgraph) subgraph;
        graph.ensureProcessingItemRegistered(crSubgraph);

        if (crSubgraph.getSubgraphDescription().merger == null) {
            throw new IllegalArgumentException(String.format(
                    "Merge point added to subgraph %s does not have merger.\n" +
                            "To participate in transitions subgraph merge point should have merger.\n" +
                            "Use 'withMerger' clause to attach merger to processor.",
                    crSubgraph.getDebugName()));
        }

        if (graph.getMergePoints().stream()
                .filter(graphMergePoint -> graphMergePoint.getType() == CRReactorGraph.MergePoint.Type.SUBGRAPH)
                .anyMatch(graphMergePoint -> graphMergePoint.getSubgraph().equals(crSubgraph))) {

            throw new IllegalArgumentException(String.format("Subgraph merge point %s already registered.", crSubgraph.getDebugName()));
        }

        CRReactorGraph.MergePoint graphMergePoint = new CRReactorGraph.MergePoint()
                .setType(CRReactorGraph.MergePoint.Type.SUBGRAPH)
                .setSubgraph(crSubgraph);
        graph.getMergePoints().add(graphMergePoint);

        return new CRMergePointBuilder<>(builderContext, graphMergePoint);
    }


    static <PayloadType> CRMergePointBuilder<PayloadType> startBuildingMergePoint(
            BuilderContext<PayloadType> builderContext,
            MergePoint<PayloadType> mergePoint) {

        val graph = builderContext.graph;

        CRMergePoint crMergePoint = (CRMergePoint) mergePoint;
        graph.ensureProcessingItemRegistered(crMergePoint);

        if (((CRMergePoint) mergePoint).getMergePointDescription().merger == null) {
            throw new IllegalArgumentException(String.format(
                    "Added merge point %s does not have merger.\n" +
                            "To participate in transitions merge point should have merger.\n" +
                            "Use 'withMerger' clause to attach merger to merge point.",
                    ((CRMergePoint) mergePoint).getDebugName()));
        }

        if (graph.getMergePoints().stream()
                .filter(graphMergePoint -> graphMergePoint.getType() == CRReactorGraph.MergePoint.Type.DETACHED)
                .anyMatch(graphMergePoint -> graphMergePoint.getMergePoint().equals(crMergePoint))) {

            throw new IllegalArgumentException(String.format("Detached merge point %s already registered.", crMergePoint.getDebugName()));
        }

        CRReactorGraph.MergePoint graphMergePoint = new CRReactorGraph.MergePoint()
                .setType(CRReactorGraph.MergePoint.Type.DETACHED)
                .setMergePoint(crMergePoint);
        graph.getMergePoints().add(graphMergePoint);

        return new CRMergePointBuilder<>(builderContext, graphMergePoint);
    }


    @Override
    public MergePointTransition<PayloadType> on(Enum<?>... mergeStatuses) {
        if (mergeStatuses.length <= 0) {
            throw new IllegalArgumentException("You should provide at least one merge status or use 'onAny' predicate.");
        }

        val onSource = ReactorReflector.getMethodInvocationPoint().orElse(null);

        return new CRMergePointTransition<>(builderContext, mergePoint, () -> {
            val transition = new CRReactorGraph.Transition(mergeStatuses);
            for (Enum<?> status : mergeStatuses) {
                transition.getTransitionOnStatusSource().put(status.name(), onSource);
            }
            return transition;
        });
    }

    @Override
    public MergePointTransition<PayloadType> onAny() {
        val onAnySource = ReactorReflector.getMethodInvocationPoint().orElse(null);

        return new CRMergePointTransition<>(builderContext, mergePoint, () ->
                new CRReactorGraph.Transition()
                        .setOnAny(true)
                        .setTransitionOnAnySource(onAnySource));
    }


    @Override
    public MergePointBuilder<PayloadType> mergePoint(Processor<? super PayloadType> processor) {
        return CRMergePointBuilder.startBuildingMergePoint(builderContext, processor);
    }

    @Override
    public MergePointBuilder<PayloadType> mergePoint(Subgraph<? super PayloadType> subgraph) {
        return CRMergePointBuilder.startBuildingMergePoint(builderContext, subgraph);
    }

    @Override
    public MergePointBuilder<PayloadType> mergePoint(MergePoint<PayloadType> mergePoint) {
        return CRMergePointBuilder.startBuildingMergePoint(builderContext, mergePoint);
    }

    @Override
    public Coordinates<PayloadType> coordinates() {
        graph.setCoordinatesSource(ReactorReflector.getMethodInvocationPoint().orElse(null));
        return new CRCoordinates<>(builderContext);
    }
}
