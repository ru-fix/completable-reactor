package ru.fix.completable.reactor.runtime.dsl;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

/**
 * @author Kamil Asfandiyarov
 */
@FunctionalInterface
public interface Handler7Args<Arg1, Arg2, Arg3, Arg4, Arg5, Arg6, Arg7, ProcessorResult> extends Serializable{
    CompletableFuture<ProcessorResult> handle(Arg1 arg1, Arg2 arg2, Arg3 arg3, Arg4 arg4, Arg5 arg5, Arg6 arg6,
                                              Arg7 arg7);
}
