package ru.fix.completable.reactor.runtime.gl;

public interface GlTransitionBuilder {

    Vertex complete();

    Vertex handleBy(Vertex vertex);

    Vertex mergeBy(Vertex vertex);
}