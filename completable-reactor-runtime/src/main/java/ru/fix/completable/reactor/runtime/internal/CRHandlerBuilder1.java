package ru.fix.completable.reactor.runtime.internal;

import ru.fix.completable.reactor.runtime.ReactorReflector;
import ru.fix.completable.reactor.runtime.dsl.Handler1Arg;
import ru.fix.completable.reactor.runtime.dsl.HandlerBuilder1;
import ru.fix.completable.reactor.runtime.dsl.HandlerBuilder2;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public class CRHandlerBuilder1<PayloadType, Arg1>  implements HandlerBuilder1<PayloadType, Arg1> {

    final CRProcessorDescription processorDescription;

    public CRHandlerBuilder1(CRProcessorDescription processorDescription) {
        this.processorDescription = processorDescription;
    }

    public <Arg2> HandlerBuilder2<PayloadType, Arg1, Arg2> passArg(Function<PayloadType, Arg2> arg) {
        processorDescription.arg2 = arg;
        return new HandlerBuilder2<>(processorDescription);
    }

    public <Arg2> HandlerBuilder2<PayloadType, Arg1, Arg2> copyArg(Function<PayloadType, Arg2> arg) {
        processorDescription.arg2 = arg;
        processorDescription.isCopyArg2 = true;
        return new HandlerBuilder2<>(contextResult, processorDescription);
    }

    public <ProcessorResult> CRProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(
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
