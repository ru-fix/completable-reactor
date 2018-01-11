package ru.fix.completable.reactor.runtime.gl;

@FunctionalInterface
public interface Router<Payload> {
    Enum route(Payload payload);
}
