package ru.fix.completable.reactor.runtime;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ru.fix.commons.profiler.impl.SimpleProfiler;
import ru.fix.completable.reactor.api.PayloadDescription;
import ru.fix.completable.reactor.ui.ReactorHtmlUIBuilder;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * @author Kamil Asfandiyarov
 */
@Slf4j
public class CompletableReactorTest {

    private SimpleProfiler profiler;
    private CompletableReactor reactor;
    private ReactorGraphBuilder graphBuilder;

    enum Status {OK, UNUSED}


    @Data
    @Accessors(chain = true)
    static class IdListPaylaod {
        final List<Integer> idSequence = new ArrayList<>();

    }

    @Before
    public void before() throws Exception {
        profiler = new SimpleProfiler();
        reactor = new CompletableReactor(profiler)
                .setDebugProcessingVertexGraphState(true);

        graphBuilder = new ReactorGraphBuilder();
    }

    static void printGraph(ReactorGraph<?>... graphs) throws Exception {

        ReactorGraph.write(graphs);

        System.out.println(
                ReactorHtmlUIBuilder.write(
                        Arrays.stream(graphs)
                                .map(ReactorGraph::serialize)
                                .collect(Collectors.toList()),
                        Paths.get(String.format("%s.%s.graph.html",
                                CompletableReactorTest.class.getSimpleName(),
                                Arrays.stream(graphs)
                                        .map(ReactorGraph::getPayloadClass)
                                        .map(Class::getSimpleName)
                                        .collect(Collectors.joining("."))))
                ).toUri());
    }

    @PayloadDescription(doc = {
            "Payload goes through processor chain and collect",
            " all processors ids that merged their results to payload.",
            "Test will check that single processor id end up at payloads idList."
    })
    static class SingleProcessorPayload extends IdListPaylaod {
    }

    @Test
    public void single_processor() throws Exception {

        GraphProcessor<IdProcessor> idProcessor1 = graphBuilder.processor(new IdProcessor(1));

        ReactorGraph graph = graphBuilder.payload(SingleProcessorPayload.class)
                .processedBy(idProcessor1)
                .withHandler(IdProcessor::handle)
                .withMerger((pld, id) -> {
                    pld.getIdSequence().add(id);
                    return Status.OK;
                })

                .startPoint()
                .handleBy(idProcessor1)

                .singleMerge(idProcessor1)
                .onAny().complete()

                .coordinates()
                .start(500, 100)
                .proc("IdProcessor@0", 100, 100)
                .merge("IdProcessor@0", 390, 502)
                .complete("IdProcessor@0", 100, 100)

                .buildGraph();

        printGraph(graph);

        reactor.registerReactorGraph(graph);

        SingleProcessorPayload resultPayload = reactor.submit(new SingleProcessorPayload())
                .getResultFuture()
                .get(10, TimeUnit.SECONDS);

        assertEquals(Arrays.asList(1), resultPayload.getIdSequence());
    }

    @PayloadDescription(doc = {
            "Payload goes through processor chain and collect",
            " all processors ids that merged their results to payload.",
            "Test will check that two processor ids end up at payloads idList in correct order."
    })
    static class TwoProcessorSequentialMergePayload extends IdListPaylaod {
    }

    @Test
    public void two_processors_sequential_merge() throws Exception {

        GraphProcessor<IdProcessor> idProcessor1 = graphBuilder.processor(new IdProcessor(1)).withId(1);
        GraphProcessor<IdProcessor> idProcessor2 = graphBuilder.processor(new IdProcessor(2)).withId(2);

        ReactorGraph graph = graphBuilder.payload(TwoProcessorSequentialMergePayload.class)
                .processedBy(idProcessor1)
                .withHandler(IdProcessor::handle)
                .withMerger((pld, id) -> {
                    pld.getIdSequence().add(id);
                    return Status.OK;
                })

                .processedBy(idProcessor2)
                .withHandler(IdProcessor::handle)
                .withMerger((pld, id) -> {
                    pld.getIdSequence().add(id);
                    return Status.OK;
                })

                .startPoint()
                .handleBy(idProcessor1, idProcessor2)

                .singleMerge(idProcessor1)
                .on(Status.OK, Status.UNUSED).merge(idProcessor2)

                .singleMerge(idProcessor2)
                .onAny().complete()

                .coordinates()
                .proc("IdProcessor@1", 254, 214)
                .proc("IdProcessor@2", 643, 215)
                .merge("IdProcessor@1", 421, 409)
                .merge("IdProcessor@2", 615, 434)
                .start(500, 100)
                .complete("IdProcessor@2", 620, 560)

                .buildGraph();

        printGraph(graph);

        reactor.registerReactorGraph(graph);


        TwoProcessorSequentialMergePayload resultPayload = reactor.submit(new TwoProcessorSequentialMergePayload())
                .getResultFuture()
                .get(10, TimeUnit.SECONDS);

        assertEquals(Arrays.asList(1, 2), resultPayload.getIdSequence());
    }

