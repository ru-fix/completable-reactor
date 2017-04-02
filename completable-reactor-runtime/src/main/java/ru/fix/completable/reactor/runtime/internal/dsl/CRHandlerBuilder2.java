package ru.fix.completable.reactor.runtime.internal.dsl;

import ru.fix.completable.reactor.api.Reactored;
import ru.fix.completable.reactor.runtime.dsl.Handler2Args;
import ru.fix.completable.reactor.runtime.dsl.HandlerBuilder2;
import ru.fix.completable.reactor.runtime.dsl.HandlerBuilder3;
import ru.fix.completable.reactor.runtime.dsl.ProcessorMergerBuilder;
import ru.fix.completable.reactor.runtime.internal.LambdaReflector;
import ru.fix.completable.reactor.runtime.internal.ReactorReflector;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public class CRHandlerBuilder2<PayloadType, Arg1, Arg2> implements HandlerBuilder2<PayloadType, Arg1, Arg2> {

    final CRProcessorDescription<PayloadType> processorDescription;

    CRHandlerBuilder2(CRProcessorDescription<PayloadType> processorDescription) {
        this.processorDescription = processorDescription;
    }

    @Override
    public <Arg3> HandlerBuilder3<PayloadType, Arg1, Arg2, Arg3> passArg(Function<PayloadType, Arg3> arg) {
        processorDescription.arg3 = arg;
        return new CRHandlerBuilder3<>(processorDescription);
    }

    @Override
    public <Arg3> HandlerBuilder3<PayloadType, Arg1, Arg2, Arg3> copyArg(Function<PayloadType, Arg3> arg) {
        processorDescription.arg3 = arg;
        processorDescription.isCopyArg3 = true;
        return new CRHandlerBuilder3<>(processorDescription);
    }

    @Override
    public <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(Handler2Args<Arg1, Arg2, ProcessorResult> handler) {
        processorDescription.handler2 = handler;
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
