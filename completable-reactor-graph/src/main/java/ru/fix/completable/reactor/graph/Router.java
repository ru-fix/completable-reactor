package ru.fix.completable.reactor.graph;

@FunctionalInterface
public interface Router<Payload> {
    Enum route(Payload payload);
}
