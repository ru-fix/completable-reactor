package ru.fix.completable.reactor.runtime.internal.dsl;

import ru.fix.completable.reactor.api.Reactored;
import ru.fix.completable.reactor.runtime.dsl.Handler0Args;
import ru.fix.completable.reactor.runtime.dsl.HandlerBuilder0;
import ru.fix.completable.reactor.runtime.dsl.ProcessorMergerBuilder;
import ru.fix.completable.reactor.runtime.internal.LambdaReflector;
import ru.fix.completable.reactor.runtime.internal.ReactorReflector;

import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public class CRHandlerBuilder0<PayloadType> implements HandlerBuilder0<PayloadType> {

    final CRProcessorDescription<PayloadType> processorDescription;

    CRHandlerBuilder0(CRProcessorDescription<PayloadType> processorDescription) {
        this.processorDescription = processorDescription;
    }

    @Override
    public <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(
            Handler0Args<ProcessorResult> handler) {

        processorDescription.handler0 = handler;

        ReactorReflector.getMethodInvocationPoint().ifPresent(source -> processorDescription.handleBySource = source);

        LambdaReflector.annotatedMethodReference(handler, Reactored.class).ifPresent(method -> {
            processorDescription.processorType = method.getMethodClass();
            processorDescription.handlerTitle = method.getMethod().getName();
            processorDescription.handlerDocs = method.getAnnotation().value();
        });

        return new CRProcessorMergerBuilder<>(processorDescription);
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
