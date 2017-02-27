package ru.fix.cpapsm.completable.reactor.runtime;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public class ArgMethodHandler1<ContextResult, PayloadType, ProcessorType, Arg1> extends BaseDescriberItem<ContextResult> {

    public ArgMethodHandler1(ContextResult contextResult, GraphProcessorDescription processorDescription) {
        super(contextResult, processorDescription);
    }

    public <Arg2> ArgMethodHandler2<ContextResult, PayloadType, ProcessorType, Arg1, Arg2> passArg(Function<PayloadType, Arg2> arg) {
        processorDescription.arg2 = arg;
        return new ArgMethodHandler2<>(contextResult, processorDescription);
    }

    public <Arg2> ArgMethodHandler2<ContextResult, PayloadType, ProcessorType, Arg1, Arg2> copyArg(Function<PayloadType, Arg2> arg) {
        processorDescription.arg2 = arg;
        processorDescription.isCopyArg2 = true;
        return new ArgMethodHandler2<>(contextResult, processorDescription);
    }

    public <ProcessorResult> ArgMethodMerger<ContextResult, PayloadType, ProcessorResult> withHandler(MethodReference1Arg<ProcessorType, Arg1, CompletableFuture<ProcessorResult>> methodReference) {
        processorDescription.handler1 = methodReference;
        ReactorReflector.getMethodInvocationPoint().ifPresent(source -> processorDescription.handleBySource = source);

        return new ArgMethodMerger<>(contextResult, mergerInfo -> {
            processorDescription.merger = mergerInfo.merger;
            processorDescription.mergeSource = mergerInfo.mergerSource;
        });
    }
}
