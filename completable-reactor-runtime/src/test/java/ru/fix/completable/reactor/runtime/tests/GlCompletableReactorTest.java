package ru.fix.completable.reactor.runtime.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fix.commons.profiler.impl.SimpleProfiler;
import ru.fix.completable.reactor.graph.Graph;
import ru.fix.completable.reactor.graph.Vertex;
import ru.fix.completable.reactor.runtime.CompletableReactor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * @author Kamil Asfandiyarov
 */
//TODO rename after removing old tests
class GlCompletableReactorTest {

    static final Logger log = LoggerFactory.getLogger(GlCompletableReactorTest.class);

    private SimpleProfiler profiler;
    private CompletableReactor reactor;


    @BeforeEach
    void before() {
        profiler = new SimpleProfiler();
        reactor = new CompletableReactor(profiler)
                .setDebugProcessingVertexGraphState(true);
    }

    @AfterEach
    void after() throws Exception {
        reactor.close();
    }


    enum Status {OK, UNUSED}


    static class IdListPayload {
        final List<Integer> idSequence = new ArrayList<>();
    }


    /**
     * Payload contains list of integer ids.
     * When payload goes through vertex chain each vertex adds their id.
     * At the end we can verify by witch vertex and in what order this payload was modified.
     * <p>
     * Test will check that single handler id end up in payload.",
     * Expected result: {1}
     */
    static class SingleProcessorGraph extends Graph<IdListPayload> {

        Vertex idProcessor1 = handler(new IdProcessor(1)::handle)
                .withMerger((pld, id) -> {
                    pld.idSequence.add(id);
                });

        {

            payload().handleBy(idProcessor1);

            idProcessor1.onAny().complete();
        }
    }

    @Test
    void single_processor() throws Exception {

        reactor.registerGraph(SingleProcessorGraph.class);

        IdListPayload resultPayload = reactor.submit(new IdListPayload())
                .getResultFuture()
                .get(10, SECONDS);

        assertEquals(Arrays.asList(1), resultPayload.idSequence);
    }

    /**
     * Test will check that two processor ids end up at payloads idList in correct order.
     * Expected result: {1, 2}
     */
    static class TwoProcessorSequentialMergeGraph extends Graph<IdListPayload> {

        Vertex idProcessor1 = handler(new IdProcessor(1)::handle)
                .withRoutingMerger((pld, id) -> {
                    pld.idSequence.add(id);
                    return Status.OK;
                });
        Vertex idProcessor2 = handler(new IdProcessor(2)::handle)
                .withRoutingMerger((pld, id) -> {
                    pld.idSequence.add(id);
                    return Status.OK;
                });

        {
            payload()
                    .handleBy(idProcessor1)
                    .handleBy(idProcessor2);

            idProcessor1.on(Status.OK).mergeBy(idProcessor2);
            idProcessor1.on(Status.UNUSED).mergeBy(idProcessor2);

            idProcessor2.onAny().complete();
        }
    }

    @Test
    public void two_processors_sequential_merge() throws Exception {

        reactor.registerGraphIfAbsent(TwoProcessorSequentialMergeGraph.class);
        CompletableReactor.Execution<IdListPayload> execution = reactor.submit(new IdListPayload());
        IdListPayload resultPayload = execution.getResultFuture().get(10, TimeUnit.SECONDS);

        assertEquals(Arrays.asList(1, 2), resultPayload.idSequence);
    }


    /**
     * Detached processor is a handler without merger.
     * Main flow does not wait for detached processor to complete.
     * In test Detached processor will run and complete deferred in time.
     * When result of chain is ready detached processor will be activated.
     * Test will check that chain execution will complete on detached processor finish.
     * <p>
     * Expected result: {1, 2}
     */
    static class DetachedProcessorGraph extends Graph<IdListPayload> {

        IdProcessor detachedProcessor = new IdProcessor(3).withLaunchingLatch();

        Vertex idProcessor1 = handler(new IdProcessor(1)::handle)
                .withRoutingMerger((pld, id) -> {
                    pld.idSequence.add(id);
                    return Status.OK;
                });

        Vertex idProcessor2 = handler(new IdProcessor(2)::handle)
                .withRoutingMerger((pld, id) -> {
                    pld.idSequence.add(id);
                    return Status.OK;
                });

        Vertex idProcessor3 = handler(detachedProcessor::handle)
                .withoutMerger();

