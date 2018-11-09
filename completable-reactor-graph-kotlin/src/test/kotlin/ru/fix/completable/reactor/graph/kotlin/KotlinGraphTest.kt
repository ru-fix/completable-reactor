package ru.fix.completable.reactor.graph.kotlin

import kotlinx.coroutines.future.await
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.apache.commons.lang3.exception.ExceptionUtils
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.containsString
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.fix.aggregating.profiler.AggregatingProfiler
import ru.fix.completable.reactor.runtime.CompletableReactor
import java.util.*
import java.util.concurrent.CompletableFuture.completedFuture
import java.util.concurrent.Semaphore
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeUnit.SECONDS
import java.util.concurrent.TimeoutException

private val log = KotlinLogging.logger {}

/**
 * @author Kamil Asfandiyarov
 */
class KotlinGraphTest {

    private lateinit var reactor: CompletableReactor


    @BeforeEach
    fun before() {
        reactor = CompletableReactor(AggregatingProfiler())
                .setDebugProcessingVertexGraphState(true)
    }

    @AfterEach
    fun after() {
        reactor.close()
    }


    internal enum class Status {
        OK, UNUSED
    }


    /**
     * IdListPayload contains list of integer ids.
     * When IdListPayload goes through processors chain each processor adds their id
     *  so at the end we can clarify by witch processor and in what order this payload was processed.
     */
    open class IdListPayload {
        val idSequence: MutableList<Int> = ArrayList()
    }


    /**
     * Payload contains list of integer ids.
     * When payload goes through vertex chain each vertex adds their id.
     * At the end we can verify by witch vertex and in what order this payload was modified.
     *
     *
     * Test will check that single handler id end up in payload.",
     * Expected result: {1}
     */
    class SingleProcessorGraph : Graph<IdListPayload>() {

        var idProcessor1 =
                handler {
                    IdProcessor(1).handle()
                }.withMerger {
                    idSequence.add(it)
                }

        init {
            payload().handleBy(idProcessor1)

            idProcessor1.onAny().complete()
        }
    }

    @Test
    fun single_processor() {

        reactor.registerGraphIfAbsent(SingleProcessorGraph::class.java)

        val resultPayload = reactor.submit(IdListPayload())
                .resultFuture
                .get(10, SECONDS)

        assertEquals(Arrays.asList(1), resultPayload.idSequence)
    }

    /**
     * Test will check that two processor ids end up at payloads idList in correct order.
     * Expected result: {1, 2}
     */
    class TwoProcessorSequentialMergeGraph : Graph<IdListPayload>() {

        var idProcessor1 = handler { IdProcessor(1).handle() }
                .withRoutingMerger {
                    idSequence.add(it)
                    Status.OK
                }

        var idProcessor2 = handler { IdProcessor(2).handle() }
                .withMerger {
                    idSequence.add(it)
                }

        init {
            payload()
                    .handleBy(idProcessor1)
                    .handleBy(idProcessor2)

            idProcessor1.on(Status.OK).mergeBy(idProcessor2)
            idProcessor1.on(Status.UNUSED).mergeBy(idProcessor2)

            idProcessor2.onAny().complete()
        }
    }

    @Test
    @Throws(Exception::class)
    fun two_processors_sequential_merge() {

        reactor.registerGraphIfAbsent(TwoProcessorSequentialMergeGraph::class.java)
        val execution = reactor.submit(IdListPayload())
        val resultPayload = execution.resultFuture.get(10, TimeUnit.SECONDS)

        assertEquals(Arrays.asList(1, 2), resultPayload.idSequence)
    }


    /**
     * Detached processor is a handler without merger.
     * Main flow does not wait for detached processor to complete.
     * In test Detached processor will run and complete deferred in time.
     * When result of chain is ready detached processor will be activated.
     * Test will check that chain execution will complete on detached processor finish.
     *
     *
     * Expected result: {1, 2}
     */
    class DetachedProcessorGraph : Graph<IdListPayload>() {

