package ru.fix.completable.reactor.runtime;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public class ArgMethodHandler3<ContextResult, PayloadType, ProcessorType, Arg1, Arg2, Arg3> extends BaseDescriberItem<ContextResult> {

    public ArgMethodHandler3(ContextResult contextResult, GraphProcessorDescription processorDescription) {
        super(contextResult, processorDescription);
    }

    public <Arg4> ArgMethodHandler4<ContextResult, PayloadType, ProcessorType, Arg1, Arg2, Arg3, Arg4> passArg(Function<PayloadType, Arg4> arg) {
        processorDescription.arg4 = arg;
        return new ArgMethodHandler4<>(contextResult, processorDescription);
    }

    public <Arg4> ArgMethodHandler4<ContextResult, PayloadType, ProcessorType, Arg1, Arg2, Arg3, Arg4> copyArg(Function<PayloadType, Arg4> arg) {
        processorDescription.arg4 = arg;
        processorDescription.isCopyArg3 = true;
        return new ArgMethodHandler4<>(contextResult, processorDescription);
    }

    public <ProcessorResult> ArgMethodMerger<ContextResult, PayloadType, ProcessorResult> withHandler(MethodReference3Args<ProcessorType, Arg1, Arg2, Arg3, CompletableFuture<ProcessorResult>> methodReference) {
        processorDescription.handler3 = methodReference;
        ReactorReflector.getMethodInvocationPoint().ifPresent(source -> processorDescription.handleBySource = source);

        return new ArgMethodMerger<>(contextResult, mergerInfo -> {
            processorDescription.merger = mergerInfo.merger;
            processorDescription.mergeSource = mergerInfo.mergerSource;
        });
    }
}
