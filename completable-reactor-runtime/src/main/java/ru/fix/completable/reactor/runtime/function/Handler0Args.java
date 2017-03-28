package ru.fix.completable.reactor.runtime.function;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

/**
 * @author Kamil Asfandiyarov
 */
@FunctionalInterface
public interface Handler0Args<Result> extends Serializable {
    CompletableFuture<Result> handle();
}
