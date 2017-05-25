package ru.fix.completable.reactor.runtime.internal.dsl;

import ru.fix.completable.reactor.runtime.dsl.Handler7Args;
import ru.fix.completable.reactor.runtime.dsl.HandlerBuilder7;
import ru.fix.completable.reactor.runtime.dsl.ProcessorMergerBuilder;

/**
 * @author Kamil Asfandiyarov
 */
public class CRHandlerBuilder7<PayloadType, Arg1, Arg2, Arg3, Arg4, Arg5, Arg6, Arg7> implements
        HandlerBuilder7<PayloadType, Arg1, Arg2, Arg3, Arg4, Arg5, Arg6, Arg7> {

    final CRProcessorDescription<PayloadType> processorDescription;

    CRHandlerBuilder7(CRProcessorDescription<PayloadType> processorDescription) {
        this.processorDescription = processorDescription;
    }

    @Override
    public <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(
            Handler7Args<Arg1, Arg2, Arg3, Arg4, Arg5, Arg6, Arg7, ProcessorResult> handler) {
        return withHandler(null, null, handler);
    }

    @Override
    public <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(
            String title,
            Handler7Args<Arg1, Arg2, Arg3, Arg4, Arg5, Arg6, Arg7, ProcessorResult> handler) {
        return withHandler(title, null, handler);
    }

    @Override
    public <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(
            String title,
            String[] docs,
            Handler7Args<Arg1, Arg2, Arg3, Arg4, Arg5, Arg6, Arg7, ProcessorResult> handler) {

        processorDescription.handler7 = handler;
        BuilderReflector.initializeProcessorDescription(handler, processorDescription);

        if (title != null) {
            processorDescription.setHandlerTitle(title);
        }

        if (docs != null) {
            processorDescription.setHandlerDocs(docs);
        }

        return new CRProcessorMergerBuilder<>(this.processorDescription);
    }
}
