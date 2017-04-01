package ru.fix.completable.reactor.runtime.internal;

import ru.fix.completable.reactor.runtime.dsl.MergePoint;

/**
 * @author Kamil Asfandiyarov
 */
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

    @Override
    public String getProfilingName() {
        return "mergePoint." + this.id;
    }

    @Override
    public String getDebugName() {
        return "mergePoint@" + id;
    }
}
