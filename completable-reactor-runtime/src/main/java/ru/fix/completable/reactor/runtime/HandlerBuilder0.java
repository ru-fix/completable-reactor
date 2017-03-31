package ru.fix.completable.reactor.runtime;

import ru.fix.completable.reactor.runtime.dsl.Handler0Args;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public class HandlerBuilder0<ContextResult, PayloadType> extends BaseDescriberItem<ContextResult> {

    HandlerBuilder0(ContextResult contextResult, GraphProcessorDescription processorDescription) {
        super(contextResult, processorDescription);
    }

    public <ProcessorResult> MergerBuilder<ContextResult, PayloadType, ProcessorResult> withHandler(
            Handler0Args<CompletableFuture<ProcessorResult>> handler) {

        processorDescription.handler0 = handler;
        ReactorReflector.getMethodInvocationPoint().ifPresent(source -> processorDescription.handleBySource = source);

        return new MergerBuilder<>(contextResult, mergerInfo -> {
            processorDescription.merger = mergerInfo.merger;
            processorDescription.mergeSource = mergerInfo.mergerSource;
            processorDescription.mergerDocs = mergerInfo.mergerDocs;
            processorDescription.mergerTitle = mergerInfo.mergerTitle;
        });
    }


    public <Arg1> HandlerBuilder1<ContextResult, PayloadType, Arg1> passArg(Function<PayloadType, Arg1> arg) {
        processorDescription.arg1 = arg;
        return new HandlerBuilder1<>(contextResult, processorDescription);
    }

    public <Arg1> HandlerBuilder1<ContextResult, PayloadType, Arg1> copyArg(Function<PayloadType, Arg1> arg) {
        processorDescription.arg1 = arg;
        processorDescription.isCopyArg1 = true;
        return new HandlerBuilder1<>(contextResult, processorDescription);
    }
}