        var detachedProcessor = IdProcessor(3).withLaunchingLatch()

        var idProcessor1 = handler { IdProcessor(1).handle() }
                .withRoutingMerger {
                    idSequence.add(it)
                    Status.OK

                }

        var idProcessor2 = handler { IdProcessor(2).handle() }
                .withMerger {
                    idSequence.add(it)
                }

        var idProcessor3 = handler { detachedProcessor.handle() }
                .withoutMerger()

        init {
            payload()
                    .handleBy(idProcessor1)
                    .handleBy(idProcessor2)
                    .handleBy(idProcessor3)

            idProcessor1.on(Status.OK).mergeBy(idProcessor2)

            idProcessor2.onAny().complete()
        }
    }


    @Test
    @Throws(Exception::class)
    fun detached_processor() {

        val graph = DetachedProcessorGraph()
        reactor.registerGraphIfAbsent(graph)

        val result = reactor.submit(IdListPayload())

        assertEquals(Arrays.asList(1, 2), result.resultFuture.get(5, TimeUnit.SECONDS).idSequence)

        assertTrue(result.resultFuture.isDone, "result future is complete")
        assertFalse(result.chainExecutionFuture.isDone, "execution chain is not complete since detached processor still working")

        graph.detachedProcessor.launch()

        result.chainExecutionFuture.get(5, TimeUnit.SECONDS)
        assertTrue(result.chainExecutionFuture.isDone, "execution chain is complete when detached processor is finished")

        //TODO add case when we have to wait for returning from handling method of detached processor before continue
        // with mergers, so detached handlers could have time to read payload before merger modifies it.
    }


    class SubgraphPaylaod : IdListPayload()

    /**
     * # Subgraph
     * Subgraph behave the same way as plain processor.
     * The only difference is that instead of simple async operation CompletableReactor launches subgraph execution.
     */
    class SubgraphPayloadGraph : Graph<SubgraphPaylaod>() {

        var idProcessor11 = handler { IdProcessor(11).handle() }
                .withMerger {
                    idSequence.add(it)
                }

        var idProcessor12 = handler { IdProcessor(12).handle() }
                .withMerger {
                    idSequence.add(it)
                }

        var idProcessor13 = handler { IdProcessor(13).handle() }
                .withMerger {
                    idSequence.add(it)
                }

        init {
            payload()
                    .handleBy(idProcessor11)
                    .handleBy(idProcessor12)

            idProcessor11.onAny().mergeBy(idProcessor12)

            idProcessor12.onAny().handleBy(idProcessor13)

            idProcessor13.onAny().complete()

        }
    }

    /**
     * # Parent Graph (IdListPayload)
     * Parent graph is a simple graph that calls subgraph during its flow.
     * Expected result: 1, 11, 12, 13, 2, 3
     */
    class ParentGraphPayloadGraph : Graph<IdListPayload>() {
        var idProcessor1 = handler { IdProcessor(1).handle() }
                .withMerger {
                    idSequence.add(it)
                }

        var idProcessor2 = handler { IdProcessor(2).handle() }
                .withMerger {
                    idSequence.add(it)
                }

        var idProcessor3 = handler { IdProcessor(3).handle() }
                .withMerger {
                    idSequence.add(it)
                }

        var subgraphProcessor = subgraph(SubgraphPaylaod::class) { SubgraphPaylaod() }
                .withMerger {
                    idSequence.addAll(it.idSequence)
                }

        init {
            payload()
                    .handleBy(idProcessor1)

            idProcessor1
                    .onAny().handleBy(idProcessor2)
                    .onAny().handleBy(subgraphProcessor)

            subgraphProcessor
                    .onAny().mergeBy(idProcessor2)

            idProcessor2
                    .onAny().handleBy(idProcessor3)

            idProcessor3
                    .onAny().complete()

        }
    }

