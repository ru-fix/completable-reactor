package ru.fix.completable.reactor.runtime.internal;

import ru.fix.completable.reactor.runtime.dsl.Handler5Args;
import ru.fix.completable.reactor.runtime.dsl.HandlerBuilder5;
import ru.fix.completable.reactor.runtime.dsl.HandlerBuilder6;
import ru.fix.completable.reactor.runtime.dsl.ProcessorMergerBuilder;

import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public class CRHandlerBuilder5<PayloadType , Arg1, Arg2, Arg3, Arg4, Arg5> implements HandlerBuilder5<PayloadType , Arg1, Arg2, Arg3, Arg4, Arg5> {
    CRProcessorDescription<PayloadType> processorDescription;

    CRHandlerBuilder5(CRProcessorDescription<PayloadType> processorDescription) {
        this.processorDescription = processorDescription;
    }

    @Override
    public <Arg6> HandlerBuilder6<PayloadType, Arg1, Arg2, Arg3, Arg4, Arg5, Arg6> passArg(Function<PayloadType, Arg5> arg) {
        processorDescription.arg6 = arg;
        return new CRHandlerBuilder6<>(processorDescription);
    }

    @Override
    public <Arg6> CRHandlerBuilder6<PayloadType, Arg1, Arg2, Arg3, Arg4, Arg5, Arg6> copyArg(Function<PayloadType, Arg5> arg) {
        processorDescription.arg6 = arg;
        processorDescription.isCopyArg6 = true;
        return new CRHandlerBuilder6<>(processorDescription);
    }

    @Override
    public <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(Handler5Args<Arg1, Arg2, Arg3, Arg4, Arg5, ProcessorResult> handler) {
        processorDescription.handler5 = handler;
        ReactorReflector.getMethodInvocationPoint().ifPresent(source -> processorDescription.handleBySource = source);

        return new CRProcessorMergerBuilder<>(processorDescription);
    }
}
