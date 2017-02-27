package ru.fix.cpapsm.completable.reactor.runtime;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public class ArgMethodHandler2<ContextResult, PayloadType, ProcessorType, Arg1, Arg2> extends BaseDescriberItem<ContextResult> {

    public ArgMethodHandler2(ContextResult contextResult, GraphProcessorDescription processorDescription) {
        super(contextResult, processorDescription);
    }

    public <Arg3> ArgMethodHandler3<ContextResult, PayloadType, ProcessorType, Arg1, Arg2, Arg3> passArg(Function<PayloadType, Arg3> arg) {
        processorDescription.arg3 = arg;
        return new ArgMethodHandler3<>(contextResult, processorDescription);
    }

    public <Arg3> ArgMethodHandler3<ContextResult, PayloadType, ProcessorType, Arg1, Arg2, Arg3> copyArg(Function<PayloadType, Arg3> arg) {
        processorDescription.arg3 = arg;
        processorDescription.isCopyArg3 = true;
        return new ArgMethodHandler3<>(contextResult, processorDescription);
    }

    public <ProcessorResult> ArgMethodMerger<ContextResult, PayloadType, ProcessorResult> withHandler(MethodReference2Args<ProcessorType, Arg1, Arg2, CompletableFuture<ProcessorResult>> methodReference) {
        processorDescription.handler2 = methodReference;
        ReactorReflector.getMethodInvocationPoint().ifPresent(source -> processorDescription.handleBySource = source);

        return new ArgMethodMerger<>(contextResult, mergerInfo -> {
            processorDescription.merger = mergerInfo.merger;
            processorDescription.mergeSource = mergerInfo.mergerSource;
        });
    }
}
