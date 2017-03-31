package ru.fix.completable.reactor.runtime;

import ru.fix.completable.reactor.runtime.dsl.Handler5Args;

import java.util.concurrent.CompletableFuture;

/**
 * @author Kamil Asfandiyarov
 */
public class HandlerBuilder5<ContextResult, PayloadType, Arg1, Arg2, Arg3, Arg4, Arg5> extends BaseDescriberItem<ContextResult> {
    HandlerBuilder5(ContextResult contextResult, GraphProcessorDescription processorDescription) {
        super(contextResult, processorDescription);
    }

    public <ProcessorResult> MergerBuilder<ContextResult, PayloadType, ProcessorResult> withHandler(Handler5Args<Arg1, Arg2, Arg3, Arg4, Arg5, CompletableFuture<ProcessorResult>> handler) {
        processorDescription.handler5 = handler;
        ReactorReflector.getMethodInvocationPoint().ifPresent(source -> processorDescription.handleBySource = source);

        return new MergerBuilder<>(contextResult, mergerInfo -> {
            processorDescription.merger = mergerInfo.merger;
            processorDescription.mergeSource = mergerInfo.mergerSource;
            processorDescription.mergerDocs = mergerInfo.mergerDocs;
            processorDescription.mergerTitle = mergerInfo.mergerTitle;
        });
    }
}
