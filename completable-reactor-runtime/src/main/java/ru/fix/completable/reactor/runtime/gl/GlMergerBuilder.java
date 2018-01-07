package ru.fix.completable.reactor.runtime.gl;

public interface GlMergerBuilder<Payload, HandlerResult> {

    Vertex withMerger(Merger<Payload, HandlerResult> merger);

    Vertex withMerger(String title, Merger<Payload, HandlerResult> merger);

    Vertex withMerger(String title,
                      String doc,
                      Merger<Payload, HandlerResult> merger);

    Vertex withMerger(String title,
                      String[] docs,
                      Merger<Payload, HandlerResult> merger);

    Vertex withoutMerger();
}
