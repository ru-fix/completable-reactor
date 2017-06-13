package ru.fix.completable.reactor.runtime.internal.dsl;

import lombok.val;
import ru.fix.completable.reactor.api.Reactored;
import ru.fix.completable.reactor.runtime.dsl.SubgraphDescription;
import ru.fix.completable.reactor.runtime.dsl.SubgraphMerger;
import ru.fix.completable.reactor.runtime.dsl.SubgraphMergerBuilder;
import ru.fix.completable.reactor.runtime.internal.LambdaReflector;
import ru.fix.completable.reactor.runtime.internal.ReactorReflector;

/**
 * @author Kamil Asfandiyarov
 */
public class CRSubgraphMergerBuilder<PayloadType, SubgraphResult> implements SubgraphMergerBuilder<PayloadType, SubgraphResult> {

    final CRSubgraphDescription<PayloadType> subgraphDescription;

    public CRSubgraphMergerBuilder(CRSubgraphDescription<PayloadType> subgraphDescription) {
        this.subgraphDescription = subgraphDescription;
    }


    @Override
    public SubgraphDescription<PayloadType> withMerger(SubgraphMerger<PayloadType, SubgraphResult> subgraphMerger) {
        return withMerger(null, new String[]{}, subgraphMerger);
    }

    @Override
    public SubgraphDescription<PayloadType> withMerger(String title, SubgraphMerger<PayloadType, SubgraphResult> subgraphMerger) {
        return withMerger(title, new String[]{}, subgraphMerger);
    }

    @Override
    public SubgraphDescription<PayloadType> withMerger(String title, String doc, SubgraphMerger<PayloadType, SubgraphResult> subgraphMerger) {
        return withMerger(title, new String[]{doc}, subgraphMerger);
    }

    @Override
    public SubgraphDescription<PayloadType> withMerger(String title, String[] docs, SubgraphMerger<PayloadType, SubgraphResult> subgraphMerger) {

        val annotatedMethod = LambdaReflector.tryReflectAnnotatedMethodReference(subgraphMerger, Reactored.class)
                .orElse(null);

        if(title == null && annotatedMethod != null) {
            title = annotatedMethod.getMethod().getName();
        }

        if((docs == null || docs.length == 0) && annotatedMethod != null) {
            docs = annotatedMethod.getAnnotation().map(Reactored::value).orElse(null);
        }

        this.subgraphDescription.merger = subgraphMerger;
        this.subgraphDescription.mergeSource = ReactorReflector.getMethodInvocationPoint().orElse(null);
        this.subgraphDescription.mergerTitle = title;
        this.subgraphDescription.mergerDocs = docs;

        return subgraphDescription;
    }

    @Override
    public SubgraphDescription<PayloadType> withoutMerger() {
        this.subgraphDescription.merger = null;
        this.subgraphDescription.mergeSource = ReactorReflector.getMethodInvocationPoint().orElse(null);
        return this.subgraphDescription;
    }
}
