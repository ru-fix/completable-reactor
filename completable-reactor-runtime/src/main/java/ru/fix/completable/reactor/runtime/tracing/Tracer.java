package ru.fix.completable.reactor.runtime.tracing;

import ru.fix.completable.reactor.api.ReactorGraphModel;

/**
 * @author Kamil Asfandiyarov
 */
public interface Tracer {
    /**
     * @param identity Graph processing item identity
     * @param payload Payload instance
     * @return tracingMarker - any object that will be passed to {@link #afterHandle(Object, Object, Throwable)} method
     */
    Object beforeHandle(ReactorGraphModel.Identity identity, Object payload);

    /**
     *
     * @param tracingMarker object returned by {@link #beforeHandle(ReactorGraphModel.Identity, Object)}
     * @param handlerResult handler result
     * @param throwable in case handler completed with an exception
     */
    void afterHandle(Object tracingMarker, Object handlerResult, Throwable throwable);

    /**
     *
     * @param identity Graph processing item identity
     * @param payload Payload instance
     * @param handleResult handler result
     * @return @return tracingMarker - any object that will be passed to {@link #afterMerger(Object, ReactorGraphModel.Identity, Object)} method
     */
    Object beforeMerge(ReactorGraphModel.Identity identity, Object payload, Object handleResult);

    /**
     *
     * @param tracingMarker object returned by {@link #beforeMerge(ReactorGraphModel.Identity, Object, Object)}
     * @param identity Graph processing item identity
     * @param payload Payload instance
     */
    void afterMerger(Object tracingMarker, ReactorGraphModel.Identity identity, Object payload);

}
