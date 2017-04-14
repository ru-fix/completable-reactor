package ru.fix.completable.reactor.runtime.internal.dsl;

import ru.fix.completable.reactor.runtime.dsl.Handler4Args;
import ru.fix.completable.reactor.runtime.dsl.HandlerBuilder4;
import ru.fix.completable.reactor.runtime.dsl.HandlerBuilder5;
import ru.fix.completable.reactor.runtime.dsl.ProcessorMergerBuilder;

import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public class CRHandlerBuilder4<PayloadType, Arg1, Arg2, Arg3, Arg4> implements HandlerBuilder4<PayloadType, Arg1, Arg2, Arg3, Arg4> {
    final CRProcessorDescription<PayloadType> processorDescription;

    CRHandlerBuilder4(CRProcessorDescription<PayloadType> processorDescription) {
        this.processorDescription = processorDescription;
    }

    @Override
    public <Arg5> HandlerBuilder5<PayloadType, Arg1, Arg2, Arg3, Arg4, Arg5> passArg(Function<PayloadType, Arg5> arg) {
        processorDescription.arg5 = arg;
        return new CRHandlerBuilder5<>(processorDescription);
    }

    @Override
    public <Arg5> HandlerBuilder5<PayloadType, Arg1, Arg2, Arg3, Arg4, Arg5> copyArg(Function<PayloadType, Arg5> arg) {
        processorDescription.arg5 = arg;
        processorDescription.isCopyArg4 = true;
        return new CRHandlerBuilder5<>(processorDescription);
    }

    @Override
    public <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(
            Handler4Args<Arg1, Arg2, Arg3, Arg4, ProcessorResult> handler) {
        return withHandler(null, null, handler);
    }

    @Override
    public <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(
            String title,
            Handler4Args<Arg1, Arg2, Arg3, Arg4, ProcessorResult> handler) {
        return withHandler(title, null, handler);
    }

    @Override
    public <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(
            String title,
            String[] docs,
            Handler4Args<Arg1, Arg2, Arg3, Arg4, ProcessorResult> handler) {

        processorDescription.handler4 = handler;

        BuilderReflector.initializeProcessorDescription(handler, processorDescription);

        if (title != null) {
            processorDescription.setHandlerTitle(title);
        }

        if (docs != null) {
            processorDescription.setHandlerDocs(docs);
        }

        return new CRProcessorMergerBuilder<>(processorDescription);
    }
}
