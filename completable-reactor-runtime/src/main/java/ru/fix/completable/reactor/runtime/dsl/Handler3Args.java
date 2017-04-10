package ru.fix.completable.reactor.runtime.dsl;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

/**
 * @author Kamil Asfandiyarov
 */
@FunctionalInterface
public interface Handler3Args<Arg1, Arg2, Arg3, ProcessorResult> extends Serializable{
    CompletableFuture<ProcessorResult> handle(Arg1 arg1, Arg2 arg2, Arg3 arg3);
}
