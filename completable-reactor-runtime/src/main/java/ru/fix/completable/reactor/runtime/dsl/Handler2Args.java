package ru.fix.completable.reactor.runtime.dsl;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

/**
 * @author Kamil Asfandiyarov
 */
@FunctionalInterface
public interface Handler2Args<Arg1, Arg2, Result> extends Serializable {
    CompletableFuture<Result> handle(Arg1 arg1, Arg2 arg2);
}
