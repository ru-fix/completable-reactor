package ru.fix.completable.reactor.runtime.dsl;

/**
 * @author Kamil Asfandiyarov
 */
public interface MergePointDescription<PayloadType> {
    MergePoint<PayloadType> buildMergePoint();
}
