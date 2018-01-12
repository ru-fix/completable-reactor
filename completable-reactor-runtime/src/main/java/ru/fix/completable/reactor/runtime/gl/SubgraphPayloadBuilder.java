package ru.fix.completable.reactor.runtime.gl;

@FunctionalInterface
public interface SubgraphPayloadBuilder<Payload, SubgraphPayload> {
    SubgraphPayload buildPayload(Payload payload);
}
