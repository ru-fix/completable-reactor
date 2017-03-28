package ru.fix.completable.reactor.runtime;

import ru.fix.completable.reactor.api.Reactored;

import java.util.concurrent.CompletableFuture;

/**
 * @author Kamil Asfandiyarov
 */

@Reactored({"IdProcessor return it's id"})
public interface IdProcessorInterface {

    @Reactored({
            "Return id of this processor.",
            "Id is Long.",
    })
    CompletableFuture<Integer> handle();
}
