package ru.fix.completable.reactor.runtime.internal.dsl;

import ru.fix.completable.reactor.runtime.ReactorGraph;
import ru.fix.completable.reactor.runtime.dsl.*;

/**
 * @author Kamil Asfandiyarov
 */
public class CRPayloadBuilder<PayloadType> implements PayloadBuilder<PayloadType> {

    final ReactorGraph<PayloadType> graph;

    public CRPayloadBuilder(ReactorGraph<PayloadType> graph) {
        this.graph = graph;
    }




    @Override
    public PayloadBuilder<PayloadType> handleBy(Processor<? super PayloadType> processor) {
        return this;
    }

    @Override
    public PayloadBuilder<PayloadType> handleBy(Subgraph<? super PayloadType> subgraph) {
        return null;
    }

    @Override
    public PayloadBuilder<PayloadType> merge(MergePoint<PayloadType> mergePoint) {
        return null;
    }

    @Override
    public MergePointBuilder<PayloadType> mergePoint(Processor<? super PayloadType> processor) {
        return null;
    }

    @Override
    public MergePointBuilder<PayloadType> mergePoint(MergePoint<PayloadType> mergePoint) {
        return null;
    }

    @Override
    public MergeGroupBuilder<PayloadType> mergeGroup() {
        return null;
    }
}
