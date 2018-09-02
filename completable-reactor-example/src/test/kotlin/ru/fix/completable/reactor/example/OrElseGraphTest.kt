package ru.fix.completable.reactor.example

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import ru.fix.aggregating.profiler.AggregatingProfiler
import ru.fix.completable.reactor.runtime.CompletableReactor
import java.util.concurrent.TimeUnit

@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [OrElseGraphTest.Config::class, OrElseGraph::class])
class OrElseGraphTest {

    @Configuration
    open class Config {

        @Bean
        open fun reactor(): CompletableReactor {
            val reactor = CompletableReactor(AggregatingProfiler())

            //For debug purpose to resolve blocked vertices that does not completed correctly
            reactor.setDebugProcessingVertexGraphState(true)

            return reactor
        }
    }

    @Autowired
    lateinit var reactor: CompletableReactor

    @Test
    fun orElseTestNoElse() {
        val payload = reactor.submit(OrElsePayload(OrElsePayload.Flow.FIRST)).resultFuture.get(10, TimeUnit.SECONDS)

        Assertions.assertNotNull(payload)

    }

    @Test
    fun orElseReallyOrElse() {
        val payload = reactor.submit(OrElsePayload(OrElsePayload.Flow.SECOND)).resultFuture.get(10, TimeUnit.SECONDS)

        Assertions.assertNotNull(payload)
    }
}