    @PayloadDescription(doc = {
            "Payload goes through processor chain and collect",
            " all processors ids that merged their results to payload.",
            "Detached processor will complete deferred.",
            "When result of chain is ready detached processor will be activated.",
            "Test will check that chain execution will complete on detached processor finish."
    })
    static class DetachedProcessorPayload extends IdListPaylaod {
    }

    @Test
    public void detached_processor() throws Exception {

        GraphProcessor<IdProcessor> idProcessor1 = graphBuilder.processor(new IdProcessor(1)).withId(1);
        GraphProcessor<IdProcessor> idProcessor2 = graphBuilder.processor(new IdProcessor(2)).withId(2);
        GraphProcessor<IdProcessor> idProcessor3 = graphBuilder.processor(new IdProcessor(3).withLaunchingLatch()).withId(3);

        ReactorGraph graph = graphBuilder.payload(DetachedProcessorPayload.class)
                .processedBy(idProcessor1)
                .withHandler(IdProcessor::handle)
                .withMerger((pld, id) -> {
                    pld.getIdSequence().add(id);
                    return Status.OK;
                })

                .processedBy(idProcessor2)
                .withHandler(IdProcessor::handle)
                .withMerger((pld, id) -> {
                    pld.getIdSequence().add(id);
                    return Status.OK;
                })

                .processedBy(idProcessor3)
                .withHandler(IdProcessor::handle)
                .withoutMerger()

                .startPoint()
                .handleBy(idProcessor1)
                .handleBy(idProcessor2)
                .handleBy(idProcessor3)

                .singleMerge(idProcessor1)
                .on(Status.OK).merge(idProcessor2)

                .singleMerge(idProcessor2)
                .onAny().complete()

                .coordinates()
                .start(500, 100)
                .proc("IdProcessor@1", 100, 100)
                .proc("IdProcessor@2", 100, 100)
                .proc("IdProcessor@3", 100, 100)
                .merge("IdProcessor@1", 457, 474)
                .merge("IdProcessor@2", 100, 100)
                .complete("IdProcessor@2", 100, 100)

                .buildGraph();

        printGraph(graph);

        reactor.registerReactorGraph(graph);

        CompletableReactor.Execution<DetachedProcessorPayload> result = reactor.submit(new DetachedProcessorPayload());

        assertEquals(Arrays.asList(1, 2), result.getResultFuture().get(5, TimeUnit.SECONDS).getIdSequence());

        assertTrue("result future is complete", result.getResultFuture().isDone());
        assertFalse("execution chain is not complete since detached processor still working", result.getChainExecutionFuture().isDone());

        idProcessor3.getProcessor().launch();

        result.getChainExecutionFuture().get(5, TimeUnit.SECONDS);
        assertTrue("execution chain is complete when detached processor is finished", result.getChainExecutionFuture().isDone());
    }

    @PayloadDescription(doc = {
            "Payload goes through processor chain and collect",
            " all processors ids that merged their results to payload.",
            "Merge group joins merge points in a way that all outgoing transitions",
            " will be activated only after all merge points from group complete.",
            "Test will check that merge points outgoing transitions activated only after all incoming",
            " into merge group transitions completes."
    })
    static class PayloadWithMergeGroup extends IdListPaylaod {
    }

