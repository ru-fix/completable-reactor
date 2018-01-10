package ru.fix.completable.reactor.runtime.gl;

import ru.fix.completable.reactor.runtime.internal.gl.ConfigContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class Vertex {

    {
        if (!this.getClass().equals(Vertex.class)) {
            // user derived class that extends Vertex
            ConfigContext.Companion.get().setVertex(this);
        }
    }

    public GlTransitionBuilder on(Enum<?>... mergeStatuses) {
        GlTransition transition = new GlTransition();
        transition.setMergeStatuses(new HashSet<>(Arrays.asList(mergeStatuses)));
        transitions.add(transition);

        return new GlTransitionBuilderImpl(
                this,
                transition
        );
    }

    public GlTransitionBuilder onAny() {
        GlTransition transition = new GlTransition();
        transition.setOnAny(true);
        transitions.add(transition);

        return new GlTransitionBuilderImpl(
                this,
                transition
        );
    }

    Handler handler;
    Merger merger;
    Router router;
    Class subgraph;

    List<GlTransition> transitions = new ArrayList<>();
}
