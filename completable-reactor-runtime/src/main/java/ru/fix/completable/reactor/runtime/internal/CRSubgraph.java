package ru.fix.completable.reactor.runtime.internal;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import ru.fix.completable.reactor.runtime.dsl.Subgraph;

/**
 * Represent processor graph instance.
 * @author Kamil Asfandiyarov
 */
@EqualsAndHashCode(doNotUseGetters = true)
@ToString
public class CRSubgraph<PayloadType> implements Subgraph<PayloadType>, CRProcessingItem {

    int id = 0;

    final CRSubgraphDescription subgraphDescription;

    public CRSubgraph(CRSubgraphDescription<PayloadType> subgraphDescription) {
        this.subgraphDescription = subgraphDescription;
    }

    public Class<PayloadType> getPayloadClass() {
        return subgraphDescription.subgraphPayload;
    }

    public Subgraph<PayloadType> setId(int id){
        this.id = id;
        return this;
    }

    public int getId() {
        return id;
    }

    @Override
    public String getProfilingName() {
        return getPayloadClass().getSimpleName();
    }

    @Override
    public String getDebugName() {
        return getPayloadClass().getName();
    }
}
