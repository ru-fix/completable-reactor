package ru.fix.cpapsm.completable.reactor.runtime;

/**
 * @author Kamil Asfandiyarov
 */
@FunctionalInterface
public interface MethodReference1Arg<ProcessorType, Arg1, Res> {
    Res apply(ProcessorType payload, Arg1 arg1);
}
