package ru.fix.completable.reactor.runtime;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.fix.completable.reactor.api.Reactored;
import ru.fix.completable.reactor.api.ReactorGraphModel;
import ru.fix.completable.reactor.runtime.dsl.ProcessorMerger;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author Kamil Asfandiyarov
 */
public class MergerBuilder<ContextResult, PayloadType, ProcessorResult> {

    @Data
    @Accessors(chain = true)
    public static class MergerInfo<PayloadType, ProcessorResult> {
        ReactorGraphModel.Source mergerSource;
        String[] mergerDocs;
        String mergerTitle;
        ProcessorMerger<PayloadType, ProcessorResult> merger;
    }

    final ContextResult contextResult;
    final Consumer<MergerInfo<PayloadType, ProcessorResult>> mergerConsumer;

    public MergerBuilder(ContextResult contextResult, Consumer<MergerInfo<PayloadType, ProcessorResult>> mergerConsumer) {
        this.contextResult = contextResult;
        this.mergerConsumer = mergerConsumer;
    }

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

        MergerInfo<PayloadType, ProcessorResult> mergerInfo = new MergerInfo<>();
        mergerInfo.merger = merger;
        mergerInfo.mergerSource = ReactorReflector.getMethodInvocationPoint().orElse(null);
        mergerInfo.mergerTitle =  title;
        mergerInfo.mergerDocs = docs;

        mergerConsumer.accept(mergerInfo);
        return contextResult;
    }

    public ContextResult withoutMerger() {
        MergerInfo<PayloadType, ProcessorResult> mergerInfo = new MergerInfo<>();
        mergerInfo.merger = null;
        mergerInfo.mergerSource = ReactorReflector.getMethodInvocationPoint().orElse(null);

        mergerConsumer.accept(mergerInfo);
        return contextResult;
    }
}
