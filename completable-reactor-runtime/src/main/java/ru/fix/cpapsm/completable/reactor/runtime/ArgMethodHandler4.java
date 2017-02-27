package ru.fix.cpapsm.completable.reactor.runtime;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public class ArgMethodHandler4<ContextResult, PayloadType, ProcessorType , Arg1, Arg2, Arg3, Arg4> extends BaseDescriberItem<ContextResult> {
    public ArgMethodHandler4(ContextResult contextResult, GraphProcessorDescription processorDescription) {
        super(contextResult, processorDescription);
    }

    public <Arg5> ArgMethodHandler5<ContextResult, PayloadType, ProcessorType, Arg1, Arg2, Arg3, Arg4, Arg5> passArg(Function<PayloadType, Arg5> arg) {
        processorDescription.arg5 = arg;
        return new ArgMethodHandler5<>(contextResult, processorDescription);
    }

    public <Arg5> ArgMethodHandler5<ContextResult, PayloadType, ProcessorType, Arg1, Arg2, Arg3, Arg4, Arg5> copyArg(Function<PayloadType, Arg5> arg) {
        processorDescription.arg5 = arg;
        processorDescription.isCopyArg4 = true;
        return new ArgMethodHandler5<>(contextResult, processorDescription);
    }

    public <ProcessorResult> ArgMethodMerger<ContextResult, PayloadType, ProcessorResult> withHandler(MethodReference4Args<ProcessorType, Arg1, Arg2, Arg3, Arg4, CompletableFuture<ProcessorResult>> methodReference) {
        processorDescription.handler4 = methodReference;
        ReactorReflector.getMethodInvocationPoint().ifPresent(source -> processorDescription.handleBySource = source);

        return new ArgMethodMerger<>(contextResult, mergerInfo -> {
            processorDescription.merger = mergerInfo.merger;
            processorDescription.mergeSource = mergerInfo.mergerSource;
        });
    }
}
