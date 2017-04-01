package ru.fix.completable.reactor.runtime.internal;

import ru.fix.completable.reactor.api.Reactored;
import ru.fix.completable.reactor.runtime.dsl.*;

import java.lang.reflect.Method;
import java.util.Optional;

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
        return withMerger(null, null, subgraphMerger);
    }

    @Override
    public SubgraphDescription<PayloadType> withMerger(String title, SubgraphMerger<PayloadType, SubgraphResult> subgraphMerger) {
        return withMerger(title, null, subgraphMerger);
    }

    @Override
    public SubgraphDescription<PayloadType> withMerger(String title, String[] docs, SubgraphMerger<PayloadType, SubgraphResult> subgraphMerger) {

        Optional<LambdaReflector.AnnotatedMethod> annotatedMethod = LambdaReflector.annotatedMethodReference(subgraphMerger, Reactored.class);

        if(title == null) {
            title = annotatedMethod
                    .map(LambdaReflector.AnnotatedMethod::getMethod)
                    .map(Method::getName)
                    .orElse(null);
        }

        if(docs == null) {
            docs = annotatedMethod
                    .map(LambdaReflector.AnnotatedMethod::getMethod)
                    .map(method -> method.getAnnotation(Reactored.class))
                    .map(Reactored::value)
                    .orElse(null);
        }

        annotatedMethod.ifPresent(reference -> this.subgraphDescription.subgraphPayload = reference.getMethodClass());

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
