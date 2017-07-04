package ru.fix.completable.reactor.runtime.dsl;

import ru.fix.completable.reactor.runtime.ReactorGraph;

/**
 * @author Kamil Asfandiyarov
 */
public interface Coordinates<PayloadType> {
    /**
     * Payload start point coordinates
     */
    Coordinates<PayloadType> start(int x, int y);

    /**
     * Processor coordinates
     */
    Coordinates<PayloadType> proc(Processor<PayloadType> processor, int x, int y);
    /**
     * Subgraph coordinates
     */
    Coordinates<PayloadType> proc(Subgraph<PayloadType> processor, int x, int y);

    /**
     * Processors merge point coordinates
     */
    Coordinates<PayloadType> merge(Processor<PayloadType> processor, int x, int y);
    /**
     * Subgraphs merge point coordinates
     */
    Coordinates<PayloadType> merge(Subgraph<PayloadType> subgraph, int x, int y);
    /**
     * Detached merge point coordinates
     */
    Coordinates<PayloadType> merge(MergePoint<PayloadType> mergePoint, int x, int y);

    /**
     * Processors merge point endpoint coordinates
     */
    Coordinates<PayloadType> complete(Processor<PayloadType> processor, int x, int y);
    /**
     * Subgraph merge point endpoint coordinates
     */
    Coordinates<PayloadType> complete(Subgraph<PayloadType> subgraph, int x, int y);

    /**
     * Detached merge point endpoint coordinates
     */
    Coordinates<PayloadType> complete(MergePoint<PayloadType> mergePoint, int x, int y);

    /**
     * Build Reactor Graph
     */
    ReactorGraph<PayloadType> buildGraph();
}
