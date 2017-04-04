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
     * Processor or Subgraph coordinates
     */
    Coordinates<PayloadType> proc(Class processorType, int id, int x, int y);
    /**
     * Processors or Subgraphs merge point coordinates
     */
    Coordinates<PayloadType> merge(Class processorType, int id, int x, int y);
    /**
     * Detached merge point coordinates
     */
    Coordinates<PayloadType> merge(int id, int x, int y);
    /**
     * Processors or Subgraphs merge point endpoint coordinates
     */
    Coordinates<PayloadType> complete(Class processorOrSubgraphType, int id, int x, int y);
    /**
     * Detached merge point endpoint coordinates
     */
    Coordinates<PayloadType> complete(int id, int x, int y);

    /**
     * Build Reactor Graph
     */
    ReactorGraph<PayloadType> buildGraph();
}
