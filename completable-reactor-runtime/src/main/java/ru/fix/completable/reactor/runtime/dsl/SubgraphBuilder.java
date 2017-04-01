package ru.fix.completable.reactor.runtime.dsl;

/**
 * @author Kamil Asfandiyarov
 */
public interface SubgraphBuilder<SubgraphPyaloadType> {
     <PayloadType> SubgraphHandlerBuilder<SubgraphPyaloadType, PayloadType> forPayload(Class<PayloadType> payloadType);
}
