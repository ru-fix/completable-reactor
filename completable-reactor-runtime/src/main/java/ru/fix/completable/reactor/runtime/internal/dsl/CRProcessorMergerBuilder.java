package ru.fix.completable.reactor.runtime.internal.dsl;

import lombok.val;
import ru.fix.completable.reactor.api.Reactored;
import ru.fix.completable.reactor.runtime.dsl.ProcessorDescription;
import ru.fix.completable.reactor.runtime.dsl.ProcessorMerger;
import ru.fix.completable.reactor.runtime.dsl.ProcessorMergerBuilder;
import ru.fix.completable.reactor.runtime.internal.LambdaReflector;
import ru.fix.completable.reactor.runtime.internal.ReactorReflector;

/**
 * @author Kamil Asfandiyarov
 */
public class CRProcessorMergerBuilder<PayloadType, ProcessorResult> implements ProcessorMergerBuilder<PayloadType, ProcessorResult> {

    final CRProcessorDescription<PayloadType> processorDescription;

    public CRProcessorMergerBuilder(CRProcessorDescription<PayloadType> processorDescription) {
        this.processorDescription = processorDescription;
    }

    public ProcessorDescription<PayloadType> withMerger(ProcessorMerger<PayloadType, ProcessorResult> processorMerger) {
        return withMerger(null, null, processorMerger);
    }

    public ProcessorDescription<PayloadType> withMerger(String title, ProcessorMerger<PayloadType, ProcessorResult> processorMerger) {
        return withMerger(title, null, processorMerger);
    }

    public ProcessorDescription<PayloadType> withMerger(String title, String[] docs, ProcessorMerger<PayloadType, ProcessorResult> processorMerger) {

        val mergerMethod = LambdaReflector.tryReflectAnnotatedMethodReference(processorMerger, Reactored.class)
                .orElse(null);

        if(title == null && mergerMethod != null) {
            title = mergerMethod.getMethod().getName();
        }

        if(docs == null && mergerMethod != null) {
            docs = mergerMethod.getAnnotation().map(Reactored::value).orElse(null);
        }

        this.processorDescription.merger = processorMerger;
        this.processorDescription.mergeSource = ReactorReflector.getMethodInvocationPoint().orElse(null);
        this.processorDescription.mergerTitle = title;
        this.processorDescription.mergerDocs = docs;

        return processorDescription;
    }

    public ProcessorDescription<PayloadType> withoutMerger() {
        this.processorDescription.merger = null;
        this.processorDescription.mergeSource = ReactorReflector.getMethodInvocationPoint().orElse(null);
        return this.processorDescription;
    }
}
