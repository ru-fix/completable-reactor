package ru.fix.completable.reactor.runtime;

/**
 * @author Kamil Asfandiyarov
 */
@FunctionalInterface
public interface MethodReference3Args<ProcessorType, Arg1, Arg2, Arg3, Res> {
    Res apply(ProcessorType payload, Arg1 arg1, Arg2 arg2, Arg3 arg3);
}
