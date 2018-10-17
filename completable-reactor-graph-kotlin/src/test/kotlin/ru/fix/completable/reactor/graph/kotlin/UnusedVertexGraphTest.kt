package ru.fix.completable.reactor.graph.kotlin

import mu.KotlinLogging
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import ru.fix.aggregating.profiler.NoopProfiler
import ru.fix.completable.reactor.runtime.CompletableReactor

private val log = KotlinLogging.logger {}

class UnusedVertexGraphTest {

    class UnusedVertexPayload

    class UnusedVertexGraph : Graph<UnusedVertexPayload>() {

        val first = mutator { /* empty vertex*/ }
        val second = mutator { /* empty vertex*/ }
        val unusedVertexField = mutator { /* empty vertex*/ }

        init {
            payload().handleBy(first)
            first.onAny().handleBy(second)
            second.onAny().complete()
        }
    }

    @Test
    fun `unused vertex field rise exception during graph building`() {
        try {
            CompletableReactor(NoopProfiler()).registerGraph(UnusedVertexGraph())
            fail("Graph should rise an exception during registration")

        } catch (exc: Exception) {

            log.info { exc.message }

            assertThat(exc.message, containsString("illegal vertices"))
            assertThat(exc.message, containsString("transition"))
            assertThat(exc.message, containsString(UnusedVertexGraph::unusedVertexField.name))
        }
    }
}