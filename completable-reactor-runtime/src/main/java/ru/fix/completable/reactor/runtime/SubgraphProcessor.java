package ru.fix.completable.reactor.runtime;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Represent processor graph instance.
 * @author Kamil Asfandiyarov
 */
@EqualsAndHashCode(doNotUseGetters = true)
@ToString
public class SubgraphProcessor<SubgraphPayloadType, PayloadType> implements ProcessingGraphItem, MergeableProcessingGraphItem<PayloadType>, HandleableProcessingGraphItem{

    int id = 0;

    final SubgraphProcessorDescription subgraphDescription;

    public SubgraphProcessor(SubgraphProcessorDescription<SubgraphPayloadType, PayloadType> subgraphDescription) {
        this.subgraphDescription = subgraphDescription;
    }

    public Class<PayloadType> getPayloadClass() {
        return subgraphDescription.subgraphPayload;
    }

    public SubgraphProcessor<SubgraphPayloadType, PayloadType> withId(int id){
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
