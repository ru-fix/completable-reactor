package ru.fix.cpapsm.completable.reactor.runtime;

/**
 * @author Kamil Asfandiyarov
 */
@FunctionalInterface
public interface MethodReference2Args<ProcessorType, Arg1, Arg2, Res> {
    Res apply(ProcessorType payload, Arg1 arg1, Arg2 arg2);
}
