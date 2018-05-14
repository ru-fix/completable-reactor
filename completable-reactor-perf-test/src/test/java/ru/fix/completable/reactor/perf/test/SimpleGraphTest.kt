package ru.fix.completable.reactor.perf.test

import org.junit.Test
import ru.fix.commons.profiler.impl.SimpleProfiler
import ru.fix.completable.reactor.runtime.CompletableReactor
import java.time.Duration
import java.time.Instant
import java.util.concurrent.*

class SimpleGraphTest {

    /**
     * Launch graph with routers and handlers that uses various pools.
     * Maintain fixed size buffer of pending reactor futures.
     */
    @Test
    fun fixed_pending_buffer_load() {

        val profiler = SimpleProfiler()
        val reactor = CompletableReactor(profiler)
        reactor.registerGraph(SimpleGraph())

        val reporter = profiler.createReporter()
        val schedule = Executors.newSingleThreadScheduledExecutor()
        schedule.scheduleAtFixedRate({
            println(reporter.buildReportAndReset())
        }, 0, 15, TimeUnit.SECONDS)

        val list = ArrayBlockingQueue<CompletableFuture<*>>(300)


        val start = Instant.now()
        for (i in 1..100_000) {

            val future = reactor.submit(
                    SimplePayload(SimplePayload.Request(
                            "simple-request-" +
                                    ThreadLocalRandom.current().nextInt().toString())
                    )).resultFuture
            list.put(future)

            future.handle { res, thr -> list.remove(future) }

            if(Duration.between(start, Instant.now()) > Duration.ofMinutes(1)){
                println("* * * * * * Break test. Use results above ^ * * * * * *")
                break
            }
        }

        reactor.close()
        println("Test complete for ${Duration.between(start, Instant.now()).toMillis()}ms")
    }
}