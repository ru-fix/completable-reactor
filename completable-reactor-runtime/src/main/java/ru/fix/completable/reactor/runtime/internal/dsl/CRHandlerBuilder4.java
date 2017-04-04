package ru.fix.completable.reactor.runtime.internal.dsl;

import ru.fix.completable.reactor.api.Reactored;
import ru.fix.completable.reactor.runtime.dsl.Handler4Args;
import ru.fix.completable.reactor.runtime.dsl.HandlerBuilder4;
import ru.fix.completable.reactor.runtime.dsl.HandlerBuilder5;
import ru.fix.completable.reactor.runtime.dsl.ProcessorMergerBuilder;
import ru.fix.completable.reactor.runtime.internal.LambdaReflector;
import ru.fix.completable.reactor.runtime.internal.ReactorReflector;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public class CRHandlerBuilder4<PayloadType, Arg1, Arg2, Arg3, Arg4> implements HandlerBuilder4<PayloadType, Arg1, Arg2, Arg3, Arg4>{
    final CRProcessorDescription<PayloadType> processorDescription;

    CRHandlerBuilder4(CRProcessorDescription<PayloadType> processorDescription) {
        this.processorDescription = processorDescription;
    }

    public <Arg5> HandlerBuilder5<PayloadType, Arg1, Arg2, Arg3, Arg4, Arg5> passArg(Function<PayloadType, Arg5> arg) {
        processorDescription.arg5 = arg;
        return new CRHandlerBuilder5<>(processorDescription);
    }

    public <Arg5> HandlerBuilder5<PayloadType, Arg1, Arg2, Arg3, Arg4, Arg5> copyArg(Function<PayloadType, Arg5> arg) {
        processorDescription.arg5 = arg;
        processorDescription.isCopyArg4 = true;
        return new CRHandlerBuilder5<>(processorDescription);
    }

    public <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(Handler4Args<Arg1, Arg2, Arg3, Arg4, ProcessorResult> handler) {
        processorDescription.handler4 = handler;
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
