package ru.fix.completable.reactor.runtime.gl;

public interface GlPayload<Payload> {
    GlPayload<Payload> handleBy(Vertex vertex);
}
