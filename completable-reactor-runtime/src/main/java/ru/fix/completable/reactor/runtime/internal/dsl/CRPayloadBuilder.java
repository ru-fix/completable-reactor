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
        if (graph.getStartPoint().getProcessingItems().contains(processingItem)) {
            throw new IllegalArgumentException(String.format(
                    "Could not add twice same processing item to handle payload." +
                            " Registered processors: %s. New processors: %s.",
                    graph.getStartPoint().getProcessingItems().stream()
                            .map(CRProcessingItem::getDebugName)
                            .collect(Collectors.joining(",")),
                    processingItem.getDebugName()));
        }

        graph.getStartPoint().getProcessingItems().add(processingItem);
    }


    @Override
    public PayloadBuilder<PayloadType> handle(Processor<? super PayloadType> processor) {
        CRProcessor<?> crProcessor = (CRProcessor<?>) processor;
        crProcessor.setName(builderContext.resolveConfigField(processor));
        graph.ensureProcessingItemRegistered(crProcessor);

        addToStartPoint(crProcessor);
        return this;
    }

    @Override
    public PayloadBuilder<PayloadType> handle(Subgraph<? super PayloadType> subgraph) {
        CRSubgraph<?> crSubgraph = (CRSubgraph<?>) subgraph;
        crSubgraph.setName(builderContext.resolveConfigField(subgraph));
        graph.ensureProcessingItemRegistered(crSubgraph);

        addToStartPoint(crSubgraph);
        return this;
    }

    @Override
    public PayloadBuilder<PayloadType> merge(MergePoint<? super PayloadType> mergePoint) {
        CRMergePoint<?> crMergePoint = (CRMergePoint<?>) mergePoint;
        crMergePoint.setName(builderContext.resolveConfigField(mergePoint));
        graph.ensureProcessingItemRegistered(crMergePoint);

        addToStartPoint(crMergePoint);
        return this;
    }

    @Override
    public MergePointBuilder<PayloadType> mergePoint(Processor<? super PayloadType> processor) {
        CRProcessor<?> crProcessor = (CRProcessor<?>) processor;
        crProcessor.setName(builderContext.resolveConfigField(processor));

        return CRMergePointBuilder.startBuildingMergePoint(builderContext, processor);
    }

    @Override
    public MergePointBuilder<PayloadType> mergePoint(Subgraph<? super PayloadType> subgraph) {
        CRSubgraph<?> crSubgraph = (CRSubgraph<?>) subgraph;
        crSubgraph.setName(builderContext.resolveConfigField(subgraph));

        return CRMergePointBuilder.startBuildingMergePoint(builderContext, subgraph);
    }

    @Override
    public MergePointBuilder<PayloadType> mergePoint(MergePoint<? super PayloadType> mergePoint) {
        CRMergePoint<?> crMergePoint = (CRMergePoint<?>) mergePoint;
        crMergePoint.setName(builderContext.resolveConfigField(mergePoint));

        return CRMergePointBuilder.startBuildingMergePoint(builderContext, mergePoint);
    }

}