        {
            payload()
                    .handleBy(idProcessor1)
                    .handleBy(idProcessor2)
                    .handleBy(idProcessor3);

            idProcessor1.on(Status.OK).mergeBy(idProcessor2);

            idProcessor2.onAny().complete();
        }
    }


    @Test
    public void detached_processor() throws Exception {

        DetachedProcessorGraph graph = new DetachedProcessorGraph();
        reactor.registerGraphIfAbsent(graph);

        CompletableReactor.Execution<IdListPayload> result = reactor.submit(new IdListPayload());

        assertEquals(Arrays.asList(1, 2), result.getResultFuture().get(5, TimeUnit.SECONDS).idSequence);

        assertTrue("result future is complete", result.getResultFuture().isDone());
        assertFalse("execution chain is not complete since detached processor still working", result.getChainExecutionFuture().isDone());

        graph.detachedProcessor.launch();

        result.getChainExecutionFuture().get(5, TimeUnit.SECONDS);
        assertTrue("execution chain is complete when detached processor is finished", result.getChainExecutionFuture().isDone());

        //TODO add case when we have to wait for returning from handling method of detached processor before continue
        // with mergers, so detached handlers could have time to read payload before merger modifies it.
    }


    static class SubgraphPaylaod extends IdListPayload {
    }

    /**
     * # Subgraph
     * Subgraph behave the same way as plain processor.
     * The only difference is that instead of simple async operation CompletableReactor launches subgraph execution.
     */
    static class SubgraphPayloadGraph extends Graph<SubgraphPaylaod> {

        Vertex idProcessor11 = handler(new IdProcessor(11)::handle)
                .withMerger((pld, id) -> {
                    pld.idSequence.add(id);
                });

        Vertex idProcessor12 = handler(new IdProcessor(12)::handle)
                .withMerger((pld, id) -> {
                    pld.idSequence.add(id);
                });

        Vertex idProcessor13 = handler(new IdProcessor(13)::handle)
                .withMerger((pld, id) -> {
                    pld.idSequence.add(id);
                });

        {
            payload()
                    .handleBy(idProcessor11)
                    .handleBy(idProcessor12);

            idProcessor11.onAny().mergeBy(idProcessor12);

            idProcessor12.onAny().handleBy(idProcessor13);

            idProcessor13.onAny().complete();

        }
    }

    /**
     * # Parent Graph (IdListPayload)
     * Parent graph is a simple graph that calls subgraph during its flow.
     * Expected result: 1, 11, 12, 13, 2, 3
     */
    static class ParentGraphPayloadGraph extends Graph<IdListPayload> {
        Vertex idProcessor1 = handler(new IdProcessor(1)::handle)
                .withMerger((pld, id) -> {
                    pld.idSequence.add(id);
                });

        Vertex idProcessor2 = handler(new IdProcessor(2)::handle)
                .withMerger((pld, id) -> {
                    pld.idSequence.add(id);
                });

        Vertex idProcessor3 = handler(new IdProcessor(3)::handle)
                .withMerger((pld, id) -> {
                    pld.idSequence.add(id);
                });

        Vertex subgraphProcessor = subgraph(SubgraphPaylaod.class, payload -> new SubgraphPaylaod())
                .withMerger((payload, result) -> {
                    payload.idSequence.addAll(result.idSequence);
                });

        {
            payload()
                    .handleBy(idProcessor1);

            idProcessor1
                    .onAny().handleBy(idProcessor2)
                    .onAny().handleBy(subgraphProcessor);

            subgraphProcessor
                    .onAny().mergeBy(idProcessor2);

            idProcessor2
                    .onAny().handleBy(idProcessor3);

            idProcessor3
                    .onAny().complete();

        }
    }

    @Test
    public void run_subgraph() throws Exception {

        reactor.registerGraphIfAbsent(ParentGraphPayloadGraph.class);
        reactor.registerGraphIfAbsent(SubgraphPayloadGraph.class);

        SubgraphPaylaod subgraphResult = reactor.submit(new SubgraphPaylaod()).getResultFuture().get(5, SECONDS);
        assertEquals(Arrays.asList(11, 12, 13), subgraphResult.idSequence);


        CompletableReactor.Execution<IdListPayload> executionResult = reactor.submit(new IdListPayload());
        IdListPayload resultPaylaod = executionResult.getResultFuture().get(5, TimeUnit.SECONDS);
        assertEquals(Arrays.asList(1, 11, 12, 13, 2, 3), resultPaylaod.idSequence);
    }


