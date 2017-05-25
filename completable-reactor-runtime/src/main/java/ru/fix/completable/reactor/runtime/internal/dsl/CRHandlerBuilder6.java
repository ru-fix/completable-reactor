package ru.fix.completable.reactor.runtime.internal.dsl;

import ru.fix.completable.reactor.runtime.dsl.Handler6Args;
import ru.fix.completable.reactor.runtime.dsl.HandlerBuilder6;
import ru.fix.completable.reactor.runtime.dsl.ProcessorMergerBuilder;

import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public class CRHandlerBuilder6<PayloadType, Arg1, Arg2, Arg3, Arg4, Arg5, Arg6>
        implements HandlerBuilder6<PayloadType, Arg1, Arg2, Arg3, Arg4, Arg5, Arg6> {

    final CRProcessorDescription<PayloadType> processorDescription;

    CRHandlerBuilder6(CRProcessorDescription<PayloadType> processorDescription) {
        this.processorDescription = processorDescription;
    }

    @Override
    public <Arg7> CRHandlerBuilder7<PayloadType, Arg1, Arg2, Arg3, Arg4, Arg5, Arg6, Arg7> passArg(
            Function<PayloadType, Arg7> arg) {
        processorDescription.arg7 = arg;
        return new CRHandlerBuilder7<>(processorDescription);
    }

    @Override
    public <Arg7> CRHandlerBuilder7<PayloadType, Arg1, Arg2, Arg3, Arg4, Arg5, Arg6, Arg7> copyArg(
            Function<PayloadType, Arg7> arg) {
        processorDescription.arg7 = arg;
        processorDescription.isCopyArg7 = true;
        return new CRHandlerBuilder7<>(processorDescription);
    }

    @Override
    public <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(
            Handler6Args<Arg1, Arg2, Arg3, Arg4, Arg5, Arg6, ProcessorResult> handler) {
        return withHandler(null, null, handler);
    }

    @Override
    public <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(
            String title,
            Handler6Args<Arg1, Arg2, Arg3, Arg4, Arg5, Arg6, ProcessorResult> handler) {
        return withHandler(title, null, handler);
    }

    @Override
    public <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(
            String title,
            String[] docs,
            Handler6Args<Arg1, Arg2, Arg3, Arg4, Arg5, Arg6, ProcessorResult> handler) {

        processorDescription.handler6 = handler;
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
