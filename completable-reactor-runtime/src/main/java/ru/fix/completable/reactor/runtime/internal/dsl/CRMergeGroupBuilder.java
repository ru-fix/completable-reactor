package ru.fix.completable.reactor.runtime.internal.dsl;

import ru.fix.completable.reactor.runtime.dsl.MergeGroupBuilder;
import ru.fix.completable.reactor.runtime.dsl.MergePoint;
import ru.fix.completable.reactor.runtime.dsl.MergePointBuilder;
import ru.fix.completable.reactor.runtime.dsl.Processor;
import ru.fix.completable.reactor.runtime.internal.CRReactorGraph;

/**
 * @author Kamil Asfandiyarov
 */
public class CRMergeGroupBuilder<PayloadType> implements MergeGroupBuilder<PayloadType> {
    final CRReactorGraph<PayloadType> graph;
    final CRReactorGraph.MergeGroup graphMergeGroup;

    public CRMergeGroupBuilder(CRReactorGraph<PayloadType> graph) {
        this.graph = graph;
        this.graphMergeGroup = new CRReactorGraph.MergeGroup();
        graph.getMergeGroups().add(graphMergeGroup);
    }

    @Override

    public MergeGroupBuilder<PayloadType> with(Processor<? super PayloadType> processor) {
        graphMergeGroup.getProcessingItems().add((CRProcessor) processor);
        return this;
    }

    @Override
    public MergeGroupBuilder<PayloadType> with(MergePoint<PayloadType> mergePoint) {
        graphMergeGroup.getProcessingItems().add((CRMergePoint) mergePoint);
        return this;
    }

    @Override
    public MergePointBuilder<PayloadType> mergePoint(Processor<? super PayloadType> processor) {
        return CRMergePointBuilder.startBuildingMergePoint(graph, processor);
    }

    @Override
    public MergePointBuilder<PayloadType> mergePoint(MergePoint<PayloadType> mergePoint) {

        return CRMergePointBuilder.startBuildingMergePoint(graph, mergePoint);
    }
}
