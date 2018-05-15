package ru.fix.completable.reactor.perf.test

import com.google.common.util.concurrent.RateLimiter
import kotlinx.coroutines.experimental.CommonPool
import org.junit.Test
import ru.fix.commons.profiler.impl.SimpleProfiler
import ru.fix.completable.reactor.runtime.CompletableReactor
import java.time.Duration
import java.time.Instant
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger

class PerfGraphTest {

    /**
     * Launch graph with routers and handlers that uses various pools.
     * Maintain fixed size buffer of pending reactor futures.
     */
    @Test
    fun fixed_pending_buffer_load() {

        val profiler = SimpleProfiler()
        val reactor = CompletableReactor(profiler)
        reactor.registerGraph(PerfGraph(profiler))

        val reporter = profiler.createReporter()
        val schedule = Executors.newSingleThreadScheduledExecutor()
        schedule.scheduleAtFixedRate({
            try {
                val report = reporter.buildReportAndReset()
                println(report)

                val pld = report.profilerCallReports.find {
                    it.name.endsWith("pld.PerfPayload")
                }

                if(pld != null) {
                    report.profilerCallReports.forEach {
                        println("${it.name} diff: ${it.callsCount - pld.callsCount}")
                    }
                }
            } catch (exc: Exception) {
                exc.printStackTrace()
            }


        }, 0, 15, TimeUnit.SECONDS)

        val counter = AtomicInteger()

        val WINDOW = 300
        val THROUGHPUT_LIMIT = 2200

        val rateLimiter = RateLimiter.create(THROUGHPUT_LIMIT.toDouble())

        ForkJoinPool.commonPool().parallelism

        println("WINDOW = $WINDOW")
        println("THROUGHPUT_LIMIT = $THROUGHPUT_LIMIT")
        println("Fixed Pools size = ${PerfGraph.POOL_SIZE}")
        println("CommonPool size = ${ForkJoinPool.commonPool().parallelism}")
        println("Handler timeout = ${PerfGraph.TIMEOUT}")


        val start = Instant.now()
        for (i in 1..100_000) {

            rateLimiter.acquire()

            while (counter.get() >= WINDOW){
                rateLimiter.acquire()
            }


            counter.incrementAndGet()
            val future = reactor.submit(
                    PerfPayload(PerfPayload.Request(
                            "simple-request-" +
                                    ThreadLocalRandom.current().nextInt().toString())
                    )).resultFuture

//            list.put(future)

//            future.handleAsync { res, thr -> list.remove(future) }
            future.handleAsync { res, thr -> counter.decrementAndGet() }

            if(Duration.between(start, Instant.now()) > Duration.ofMinutes(1)){
                println("* * * * * * Break test. Use results above ^ * * * * * *")
                break
            }
        }

        reactor.close()
        println("Test complete for ${Duration.between(start, Instant.now()).toMillis()}ms")
    }
}