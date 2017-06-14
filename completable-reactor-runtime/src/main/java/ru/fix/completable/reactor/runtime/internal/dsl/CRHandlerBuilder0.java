package ru.fix.completable.reactor.runtime.internal.dsl;

import ru.fix.completable.reactor.runtime.dsl.Handler0Args;
import ru.fix.completable.reactor.runtime.dsl.HandlerBuilder0;
import ru.fix.completable.reactor.runtime.dsl.ProcessorMergerBuilder;

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

    @Override
    public <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(
            Handler0Args<ProcessorResult> handler) {

        return withHandler(null, new String[]{}, handler);
    }

    @Override
    public <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(
            String title,
            Handler0Args<ProcessorResult> handler) {

        return withHandler(title, new String[]{}, handler);
    }

    @Override
    public <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(
            String title,
            String doc,
            Handler0Args<ProcessorResult> handler) {

        return withHandler(title, new String[]{doc}, handler);
    }

    @Override
    public <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(
            String title,
            String[] docs,
            Handler0Args<ProcessorResult> handler) {

        processorDescription.handler0 = handler;
        BuilderReflector.initializeProcessorDescription(handler, processorDescription);

        if(title != null) {
            processorDescription.setHandlerTitle(title);
        }

        if(docs != null && docs.length != 0){
            processorDescription.setHandlerDocs(docs);
        }

        return new CRProcessorMergerBuilder<>(processorDescription);
    }
}
