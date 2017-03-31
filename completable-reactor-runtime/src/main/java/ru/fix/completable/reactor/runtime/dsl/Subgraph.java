package ru.fix.completable.reactor.runtime.dsl;

/**
 * @author Kamil Asfandiyarov
 */
public interface Subgraph<PayloadType> {
    Subgraph<PayloadType> setId(int id);
}
