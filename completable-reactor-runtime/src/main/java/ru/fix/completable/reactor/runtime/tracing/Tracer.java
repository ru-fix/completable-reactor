package ru.fix.completable.reactor.runtime.tracing;


/**
 * @author Kamil Asfandiyarov
 */
public interface Tracer {
    /**
     * @return true if this payload instance should be traced
     */
    boolean isTraceable(Object payload);

    /**
     * @param vertexName Vertex name
     * @param payload Payload instance
     * @return tracingMarker - any object that will be passed to
     * {@link #afterHandle(Object, String, Object, Throwable)} method
     */
    Object beforeHandle(String vertexName, Object payload);

    /**
     *
     * @param tracingMarker object returned by {@link #beforeHandle(String, Object)}
     * @param handlerResult handler result
     * @param throwable in case handler completed with an exception
     */
    void afterHandle(Object tracingMarker,
                     String vertexName,
                     Object handlerResult,
                     Throwable throwable);

    /**
     *
     * @param vertexName Graph vertex name
     * @param payload Payload instance
     * @param handleResult handler result
     * @return @return tracingMarker - any object that will be passed to
     * {@link #afterMerger(Object, String, Object)} method
     */
    Object beforeMerge(String vertexName,
                       Object payload,
                       Object handleResult);

    /**
     *
     * @param tracingMarker object returned by {@link #beforeMerge(String, Object, Object)}
     * @param vertexName Graph processing vertex name
     * @param payload Payload instance
     */
    void afterMerger(Object tracingMarker, String vertexName, Object payload);
}
