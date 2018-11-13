package ru.fix.completable.reactor.jmh

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Scope
import org.openjdk.jmh.annotations.State
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicLong
import java.util.function.Supplier

@State(Scope.Benchmark)
open class BaselineJmh {

    val atomic = AtomicLong()

    @Benchmark
    fun increment_atomic_log(): Long {
        return atomic.incrementAndGet()
    }

    val DAO_POOL_SIZE = 10
    val DAO = Executors.newFixedThreadPool(DAO_POOL_SIZE)

    val HTTP_POOL_SIZE = 10
    val HTTP = Executors.newFixedThreadPool(HTTP_POOL_SIZE)

    val SMPP_POOL_SIZE = 10
    val SMPP = Executors.newFixedThreadPool(SMPP_POOL_SIZE)

    val service = Service()


    @Benchmark
    fun work_load() {

        fun dao() = CompletableFuture.supplyAsync(Supplier { service.databaseQuery() }, DAO)
        fun smpp() = CompletableFuture.supplyAsync(Supplier { service.sendSmppMessage() }, SMPP)
        fun http() = CompletableFuture.supplyAsync(Supplier { service.sendHttpNotification() }, HTTP)

        dao()
                .thenCompose { smpp() }
                .thenCompose { http() }
                .join()
    }

}


class Service {

    companion object {
        const val DAO_RESP_TIMEOUT = 2L
        const val SMPP_RESP_TIMEOUT = 2L
        const val HTTP_RESP_TIMEOUT = 2L
    }

    fun databaseQuery(): String {

        return "query"
    }

    fun sendSmppMessage(): String {
        Thread.sleep(SMPP_RESP_TIMEOUT)
        return "sms"
    }

    fun sendHttpNotification(): String {
        Thread.sleep(HTTP_RESP_TIMEOUT)
        return "notify"
    }
}