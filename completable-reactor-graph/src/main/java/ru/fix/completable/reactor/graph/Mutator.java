package ru.fix.completable.reactor.graph;

@FunctionalInterface
public interface Mutator<Payload> {
    void mutate(Payload payload);
}
