package ru.fix.completable.reactor.runtime;

import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public class SubgraphHandler<ContextResult, PayloadType, SubgraphPayloadType> {
    final ContextResult contextResult;
    final SubgraphProcessorDescription subgraphDescription;

    public SubgraphHandler(ContextResult contextResult, SubgraphProcessorDescription<SubgraphPayloadType, PayloadType> subgraphDescription) {
        this.contextResult = contextResult;
        this.subgraphDescription = subgraphDescription;
    }

    public ArgMethodMerger<ContextResult, PayloadType, SubgraphPayloadType> passArg(Function<PayloadType, SubgraphPayloadType> arg) {
        this.subgraphDescription.arg = arg;
        return new ArgMethodMerger<>(contextResult, mergerInfo -> {
            this.subgraphDescription.merger = mergerInfo.merger;
            this.subgraphDescription.mergeSource = mergerInfo.mergerSource;
            this.subgraphDescription.mergerDocs = mergerInfo.mergerDocs;
        });
    }

    public ArgMethodMerger<ContextResult, PayloadType, SubgraphPayloadType> copyArg(Function<PayloadType, SubgraphPayloadType> arg) {
        this.subgraphDescription.arg = arg;
        this.subgraphDescription.isCopyArg = true;
        return new ArgMethodMerger<>(contextResult, mergerInfo -> {
            this.subgraphDescription.merger = mergerInfo.merger;
            this.subgraphDescription.mergeSource = mergerInfo.mergerSource;
            this.subgraphDescription.mergerDocs = mergerInfo.mergerDocs;
        });
    }
}