    /**
     * Test demonstrates usage of mocked processor instead of real one.
     * Test will check that single processor id end up at payloads idList.
     * <p>
     * Expected result: {42}
     */
    static class SingleInterfaceProcessorGraph extends Graph<IdListPayload> {

        //injected by mockito
        IdProcessorInterface processorInterface;

        Vertex idProcessor1 = handler(() -> processorInterface.handle())
                .withMerger((pld, id) -> {
                    pld.idSequence.add(id);
                });

        {
            payload()
                    .handleBy(idProcessor1);

            idProcessor1
                    .onAny().complete();

        }

    }

    @Test
    public void use_interface_mock_as_processor_with_mockito() throws Exception {

        IdProcessorInterface processorInterface = Mockito.mock(IdProcessorInterface.class);
        Mockito.when(processorInterface.handle()).thenReturn(CompletableFuture.completedFuture(42));

        SingleInterfaceProcessorGraph graph = new SingleInterfaceProcessorGraph();
        graph.processorInterface = processorInterface;

        reactor.registerGraphIfAbsent(graph);


        IdListPayload resultPayload = reactor.submit(new IdListPayload())
                .getResultFuture()
                .get(10, TimeUnit.SECONDS);

        assertEquals(Arrays.asList(42), resultPayload.idSequence);
    }

    static class SingleClassProcessorMockGraph extends Graph<IdListPayload> {

        IdProcessor processor;

        Vertex idProcessor1 = handler(() -> processor.handle())
                .withMerger((pld, id) -> {
                    pld.idSequence.add(id);
                });

        {
            payload()
                    .handleBy(idProcessor1);

            idProcessor1
                    .onAny().complete();

        }
    }

    @Test
    public void use_class_mock_as_processor_with_mockito() throws Exception {

        IdProcessor processor = Mockito.mock(IdProcessor.class);
        Mockito.when(processor.handle()).thenReturn(CompletableFuture.completedFuture(78));

        SingleClassProcessorMockGraph graph = new SingleClassProcessorMockGraph();
        graph.processor = processor;

        reactor.registerGraphIfAbsent(graph);

        IdListPayload resultPayload = reactor.submit(new IdListPayload())
                .getResultFuture()
                .get(10, TimeUnit.SECONDS);

        assertEquals(Arrays.asList(78), resultPayload.idSequence);
    }


    /**
     * Router works as an regular merge point
     * but there is no processor or subgraph or theirs result to merge.
     * Router simply modify payload and send it through outgoing transitions.
     * Expected result: {42, 1, 0}
     */

    static class RouterFromStartPointGraph extends Graph<IdListPayload> {

        Vertex idProcessor0 = handler(new IdProcessor(0)::handle)
                .withMerger((pld, id) -> {
                    pld.idSequence.add(id);
                });

        Vertex idProcessor1 = handler(new IdProcessor(1)::handle)
                .withMerger((pld, id) -> {
                    pld.idSequence.add(id);
                });

        Vertex mergePoint = router(
                /*
                    # add 42
                    Simple router example,
                    that adds 42 to payload sequience
                */
                pld -> {
                    pld.idSequence.add(42);
                    return Status.OK;
                });

        {
            payload()
                    .handleBy(idProcessor0)
                    .handleBy(idProcessor1)
                    .handleBy(mergePoint);

            mergePoint
                    .onAny().mergeBy(idProcessor1);

            idProcessor1
                    .onAny().mergeBy(idProcessor0);

            idProcessor0
                    .onAny().complete();

            coordinates()
                    .payload(107, 9)
                    .handler(idProcessor0, 22, 78)
                    .handler(idProcessor1, 211, 79)
                    .merger(idProcessor0, 126, 215)
                    .merger(idProcessor1, 267, 187)
                    .router(mergePoint, 424, 160)
                    .complete(idProcessor0, 57, 274);

        }
    }

    @Test
    public void router_from_start_point() throws Exception {
        reactor.registerGraphIfAbsent(RouterFromStartPointGraph.class);

        CompletableReactor.Execution<IdListPayload> result = reactor.submit(new IdListPayload());

        IdListPayload resultPayload = result
                .getResultFuture()
                .get(10, TimeUnit.SECONDS);

        assertEquals(Arrays.asList(42, 1, 0), resultPayload.idSequence);
    }

