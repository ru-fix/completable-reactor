package ru.fix.completable.reactor.runtime.dsl;

import ru.fix.completable.reactor.api.Reactored;
import ru.fix.completable.reactor.runtime.LambdaReflector;
import ru.fix.completable.reactor.runtime.ReactorReflector;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * @author Kamil Asfandiyarov
 */
public interface MergerBuilder<ContextResult, PayloadType, ProcessorResult> {
    public ContextResult withMerger(ProcessorMerger<PayloadType, ProcessorResult> merger) {
        return withMerger(null, null, merger);
    }

    public ContextResult withMerger(String title, ProcessorMerger<PayloadType, ProcessorResult> merger) {
        return withMerger(title, null, merger);
    }

    public ContextResult withMerger(String title, String[] docs, ProcessorMerger<PayloadType, ProcessorResult> merger) {

        Optional<LambdaReflector.AnnotatedMethod> mergerMethod = LambdaReflector.annotatedMethodReference(merger, Reactored.class);

        if(title == null) {
            title = mergerMethod
                    .map(LambdaReflector.AnnotatedMethod::getMethod)
                    .map(Method::getName)
                    .orElse(null);
        }

        if(docs == null) {
            docs = mergerMethod
                    .map(LambdaReflector.AnnotatedMethod::getMethod)
                    .map(method -> method.getAnnotation(Reactored.class))
                    .map(Reactored::value)
                    .orElse(null);
        }

        ru.fix.completable.reactor.runtime.MergerBuilder.MergerInfo<PayloadType, ProcessorResult> mergerInfo = new ru.fix.completable.reactor.runtime.MergerBuilder.MergerInfo<>();
        mergerInfo.merger = merger;
        mergerInfo.mergerSource = ReactorReflector.getMethodInvocationPoint().orElse(null);
        mergerInfo.mergerTitle =  title;
        mergerInfo.mergerDocs = docs;

        mergerConsumer.accept(mergerInfo);
        return contextResult;
    }

    public ContextResult withoutMerger() {
}
