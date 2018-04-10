package ru.fix.completable.reactor.graph;

// Interface written in Java due to void returning value
// Kotlin Unit type as returning value treated on Java side as Object

@FunctionalInterface
public interface Mutator<Payload> {
    void mutate(Payload payload);
}