    @Test
    @Throws(Exception::class)
    fun run_subgraph() {

        reactor.registerGraphIfAbsent(ParentGraphPayloadGraph::class.java)
        reactor.registerGraphIfAbsent(SubgraphPayloadGraph::class.java)

        val subgraphResult = reactor.submit(SubgraphPaylaod()).resultFuture.get(5, SECONDS)
        assertEquals(Arrays.asList(11, 12, 13), subgraphResult.idSequence)


        val executionResult = reactor.submit(IdListPayload())
        val resultPaylaod = executionResult.resultFuture.get(5, TimeUnit.SECONDS)
        assertEquals(Arrays.asList(1, 11, 12, 13, 2, 3), resultPaylaod.idSequence)
    }


    /**
     * Router works as an regular merge point
     * but there is no processor or subgraph or theirs result to merge.
     * Router simply modify payload and send it through outgoing transitions.
     * Expected result: {42, 1, 0}
     */

    class RouterFromStartPointGraph : Graph<IdListPayload>() {

        var idProcessor0 = handler { IdProcessor(0).handle() }
                .withMerger {
                    idSequence.add(it)
                }

        var idProcessor1 = handler { IdProcessor(1).handle() }
                .withMerger {
                    idSequence.add(it)
                }

        var mergePoint = mutator {
            idSequence.add(42)
        }

        init {
            payload()
                    .handleBy(idProcessor0)
                    .handleBy(idProcessor1)
                    .handleBy(mergePoint)

            mergePoint
                    .onAny().mergeBy(idProcessor1)

            idProcessor1
                    .onAny().mergeBy(idProcessor0)

            idProcessor0
                    .onAny().complete()

            coordinates()
                    .pd(107, 9)
                    .vx(idProcessor0, 22, 78, 126, 215)
                    .vx(idProcessor1, 211, 79, 267, 187)
                    .vx(mergePoint, 424, 160)
                    .ct(idProcessor0, 57, 274)

        }
    }

    @Test
    @Throws(Exception::class)
    fun router_from_start_point() {
        reactor.registerGraphIfAbsent(RouterFromStartPointGraph::class.java)

        val result = reactor.submit(IdListPayload())

        val resultPayload = result
                .resultFuture
                .get(10, TimeUnit.SECONDS)

        assertEquals(Arrays.asList(42, 1, 0), resultPayload.idSequence)
    }

    /**
     * Router works as an regular merge point,
     * but there is no processor or subgraph or theirs result to merge.
     * Router simply modify payload and send it through outgoing transitions.
     * Expected result: {0, 1, 42}
     */
    class RouterFromProcessorsMergerGraph : Graph<IdListPayload>() {

        var idProcessor0 = handler { IdProcessor(0).handle() }
                .withMerger {
                    idSequence.add(it)
                    Status.OK
                }

        var idProcessor1 = handler { IdProcessor(1).handle() }
                .withMerger {
                    idSequence.add(it)
                    Status.OK
                }

        var router = router {
            idSequence.add(42)
            Status.OK
        }

        init {
            payload()
                    .handleBy(idProcessor0)
                    .handleBy(idProcessor1)

            idProcessor0
                    .onAny().mergeBy(idProcessor1)

            idProcessor1
                    .onAny().handleBy(router)

            router
                    .onAny().complete()

            coordinates()
                    .vx(idProcessor1, 85, 180)
        }
    }

    @Test
    @Throws(Exception::class)
    fun router_from_processors_merge_point() {

        reactor.registerGraphIfAbsent(RouterFromProcessorsMergerGraph::class.java)

        val result = reactor.submit(IdListPayload())

        val resultPayload = result
                .resultFuture
                .get(10, TimeUnit.SECONDS)

        assertEquals(Arrays.asList(0, 1, 42), resultPayload.idSequence)

    }


    /**
     * OptionalProcessorExecution shows how to use detached merge point to avoid unnecessary processor execution
     * Expected result for right: {1 ,2}
     * Expected result for left: {2}
     */
    class OptionalProcessorExecutionPayload(var whereToGo: OPTIONAL_DECISION) : IdListPayload()

