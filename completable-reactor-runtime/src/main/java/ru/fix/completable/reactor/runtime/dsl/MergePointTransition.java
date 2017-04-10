package ru.fix.completable.reactor.runtime.dsl;

/**
 * @author Kamil Asfandiyarov
 */
public interface MergePointTransition<PayloadType> {

    MergePointBuilder<PayloadType> complete();

    MergePointBuilder<PayloadType> handleBy(Processor<? super PayloadType> processor);

    MergePointBuilder<PayloadType> handleBy(Subgraph<? super PayloadType> subgraph);

    MergePointBuilder<PayloadType> merge(Processor<? super PayloadType> processor);

    MergePointBuilder<PayloadType> merge(Subgraph<? super PayloadType> subgraph);

    MergePointBuilder<PayloadType> merge(MergePoint<? super PayloadType> mergePoint);
}
