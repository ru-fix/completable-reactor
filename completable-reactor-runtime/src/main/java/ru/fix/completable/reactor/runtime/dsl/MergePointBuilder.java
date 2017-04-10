package ru.fix.completable.reactor.runtime.dsl;

/**
 * @author Kamil Asfandiyarov
 */
public interface MergePointBuilder<PayloadType> {

    MergePointTransition<PayloadType> on(Enum<?>... mergeStatuses);

    MergePointTransition<PayloadType> onAny();

    MergeGroupBuilder<PayloadType> mergeGroup();

    MergePointBuilder<PayloadType> mergePoint(Processor<? super PayloadType> processor);

    MergePointBuilder<PayloadType> mergePoint(Subgraph<? super PayloadType> subgraph);

    MergePointBuilder<PayloadType> mergePoint(MergePoint<PayloadType> mergePoint);

    Coordinates<PayloadType> coordinates();
}
