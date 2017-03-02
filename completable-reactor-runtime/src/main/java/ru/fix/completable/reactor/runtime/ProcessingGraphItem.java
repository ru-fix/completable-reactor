package ru.fix.completable.reactor.runtime;

/**
 * @author Kamil Asfandiyarov
 */
public interface ProcessingGraphItem {

    /**
     * @return short identificator that goes to profiler as metric name. [A-Za-z0-9]
     */
    String getProfilingName();
}
