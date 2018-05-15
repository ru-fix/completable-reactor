package ru.fix.completable.reactor.perf.test

import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService
import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit

class AsyncService(
        private val pool: ExecutorService,
        private val timeout: Int) {

    private val semaphore = Semaphore(0)

    fun asyncMethod(arg: String): CompletableFuture<String> {
        return CompletableFuture<String>().also { future ->
            pool.submit {
                semaphore.tryAcquire(timeout.toLong(), TimeUnit.MILLISECONDS)
                future.complete("value for arg: $arg")
            }
        }
    }

}