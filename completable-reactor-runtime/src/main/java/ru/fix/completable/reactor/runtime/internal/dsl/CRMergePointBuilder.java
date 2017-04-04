package ru.fix.completable.reactor.runtime.internal.dsl;

import lombok.val;
import ru.fix.completable.reactor.runtime.dsl.*;
import ru.fix.completable.reactor.runtime.internal.CRReactorGraph;
import ru.fix.completable.reactor.runtime.internal.ReactorReflector;

/**
 * @author Kamil Asfandiyarov
 */
public class CRMergePointBuilder<PayloadType> implements MergePointBuilder<PayloadType> {

    final CRReactorGraph<PayloadType> graph;
    final CRReactorGraph.MergePoint mergePoint;

    CRMergePointBuilder(CRReactorGraph<PayloadType> graph, CRReactorGraph.MergePoint graphMergePoint) {
        this.graph = graph;
        this.mergePoint = graphMergePoint;
    }


    static <PayloadType> CRMergePointBuilder<PayloadType> startBuildingMergePoint(
            CRReactorGraph<PayloadType> graph,
            Processor<? super PayloadType> processor) {

        CRProcessor crProcessor = (CRProcessor) processor;
        graph.ensureProcessingItemRegistered(crProcessor);

        if (((CRProcessor) processor).getProcessorDescription().merger == null) {
            throw new IllegalArgumentException(String.format(
                    "Merge point added to processor %s does not have merger.\n" +
                            "To participate in transitions processor merge point should have merger.\n" +
                            "Use 'withMerger' clause to attach merger to processor.",
                    ((CRProcessor) processor).getDebugName()));
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

        return new CRMergePointBuilder<>(graph, graphMergePoint);
    }

    static <PayloadType> CRMergePointBuilder<PayloadType> startBuildingMergePoint(
            CRReactorGraph<PayloadType> graph,
            MergePoint<PayloadType> mergePoint) {

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

        return new CRMergePointBuilder<>(graph, graphMergePoint);
    }


    @Override
    public MergePointTransition<PayloadType> on(Enum<?>... mergeStatuses) {
        if (mergeStatuses.length <= 0) {
            throw new IllegalArgumentException("You should provide at least one merge status or use 'onAny' predicate.");
        }

        val onSource = ReactorReflector.getMethodInvocationPoint().orElse(null);

        return new CRMergePointTransition<>(graph, mergePoint, () -> {
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

        return new CRMergePointTransition<>(graph, mergePoint, () ->
                new CRReactorGraph.Transition()
                        .setOnAny(true)
                        .setTransitionOnAnySource(onAnySource));
    }

    @Override
    public MergeGroupBuilder<PayloadType> mergeGroup() {
        return new CRMergeGroupBuilder<>(graph);
    }

    @Override
    public MergePointBuilder<PayloadType> mergePoint(Processor<? super PayloadType> processor) {
        return CRMergePointBuilder.startBuildingMergePoint(graph, processor);
    }

    @Override
    public MergePointBuilder<PayloadType> mergePoint(MergePoint<PayloadType> mergePoint) {
        return CRMergePointBuilder.startBuildingMergePoint(graph, mergePoint);
    }

    @Override
    public Coordinates<PayloadType> coordinates() {
        return new CRCoordinates<>(graph);
    }

}
