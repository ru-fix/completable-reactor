package ru.fix.completable.reactor.runtime.gl;

import ru.fix.completable.reactor.runtime.gl.GlMergerBuilder;
import ru.fix.completable.reactor.runtime.gl.Merger;
import ru.fix.completable.reactor.runtime.gl.Vertex;

class GlMergerBuilderImpl<Payload, HandlerResult> implements GlMergerBuilder<Payload, HandlerResult> {

    private final Vertex vertex;

    public GlMergerBuilderImpl(Vertex vertex) {
        this.vertex = vertex;
    }

    @Override
    public Vertex withMerger(Merger<Payload, HandlerResult> merger) {
        if(vertex.merger != null){
            throw new IllegalStateException("withMerger method used twice on same vertex");
        }
        vertex.merger = merger;
        return vertex;
    }

    @Override
    public Vertex withMerger(String title, Merger<Payload, HandlerResult> merger) {
        return withMerger(merger);
    }

    @Override
    public Vertex withMerger(String title, String doc, Merger<Payload, HandlerResult> merger) {
        return withMerger(merger);
    }

    @Override
    public Vertex withMerger(String title, String[] docs, Merger<Payload, HandlerResult> merger) {
        return withMerger(merger);
    }

    @Override
    public Vertex withoutMerger() {
        return vertex;
    }
}