    /**
     * Router works as an regular merge point,
     * but there is no processor or subgraph or theirs result to merge.
     * Router simply modify payload and send it through outgoing transitions.
     * Expected result: {0, 1, 42}
     */
    static class RouterFromProcessorsMergerGraph extends Graph<IdListPayload> {

        Vertex idProcessor0 = handler(new IdProcessor(0)::handle)
                .withMerger((pld, id) -> {
                    pld.idSequence.add(id);
                });

        Vertex idProcessor1 = handler(new IdProcessor(1)::handle)
                .withMerger((pld, id) -> {
                    pld.idSequence.add(id);
                });

        Vertex router = router(
                /*
                  # addMergePointId
                  Adds merge point id
                  to payload sequence
                */
                pld -> {
                    pld.idSequence.add(42);
                    return Status.OK;
                });

        {
            payload()
                    .handleBy(idProcessor0)
                    .handleBy(idProcessor1);

            idProcessor0
                    .onAny().mergeBy(idProcessor1);

            idProcessor1
                    .onAny().handleBy(router);

            router
                    .onAny().complete();

            coordinates()
                    .merger(idProcessor1, 85, 180);
        }
    }

    @Test
    public void router_from_processors_merge_point() throws Exception {

        reactor.registerGraphIfAbsent(RouterFromProcessorsMergerGraph.class);

        CompletableReactor.Execution<IdListPayload> result = reactor.submit(new IdListPayload());

        IdListPayload resultPayload = result
                .getResultFuture()
                .get(10, TimeUnit.SECONDS);

        assertEquals(Arrays.asList(0, 1, 42), resultPayload.idSequence);

    }


    /**
     * OptionalProcessorExecution shows how to use detached merge point to avoid unnecessary processor execution
     * Expected result for right: {1 ,2}
     * Expected result for left: {2}
     */
    static class OptionalProcessorExecutionPayload extends IdListPayload {
        OPTIONAL_DECISION whereToGo;

        public OptionalProcessorExecutionPayload(OPTIONAL_DECISION whereToGo) {
            this.whereToGo = whereToGo;
        }
    }

    enum OPTIONAL_DECISION {
        LEFT, RIGHT
    }

    static class OptionalProcessorExecutionGraph extends Graph<OptionalProcessorExecutionPayload> {
        Vertex idProcessor1 = handler(new IdProcessor(1)::handle)
                .withMerger((pld, id) -> {
                    pld.idSequence.add(id);
                });

        Vertex idProcessor2 = handler(new IdProcessor(2)::handle)
                .withMerger((pld, id) -> {
                    pld.idSequence.add(id);
                });

        Vertex idProcessor3 = handler(new IdProcessor(3)::handle)
                .withMerger((pld, id) -> {
                    pld.idSequence.add(id);
                });

        Vertex mergePoint = router(
                /*
                    #getWhereToGo
                    returns destination from payload
                */
                pld -> {
                    return pld.whereToGo;
                });


        {
            payload()
                    .handleBy(mergePoint);

            mergePoint
                    .on(OPTIONAL_DECISION.LEFT).handleBy(idProcessor2)
                    .on(OPTIONAL_DECISION.RIGHT).handleBy(idProcessor1);

            idProcessor1
                    .onAny().handleBy(idProcessor2);

            idProcessor2
                    .onAny().handleBy(idProcessor3);

            idProcessor3
                    .onAny().complete();

            coordinates()
                    .payload(0, 17)
                    .handler(idProcessor1, 246, 147)
                    .handler(idProcessor2, -17, 252)
                    .handler(idProcessor3, -15, 413)
                    .merger(idProcessor1, 287, 223)
                    .merger(idProcessor2, 21, 328)
                    .merger(idProcessor3, 23, 504)
                    .router(mergePoint, 98, 79)
                    .complete(idProcessor3, 129, 537);

        }
    }


