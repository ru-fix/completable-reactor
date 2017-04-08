package ru.fix.completable.reactor.runtime.internal.dsl;

import ru.fix.completable.reactor.runtime.dsl.Handler1Arg;
import ru.fix.completable.reactor.runtime.dsl.HandlerBuilder1;
import ru.fix.completable.reactor.runtime.dsl.HandlerBuilder2;

import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public class CRHandlerBuilder1<PayloadType, Arg1>  implements HandlerBuilder1<PayloadType, Arg1> {

    final CRProcessorDescription<PayloadType> processorDescription;

    CRHandlerBuilder1(CRProcessorDescription<PayloadType> processorDescription) {
        this.processorDescription = processorDescription;
    }

    @Override
    public <Arg2> HandlerBuilder2<PayloadType, Arg1, Arg2> passArg(Function<PayloadType, Arg2> arg) {
        processorDescription.arg2 = arg;
        return new CRHandlerBuilder2<>(processorDescription);
    }

    @Override
    public <Arg2> HandlerBuilder2<PayloadType, Arg1, Arg2> copyArg(Function<PayloadType, Arg2> arg) {
        processorDescription.arg2 = arg;
        processorDescription.isCopyArg2 = true;
        return new CRHandlerBuilder2<>(processorDescription);
    }

    @Override
    public <ProcessorResult> CRProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(
            Handler1Arg<Arg1, ProcessorResult> handler) {

        processorDescription.handler1 = handler;
        BuilderReflector.initializeProcessorDescription(handler, processorDescription);

        return new CRProcessorMergerBuilder<>(processorDescription);
    }
}
