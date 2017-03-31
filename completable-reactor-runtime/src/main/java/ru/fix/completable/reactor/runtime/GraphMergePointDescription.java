package ru.fix.completable.reactor.runtime;

import lombok.ToString;
import ru.fix.completable.reactor.api.ReactorGraphModel;
import ru.fix.completable.reactor.runtime.dsl.MergePointMerger;

/**
 * @author Kamil Asfandiyarov
 */
@ToString
public class GraphMergePointDescription<PayloadType> {

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

    public GraphMergePoint<PayloadType> buildMergePoint(){
        return new GraphMergePoint<>(this);
    }

}