    enum class OPTIONAL_DECISION {
        LEFT, RIGHT
    }

    class OptionalProcessorExecutionGraph : Graph<OptionalProcessorExecutionPayload>() {
        var idProcessor1 = handler { IdProcessor(1).handle() }
                .withMerger {
                    idSequence.add(it)
                    Status.OK
                }

        var idProcessor2 = handler { IdProcessor(2).handle() }
                .withMerger {
                    idSequence.add(it)
                    Status.OK
                }

        var idProcessor3 = handler { IdProcessor(3).handle() }
                .withMerger {
                    idSequence.add(it)
                    Status.OK
                }

        var mergePoint = router { whereToGo }


        init {
            payload()
                    .handleBy(mergePoint)

            mergePoint
                    .on(OPTIONAL_DECISION.LEFT).handleBy(idProcessor2)
                    .on(OPTIONAL_DECISION.RIGHT).handleBy(idProcessor1)

            idProcessor1
                    .onAny().handleBy(idProcessor2)

            idProcessor2
                    .onAny().handleBy(idProcessor3)

            idProcessor3
                    .onAny().complete()

            coordinates()
                    .pd(0, 17)
                    .vx(mergePoint, 98, 79)
                    .vx(idProcessor1, 246, 147, 287, 223)
                    .vx(idProcessor2, -17, 252, 21, 328)
                    .vx(idProcessor3, -15, 413, 23, 504)
                    .ct(idProcessor3, 95, 542);

        }
    }


    @Test
    @Throws(Exception::class)
    fun optional_processor_execution() {

        reactor.registerGraphIfAbsent(OptionalProcessorExecutionGraph::class.java)

        var result: CompletableReactor.Execution<OptionalProcessorExecutionPayload> = reactor.submit(
                OptionalProcessorExecutionPayload(OPTIONAL_DECISION.RIGHT))

        var resultPayload = result
                .resultFuture
                .get(10, TimeUnit.SECONDS)

        assertEquals(Arrays.asList(1, 2, 3), resultPayload.idSequence)


        result = reactor.submit(OptionalProcessorExecutionPayload(OPTIONAL_DECISION.LEFT))


        resultPayload = result
                .resultFuture
                .get(10, TimeUnit.SECONDS)

        assertEquals(Arrays.asList(2, 3), resultPayload.idSequence)
    }


    class DeadTransitionBreaksFlowPayload(var flowDecision: FlowDecision) : IdListPayload() {
        enum class FlowDecision {
            THREE, TWO
        }
    }

    /*
        It possible for Router to have several outgoing transitions.
        This transitions could be executed conditionally.
        If condition evaluated to false then this outgoing transitions marked as dead.
        If all incoming transitions to handler or subgraph marked as dead, then this handler or subgraph and it's merge point marked as dead.
    */
    class DeadTransitionBreaksFlowGraph : Graph<DeadTransitionBreaksFlowPayload>() {

        fun idProcessor1Tempalte() = handler { IdProcessor(1).handle() }
                .withMerger {
                    idSequence.add(it)
                    Status.OK
                }

        var idProcessor1 = idProcessor1Tempalte()
        var idProcessor1b = idProcessor1Tempalte()

        var idProcessor2 = handler { IdProcessor(2).handle() }
                .withMerger {
                    idSequence.add(it)
                    Status.OK
                }

        fun idProcessor3Template() = handler { IdProcessor(3).handle() }
                .withMerger {
                    idSequence.add(it)
                    Status.OK
                }

        var idProcessor3 = idProcessor3Template()

        var idProcessor3b = idProcessor3Template()


        var idProcessor4 = handler { IdProcessor(4).handle() }
                .withMerger {
                    idSequence.add(it)
                    Status.OK
                }


        var decisionMergePoint = router { flowDecision }

