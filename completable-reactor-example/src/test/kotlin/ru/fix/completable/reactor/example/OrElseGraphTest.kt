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
            return CompletableReactor(AggregatingProfiler())
        }
    }

    @Autowired
    lateinit var reactor: CompletableReactor

    @Test
    fun firstTransition() {
        val payload = submit(OrElsePayload(OrElsePayload.Flow.FIRST))

        Assertions.assertEquals(OrElsePayload.Flow.FIRST, payload.response.result)
    }

    @Test
    fun secondTransition() {
        val payload = submit(OrElsePayload(OrElsePayload.Flow.SECOND))

        Assertions.assertEquals(OrElsePayload.Flow.SECOND, payload.response.result)
    }

    @Test
    fun thirdTransition() {
        val payload = submit(OrElsePayload(OrElsePayload.Flow.THIRD))

        Assertions.assertEquals(OrElsePayload.Flow.THIRD, payload.response.result)
    }

    @Test
    fun fourthTransition() {
        val payload = submit(OrElsePayload(OrElsePayload.Flow.FOURTH))

        Assertions.assertEquals(OrElsePayload.Flow.FOURTH, payload.response.result)
    }

    private fun submit(payload: OrElsePayload): OrElsePayload {
        return reactor.submit(payload).resultFuture.get(10, TimeUnit.SECONDS)
    }
}