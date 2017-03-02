package ru.fix.completable.reactor.runtime;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Represent processor graph instance.
 * @author Kamil Asfandiyarov
 */
@EqualsAndHashCode(doNotUseGetters = true)
@ToString
public class GraphProcessor<ProcessorType> implements ProcessingGraphItem {
    final ProcessorType processor;
    int id = 0;

    public GraphProcessor(ProcessorType processor) {
        this.processor = processor;
    }

    public GraphProcessor<ProcessorType> withId(int id){
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
}
