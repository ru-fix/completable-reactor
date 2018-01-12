package ru.fix.completable.reactor.runtime.dsl;


/**
 * @author Kamil Asfandiyarov
 */
public interface PayloadBuilder<PayloadType> {

    PayloadBuilder<PayloadType> handle(Processor<? super PayloadType> processor);

    PayloadBuilder<PayloadType> handle(Subgraph<? super PayloadType> subgraph);

    PayloadBuilder<PayloadType> merge(MergePoint<? super PayloadType> mergePoint);

    MergePointBuilder<PayloadType> mergePoint(Processor<? super PayloadType> processor);

    MergePointBuilder<PayloadType> mergePoint(Subgraph<? super PayloadType> subgraph);

    MergePointBuilder<PayloadType> mergePoint(MergePoint<? super PayloadType> mergePoint);
}