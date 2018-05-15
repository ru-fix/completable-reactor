package ru.fix.completable.reactor.perf.test

import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService

class AsyncService(
        private val pool: ExecutorService,
        private val timeout: Int) {

    fun asyncMethod(arg: String): CompletableFuture<String> {
        return CompletableFuture<String>().also { future ->
            pool.submit {
                Thread.sleep(timeout.toLong())
                future.complete("value for arg: $arg")
            }
        }
    }

}