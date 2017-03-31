package ru.fix.completable.reactor.runtime.dsl;

import ru.fix.completable.reactor.runtime.ReactorGraph;

/**
 * @author Kamil Asfandiyarov
 */
public interface Coordinates<PayloadType> {
    Coordinates<PayloadType> start(int x, int y);
    Coordinates<PayloadType> proc(Class clazz, int id, int x, int y);
    Coordinates<PayloadType> merge(Class clazz, int id, int x, int y);
    Coordinates<PayloadType> merge(int id, int x, int y);
    Coordinates<PayloadType> complete(Class clazz, int id, int x, int y);
    ReactorGraph<PayloadType> buildGraph();
}
