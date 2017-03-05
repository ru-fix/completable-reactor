package ru.fix.completable.reactor.runtime;

/**
 * @author Kamil Asfandiyarov
 */
public class GraphMergePoint<PayloadType> implements ProcessingGraphItem, MergeableProcessingGraphItem {
    int id = 0;

    public GraphMergePoint<PayloadType> withId(int id){
        this.id = id;
        return this;
    }

    @Override
    public String getProfilingName() {
        return "mergePoint" + this.id;
    }
}
