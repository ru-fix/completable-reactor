package ru.fix.completable.reactor.runtime.dsl;

/**
 * @author Kamil Asfandiyarov
 */
public interface Processor<PayloadType> {
    Processor<PayloadType> setId(int id);
}