    @Test
    public void outgoing_transitions_should_wait_all_merges_to_complete_in_merge_grup() throws Exception {

        GraphProcessor<IdProcessor> idProcessor1 = graphBuilder.processor(new IdProcessor(1)).withId(1);
        GraphProcessor<IdProcessor> idProcessor2 = graphBuilder.processor(new IdProcessor(2)).withId(2);
        GraphProcessor<IdProcessor> idProcessor3 = graphBuilder.processor(new IdProcessor(3).withLaunchingLatch()).withId(3);

        AtomicBoolean mergingIsDone = new AtomicBoolean();

        ReactorGraph graph = graphBuilder.payload(PayloadWithMergeGroup.class)
                .processedBy(idProcessor1)
                .withHandler(IdProcessor::handle)
                .withMerger((pld, id) -> {
                    mergingIsDone.set(true);

                    pld.getIdSequence().add(id);
                    return Status.OK;
                })

                .processedBy(idProcessor2)
                .withHandler(IdProcessor::handle)
                .withMerger((pld, id) -> {
                    pld.getIdSequence().add(id);
                    return Status.OK;
                })

                .processedBy(idProcessor3)
                .withHandler(IdProcessor::handle)
                .withMerger((pld, id) -> {
                    pld.getIdSequence().add(id);
                    return Status.OK;
                })

                .startPoint()
                .handleBy(idProcessor1)
                .handleBy(idProcessor2)
                .handleBy(idProcessor3)

                .multiMerge()
                .merge(idProcessor1)
//                .onAny().complete()
                .onAny().merge(idProcessor2)

                .merge(idProcessor2)
                .onAny().merge(idProcessor3)

                .merge(idProcessor3)
                .onAny().complete()

                .coordinates()

                .buildGraph();

        printGraph(graph);

        reactor.registerReactorGraph(graph);

        CompletableReactor.Execution<PayloadWithMergeGroup> result = reactor.submit(new PayloadWithMergeGroup(), TimeUnit.DAYS.toMillis(1));
        try {
            log.info("Waiting for 1 seconds to ensure that merge group waits all merge points to complete.");
            result.getResultFuture().get(1, TimeUnit.SECONDS);

            fail("result future completed before processor 3 is complete");
        } catch (TimeoutException exc) {
        }
        log.info("Done waiting.");

        assertFalse("merging within merge group starts only after all processors handlings is complete", mergingIsDone.get());

        idProcessor3.getProcessor().launch();

        assertEquals(Arrays.asList(1, 2, 3), result.getResultFuture().get(3, TimeUnit.SECONDS).getIdSequence());

        assertTrue("merging within merge group starts only after all processors handlings is complete", mergingIsDone.get());
        assertTrue("result future is complete", result.getResultFuture().isDone());

        result.getChainExecutionFuture().get(5, TimeUnit.SECONDS);
        assertTrue("execution chain is complete", result.getChainExecutionFuture().isDone());
    }

    static class SubgraphPayload extends IdListPaylaod {
    }

    static class ParentGraphPayload extends IdListPaylaod {
    }

    @Test
    public void run_subgraph() throws Exception {
        GraphProcessor<IdProcessor> idProcessor11 = graphBuilder.processor(new IdProcessor(11)).withId(11);
        GraphProcessor<IdProcessor> idProcessor12 = graphBuilder.processor(new IdProcessor(12)).withId(12);
        GraphProcessor<IdProcessor> idProcessor13 = graphBuilder.processor(new IdProcessor(13)).withId(13);

        GraphProcessorDescription<IdProcessor, IdListPaylaod> description = graphBuilder.describe(IdProcessor.class, IdListPaylaod.class)
                .withHandler(IdProcessor::handle)
                .withMerger((pld, id) -> {
                    pld.getIdSequence().add(id);
                    return Status.OK;
                });

        ReactorGraph<SubgraphPayload> childGraph = graphBuilder.payload(SubgraphPayload.class)
                .processedBy(idProcessor11)
                .describe(description)

                .processedBy(idProcessor12)
                .describe(description)

                .processedBy(idProcessor13)
                .describe(description)

                .startPoint()
                .handleBy(idProcessor11, idProcessor12)

                .multiMerge()
                .merge(idProcessor11).onAny().merge(idProcessor12)
                .merge(idProcessor12).onAny().handleBy(idProcessor13)

                .singleMerge(idProcessor13).onAny().complete()
                .coordinates()
                .proc("IdProcessor@11", 306, 216)
                .proc("IdProcessor@12", 612, 218)
                .proc("IdProcessor@13", 539, 596)
                .merge("IdProcessor@11", 430, 365)
                .merge("IdProcessor@12", 620, 421)
                .merge("IdProcessor@13", 613, 693)
                .start(500, 100)
                .complete("IdProcessor@13", 587, 776)

                .buildGraph();

        reactor.registerReactorGraph(childGraph);

        GraphProcessor<IdProcessor> idProcessor1 = graphBuilder.processor(new IdProcessor(1)).withId(1);
        GraphProcessor<IdProcessor> idProcessor2 = graphBuilder.processor(new IdProcessor(2)).withId(2);
        GraphProcessor<IdProcessor> idProcessor3 = graphBuilder.processor(new IdProcessor(3)).withId(3);

        SubgraphProcessor<SubgraphPayload> subgraphProcessor = graphBuilder.subgraphProcessor(SubgraphPayload.class);

        ReactorGraph<ParentGraphPayload> parentGraph = graphBuilder.payload(ParentGraphPayload.class)
                .processedBy(idProcessor1)
                .describe(description)

                .processedBy(idProcessor2)
                .describe(description)

                .processedBy(idProcessor3)
                .describe(description)

                .processedBy(subgraphProcessor)
                .passArg(payload -> new SubgraphPayload())
                .withMerger((payload, result) -> {
                    payload.getIdSequence().addAll(result.getIdSequence());
                    return Status.OK;
                })

                .startPoint()
                .handleBy(idProcessor1)

                .singleMerge(idProcessor1)
                .onAny().handleBy(idProcessor2)
                .onAny().handleBy(subgraphProcessor)

                .multiMerge()
                .merge(subgraphProcessor).onAny().merge(idProcessor2)
                .merge(idProcessor2).onAny().handleBy(idProcessor3)

                .singleMerge(idProcessor3).onAny().complete()
                .coordinates()

                .proc("IdProcessor@1", 406, 228)
                .proc("IdProcessor@2", 626, 408)
                .proc("IdProcessor@3", 415, 730)
                .proc("SubgraphPayload@0", 195, 418)
                .merge("IdProcessor@1", 475, 342)
                .merge("SubgraphPayload@0", 341, 565)
                .merge("IdProcessor@2", 488, 620)
                .merge("IdProcessor@3", 490, 840)
                .start(460, 120)
                .complete("IdProcessor@3", 460, 930)

                .buildGraph();

        printGraph(childGraph, parentGraph);

        reactor.registerReactorGraph(parentGraph);

        ParentGraphPayload resultPaylaod = reactor.submit(new ParentGraphPayload()).getResultFuture().get(5, TimeUnit.SECONDS);
        assertEquals(Arrays.asList(1, 11, 12, 13, 2, 3), resultPaylaod.getIdSequence());
    }

