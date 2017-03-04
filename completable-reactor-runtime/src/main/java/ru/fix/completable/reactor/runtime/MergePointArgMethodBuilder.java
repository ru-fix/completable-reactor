package ru.fix.completable.reactor.runtime;

/**
 * @author Kamil Asfandiyarov
 */
public class MergePointArgMethodBuilder<ContextResult, PayloadType> extends MergePointArgMethodMerger<ContextResult, PayloadType> {
    final ReactorGraph.ProcessorInfo processorInfo;

    MergePointArgMethodBuilder(ContextResult contextResult, ReactorGraph.ProcessorInfo processorInfo) {
        super(contextResult, new GraphMergePointDescription<PayloadType>());
        this.processorInfo = processorInfo;
        this.processorInfo.detachedMergePointDescription = this.description;
    }


    public ContextResult describe(GraphMergePointDescription<? super PayloadType> description) {
        this.processorInfo.detachedMergePointDescription = description;
        return super.contextResult;
    }
}
