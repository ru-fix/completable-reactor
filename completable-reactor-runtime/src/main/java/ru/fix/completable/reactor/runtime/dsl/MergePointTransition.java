package ru.fix.completable.reactor.runtime.dsl;

/**
 * @author Kamil Asfandiyarov
 */
public interface MergePointTransition<PayloadType> {

    MergePointBuilder<PayloadType> complete();

    MergePointBuilder<PayloadType> handle(Processor<? super PayloadType> processor);

    MergePointBuilder<PayloadType> handle(Subgraph<? super PayloadType> subgraph);

    MergePointBuilder<PayloadType> merge(Processor<? super PayloadType> processor);

    MergePointBuilder<PayloadType> merge(Subgraph<? super PayloadType> subgraph);

    MergePointBuilder<PayloadType> merge(MergePoint<? super PayloadType> mergePoint);
}