    @PayloadDescription(doc = {
            "Payload goes through processor chain and collect",
            " all processors ids that merged their results to payload.",
            "Test will check that single processor id end up at payloads idList."
    })
    static class SingleInterfaceProcessorPayload extends IdListPaylaod {
    }

    @Test
    public void use_interface_mock_as_processor_with_mockito() throws Exception {

        IdProcessorInterface processorInterface = Mockito.mock(IdProcessorInterface.class);
        Mockito.when(processorInterface.handle()).thenReturn(CompletableFuture.completedFuture(42));


        GraphProcessor<IdProcessorInterface> idProcessor1 = graphBuilder.processor(processorInterface);

        ReactorGraph<SingleInterfaceProcessorPayload> graph = graphBuilder.payload(SingleInterfaceProcessorPayload.class)
                .processedBy(idProcessor1)
                .withHandler(IdProcessorInterface::handle)
                .withMerger((pld, id) -> {
                    pld.getIdSequence().add(id);
                    return Status.OK;
                })

                .startPoint()
                .handleBy(idProcessor1)

                .singleMerge(idProcessor1)
                .onAny().complete()

                .coordinates()
                .proc("IdProcessorInterface@0", 450, 184)
                .merge("IdProcessorInterface@0", 522, 299)
                .start(500, 100)
                .complete("IdProcessorInterface@0", 498, 398)

                .buildGraph();

        printGraph(graph);

        reactor.registerReactorGraph(graph);

        SingleInterfaceProcessorPayload resultPayload = reactor.submit(new SingleInterfaceProcessorPayload())
                .getResultFuture()
                .get(10, TimeUnit.SECONDS);

        assertEquals(Arrays.asList(42), resultPayload.getIdSequence());
    }


