package ru.fix.completable.reactor.runtime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fix.completable.reactor.runtime.debug.ToStringDebugSerializer;
import ru.fix.completable.reactor.runtime.tracing.Tracer;

/**
 * @author Kamil Asfandiyarov
 */
public abstract class LogTracer implements Tracer {
    private final Logger log = LoggerFactory.getLogger(LogTracer.class);

    final ToStringDebugSerializer serializer = new ToStringDebugSerializer();

    @Override
    public Object beforeHandle(String vertexName, Object payload) {
        log.info("beforeHandle {}: {}", vertexName, serializer.dumpObject(payload));
        return null;
    }

    @Override
    public void afterHandle(Object tracingMarker,
                            String vertexName,
                            Object handlerResult,
                            Throwable throwable) {
        log.info("afterHandle {}: {}", vertexName, serializer.dumpObject(handlerResult), throwable);
    }

    @Override
    public Object beforeMerge(String vertexName,
                              Object payload,
                              Object handleResult) {
        log.info("beforeMerge {}: {}, {}",
                vertexName,
                serializer.dumpObject(payload),
                serializer.dumpObject(handleResult));
        return null;
    }

    @Override
    public void afterMerger(Object tracingMarker,
                            String vertexName,
                            Object payload) {
        log.info("afterMerger {}: {}", vertexName, serializer.dumpObject(payload));
    }
}
