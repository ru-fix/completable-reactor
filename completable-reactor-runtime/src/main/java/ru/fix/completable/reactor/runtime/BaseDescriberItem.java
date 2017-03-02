package ru.fix.completable.reactor.runtime;

/**
 * @author Kamil Asfandiyarov
 */
public class BaseDescriberItem<ContextResult> {
    final ContextResult contextResult;
    final GraphProcessorDescription processorDescription;

    public BaseDescriberItem(ContextResult contextResult, GraphProcessorDescription processorDescription) {
        this.contextResult = contextResult;
        this.processorDescription = processorDescription;
    }
}
