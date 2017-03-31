package ru.fix.completable.reactor.runtime;

/**
 * @author Kamil Asfandiyarov
 */
class BaseDescriberItem<ContextResult> {
    final ContextResult contextResult;
    final GraphProcessorDescription processorDescription;

    BaseDescriberItem(ContextResult contextResult, GraphProcessorDescription processorDescription) {
        this.contextResult = contextResult;
        this.processorDescription = processorDescription;
    }
}
