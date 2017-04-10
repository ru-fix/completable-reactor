package ru.fix.completable.reactor.runtime.internal.dsl;

import lombok.val;
import ru.fix.completable.reactor.api.Reactored;
import ru.fix.completable.reactor.runtime.internal.LambdaReflector;
import ru.fix.completable.reactor.runtime.internal.ReactorReflector;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author Kamil Asfandiyarov
 */
public class BuilderReflector {

    static void initializeProcessorDescription(Serializable methodReference, CRProcessorDescription processorDescription){

        ReactorReflector.getMethodInvocationPoint().ifPresent(source -> processorDescription.withHandlerSource = source);

        val method = LambdaReflector.annotatedMethodReference(methodReference, Reactored.class);

        processorDescription.processorType = method.getMethodClass();

        Optional.ofNullable(method.getMethodClass().getAnnotation(Reactored.class))
                .map(annotation -> (Reactored)annotation)
                .map(Reactored::value)
                .ifPresent(processorDescription::setProcessorDoc);

        processorDescription.handlerTitle = method.getMethod().getName();
        processorDescription.handlerDocs = method.getAnnotation().map(Reactored::value).orElse(null);
    }
}
