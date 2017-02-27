package ru.fix.cpapsm.completable.reactor.runtime;

import java.util.concurrent.CompletableFuture;

/**
 * @author Kamil Asfandiyarov
 */
public class ArgMethodHandler5<ContextResult, PayloadType, ProcessorType , Arg1, Arg2, Arg3, Arg4, Arg5> extends BaseDescriberItem<ContextResult> {
    public ArgMethodHandler5(ContextResult contextResult, GraphProcessorDescription processorDescription) {
        super(contextResult, processorDescription);
    }

    public <ProcessorResult> ArgMethodMerger<ContextResult, PayloadType, ProcessorResult> withHandler(MethodReference5Args<ProcessorType, Arg1, Arg2, Arg3, Arg4, Arg5, CompletableFuture<ProcessorResult>> methodReference) {
        processorDescription.handler5 = methodReference;
        ReactorReflector.getMethodInvocationPoint().ifPresent(source -> processorDescription.handleBySource = source);

        return new ArgMethodMerger<>(contextResult, mergerInfo -> {
            processorDescription.merger = mergerInfo.merger;
            processorDescription.mergeSource = mergerInfo.mergerSource;
        });
    }
}
