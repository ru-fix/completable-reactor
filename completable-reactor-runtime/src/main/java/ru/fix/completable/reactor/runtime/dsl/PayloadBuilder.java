package ru.fix.completable.reactor.runtime.dsl;


/**
 * @author Kamil Asfandiyarov
 */
public interface PayloadBuilder<PayloadType> {

    PayloadBuilder<PayloadType> handleBy(Processor<? super PayloadType> processor);

    PayloadBuilder<PayloadType> handleBy(Subgraph<? super PayloadType> subgraph);

    PayloadBuilder<PayloadType> merge(MergePoint<PayloadType> mergePoint);

    MergePointBuilder<PayloadType> mergePoint(Processor<? super PayloadType> processor);

    MergePointBuilder<PayloadType> mergePoint(MergePoint<PayloadType> mergePoint);

    MergeGroupBuilder<PayloadType> mergeGroup();
}