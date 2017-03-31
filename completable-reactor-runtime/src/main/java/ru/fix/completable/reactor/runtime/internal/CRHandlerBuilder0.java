package ru.fix.completable.reactor.runtime.internal;

import ru.fix.completable.reactor.runtime.ReactorReflector;
import ru.fix.completable.reactor.runtime.dsl.Handler0Args;
import ru.fix.completable.reactor.runtime.dsl.HandlerBuilder0;


import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public class CRHandlerBuilder0<PayloadType> implements HandlerBuilder0<PayloadType> {

    final CRProcessorDescription<PayloadType> processorDescription;

    public CRHandlerBuilder0(CRProcessorDescription<PayloadType> processorDescription) {
        this.processorDescription = processorDescription;
    }

    @Override
    public <ProcessorResult> MergerBuilder<PayloadType, ProcessorResult> withHandler(
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

    @Override
    public <Arg1> CRHandlerBuilder1<PayloadType, Arg1> passArg(Function<PayloadType, Arg1> arg) {
        processorDescription.arg1 = arg;
        return new CRHandlerBuilder1<>(processorDescription);
    }

    @Override
    public <Arg1> CRHandlerBuilder1<PayloadType, Arg1> copyArg(Function<PayloadType, Arg1> arg) {
        processorDescription.arg1 = arg;
        processorDescription.isCopyArg1 = true;
        return new CRHandlerBuilder1<>(processorDescription);
    }
}
