package ru.fix.completable.reactor.runtime.dsl;

/**
 * @author Kamil Asfandiyarov
 */
public interface ProcessorDescription<PayloadType> {
    Processor<PayloadType> buildProcessor();
}
