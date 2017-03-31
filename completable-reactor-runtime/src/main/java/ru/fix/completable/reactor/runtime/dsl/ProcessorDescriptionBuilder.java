package ru.fix.completable.reactor.runtime.dsl;

/**
 * @author Kamil Asfandiyarov
 */
public interface ProcessorDescriptionBuilder {

    <PayloadType> HandlerBuilder0<PayloadType> forPayload(
            Class<PayloadType> payloadType
    );
}
