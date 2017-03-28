package ru.fix.completable.reactor.runtime;

import ru.fix.completable.reactor.api.Reactored;
import ru.fix.completable.reactor.runtime.function.MergePointMerger;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * @author Kamil Asfandiyarov
 */
public class MergePointArgMethodMerger<ContextResult, PayloadType> {

    final ContextResult contextResult;
    final GraphMergePointDescription<PayloadType> description;

    MergePointArgMethodMerger(ContextResult contextResult, GraphMergePointDescription<PayloadType> description) {
        this.contextResult = contextResult;
        this.description = description;
    }

    public ContextResult withMerger(MergePointMerger<PayloadType> merger){
        return withMerger(null,null, merger);
    }

    public ContextResult withMerger(String title, MergePointMerger<PayloadType> merger){
        return withMerger(title, null, merger);
    }

    public ContextResult withMerger(String title, String[] docs, MergePointMerger<PayloadType> merger){

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

        description.merger = merger;
        description.mergerSource = ReactorReflector.getMethodInvocationPoint().orElse(null);
        description.mergerTitle = title;
        description.mergerDocs = docs;

        return contextResult;
    }
}
