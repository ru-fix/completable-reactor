package ru.fix.completable.reactor.runtime.internal.dsl;

import ru.fix.completable.reactor.runtime.dsl.*;
import ru.fix.completable.reactor.runtime.internal.CRProcessingItem;
import ru.fix.completable.reactor.runtime.internal.CRReactorGraph;

import java.util.stream.Collectors;

/**
 * @author Kamil Asfandiyarov
 */
public class CRPayloadBuilder<PayloadType> implements PayloadBuilder<PayloadType> {

    final BuilderContext<PayloadType> builderContext;
    final CRReactorGraph<PayloadType> graph;

    public CRPayloadBuilder(BuilderContext<PayloadType> builderContext) {
        this.builderContext = builderContext;
        this.graph = builderContext.graph;
    }

    private void addToStartPoint(CRProcessingItem processingItem) {
        if ((graph.getStartPoint().getProcessors().contains(processingItem))) {
            throw new IllegalArgumentException(String.format(
                    "Could not add twice same processing item to handle payload." +
                            " Registered processors: %s. New processors: %s.",
                    graph.getStartPoint().getProcessors().stream()
                            .map(CRProcessingItem::getDebugName)
                            .collect(Collectors.joining(",")),
                    processingItem.getDebugName()));
        }

        graph.getStartPoint().getProcessors().add(processingItem);
    }


    @Override
    public PayloadBuilder<PayloadType> handleBy(Processor<? super PayloadType> processor) {
        CRProcessor<?> crProcessor = (CRProcessor<?>) processor;
        graph.ensureProcessingItemRegistered(crProcessor);

        addToStartPoint(crProcessor);
        return this;
    }

    @Override
    public PayloadBuilder<PayloadType> handleBy(Subgraph<? super PayloadType> subgraph) {
        CRSubgraph<?> crSubgraph = (CRSubgraph<?>) subgraph;
        graph.ensureProcessingItemRegistered(crSubgraph);

        addToStartPoint(crSubgraph);
        return this;
    }

    @Override
    public PayloadBuilder<PayloadType> merge(MergePoint<PayloadType> mergePoint) {
        CRMergePoint<?> crMergePoint = (CRMergePoint<?>) mergePoint;
        graph.ensureProcessingItemRegistered(crMergePoint);

        addToStartPoint(crMergePoint);
        return this;
    }

    @Override
    public MergePointBuilder<PayloadType> mergePoint(Processor<? super PayloadType> processor) {
        return CRMergePointBuilder.startBuildingMergePoint(builderContext, processor);
    }

    @Override
    public MergePointBuilder<PayloadType> mergePoint(MergePoint<PayloadType> mergePoint) {
        return CRMergePointBuilder.startBuildingMergePoint(builderContext, mergePoint);
    }

    @Override
    public MergeGroupBuilder<PayloadType> mergeGroup() {
        return new CRMergeGroupBuilder<>(builderContext);
    }
}
