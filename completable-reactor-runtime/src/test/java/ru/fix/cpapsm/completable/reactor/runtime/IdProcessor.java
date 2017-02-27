package ru.fix.cpapsm.completable.reactor.runtime;

import lombok.ToString;
import ru.fix.cpapsm.completable.reactor.api.HandlerDescription;
import ru.fix.cpapsm.completable.reactor.api.ProcessorDescription;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @author Kamil Asfandiyarov
 */

@ProcessorDescription(doc = {"IdProcessor return it's id"})
@ToString
public class IdProcessor implements IdProcessorInterface {
    final Integer id;
    CompletableFuture<Void> launchingLatch = CompletableFuture.completedFuture(null);

    public IdProcessor(Integer id) {
        this.id = id;
    }

    public IdProcessor withLaunchingLatch(){
        this.launchingLatch = new CompletableFuture<>();
        return this;
    }

    public void launch(){
        Objects.requireNonNull(launchingLatch);
        launchingLatch.complete(null);
    }

    @HandlerDescription(doc = {
            "Return id of this processor.",
            "Id is Long.",
    })
    public CompletableFuture<Integer> handle() {
        return launchingLatch.thenApply(any -> id);
    }
}
