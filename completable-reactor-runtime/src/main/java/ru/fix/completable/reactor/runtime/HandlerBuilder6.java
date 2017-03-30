package ru.fix.completable.reactor.runtime;

import ru.fix.completable.reactor.runtime.function.Handler6Args;

import java.util.concurrent.CompletableFuture;

/**
 * @author Kamil Asfandiyarov
 */
public class HandlerBuilder6<ContextResult, PayloadType, Arg1, Arg2, Arg3, Arg4, Arg5, Arg6> extends BaseDescriberItem<ContextResult> {
    public HandlerBuilder6(ContextResult contextResult, GraphProcessorDescription processorDescription) {
        super(contextResult, processorDescription);
    }

    public <ProcessorResult> MergerBuilder<ContextResult, PayloadType, ProcessorResult> withHandler(
            Handler6Args<Arg1, Arg2, Arg3, Arg4, Arg5, Arg6, CompletableFuture<ProcessorResult>> handler) {

        processorDescription.handler6 = handler;
        ReactorReflector.getMethodInvocationPoint().ifPresent(source -> processorDescription.handleBySource = source);

        return new MergerBuilder<>(contextResult, mergerInfo -> {
            processorDescription.merger = mergerInfo.merger;
            processorDescription.mergeSource = mergerInfo.mergerSource;
            processorDescription.mergerDocs = mergerInfo.mergerDocs;
            processorDescription.mergerTitle = mergerInfo.mergerTitle;
        });
    }
}