    @Test
    public void optional_processor_execution() throws Exception {

        reactor.registerGraphIfAbsent(OptionalProcessorExecutionGraph.class);

        CompletableReactor.Execution<OptionalProcessorExecutionPayload> result = reactor.submit(
                new OptionalProcessorExecutionPayload(OPTIONAL_DECISION.RIGHT));

        OptionalProcessorExecutionPayload resultPayload = result
                .getResultFuture()
                .get(10, TimeUnit.SECONDS);

        assertEquals(Arrays.asList(1, 2, 3), resultPayload.idSequence);


        result = reactor.submit(new OptionalProcessorExecutionPayload(OPTIONAL_DECISION.LEFT));


        resultPayload = result
                .getResultFuture()
                .get(10, TimeUnit.SECONDS);

        assertEquals(Arrays.asList(2, 3), resultPayload.idSequence);
    }


    static class DeadTransitionBreaksFlowPayload extends IdListPayload {
        public enum FlowDecision {
            THREE, TWO
        }

        FlowDecision flowDecision;

        public DeadTransitionBreaksFlowPayload(FlowDecision flowDecision) {
            this.flowDecision = flowDecision;
        }
    }

    /*
        It possible for Router to have several outgoing transitions.
        This transitions could be executed conditionally.
        If condition evaluated to false then this outgoing transitions marked as dead.
        If all incoming transitions to handler or subgraph marked as dead, then this handler or subgraph and it's merge point marked as dead.
    */
    static class DeadTransitionBreaksFlowGraph extends Graph<DeadTransitionBreaksFlowPayload> {

        Vertex idProcessor1 = handler(new IdProcessor(1)::handle)
                .withRoutingMerger((pld, id) -> {
                    pld.idSequence.add(id);
                    return pld.flowDecision;
                });

        Vertex idProcessor1b = idProcessor1.clone();

        Vertex idProcessor2 = handler(new IdProcessor(2)::handle)
                .withRoutingMerger((pld, id) -> {
                    pld.idSequence.add(id);
                    return Status.OK;
                });

        Vertex idProcessor3 = handler(new IdProcessor(3)::handle)
                .withRoutingMerger((pld, id) -> {
                    pld.idSequence.add(id);
                    return Status.OK;
                });

        Vertex idProcessor3b = idProcessor3.clone();


        Vertex idProcessor4 = handler(new IdProcessor(4)::handle)
                .withRoutingMerger((pld, id) -> {
                    pld.idSequence.add(id);
                    return Status.OK;
                });


        Vertex decisionMergePoint = router(pld -> pld.flowDecision);

        {
            payload()
                    .handleBy(decisionMergePoint);

            decisionMergePoint
                    .on(DeadTransitionBreaksFlowPayload.FlowDecision.THREE).handleBy(idProcessor1)
                    .on(DeadTransitionBreaksFlowPayload.FlowDecision.THREE).handleBy(idProcessor2)
                    .on(DeadTransitionBreaksFlowPayload.FlowDecision.THREE).handleBy(idProcessor3)
                    .on(DeadTransitionBreaksFlowPayload.FlowDecision.TWO).handleBy(idProcessor1b)
                    .on(DeadTransitionBreaksFlowPayload.FlowDecision.TWO).handleBy(idProcessor3b);

            idProcessor1.onAny().mergeBy(idProcessor2);

            idProcessor2.onAny().mergeBy(idProcessor3);

            idProcessor3.onAny().handleBy(idProcessor4);

            idProcessor4.onAny().complete();

            idProcessor1b.onAny().mergeBy(idProcessor3b);

            idProcessor3b.onAny().handleBy(idProcessor4);

            coordinates()
                    .handler(idProcessor1, -206, 132)
                    .handler(idProcessor1b, 254, 139)
                    .handler(idProcessor2, -64, 140)
                    .handler(idProcessor3, 63, 140)
                    .handler(idProcessor3b, 391, 131)
                    .handler(idProcessor4, 196, 362)
                    .merger(idProcessor1, -158, 214)
                    .merger(idProcessor2, -21, 253)
                    .merger(idProcessor3, 111, 272)
                    .merger(idProcessor3b, 421, 241)
                    .complete(idProcessor4, 250, 503);


        }
    }

