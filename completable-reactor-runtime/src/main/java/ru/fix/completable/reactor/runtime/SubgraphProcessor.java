package ru.fix.completable.reactor.runtime;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Represent processor graph instance.
 * @author Kamil Asfandiyarov
 */
@EqualsAndHashCode(doNotUseGetters = true)
@ToString
public class SubgraphProcessor<PayloadType> implements ProcessingGraphItem{
    final Class<PayloadType> payloadClass;
    int id = 0;

    public SubgraphProcessor(Class<PayloadType> payloadClass) {
        this.payloadClass = payloadClass;
    }

    public Class<PayloadType> getPayloadClass() {
        return payloadClass;
    }

    public SubgraphProcessor<PayloadType> withId(int id){
        this.id = id;
        return this;
    }

    public int getId() {
        return id;
    }

    @Override
    public String getProfilingName() {
        return payloadClass.getSimpleName();
    }
}
