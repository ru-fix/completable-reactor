package ru.fix.completable.reactor.runtime.internal.dsl;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.fix.completable.reactor.api.ReactorGraphModel;
import ru.fix.completable.reactor.runtime.dsl.MergePoint;
import ru.fix.completable.reactor.runtime.internal.CRProcessingItem;

/**
 * @author Kamil Asfandiyarov
 */
@EqualsAndHashCode(doNotUseGetters = true)
@Data
public class CRMergePoint<PayloadType> implements MergePoint<PayloadType>, CRProcessingItem {

    final ReactorGraphModel.Identity identity = new ReactorGraphModel.Identity()
            .setType(ReactorGraphModel.Identity.Type.MERGE_POINT);

    CRMergePointDescription<PayloadType> mergePointDescription;

    CRMergePoint(CRMergePointDescription<PayloadType> mergePointDescription) {
        this.mergePointDescription = mergePointDescription;
    }

    @Override
    public CRMergePoint<PayloadType> setId(int id){
        this.identity.setId(id);
        return this;
    }

    public int getId() {
        return this.identity.getId();
    }

    @Override
    public String getProfilingName() {
        return "mergePoint." + this.identity.getId();
    }

    @Override
    public String getDebugName() {
        return identity.toString();
    }
}
