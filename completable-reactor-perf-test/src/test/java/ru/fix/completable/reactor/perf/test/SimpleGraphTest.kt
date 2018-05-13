package ru.fix.completable.reactor.perf.test

import com.google.common.util.concurrent.RateLimiter
import org.junit.Test
import ru.fix.commons.profiler.impl.SimpleProfiler
import ru.fix.completable.reactor.runtime.CompletableReactor
import java.util.*
import java.util.concurrent.*

class SimpleGraphTest {


    @Test
    fun run() {

        val profiler = SimpleProfiler()
        val reactor = CompletableReactor(profiler)
        reactor.registerGraph(SimpleGraph())

        val reporter = profiler.createReporter()
        val schedule = Executors.newSingleThreadScheduledExecutor()
        schedule.scheduleAtFixedRate({
            println(reporter.buildReportAndReset())
        }, 0, 15, TimeUnit.SECONDS)

        val list = ArrayBlockingQueue<CompletableFuture<*>>(100)


        for (i in 1..100_000) {

            val future = reactor.submit(
                    SimplePayload(SimplePayload.Request(
                            "simple-request-" +
                                    ThreadLocalRandom.current().nextInt().toString())
                    )).resultFuture
            list.put(future)

            future.handle { res, thr -> list.remove(future) }
        }

        reactor.close()
    }
}