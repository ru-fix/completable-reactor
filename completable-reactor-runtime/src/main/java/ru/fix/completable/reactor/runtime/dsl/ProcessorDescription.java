package ru.fix.completable.reactor.runtime.dsl;

/**
 * Describe processor handing and merging methods
 *
 * @author Kamil Asfandiyarov
 */
public interface ProcessorDescription<PayloadType> {
    Processor<PayloadType> buildProcessor();
}
