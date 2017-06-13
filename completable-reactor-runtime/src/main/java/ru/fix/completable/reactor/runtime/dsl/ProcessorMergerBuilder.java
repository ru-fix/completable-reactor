package ru.fix.completable.reactor.runtime.dsl;

/**
 * @author Kamil Asfandiyarov
 */
public interface ProcessorMergerBuilder<PayloadType, ProcessorResult> {

    ProcessorDescription<PayloadType> withMerger(ProcessorMerger<PayloadType, ProcessorResult> processorMerger);

    ProcessorDescription<PayloadType> withMerger(String title, ProcessorMerger<PayloadType, ProcessorResult> processorMerger);

    ProcessorDescription<PayloadType> withMerger(String title, String doc, ProcessorMerger<PayloadType, ProcessorResult> processorMerger);

    ProcessorDescription<PayloadType> withMerger(String title, String[] docs, ProcessorMerger<PayloadType, ProcessorResult> processorMerger);

    ProcessorDescription<PayloadType> withoutMerger();
}
