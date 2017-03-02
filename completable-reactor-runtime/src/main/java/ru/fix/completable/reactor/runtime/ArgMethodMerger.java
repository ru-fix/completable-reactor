package ru.fix.completable.reactor.runtime;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.fix.completable.reactor.api.ReactorGraphModel;

import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * @author Kamil Asfandiyarov
 */
public class ArgMethodMerger<ContextResult, PayloadType, ProcessorResult> {

    @Data
    @Accessors(chain = true)
    public static class MergerInfo<PayloadType, ProcessorResult>{
        ReactorGraphModel.Source mergerSource;
        BiFunction<PayloadType, ProcessorResult, Enum> merger;
    }

    final ContextResult contextResult;
    final Consumer<MergerInfo<PayloadType, ProcessorResult>> mergerConsumer;

    public ArgMethodMerger(ContextResult contextResult, Consumer<MergerInfo<PayloadType, ProcessorResult>> mergerConsumer) {
        this.contextResult = contextResult;
        this.mergerConsumer = mergerConsumer;
    }

    public ContextResult withMerger(BiFunction<PayloadType, ProcessorResult, Enum> merger) {
        MergerInfo<PayloadType, ProcessorResult> mergerInfo = new MergerInfo<>();
        mergerInfo.merger = merger;
        mergerInfo.mergerSource = ReactorReflector.getMethodInvocationPoint().orElse(null);

        mergerConsumer.accept(mergerInfo);
        return contextResult;
    }

    public ContextResult withoutMerger() {
        MergerInfo<PayloadType, ProcessorResult> mergerInfo = new MergerInfo<>();
        mergerInfo.merger = null;
        mergerInfo.mergerSource = ReactorReflector.getMethodInvocationPoint().orElse(null);

        mergerConsumer.accept(mergerInfo);
        return contextResult;
    }
}
