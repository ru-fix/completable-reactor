package ru.fix.completable.reactor.runtime.internal;

import ru.fix.completable.reactor.api.ReactorGraphModel;

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

    /**
     * @return Model identity
     */
    ReactorGraphModel.Identity serializeIdentity();
}