    @Test
    public void dead_transition_breaks_flow() throws Exception {

        reactor.registerGraphIfAbsent(DeadTransitionBreaksFlowGraph.class);

        DeadTransitionBreaksFlowPayload result = reactor.submit(
                new DeadTransitionBreaksFlowPayload(DeadTransitionBreaksFlowPayload.FlowDecision.THREE))
                .getResultFuture()
                .get(5, TimeUnit.MINUTES);

        assertEquals(Arrays.asList(1, 2, 3, 4), result.idSequence);

        result = reactor.submit(
                new DeadTransitionBreaksFlowPayload(DeadTransitionBreaksFlowPayload.FlowDecision.TWO))
                .getResultFuture()
                .get(5, TimeUnit.MINUTES);

        assertEquals(Arrays.asList(1, 3, 4), result.idSequence);
    }

    /**
     * Implicit merge group generated for start point if it linked with detached merge point
     * and if this merge point does not belong to other merge group that contains processor
     * or subgraph merge point.
     * Expected result: {2, 0, 1, 3}
     */
    static class StartPointRouterGraph extends Graph<IdListPayload> {

        Semaphore mergePoint2Semaphore = new Semaphore(0);

        Vertex idProcessor0 = handler(new IdProcessor(0)::handle)
                .withRoutingMerger((pld, id) -> {
                    pld.idSequence.add(id);
                    return Status.OK;
                });

        Vertex idProcessor1 = handler(new IdProcessor(1)::handle)
                .withRoutingMerger((pld, id) -> {
                    pld.idSequence.add(id);
                    return Status.OK;
                });

        Vertex router2 = router(
                pld -> {
                    try {
                        mergePoint2Semaphore.acquire();
                    } catch (Exception exc) {
                        log.error("Failed to acquire semaphore", exc);
                    }

                    pld.idSequence.add(2);
                    return GlCompletableReactorTest.Status.OK;
                });

        Vertex idProcessor3 = handler(new IdProcessor(3)::handle)
                .withRoutingMerger((pld, id) -> {
                    pld.idSequence.add(id);
                    return Status.OK;
                });

        {
            payload()
                    .handleBy(idProcessor0)
                    .handleBy(idProcessor1)
                    .handleBy(router2);

            idProcessor0.onAny().mergeBy(idProcessor1);

            idProcessor1
                    .onAny().mergeBy(idProcessor3);

            router2
                    .onAny().handleBy(idProcessor3)
                    .onAny().mergeBy(idProcessor0);

            idProcessor3.onAny().complete();

            coordinates()
                    .handler(idProcessor0, 13, 72)
                    .handler(idProcessor1, -201, 70)
                    .handler(idProcessor3, 278, 158)
                    .router(router2, 207, 65)
                    .merger(idProcessor0, 89, 207)
                    .merger(idProcessor1, -113, 249)
                    .merger(idProcessor3, 196, 299)
                    .complete(idProcessor3, 185, 391);

        }
    }

    @Test
    public void graph_with_router_connected_to_start_point() throws Exception {

        StartPointRouterGraph graph = new StartPointRouterGraph();

        reactor.registerGraphIfAbsent(graph);

        CompletableFuture<IdListPayload> resultFuture = reactor.submit(new IdListPayload()).getResultFuture();

        try {
            resultFuture.get(1, TimeUnit.SECONDS);
            fail("Failed to wait for mergePoint2");
        } catch (TimeoutException exc) {
            //ignore timeout exception
        }

        graph.mergePoint2Semaphore.release();

        IdListPayload result = resultFuture.get(5, TimeUnit.MINUTES);

        assertEquals(Arrays.asList(2, 0, 1, 3), result.idSequence);
    }

    /**
     * Dead transition deactivates merger and all outgoing transitions
     * Expected result: {2, 0, 1, 3}
     */
    static class DeadTransitionGraph extends Graph<IdListPayload> {

        Semaphore processor4mergerSemaphore = new Semaphore(0);

        public enum Status {
            FIRST, SECOND
        }

        Vertex idProcessor0 = handler(new IdProcessor(0)::handle)
                .withRoutingMerger((pld, id) -> {
                    pld.idSequence.add(id);
                    return DeadTransitionGraph.Status.FIRST;
                });

        Vertex idProcessor1 = handler(new IdProcessor(1)::handle)
                .withRoutingMerger((pld, id) -> {
                    pld.idSequence.add(id);
                    return GlCompletableReactorTest.Status.OK;
                });

        Vertex idProcessor2 = handler(new IdProcessor(2)::handle)
                .withRoutingMerger((pld, id) -> {
                    pld.idSequence.add(id);
                    return GlCompletableReactorTest.Status.OK;
                });

