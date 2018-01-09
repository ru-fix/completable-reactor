package ru.fix.completable.reactor.runtime.gl;

import ru.fix.completable.reactor.runtime.internal.gl.ConfigContext;

public class Vertex {

    {
        if (!this.getClass().equals(Vertex.class)) {
            // user derived class that extends Vertex
            ConfigContext.get().setVertex(this);
        }
    }

    public GlTransitionBuilder on(Enum<?>... mergeStatuses) {
        return new GlTransitionBuilderImpl(
                this,
                new GlTransition()
                        .setMergeStatuses(mergeStatuses)
        );
    }

    public GlTransitionBuilder onAny() {
        return new GlTransitionBuilderImpl(
                this,
                new GlTransition()
                        .setOnAny(true)
        );
    }

    Handler handler;
    Merger merger;
    Router router;
    Class subgraph;
}
