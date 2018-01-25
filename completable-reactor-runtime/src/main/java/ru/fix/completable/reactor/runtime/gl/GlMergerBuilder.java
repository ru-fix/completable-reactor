package ru.fix.completable.reactor.runtime.gl;

public interface GlMergerBuilder<Payload, HandlerResult> {

    Vertex withMerger(Merger<Payload, HandlerResult> merger);

    Vertex withoutMerger();
}
