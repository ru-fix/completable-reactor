package ru.fix.completable.reactor.runtime.internal;

import lombok.ToString;
import ru.fix.completable.reactor.api.ReactorGraphModel;
import ru.fix.completable.reactor.runtime.dsl.MergePoint;
import ru.fix.completable.reactor.runtime.dsl.MergePointDescription;
import ru.fix.completable.reactor.runtime.dsl.MergePointMerger;

/**
 * @author Kamil Asfandiyarov
 */
@ToString
public class CRMergePointDescription<PayloadType> implements MergePointDescription<PayloadType>{

    /**
     * {@code Function<PayloadType, Enum>}
     */
    MergePointMerger<PayloadType> merger;

    /**
     * Source where {@code withMerger} method was used
     */
    ReactorGraphModel.Source mergerSource;

    /**
     * Merger title
     */
    String mergerTitle;
    /**
     * Merger documentation;
     */
    String[] mergerDocs;

    public MergePoint<PayloadType> buildMergePoint(){
        return new CRMergePoint<>(this);
    }

}
