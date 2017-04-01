package ru.fix.completable.reactor.runtime.internal;

import ru.fix.completable.reactor.runtime.dsl.SubgraphBuilder;
import ru.fix.completable.reactor.runtime.dsl.SubgraphHandlerBuilder;

/**
 * @author Kamil Asfandiyarov
 */
public class CRSubgraphBuilder<SubgraphPayloadType> implements SubgraphBuilder<SubgraphPayloadType> {
    final Class<SubgraphPayloadType> subgraphPayloadType;

    public CRSubgraphBuilder(Class<SubgraphPayloadType> subgraphPayloadType) {
        this.subgraphPayloadType = subgraphPayloadType;
    }

    @Override
    public <PayloadType> SubgraphHandlerBuilder<SubgraphPayloadType, PayloadType> forPayload(Class<PayloadType> payloadType) {
        CRSubgraphDescription<SubgraphPayloadType> subgraphDescription = new CRSubgraphDescription<>(subgraphPayloadType);

        return new CRSubgraphHandlerBuilder<SubgraphPayloadType>(subgraphDescription);
    }
}