        Vertex idProcessor3 = handler(new IdProcessor(3)::handle)
                .withRoutingMerger((pld, id) -> {
                    pld.idSequence.add(id);
                    return GlCompletableReactorTest.Status.OK;
                });


        Vertex idProcessor4 = handler(new IdProcessor(4)::handle)
                .withRoutingMerger((pld, id) -> {
                    try {
                        pld.idSequence.add(id);
                        processor4mergerSemaphore.acquire();
                        return GlCompletableReactorTest.Status.OK;
                    } catch (Exception exc) {
                        throw new RuntimeException(exc);
                    }
                });


        {
            payload()
                    .handleBy(idProcessor0)
                    .handleBy(idProcessor1)
                    .handleBy(idProcessor2);

            idProcessor0
                    .on(DeadTransitionGraph.Status.FIRST).handleBy(idProcessor4)
                    .on(DeadTransitionGraph.Status.SECOND).mergeBy(idProcessor1);

            idProcessor1
                    .onAny().mergeBy(idProcessor2);

            idProcessor2
                    .onAny().handleBy(idProcessor3);

            idProcessor3
                    .onAny().complete();

            idProcessor4
                    .onAny().complete();

            coordinates()
                    .payload(461, 96)
                    .handler(idProcessor0, 366, 177)
                    .handler(idProcessor2, 649, 181)
                    .handler(idProcessor1, 502, 178)
                    .handler(idProcessor3, 708, 396)
                    .handler(idProcessor4, 289, 339)
                    .merger(idProcessor0, 407, 250)
                    .merger(idProcessor1, 538, 304)
                    .merger(idProcessor2, 682, 315)
                    .merger(idProcessor3, 755, 477)
                    .merger(idProcessor4, 330, 436)
                    .complete(idProcessor3, 820, 514)
                    .complete(idProcessor4, 396, 475);
        }
    }

    @Test
    public void dead_transition_kills_merge_point_and_all_outgoing_transitions() throws Exception {

        DeadTransitionGraph graph = new DeadTransitionGraph();

        reactor.registerGraphIfAbsent(graph);

        CompletableFuture<IdListPayload> resultFuture = reactor.submit(new IdListPayload()).getResultFuture();

        try {
            log.info("wait for 2 seconds to test if graph executes merge points number 2 or 3");
            IdListPayload result = resultFuture.get(2, TimeUnit.SECONDS);
            fail("Failed to wait graph execution. " + result);
        } catch (TimeoutException exc) {
            //ignore timeout exception
        }

        graph.processor4mergerSemaphore.release();

        IdListPayload result = resultFuture.get(5, TimeUnit.MINUTES);

        assertEquals(Arrays.asList(0, 4), result.idSequence);
    }


    /**
     * Test will check that single detached merge point id end up at payloads idList.
     * Expected result: {1}
     */
    static class SingleRouterGraph extends Graph<IdListPayload> {

        Vertex mergePoint = router(pld -> {
            pld.idSequence.add(1);
            return Status.OK;
        });

        {
            payload()
                    .handleBy(mergePoint);

            mergePoint.onAny().complete();
        }
    }

    @Test
    public void single_detached_merge_point() throws Exception {


        reactor.registerGraphIfAbsent(SingleRouterGraph.class);

        IdListPayload resultPayload = reactor.submit(new IdListPayload())
                .getResultFuture()
                .get(10, TimeUnit.SECONDS);

        assertEquals(Arrays.asList(1), resultPayload.idSequence);
    }


    /**
     * Handler return null as a result.
     */
    static class ReturnNullInHandlerGraph extends Graph<IdListPayload> {
        Vertex handler1 =
                handler(
                        // returns null result
                        pld -> null

                ).withMerger((pld, result) -> {
                    assertNull(result);
                    pld.idSequence.add(1);
                });

        {
            payload()
                    .handleBy(handler1);

            handler1.onAny().complete();
        }
    }

    @Test
    void handler_returns_null_as_a_result() throws Exception {
        reactor.registerGraphIfAbsent(ReturnNullInHandlerGraph.class);

        try {

            reactor.submit(new IdListPayload())
                    .getResultFuture()
                    .get(10, TimeUnit.SECONDS);

            fail("When handler returns NULL result future should complete exceptionally.");

        } catch (Exception exc) {
            log.info("When handler returns NULL result future completed with NPE");
        }
    }
}