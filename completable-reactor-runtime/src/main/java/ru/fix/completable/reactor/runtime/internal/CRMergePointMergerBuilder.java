package ru.fix.completable.reactor.runtime.internal;

import ru.fix.completable.reactor.api.Reactored;
import ru.fix.completable.reactor.runtime.dsl.*;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * @author Kamil Asfandiyarov
 */
public class CRMergePointMergerBuilder<PayloadType> implements MergePointMergerBuilder<PayloadType> {

    final CRMergePointDescription<PayloadType> mergePointDescription;

    public CRMergePointMergerBuilder(CRMergePointDescription<PayloadType> mergePointDescription) {
        this.mergePointDescription = mergePointDescription;
    }

    @Override
    public MergePointDescription<PayloadType> withMerger(MergePointMerger<PayloadType> mergePointMerger) {
        return withMerger(null, null, mergePointMerger);
    }

    @Override
    public MergePointDescription<PayloadType> withMerger(String title, MergePointMerger<PayloadType> mergePointMerger) {
        return withMerger(title, null, mergePointMerger);
    }

    @Override
    public MergePointDescription<PayloadType> withMerger(String title, String[] docs, MergePointMerger<PayloadType> mergePointMerger) {

        Optional<LambdaReflector.AnnotatedMethod> mergerMethod = LambdaReflector.annotatedMethodReference(mergePointMerger, Reactored.class);

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

        this.mergePointDescription.merger = mergePointMerger;
        this.mergePointDescription.mergerSource = ReactorReflector.getMethodInvocationPoint().orElse(null);
        this.mergePointDescription.mergerTitle = title;
        this.mergePointDescription.mergerDocs = docs;

        return this.mergePointDescription;
    }

    public MergePointDescription<PayloadType> withoutMerger() {
        this.mergePointDescription.merger = null;
        this.mergePointDescription.mergerSource = ReactorReflector.getMethodInvocationPoint().orElse(null);
        return this.mergePointDescription;
    }
}
