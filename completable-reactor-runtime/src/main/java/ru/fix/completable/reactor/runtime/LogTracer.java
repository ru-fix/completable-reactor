package ru.fix.completable.reactor.runtime;

import lombok.extern.slf4j.Slf4j;
import ru.fix.completable.reactor.api.ReactorGraphModel;
import ru.fix.completable.reactor.runtime.debug.ToStringDebugSerializer;
import ru.fix.completable.reactor.runtime.tracing.Tracer;

/**
 * @author Kamil Asfandiyarov
 */
@Slf4j
public abstract class LogTracer implements Tracer {

    final ToStringDebugSerializer serializer = new ToStringDebugSerializer();

    @Override
    public Object beforeHandle(ReactorGraphModel.Identity identity, Object payload) {
        log.info("beforeHandle {}: {}", identity, serializer.dumpObject(payload));
        return null;
    }

    @Override
    public void afterHandle(Object tracingMarker,
                            ReactorGraphModel.Identity identity,
                            Object handlerResult,
                            Throwable throwable) {
        log.info("afterHandle {}: {}", identity, serializer.dumpObject(handlerResult), throwable);
    }

    @Override
    public Object beforeMerge(ReactorGraphModel.Identity identity,
                              Object payload,
                              Object handleResult) {
        log.info("beforeMerge {}: {}, {}",
                identity,
                serializer.dumpObject(payload),
                serializer.dumpObject(handleResult));
        return null;
    }

    @Override
    public void afterMerger(Object tracingMarker,
                            ReactorGraphModel.Identity identity,
                            Object payload) {
        log.info("afterMerger {}: {}", identity, serializer.dumpObject(payload));
    }
}
