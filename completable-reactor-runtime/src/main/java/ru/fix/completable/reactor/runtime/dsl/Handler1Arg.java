package ru.fix.completable.reactor.runtime.dsl;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

/**
 * @author Kamil Asfandiyarov
 */
@FunctionalInterface
public interface Handler1Arg<Arg1, ProcessorResult> extends Serializable{
    CompletableFuture<ProcessorResult> handle(Arg1 arg1);
}
