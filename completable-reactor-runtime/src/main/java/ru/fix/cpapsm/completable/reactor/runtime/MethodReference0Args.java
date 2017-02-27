package ru.fix.cpapsm.completable.reactor.runtime;

/**
 * @author Kamil Asfandiyarov
 */
@FunctionalInterface
public interface MethodReference0Args<ProcessorType, Res> {
    Res apply(ProcessorType payload);
}
