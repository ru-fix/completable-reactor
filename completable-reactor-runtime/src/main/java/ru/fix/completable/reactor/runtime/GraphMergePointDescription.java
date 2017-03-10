package ru.fix.completable.reactor.runtime;

import ru.fix.completable.reactor.api.ReactorGraphModel;

import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public class GraphMergePointDescription<PayloadType> {

    /**
     * {@code Function<PayloadType, Enum>}
     */
    Function<PayloadType, Enum> merger;

    /**
     * Source where {@code withMerger} method was used
     */
    ReactorGraphModel.Source mergerSource;

    /**
     * Merger documentation;
     */
    String[] mergerDocs;

    public GraphMergePoint<PayloadType> buildMergePoint(){
        return new GraphMergePoint<>(this);
    }

}
