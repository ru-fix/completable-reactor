package ru.fix.completable.reactor.runtime.internal.dsl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.fix.completable.reactor.api.ReactorGraphModel;
import ru.fix.completable.reactor.runtime.dsl.Subgraph;
import ru.fix.completable.reactor.runtime.internal.CRProcessingItem;

/**
 * Represent processor graph instance.
 * @author Kamil Asfandiyarov
 */
@EqualsAndHashCode(doNotUseGetters = true)
@Data
public class CRSubgraph<PayloadType> implements Subgraph<PayloadType>, CRProcessingItem {

    final ReactorGraphModel.Identity identity = new ReactorGraphModel.Identity()
            .setType(ReactorGraphModel.Identity.Type.SUBGRAPH);

    final CRSubgraphDescription subgraphDescription;

    public CRSubgraph(CRSubgraphDescription<PayloadType> subgraphDescription) {
        this.subgraphDescription = subgraphDescription;
        this.identity.setClassName(getPayloadClass().getSimpleName());
    }

    public Class<PayloadType> getPayloadClass() {
        return subgraphDescription.subgraphPayload;
    }

    @Override
    public Subgraph<PayloadType> setId(int id){
        this.identity.setId(id);
        return this;
    }

    public int getId() {
        return identity.getId();
    }

    @Override
    public String getProfilingName() {
        return getPayloadClass().getSimpleName();
    }

    @Override
    public String getDebugName() {
        return getPayloadClass().getName() + "@" + identity.getId();
    }

    @Override
    public ReactorGraphModel.Identity serializeIdentity() {
        return this.identity;
    }
}
