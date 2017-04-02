package ru.fix.completable.reactor.runtime.internal.dsl;

import ru.fix.completable.reactor.api.Reactored;
import ru.fix.completable.reactor.runtime.dsl.Handler1Arg;
import ru.fix.completable.reactor.runtime.dsl.HandlerBuilder1;
import ru.fix.completable.reactor.runtime.dsl.HandlerBuilder2;
import ru.fix.completable.reactor.runtime.internal.LambdaReflector;
import ru.fix.completable.reactor.runtime.internal.ReactorReflector;

import java.util.Optional;
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
        ReactorReflector.getMethodInvocationPoint().ifPresent(source -> processorDescription.handleBySource = source);

        LambdaReflector.annotatedMethodReference(handler, Reactored.class).ifPresent(method -> {
            processorDescription.processorType = method.getMethodClass();

            Optional.ofNullable(method.getMethodClass().getAnnotation(Reactored.class))
                    .map(annotation -> (Reactored)annotation)
                    .map(Reactored::value)
                    .ifPresent(processorDescription::setProcessorDoc);

            processorDescription.handlerTitle = method.getMethod().getName();
            processorDescription.handlerDocs = method.getAnnotation().value();
        });

        return new CRProcessorMergerBuilder<>(processorDescription);
    }
}
