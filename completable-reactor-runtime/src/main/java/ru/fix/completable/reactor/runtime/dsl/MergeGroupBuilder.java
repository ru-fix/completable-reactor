package ru.fix.completable.reactor.runtime.dsl;

/**
 * @author Kamil Asfandiyarov
 */
public interface MergeGroupBuilder<PayloadType> {

    MergeGroupBuilder<PayloadType> with(Processor<? super PayloadType> processor);

    MergeGroupBuilder<PayloadType> with(Subgraph<? super PayloadType> subgraph);

    MergeGroupBuilder<PayloadType> with(MergePoint<PayloadType> mergePoint);

    MergePointBuilder<PayloadType> mergePoint(Processor<? super PayloadType> processor);

    MergePointBuilder<PayloadType> mergePoint(MergePoint<PayloadType> mergePoint);

}
