package ru.fix.completable.reactor.runtime.internal;

import ru.fix.completable.reactor.runtime.dsl.Handler6Args;
import ru.fix.completable.reactor.runtime.dsl.HandlerBuilder6;
import ru.fix.completable.reactor.runtime.dsl.ProcessorMergerBuilder;

/**
 * @author Kamil Asfandiyarov
 */
public class CRHandlerBuilder6<PayloadType, Arg1, Arg2, Arg3, Arg4, Arg5, Arg6> implements HandlerBuilder6<PayloadType, Arg1, Arg2, Arg3, Arg4, Arg5, Arg6> {

    final CRProcessorDescription<PayloadType> processorDescription;

    CRHandlerBuilder6(CRProcessorDescription<PayloadType> processorDescription) {
        this.processorDescription = processorDescription;
    }

    @Override
    public <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(
            Handler6Args<Arg1, Arg2, Arg3, Arg4, Arg5, Arg6, ProcessorResult> handler) {

        processorDescription.handler6 = handler;
        ReactorReflector.getMethodInvocationPoint().ifPresent(source -> processorDescription.handleBySource = source);

        return new CRProcessorMergerBuilder<>(this.processorDescription);
    }
}
