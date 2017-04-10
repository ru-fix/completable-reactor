package ru.fix.completable.reactor.runtime.internal.dsl;

import ru.fix.completable.reactor.api.Reactored;
import ru.fix.completable.reactor.runtime.dsl.MergePointDescription;
import ru.fix.completable.reactor.runtime.dsl.MergePointMerger;
import ru.fix.completable.reactor.runtime.dsl.MergePointMergerBuilder;
import ru.fix.completable.reactor.runtime.internal.LambdaReflector;
import ru.fix.completable.reactor.runtime.internal.ReactorReflector;

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

        LambdaReflector.AnnotatedMethod<Reactored> mergerMethod = LambdaReflector.annotatedMethodReference(
                mergePointMerger,
                Reactored.class);

        if(title == null) {
            title = mergerMethod.getMethod().getName();
        }

        if(docs == null) {
            docs = mergerMethod.getAnnotation().map(Reactored::value).orElse(null);
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
