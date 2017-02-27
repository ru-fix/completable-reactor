package ru.fix.cpapsm.completable.reactor.runtime;

/**
 * @author Kamil Asfandiyarov
 */
public class ArgMethodHandler<ContextResult, PayloadType, ProcessorType > extends ArgMethodHandler0<ContextResult, PayloadType, ProcessorType> {
    final ReactorGraph.ProcessorInfo processorInfo;

    public ArgMethodHandler(ContextResult contextResult, ReactorGraph.ProcessorInfo processorInfo) {
        super(contextResult, processorInfo.description);

        this.processorInfo = processorInfo;
    }
    public ContextResult describe(GraphProcessorDescription<ProcessorType, ? super PayloadType> description) {
        this.processorInfo.description = description;

        return contextResult;
    }
}
