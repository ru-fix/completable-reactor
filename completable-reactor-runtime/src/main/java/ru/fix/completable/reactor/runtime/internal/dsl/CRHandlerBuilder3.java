package ru.fix.completable.reactor.runtime.internal.dsl;

import ru.fix.completable.reactor.api.Reactored;
import ru.fix.completable.reactor.runtime.dsl.Handler3Args;
import ru.fix.completable.reactor.runtime.dsl.HandlerBuilder3;
import ru.fix.completable.reactor.runtime.dsl.HandlerBuilder4;
import ru.fix.completable.reactor.runtime.dsl.ProcessorMergerBuilder;
import ru.fix.completable.reactor.runtime.internal.LambdaReflector;
import ru.fix.completable.reactor.runtime.internal.ReactorReflector;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public class CRHandlerBuilder3<PayloadType, Arg1, Arg2, Arg3> implements HandlerBuilder3<PayloadType, Arg1, Arg2, Arg3> {

    final CRProcessorDescription<PayloadType> processorDescription;

    CRHandlerBuilder3(CRProcessorDescription<PayloadType> processorDescription) {
        this.processorDescription = processorDescription;
    }

    public <Arg4> HandlerBuilder4<PayloadType, Arg1, Arg2, Arg3, Arg4> passArg(Function<PayloadType, Arg4> arg) {
        processorDescription.arg4 = arg;
        return new CRHandlerBuilder4<>(processorDescription);
    }

    public <Arg4> HandlerBuilder4<PayloadType, Arg1, Arg2, Arg3, Arg4> copyArg(Function<PayloadType, Arg4> arg) {
        processorDescription.arg4 = arg;
        processorDescription.isCopyArg3 = true;
        return new CRHandlerBuilder4<>(processorDescription);
    }

    public <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(Handler3Args<Arg1, Arg2, Arg3, ProcessorResult> handler) {
        processorDescription.handler3 = handler;
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

        return new CRProcessorMergerBuilder<>(processorDescription);
    }
}
