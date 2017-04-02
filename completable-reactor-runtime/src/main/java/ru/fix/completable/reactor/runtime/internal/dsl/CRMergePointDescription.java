package ru.fix.completable.reactor.runtime.internal.dsl;

import lombok.Data;
import ru.fix.completable.reactor.api.ReactorGraphModel;
import ru.fix.completable.reactor.runtime.dsl.MergePoint;
import ru.fix.completable.reactor.runtime.dsl.MergePointDescription;
import ru.fix.completable.reactor.runtime.dsl.MergePointMerger;

import java.util.Objects;

/**
 * @author Kamil Asfandiyarov
 */
@Data
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
        Objects.requireNonNull(merger, "MergePoint merger is null.");

        return new CRMergePoint<>(this);
    }

}
