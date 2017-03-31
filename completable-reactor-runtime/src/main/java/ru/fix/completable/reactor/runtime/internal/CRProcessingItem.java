package ru.fix.completable.reactor.runtime.internal;

/**
 * @author Kamil Asfandiyarov
 */
public interface CRProcessingItem {

    /**
     * @return short identificator that goes to profiler as metric name. [A-Za-z0-9]
     */
    String getProfilingName();

    /**
     * @return name displayed in logs.
     */
    String getDebugName();
}
