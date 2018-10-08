package ru.fix.completable.reactor.runtime.tests

import org.junit.jupiter.api.Test
import ru.fix.aggregating.profiler.AggregatingProfiler
import ru.fix.completable.reactor.graph.runtime.GlGraph
import ru.fix.completable.reactor.graph.runtime.GlVertex
import ru.fix.completable.reactor.runtime.LogTracer
import ru.fix.completable.reactor.runtime.debug.ToStringDebugSerializer
import ru.fix.completable.reactor.runtime.execution.ExecutionBuilder
import java.util.concurrent.CompletableFuture

class ExecutionBuilderTest {


    class TestTracer : LogTracer() {
        override fun isTraceable(payload: Any?): Boolean {
            return false
        }
    }

    @Test
    fun buildTest() {

        val graph = GlGraph()

        val startPoint = GlVertex()




        val builder = ExecutionBuilder(
                AggregatingProfiler(),
                { return@ExecutionBuilder CompletableFuture.completedFuture(null) },
                ToStringDebugSerializer(),
                TestTracer()
        )

    }
}