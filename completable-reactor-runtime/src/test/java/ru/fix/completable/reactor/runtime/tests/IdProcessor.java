package ru.fix.completable.reactor.runtime.tests;

import lombok.ToString;
import ru.fix.completable.reactor.api.Reactored;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @author Kamil Asfandiyarov
 */

@Reactored({"IdProcessor return it's id"})
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

    @Reactored({
            "Return id of this processor.",
            "Id is Long.",
    })
    public CompletableFuture<Integer> handle() {
        return launchingLatch.thenApply(any -> id);
    }
}