        init {
            payload()
                    .handleBy(decisionMergePoint)

            decisionMergePoint
                    .on(DeadTransitionBreaksFlowPayload.FlowDecision.THREE).handleBy(idProcessor1)
                    .on(DeadTransitionBreaksFlowPayload.FlowDecision.THREE).handleBy(idProcessor2)
                    .on(DeadTransitionBreaksFlowPayload.FlowDecision.THREE).handleBy(idProcessor3)
                    .on(DeadTransitionBreaksFlowPayload.FlowDecision.TWO).handleBy(idProcessor1b)
                    .on(DeadTransitionBreaksFlowPayload.FlowDecision.TWO).handleBy(idProcessor3b)

            idProcessor1.onAny().mergeBy(idProcessor2)

            idProcessor2.onAny().mergeBy(idProcessor3)

            idProcessor3.onAny().handleBy(idProcessor4)

            idProcessor4.onAny().complete()

            idProcessor1b.onAny().mergeBy(idProcessor3b)

            idProcessor3b.onAny().handleBy(idProcessor4)

            coordinates()
                    .vx(idProcessor1b, 254, 139)
                    .vx(idProcessor4, 196, 362)
                    .vx(idProcessor1, -206, 132, -158, 214)
                    .vx(idProcessor2, -64, 140, -21, 253)
                    .vx(idProcessor3, 63, 140, 111, 272)
                    .vx(idProcessor3b, 391, 131, 421, 241)
                    .ct(idProcessor4, 245, 504);


        }
    }

    @Test
    @Throws(Exception::class)
    fun dead_transition_breaks_flow() {

        reactor.registerGraphIfAbsent(DeadTransitionBreaksFlowGraph::class.java)

        var result = reactor.submit(
                DeadTransitionBreaksFlowPayload(DeadTransitionBreaksFlowPayload.FlowDecision.THREE))
                .resultFuture
                .get(5, TimeUnit.MINUTES)

        assertEquals(Arrays.asList(1, 2, 3, 4), result.idSequence)

        result = reactor.submit(
                DeadTransitionBreaksFlowPayload(DeadTransitionBreaksFlowPayload.FlowDecision.TWO))
                .resultFuture
                .get(5, TimeUnit.MINUTES)

        assertEquals(Arrays.asList(1, 3, 4), result.idSequence)
    }

    /**
     * Implicit merge group generated for start point if it linked with detached merge point
     * and if this merge point does not belong to other merge group that contains processor
     * or subgraph merge point.
     * Expected result: {2, 0, 1, 3}
     */
    class StartPointRouterGraph : Graph<IdListPayload>() {

        var mergePoint2Semaphore = Semaphore(0)

        var idProcessor0 = handler { IdProcessor(0).handle() }
                .withMerger {
                    idSequence.add(it)
                    Status.OK
                }

        var idProcessor1 = handler { IdProcessor(1).handle() }
                .withMerger {
                    idSequence.add(it)
                    Status.OK
                }

        var router2 = router {
            try {
                mergePoint2Semaphore.acquire()
            } catch (exc: Exception) {
                log.error("Failed to acquire semaphore", exc)
            }

            idSequence.add(2)
            Status.OK
        }

        var idProcessor3 = handler { IdProcessor(3).handle() }
                .withMerger {
                    idSequence.add(it)
                    Status.OK
                }

        init {
            payload()
                    .handleBy(idProcessor0)
                    .handleBy(idProcessor1)
                    .handleBy(router2)

            idProcessor0.onAny().mergeBy(idProcessor1)

            idProcessor1
                    .onAny().mergeBy(idProcessor3)

            router2
                    .onAny().handleBy(idProcessor3)
                    .onAny().mergeBy(idProcessor0)

            idProcessor3.onAny().complete()

            coordinates()
                    .vx(router2, 207, 65)
                    .vx(idProcessor0, 13, 72, 89, 207)
                    .vx(idProcessor1, -201, 70, -113, 249)
                    .vx(idProcessor3, 278, 158, 196, 299)
                    .ct(idProcessor3, 191, 390);

        }
    }

