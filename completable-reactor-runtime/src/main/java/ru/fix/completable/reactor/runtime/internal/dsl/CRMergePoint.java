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
    int id = 0;

    CRMergePointDescription<PayloadType> mergePointDescription;

    CRMergePoint(CRMergePointDescription<PayloadType> mergePointDescription) {
        this.mergePointDescription = mergePointDescription;
    }

    @Override
    public CRMergePoint<PayloadType> setId(int id){
        this.id = id;
        return this;
    }

    public int getId() {
        return id;
    }

    @Override
    public String getProfilingName() {
        return "mergePoint." + this.id;
    }

    @Override
    public String getDebugName() {
        return "mergePoint@" + id;
    }

    @Override
    public ReactorGraphModel.Identity serializeIdentity() {
        return new ReactorGraphModel.Identity()
                .setType(ReactorGraphModel.Identity.Type.MERGE_POINT)
                .setId(id);
    }
}
