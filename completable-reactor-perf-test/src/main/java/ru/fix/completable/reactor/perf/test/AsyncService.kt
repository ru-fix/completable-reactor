package ru.fix.completable.reactor.perf.test

import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutorService

class AsyncService(val pool: ExecutorService, val timeout: Int){

    fun asyncMethod(arg: String): CompletableFuture<String> {
        return CompletableFuture<String>().also {
            pool.submit {
                Thread.sleep(timeout.toLong())
                it.complete("value for arg: $arg")
            }
        }
    }

}