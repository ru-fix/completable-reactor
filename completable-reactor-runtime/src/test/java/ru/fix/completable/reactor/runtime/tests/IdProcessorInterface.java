package ru.fix.completable.reactor.runtime.tests;

import java.util.concurrent.CompletableFuture;

/**
 * @author Kamil Asfandiyarov
 */

public interface IdProcessorInterface {

    CompletableFuture<Integer> handle();
}
