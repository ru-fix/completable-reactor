package ru.fix.completable.reactor.runtime;

import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public class MergePointArgMethodMerger<ContextResult, PayloadType> {

    final ContextResult contextResult;
    final GraphMergePointDescription<PayloadType> description;

    MergePointArgMethodMerger(ContextResult contextResult, GraphMergePointDescription<PayloadType> description) {
        this.contextResult = contextResult;
        this.description = description;
    }

    public ContextResult withMerger(Function<PayloadType, Enum> merger){
        return withMerger(null, merger);
    }

    public ContextResult withMerger(String[] docs, Function<PayloadType, Enum> merger){

        description.merger = merger;
        description.mergerSource = ReactorReflector.getMethodInvocationPoint().orElse(null);
        description.mergerDocs = docs;

        return contextResult;
    }
}