    @Test
    @Throws(Exception::class)
    fun graph_with_router_connected_to_start_point() {

        val graph = StartPointRouterGraph()

        reactor.registerGraphIfAbsent(graph)

        val resultFuture = reactor.submit(IdListPayload()).resultFuture

        try {
            resultFuture.get(1, TimeUnit.SECONDS)
            fail<Any>("Failed to wait for mergePoint2")
        } catch (exc: TimeoutException) {
            //ignore timeout exception
        }

        graph.mergePoint2Semaphore.release()

        val result = resultFuture.get(5, TimeUnit.MINUTES)

        assertEquals(Arrays.asList(2, 0, 1, 3), result.idSequence)
    }


    /**
     * Dead transition deactivates merger and all outgoing transitions
     * Expected result: {2, 0, 1, 3}
     */
    class DeadTransitionGraph : Graph<IdListPayload>() {

        enum class Status {
            FIRST, SECOND, OK
        }


        val processor4mergerSemaphore = Semaphore(0)

        val idProcessor0 = handler { IdProcessor(0).handle() }
                .withRoutingMerger {
                    idSequence.add(it)
                    DeadTransitionGraph.Status.FIRST
                }

        val idProcessor1 = handler { IdProcessor(1).handle() }
                .withRoutingMerger {
                    idSequence.add(it)
                    Status.OK
                }

        val idProcessor2 = handler { IdProcessor(2).handle() }
                .withRoutingMerger {
                    idSequence.add(it)
                    Status.OK
                }

        val idProcessor3 = handler { IdProcessor(3).handle() }
                .withRoutingMerger {
                    idSequence.add(it)
                    Status.OK
                }


        val idProcessor4 = handler { IdProcessor(4).handle() }
                .withRoutingMerger {
                    try {
                        idSequence.add(it)
                        processor4mergerSemaphore.acquire()
                        Status.OK
                    } catch (exc: Exception) {
                        throw RuntimeException(exc)
                    }
                }


        init {
            payload()
                    .handleBy(idProcessor0)
                    .handleBy(idProcessor1)
                    .handleBy(idProcessor2)

            idProcessor0
                    .on(DeadTransitionGraph.Status.FIRST).handleBy(idProcessor4)
                    .on(DeadTransitionGraph.Status.SECOND).mergeBy(idProcessor1)

            idProcessor1
                    .onAny().mergeBy(idProcessor2)

            idProcessor2
                    .onAny().handleBy(idProcessor3)

            idProcessor3
                    .onAny().complete()

            idProcessor4
                    .onAny().complete()

            coordinates()
                    .pd(461, 96)
                    .vx(idProcessor0, 366, 177, 407, 250)
                    .vx(idProcessor1, 502, 178, 538, 304)
                    .vx(idProcessor2, 649, 181, 682, 315)
                    .vx(idProcessor3, 708, 396, 755, 477)
                    .vx(idProcessor4, 289, 339, 330, 436)
                    .ct(idProcessor3, 814, 518)
                    .ct(idProcessor4, 396, 475)
        }
    }

    @Test
    @Throws(Exception::class)
    fun dead_transition_kills_merge_point_and_all_outgoing_transitions() {

        val graph = DeadTransitionGraph()

        reactor.registerGraphIfAbsent(graph)

        val resultFuture = reactor.submit(IdListPayload()).resultFuture

        try {
            log.info("wait for 2 seconds to test if graph executes merge points number 2 or 3")
            val result = resultFuture.get(2, TimeUnit.SECONDS)
            fail<Any>("Failed to wait graph execution. $result")
        } catch (exc: TimeoutException) {
            //ignore timeout exception
        }

        graph.processor4mergerSemaphore.release()

        val result = resultFuture.get(5, TimeUnit.MINUTES)

        assertEquals(Arrays.asList(0, 4), result.idSequence)
    }


