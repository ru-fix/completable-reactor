package ru.fix.completable.reactor.runtime;

import ru.fix.completable.reactor.runtime.function.Handler1Arg;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public class HandlerBuilder1<ContextResult, PayloadType, ProcessorType, Arg1> extends BaseDescriberItem<ContextResult> {

    public HandlerBuilder1(ContextResult contextResult, GraphProcessorDescription processorDescription) {
        super(contextResult, processorDescription);
    }

    public <Arg2> HandlerBuilder2<ContextResult, PayloadType, ProcessorType, Arg1, Arg2> passArg(Function<PayloadType, Arg2> arg) {
        processorDescription.arg2 = arg;
        return new HandlerBuilder2<>(contextResult, processorDescription);
    }

    public <Arg2> HandlerBuilder2<ContextResult, PayloadType, ProcessorType, Arg1, Arg2> copyArg(Function<PayloadType, Arg2> arg) {
        processorDescription.arg2 = arg;
        processorDescription.isCopyArg2 = true;
        return new HandlerBuilder2<>(contextResult, processorDescription);
    }

    public <ProcessorResult> MergerBuilder<ContextResult, PayloadType, ProcessorResult> withHandler(
            Handler1Arg<Arg1, CompletableFuture<ProcessorResult>> handler) {

        processorDescription.handler1 = handler;
        ReactorReflector.getMethodInvocationPoint().ifPresent(source -> processorDescription.handleBySource = source);

        return new MergerBuilder<>(contextResult, mergerInfo -> {
            processorDescription.merger = mergerInfo.merger;
            processorDescription.mergeSource = mergerInfo.mergerSource;
            processorDescription.mergerDocs = mergerInfo.mergerDocs;
            processorDescription.mergerTitle = mergerInfo.mergerTitle;
        });
    }
}