    @Test
    public void use_class_mock_as_processor_with_mockito() throws Exception {

        IdProcessor processor = Mockito.mock(IdProcessor.class);
        Mockito.when(processor.handle()).thenReturn(CompletableFuture.completedFuture(78));

        GraphProcessor<IdProcessorInterface> idProcessor1 = graphBuilder.processor(processor);

        ReactorGraph<SingleInterfaceProcessorPayload> graph = graphBuilder.payload(SingleInterfaceProcessorPayload.class)
                .processedBy(idProcessor1)
                .withHandler(IdProcessorInterface::handle)
                .withMerger((pld, id) -> {
                    pld.getIdSequence().add(id);
                    return Status.OK;
                })

                .startPoint()
                .handleBy(idProcessor1)

                .singleMerge(idProcessor1)
                .onAny().complete()

                .coordinates()
                .proc("IdProcessor@0", 450, 184)
                .merge("IdProcessor@0", 522, 299)
                .start(500, 100)
                .complete("IdProcessor@0", 498, 398)

                .buildGraph();

        printGraph(graph);

        reactor.registerReactorGraph(graph);

        SingleInterfaceProcessorPayload resultPayload = reactor.submit(new SingleInterfaceProcessorPayload())
                .getResultFuture()
                .get(10, TimeUnit.SECONDS);

        assertEquals(Arrays.asList(78), resultPayload.getIdSequence());
    }


    @PayloadDescription(doc = {
            "Payload goes through processor chain and collect",
            " all processors ids that merged their results to payload.",
            "Test will check that parallel processors work fine when only one of",
            " transitions are activated."
    })
    @Data
    static class DeadBranchPayload extends IdListPaylaod {
        ThreeStateStatus threeStateStatus;
    }

    enum ThreeStateStatus {A, B, AB}

    @Test
    public void parallel_processors_with_one_dead_branch_way() throws Exception {
        GraphProcessor<IdProcessor> idProcessor0 = graphBuilder.processor(new IdProcessor(0)).withId(0);
        GraphProcessor<IdProcessor> idProcessor1 = graphBuilder.processor(new IdProcessor(1)).withId(1);
        GraphProcessor<IdProcessor> idProcessor2 = graphBuilder.processor(new IdProcessor(2)).withId(2);

        ReactorGraph<DeadBranchPayload> graph = graphBuilder.payload(DeadBranchPayload.class)
                .processedBy(idProcessor0)
                .withHandler(IdProcessor::handle)
                .withMerger((pld, id) -> {
                    pld.getIdSequence().add(id);
                    pld.setThreeStateStatus(ThreeStateStatus.A);
                    return ThreeStateStatus.A;
                })

                .processedBy(idProcessor1)
                .withHandler(IdProcessor::handle)
                .withMerger((pld, id) -> {
                    pld.getIdSequence().add(id);
                    return pld.getThreeStateStatus();
                })

                .processedBy(idProcessor2)
                .withHandler(IdProcessor::handle)
                .withMerger((pld, id) -> {
                    pld.getIdSequence().add(id);
                    return Status.OK;
                })

                .startPoint()
                .handleBy(idProcessor0)

                .singleMerge(idProcessor0)
                .on(ThreeStateStatus.A).handleBy(idProcessor1)
                .on(ThreeStateStatus.B).handleBy(idProcessor2)
                .on(ThreeStateStatus.AB).handleBy(idProcessor1, idProcessor2)

                .multiMerge()
                .merge(idProcessor1)
                .on(ThreeStateStatus.A).complete()
                .on(ThreeStateStatus.AB, ThreeStateStatus.B).merge(idProcessor2)


                .merge(idProcessor2)
                .onAny().complete()

                .coordinates()

                .start(500, 100)

                .proc("IdProcessor@0", 420, 210)
                .proc("IdProcessor@1", 600, 420)
                .proc("IdProcessor@2", 260, 420)

                .merge("IdProcessor@0", 500, 320)
                .merge("IdProcessor@1", 560, 510)
                .merge("IdProcessor@2", 450, 540)

                .complete("IdProcessor@1", 651, 595)
                .complete("IdProcessor@2", 520, 660)

                .buildGraph();

        printGraph(graph);

        reactor.registerReactorGraph(graph);

        CompletableReactor.Execution<DeadBranchPayload> result = reactor.submit(new DeadBranchPayload());

        DeadBranchPayload resultPayload = result
                .getResultFuture()
                .get(10, TimeUnit.SECONDS);

        assertEquals(Arrays.asList(0, 1), resultPayload.getIdSequence());
    }


    @PayloadDescription(doc = {
            "Detached Payload goes through processor chain and collect",
            "all processors ids that merged their results to payload.",
            "Detached merge point works as an regular merge point but there is no processor",
            "or processors result to merge."

    })
    @Data
    static class DetachedMergePointFromStartPointPayload extends IdListPaylaod {
    }

