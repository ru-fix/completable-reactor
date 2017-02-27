package ru.fix.cpapsm.completable.reactor.runtime;

import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public class SubgraphHandler<ContextResult, PayloadType, SubgraphPayloadType> {
    final ContextResult contextResult;
    final ReactorGraph.ProcessorInfo processorInfo;

    public SubgraphHandler(ContextResult contextResult, ReactorGraph.ProcessorInfo processorInfo) {
        this.contextResult = contextResult;
        this.processorInfo = processorInfo;
        this.processorInfo.subgraphDescription = new SubgraphProcessorDescription();
    }
    public <SubgraphPayloadType> ContextResult describe(SubgraphProcessorDescription<SubgraphPayloadType, PayloadType> description) {
        this.processorInfo.subgraphDescription = description;
        return contextResult;
    }

    public ArgMethodMerger<ContextResult, PayloadType, SubgraphPayloadType> passArg(Function<PayloadType, SubgraphPayloadType> arg) {
        this.processorInfo.subgraphDescription.arg = arg;
        return new ArgMethodMerger<>(contextResult, mergerInfo -> {
            this.processorInfo.subgraphDescription.merger = mergerInfo.merger;
            this.processorInfo.subgraphDescription.mergeSource = mergerInfo.mergerSource;
        });
    }

    public ArgMethodMerger<ContextResult, PayloadType, SubgraphPayloadType> copyArg(Function<PayloadType, SubgraphPayloadType> arg) {
        this.processorInfo.subgraphDescription.arg = arg;
        this.processorInfo.subgraphDescription.isCopyArg = true;
        return new ArgMethodMerger<>(contextResult, mergerInfo -> {
            this.processorInfo.subgraphDescription.merger = mergerInfo.merger;
            this.processorInfo.subgraphDescription.mergeSource = mergerInfo.mergerSource;
        });
    }
}
