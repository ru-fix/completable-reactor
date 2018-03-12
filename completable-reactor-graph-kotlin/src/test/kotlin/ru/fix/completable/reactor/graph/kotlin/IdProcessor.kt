package ru.fix.completable.reactor.graph.kotlin


import java.util.Objects
import java.util.concurrent.CompletableFuture

/**
 * IdProcessor return it's id
 *
 * @author Kamil Asfandiyarov
 */
class IdProcessor(val id: Int){
    var launchingLatch = CompletableFuture.completedFuture<Void>(null)

    fun withLaunchingLatch(): IdProcessor {
        this.launchingLatch = CompletableFuture()
        return this
    }

    fun launch() {
        Objects.requireNonNull(launchingLatch)
        launchingLatch.complete(null)
    }

    /**
     * Return id of this processor.
     * Id is Long.
     */
    fun handle(): CompletableFuture<Int> {
        return launchingLatch.thenApply { any -> id }
    }
}
