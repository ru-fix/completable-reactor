package ru.fix.completable.reactor.runtime.gl;

public interface TransitionBuilder {

    Vertex complete();

    Vertex handleBy(Vertex vertex);

    Vertex mergeBy(Vertex vertex);
}