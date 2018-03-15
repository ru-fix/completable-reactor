package ru.fix.completable.reactor.graph;

public interface MergerBuilder<Payload, HandlerResult> {

    Vertex withMerger(Merger<Payload, HandlerResult> merger);

    Vertex withEmptyMerger();

    Vertex withoutMerger();
}
