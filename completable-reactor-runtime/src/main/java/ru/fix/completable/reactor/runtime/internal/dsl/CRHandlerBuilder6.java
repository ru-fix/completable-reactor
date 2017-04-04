package ru.fix.completable.reactor.runtime.internal.dsl;

import ru.fix.completable.reactor.api.Reactored;
import ru.fix.completable.reactor.runtime.dsl.Handler6Args;
import ru.fix.completable.reactor.runtime.dsl.HandlerBuilder6;
import ru.fix.completable.reactor.runtime.dsl.ProcessorMergerBuilder;
import ru.fix.completable.reactor.runtime.internal.LambdaReflector;
import ru.fix.completable.reactor.runtime.internal.ReactorReflector;

import java.util.Optional;

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
        ReactorReflector.getMethodInvocationPoint().ifPresent(source -> processorDescription.withHandlerSource = source);

        LambdaReflector.annotatedMethodReference(handler, Reactored.class).ifPresent(method -> {
            processorDescription.processorType = method.getMethodClass();

            Optional.ofNullable(method.getMethodClass().getAnnotation(Reactored.class))
                    .map(annotation -> (Reactored)annotation)
                    .map(Reactored::value)
                    .ifPresent(processorDescription::setProcessorDoc);

            processorDescription.handlerTitle = method.getMethod().getName();
            processorDescription.handlerDocs = method.getAnnotation().value();
        });

        return new CRProcessorMergerBuilder<>(this.processorDescription);
    }
}
