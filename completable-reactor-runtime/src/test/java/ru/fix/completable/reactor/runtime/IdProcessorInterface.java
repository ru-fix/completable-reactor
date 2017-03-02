package ru.fix.completable.reactor.runtime;

import ru.fix.completable.reactor.api.HandlerDescription;
import ru.fix.completable.reactor.api.ProcessorDescription;

import java.util.concurrent.CompletableFuture;

/**
 * @author Kamil Asfandiyarov
 */

@ProcessorDescription(doc = {"IdProcessor return it's id"})
public interface IdProcessorInterface {

    @HandlerDescription(doc = {
            "Return id of this processor.",
            "Id is Long.",
    })
    CompletableFuture<Integer> handle();
}
