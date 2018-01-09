package ru.fix.completable.reactor.runtime.gl;

final class GlTransitionBuilderImpl implements GlTransitionBuilder {

    final Vertex vertex;
    final GlTransition transition;

    public GlTransitionBuilderImpl(Vertex vertex, GlTransition transition) {
        this.vertex = vertex;
        this.transition = transition;
    }

    @Override
    public Vertex complete() {
        transition.setComplete(true);
        return vertex;
    }

    @Override
    public Vertex handleBy(Vertex vertex) {
        transition.setHandleBy(vertex);
        return vertex;
    }

    @Override
    public Vertex mergeBy(Vertex vertex) {
        transition.setMergeBy(vertex);
        return vertex;
    }
}
