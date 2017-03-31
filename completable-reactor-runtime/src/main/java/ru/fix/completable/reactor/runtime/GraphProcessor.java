package ru.fix.completable.reactor.runtime;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Represent processor graph instance.
 * @author Kamil Asfandiyarov
 */
@EqualsAndHashCode(doNotUseGetters = true)
@ToString
public class GraphProcessor<PayloadType> implements ProcessingGraphItem, MergeableProcessingGraphItem<PayloadType>, HandleableProcessingGraphItem {
    final Class processorType;
    final GraphProcessorDescription<PayloadType> processorDescription;

    int id = 0;

    GraphProcessor(Class processorType, GraphProcessorDescription<PayloadType> description) {
        this.processorType = processorType;
        this.processorDescription = description;
    }

    public GraphProcessor<PayloadType> setId(int id){
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
