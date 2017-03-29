package ru.fix.completable.reactor.runtime;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Represent processor graph instance.
 * @author Kamil Asfandiyarov
 */
@EqualsAndHashCode(doNotUseGetters = true)
@ToString
public class GraphProcessor<ProcessorType, PayloadType> implements ProcessingGraphItem, MergeableProcessingGraphItem<PayloadType>, HandleableProcessingGraphItem {
    final ProcessorType processor;
    final GraphProcessorDescription<ProcessorType, PayloadType> processorDescription;

    int id = 0;

    GraphProcessor(ProcessorType processor, GraphProcessorDescription<ProcessorType, PayloadType> description) {
        this.processor = processor;
        this.processorDescription = description;
    }

    public GraphProcessor<ProcessorType, PayloadType> withId(int id){
        this.id = id;
        return this;
    }

    public ProcessorType getProcessor() {
        return processor;
    }

    public Class<ProcessorType> getProcessorClass() {
        return (Class<ProcessorType>) processor.getClass();
    }

    public int getId() {
        return id;
    }

    @Override
    public String getProfilingName() {
        return processor.getClass().getSimpleName();
    }

    @Override
    public String getDebugName() {
        return processor.getClass().getName() + "@" + id;
    }
}
