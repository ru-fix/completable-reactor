package ru.fix.completable.reactor.jmh

import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import java.util.concurrent.*
import java.util.function.Supplier

@State(Scope.Benchmark)
open class BaselineJmh {

    val POOL_SIZE = 4

    val DAO = Executors.newFixedThreadPool(POOL_SIZE)
    val HTTP = Executors.newFixedThreadPool(POOL_SIZE)
    val SMPP = Executors.newFixedThreadPool(POOL_SIZE)
    val IMDG = Executors.newFixedThreadPool(POOL_SIZE)
    val NOSQL = Executors.newFixedThreadPool(POOL_SIZE)

    val dao = DAO.asCoroutineDispatcher()
    val smpp = SMPP.asCoroutineDispatcher()
    val http = HTTP.asCoroutineDispatcher()
    val imdg = IMDG.asCoroutineDispatcher()
    val nosql = NOSQL.asCoroutineDispatcher()

    val service = Service()


    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    fun pools_cf() {

        CompletableFuture.supplyAsync(Supplier { service.databaseQuery() }, DAO)
                .thenApplyAsync(java.util.function.Function<String, String> { service.sendSmppMessage() }, SMPP)
                .thenApplyAsync(java.util.function.Function<String, String> { service.sendHttpNotification() }, HTTP)
                .thenApplyAsync(java.util.function.Function<String, String> { service.sendHttpNotification() }, IMDG)
                .thenApplyAsync(java.util.function.Function<String, String> { service.sendHttpNotification() }, NOSQL)

                .join()
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    fun pools_executor() {

        DAO.submit(Callable<Future<Future<Future<Future<String>>>>> {

            service.databaseQuery()
            SMPP.submit(Callable<Future<Future<Future<String>>>> {

                service.sendSmppMessage()
                HTTP.submit(Callable<Future<Future<String>>> {
                    service.sendHttpNotification()

                    IMDG.submit(Callable<Future<String>> {
                        service.imdgQuery()
                        NOSQL.submit(Callable<String> { service.noSqlQuery() })
                    })

                })
            })
        }).get().get().get()
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    fun pools_coroutines() {

        runBlocking(dao) {
            service.databaseQuery()
            withContext(smpp) {
                service.sendSmppMessage()
            }
            withContext(http) {
                service.sendHttpNotification()
                withContext(imdg) {
                    service.imdgQuery()
                    withContext(nosql) {
                        service.noSqlQuery()
                    }
                }
            }
        }
    }
}


class Service {

    fun load(): String {
        Blackhole.consumeCPU(1000)
        return "" + System.currentTimeMillis()
    }

    fun databaseQuery(): String {
        return load()
    }

    fun sendSmppMessage(): String {
        return load()
    }

    fun sendHttpNotification(): String {
        return load()
    }

    fun imdgQuery(): String {
        return load()
    }

    fun noSqlQuery(): String {
        return load()
    }
}