    @Test
    public void detached_merge_point_from_start_point() throws Exception {
        final int MERGE_POINT_ID = 42;

        GraphProcessor<IdProcessor> idProcessor0 = graphBuilder.processor(new IdProcessor(0)).withId(0);
        GraphProcessor<IdProcessor> idProcessor1 = graphBuilder.processor(new IdProcessor(1)).withId(1);

        GraphMergePoint<DetachedMergePointFromStartPointPayload> mergePoint = graphBuilder.mergePoint(DetachedMergePointFromStartPointPayload.class).withId(0);


        ReactorGraph<DetachedMergePointFromStartPointPayload> graph = graphBuilder.payload(DetachedMergePointFromStartPointPayload.class)
                .processedBy(idProcessor0)
                .withHandler(IdProcessor::handle)
                .withMerger((pld, id) -> {
                    pld.getIdSequence().add(id);
                    return Status.OK;
                })

                .processedBy(idProcessor1)
                .withHandler(IdProcessor::handle)
                .withMerger((pld, id) -> {
                    pld.getIdSequence().add(id);
                    return Status.OK;
                })

                .processedBy(mergePoint)
                .withMerger(new String[]{
                                "merge point documentation",
                                "here"},
                        pld -> {
                            pld.getIdSequence().add(MERGE_POINT_ID);
                            return Status.OK;
                        })

                .startPoint()
                .handleBy(idProcessor0, idProcessor1, mergePoint)

                .multiMerge()
                .merge(mergePoint)
                .onAny().merge(idProcessor1)

                .merge(idProcessor1)
                .onAny().merge(idProcessor0)

                .merge(idProcessor0)
                .onAny().complete()


                .coordinates()

                .buildGraph();

        printGraph(graph);

        reactor.registerReactorGraph(graph);

        CompletableReactor.Execution<DetachedMergePointFromStartPointPayload> result = reactor.submit(new DetachedMergePointFromStartPointPayload());

        DetachedMergePointFromStartPointPayload resultPayload = result
                .getResultFuture()
                .get(10, TimeUnit.SECONDS);

        assertEquals(Arrays.asList(42, 1, 0), resultPayload.getIdSequence());

    }

    @PayloadDescription(doc = {
            "Detached Payload goes through processor chain and collect",
            "all processors ids that merged their results to payload.",
            "Detached merge point works as an regular merge point but there is no processor",
            "or processors result to merge."

    })
    @Data
    static class DetachedMergePointFromProcessorsMergePointPayload extends IdListPaylaod {
    }

    @Test
    public void detached_merge_point_from_processors_merge_point() throws Exception {
        final int MERGE_POINT_ID = 42;

        GraphProcessor<IdProcessor> idProcessor0 = graphBuilder.processor(new IdProcessor(0)).withId(0);
        GraphProcessor<IdProcessor> idProcessor1 = graphBuilder.processor(new IdProcessor(1)).withId(1);

        GraphMergePoint<DetachedMergePointFromStartPointPayload> mergePoint = graphBuilder.mergePoint(DetachedMergePointFromStartPointPayload.class).withId(0);


        ReactorGraph<DetachedMergePointFromProcessorsMergePointPayload> graph = graphBuilder.payload(DetachedMergePointFromProcessorsMergePointPayload.class)
                .processedBy(idProcessor0)
                .withHandler(IdProcessor::handle)
                .withMerger((pld, id) -> {
                    pld.getIdSequence().add(id);
                    return Status.OK;
                })

                .processedBy(idProcessor1)
                .withHandler(IdProcessor::handle)
                .withMerger((pld, id) -> {
                    pld.getIdSequence().add(id);
                    return Status.OK;
                })

                .processedBy(mergePoint)
                .withMerger(new String[]{
                                "merge point documentation",
                                "here"},
                        pld -> {
                            pld.getIdSequence().add(MERGE_POINT_ID);
                            return Status.OK;
                        })

                .startPoint()
                .handleBy(idProcessor0, idProcessor1)

                .multiMerge()
                .merge(idProcessor0)
                .onAny().merge(idProcessor1)

                .merge(idProcessor1)
                .onAny().merge(mergePoint)

                .merge(mergePoint)
                .onAny().complete()

                .coordinates()

                .buildGraph();

        printGraph(graph);

        reactor.registerReactorGraph(graph);

        CompletableReactor.Execution<DetachedMergePointFromProcessorsMergePointPayload> result = reactor.submit(new DetachedMergePointFromProcessorsMergePointPayload());

        DetachedMergePointFromProcessorsMergePointPayload resultPayload = result
                .getResultFuture()
                .get(10, TimeUnit.SECONDS);

        assertEquals(Arrays.asList(0, 1, 42), resultPayload.getIdSequence());

    }
}