package ru.fix.completable.reactor.runtime.dsl;

/**
 * @author Kamil Asfandiyarov
 */
public interface MergePoint<PayloadType> {
    MergePoint<PayloadType> setId(int id);
}