    /**
     * Test will check that single detached merge point id end up at payloads idList.
     * Expected result: {1}
     */
    class SingleRouterGraph : Graph<IdListPayload>() {

        var mergePoint = router {
            idSequence.add(1)
            Status.OK
        }

        init {
            payload()
                    .handleBy(mergePoint)

            mergePoint.onAny().complete()
        }
    }

    @Test
    fun single_detached_merge_point() {


        reactor.registerGraphIfAbsent(SingleRouterGraph::class.java)

        val resultPayload = reactor.submit(IdListPayload())
                .resultFuture
                .get(10, TimeUnit.SECONDS)

        assertEquals(Arrays.asList(1), resultPayload.idSequence)
    }


    /**
     * Handler return null as a result.
     */
    class ReturnNullInHandlerGraph : Graph<IdListPayload>() {


        val nullResultHandler = NullResultHandler()

        var vx1 =
                handler {
                    nullResultHandler.handleWithNullResult()

                }.withMerger {
                    fail<Any>("Merger should not be invoked when handler returned NULL")
                    idSequence.add(1)
                    Status.OK
                }

        init {
            payload()
                    .handleBy(vx1)

            vx1.onAny().complete()
        }
    }

    @Test
    fun handler_returns_null_as_a_result() {
        reactor.registerGraphIfAbsent(ReturnNullInHandlerGraph::class.java)

        try {

            reactor.submit(IdListPayload())
                    .resultFuture
                    .get(10, TimeUnit.SECONDS)

            fail<Nothing>("When handler returns NULL result future should complete exceptionally.")

        } catch (exc: Exception) {
            log.info(exc) { "When handler returns NULL result future completed with NPE" }
        }

    }

    class RiseExceptionForImplicitEmptyMergerWithOnTransitionGraph : Graph<IdListPayload>() {

        enum class STATUS { OK }

        val vertex = handler { completedFuture("data") }.withoutMerger()
        val vertex2 = handler { completedFuture("data2") }.withoutMerger()

        init {
            payload().handleBy(vertex)
            vertex.on(STATUS.OK).handleBy(vertex2)
            vertex2.onAny().complete()
        }
    }

    @Test
    fun vertex_with_implicit_empty_merger_rise_exception_when_used_in_on_transition() {
        try {
            reactor.registerGraph(RiseExceptionForImplicitEmptyMergerWithOnTransitionGraph::class.java)
            fail<Nothing>("should rise exception")

        } catch (exc: Exception) {
            log.info(exc) { "Expected exception" }

            ExceptionUtils.getStackTrace(exc).let {
                assertThat(it, containsString("on() transition"))
                assertThat(it, containsString("does not have merger"))
            }
        }
    }

    class ImplicitEmptyMergerWithOnAnyTransitionGraph : Graph<IdListPayload>() {

        val vertex11 = handler {
            idSequence.add(11)
            completedFuture("data")
        }.withoutMerger()

        val vertex12 = handler {
            idSequence.add(12)
            completedFuture("data")
        }.withoutMerger()

        val vertex2 = handler {
            idSequence.add(2)
            completedFuture("data2")
        }.withoutMerger()

        init {
            payload()
                    .handleBy(vertex11)
                    .handleBy(vertex12)

            vertex11
                    .onAny().mergeBy(vertex12)

            vertex12
                    .onAny().handleBy(vertex2)

            vertex2.onAny().complete()
        }
    }

    @Test
    fun vertex_with_implicit_empty_merger_works_with_onAny_transition() = runBlocking {
        reactor.registerGraph(ImplicitEmptyMergerWithOnAnyTransitionGraph::class.java)

        reactor.submit(IdListPayload()).resultFuture.await().idSequence.also {
            assertTrue(it.contains(11))
            assertTrue(it.contains(12))
            assertTrue(it.contains(2))
        }

        return@runBlocking
    }
}