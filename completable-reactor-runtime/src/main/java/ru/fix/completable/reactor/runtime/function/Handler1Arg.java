package ru.fix.completable.reactor.runtime.function;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

/**
 * @author Kamil Asfandiyarov
 */
@FunctionalInterface
public interface Handler1Arg<Arg1, Result> extends Serializable{
    CompletableFuture<Result> apply(Arg1 arg1);
}
