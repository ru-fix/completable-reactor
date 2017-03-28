package ru.fix.completable.reactor.runtime;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ru.fix.commons.profiler.impl.SimpleProfiler;
import ru.fix.completable.reactor.api.Reactored;
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
    private GraphProcessorDescription<IdProcessor, IdListPaylaod> idListProcessorDescription;

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

        idListProcessorDescription = graphBuilder.describeProcessor(IdProcessor.class)
                .forPayload(IdListPaylaod.class)
                .withHandler(IdProcessor::handle)
                .withMerger((pld, id) -> {
                    pld.getIdSequence().add(id);
                    return Status.OK;
                });
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


    @Reactored({
            "Payload goes through processor chain and collect",
            " all processors ids that merged their results to payload.",
            "Test will check that single processor id end up at payloads idList."
    })
    static class SingleProcessorPayload extends IdListPaylaod {
    }

    @Test
    public void single_processor() throws Exception {

        GraphProcessor<IdProcessor, IdListPaylaod> idProcessor1 = idListProcessorDescription.buildProcessor(new IdProcessor(1));

        ReactorGraph graph = graphBuilder.payload(SingleProcessorPayload.class)
                .startPoint()
                .handleBy(idProcessor1)

                .singleMerge(idProcessor1)
                .onAny().complete()

                .coordinates()
                .start(226, 98)
                .proc("IdProcessor@0", 261, 163)
                .merge("IdProcessor@0", 300, 251)
                .complete("IdProcessor@0", 308, 336)

                .buildGraph();

        printGraph(graph);

        reactor.registerReactorGraph(graph);

        SingleProcessorPayload resultPayload = reactor.submit(new SingleProcessorPayload())
                .getResultFuture()
                .get(10, TimeUnit.SECONDS);

        assertEquals(Arrays.asList(1), resultPayload.getIdSequence());
    }

    @Reactored({
            "Payload goes through processor chain and collect",
            " all processors ids that merged their results to payload.",
            "Test will check that two processor ids end up at payloads idList in correct order."
    })
    static class TwoProcessorSequentialMergePayload extends IdListPaylaod {
    }

    @Test
    public void two_processors_sequential_merge() throws Exception {

        GraphProcessor<IdProcessor, IdListPaylaod> idProcessor1 = idListProcessorDescription.buildProcessor(new IdProcessor(1)).withId(1);
        GraphProcessor<IdProcessor, IdListPaylaod> idProcessor2 = idListProcessorDescription.buildProcessor(new IdProcessor(2)).withId(2);

        ReactorGraph graph = graphBuilder.payload(TwoProcessorSequentialMergePayload.class)

                .startPoint()
                .handleBy(idProcessor1)
                .handleBy(idProcessor2)

                .singleMerge(idProcessor1)
                .on(Status.OK, Status.UNUSED).merge(idProcessor2)

                .singleMerge(idProcessor2)
                .onAny().complete()

                .coordinates()
                .start(366, 103)
                .proc("IdProcessor@1", 358, 184)
                .proc("IdProcessor@2", 549, 183)
                .merge("IdProcessor@1", 427, 291)
                .merge("IdProcessor@2", 571, 356)
                .complete("IdProcessor@2", 610, 454)

                .buildGraph();

        printGraph(graph);

        reactor.registerReactorGraph(graph);


        TwoProcessorSequentialMergePayload resultPayload = reactor.submit(new TwoProcessorSequentialMergePayload())
                .getResultFuture()
                .get(10, TimeUnit.SECONDS);

        assertEquals(Arrays.asList(1, 2), resultPayload.getIdSequence());
    }

    @Reactored({
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

        GraphProcessor<IdProcessor, IdListPaylaod> idProcessor1 = idListProcessorDescription.buildProcessor(new IdProcessor(1)).withId(1);
        GraphProcessor<IdProcessor, IdListPaylaod> idProcessor2 = idListProcessorDescription.buildProcessor(new IdProcessor(2)).withId(2);
        GraphProcessor<IdProcessor, DetachedProcessorPayload> idProcessor3 = graphBuilder.describeProcessor(IdProcessor.class)
                .forPayload(DetachedProcessorPayload.class)
                .withHandler(IdProcessor::handle)
                .withoutMerger()
                .buildProcessor(new IdProcessor(3).withLaunchingLatch()).withId(3);

        ReactorGraph graph = graphBuilder.payload(DetachedProcessorPayload.class)
                .startPoint()
                .handleBy(idProcessor1)
                .handleBy(idProcessor2)
                .handleBy(idProcessor3)

                .singleMerge(idProcessor1)
                .on(Status.OK).merge(idProcessor2)

                .singleMerge(idProcessor2)
                .onAny().complete()

                .coordinates()
                .start(489, 96)
                .proc("IdProcessor@1", 364, 178)
                .proc("IdProcessor@2", 530, 180)
                .proc("IdProcessor@3", 701, 172)
                .merge("IdProcessor@1", 414, 268)
                .merge("IdProcessor@2", 589, 341)
                .complete("IdProcessor@2", 701, 378)

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

    @Reactored({
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

        AtomicBoolean mergingIsDone = new AtomicBoolean();

        GraphProcessor<IdProcessor, PayloadWithMergeGroup> idProcessor1 = graphBuilder.describeProcessor(IdProcessor.class)
                .forPayload(PayloadWithMergeGroup.class)
                .withHandler(IdProcessor::handle)
                .withMerger((pld, id) -> {
                    mergingIsDone.set(true);

                    pld.getIdSequence().add(id);
                    return Status.OK;
                })
                .buildProcessor(new IdProcessor(1)).withId(1);

        GraphProcessor<IdProcessor, IdListPaylaod> idProcessor2 = idListProcessorDescription.buildProcessor(new IdProcessor(2)).withId(2);
        GraphProcessor<IdProcessor, IdListPaylaod> idProcessor3 = idListProcessorDescription.buildProcessor(new IdProcessor(3).withLaunchingLatch()).withId(3);


        ReactorGraph graph = graphBuilder.payload(PayloadWithMergeGroup.class)
                .startPoint()
                .handleBy(idProcessor1)
                .handleBy(idProcessor2)
                .handleBy(idProcessor3)

                .multiMerge()
                .merge(idProcessor1)
                .onAny().merge(idProcessor2)

                .merge(idProcessor2)
                .onAny().merge(idProcessor3)

                .merge(idProcessor3)
                .onAny().complete()

                .coordinates()
                .start(500, 100)
                .proc("IdProcessor@1", 100, 100)
                .proc("IdProcessor@2", 100, 100)
                .proc("IdProcessor@3", 638, 179)
                .merge("IdProcessor@1", 100, 100)
                .merge("IdProcessor@2", 100, 100)
                .merge("IdProcessor@3", 100, 100)
                .complete("IdProcessor@3", 100, 100)

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
        GraphProcessor<IdProcessor, IdListPaylaod> idProcessor11 = idListProcessorDescription.buildProcessor(new IdProcessor(11)).withId(11);
        GraphProcessor<IdProcessor, IdListPaylaod> idProcessor12 = idListProcessorDescription.buildProcessor(new IdProcessor(12)).withId(12);
        GraphProcessor<IdProcessor, IdListPaylaod> idProcessor13 = idListProcessorDescription.buildProcessor(new IdProcessor(13)).withId(13);


        ReactorGraph<SubgraphPayload> childGraph = graphBuilder.payload(SubgraphPayload.class)
                .startPoint()
                .handleBy(idProcessor11)
                .handleBy(idProcessor12)

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

        GraphProcessor<IdProcessor, IdListPaylaod> idProcessor1 = idListProcessorDescription.buildProcessor(new IdProcessor(1)).withId(1);
        GraphProcessor<IdProcessor, IdListPaylaod> idProcessor2 = idListProcessorDescription.buildProcessor(new IdProcessor(2)).withId(2);
        GraphProcessor<IdProcessor, IdListPaylaod> idProcessor3 = idListProcessorDescription.buildProcessor(new IdProcessor(3)).withId(3);

        SubgraphProcessor<SubgraphPayload, ParentGraphPayload> subgraphProcessor = graphBuilder.describeSubgraph(SubgraphPayload.class)
                .forPayload(ParentGraphPayload.class)
                .passArg(payload -> new SubgraphPayload())
                .withMerger((payload, result) -> {
                    payload.getIdSequence().addAll(result.getIdSequence());
                    return Status.OK;
                })
                .buildSubgraph();

        ReactorGraph<ParentGraphPayload> parentGraph = graphBuilder.payload(ParentGraphPayload.class)
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

    @Reactored({
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


        GraphProcessor<IdProcessorInterface, SingleInterfaceProcessorPayload> idProcessor1 = graphBuilder.describeProcessor(IdProcessorInterface.class)
                .forPayload(SingleInterfaceProcessorPayload.class)
                .withHandler(IdProcessorInterface::handle)
                .withMerger((pld, id) -> {
                    pld.getIdSequence().add(id);
                    return Status.OK;
                })
                .buildProcessor(processorInterface);

        ReactorGraph<SingleInterfaceProcessorPayload> graph = graphBuilder.payload(SingleInterfaceProcessorPayload.class)
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

        GraphProcessor<IdProcessorInterface, SingleInterfaceProcessorPayload> idProcessor1 = graphBuilder.describeProcessor(IdProcessorInterface.class)
                .forPayload(SingleInterfaceProcessorPayload.class)
                .withHandler(IdProcessorInterface::handle)
                .withMerger((pld, id) -> {
                    pld.getIdSequence().add(id);
                    return Status.OK;
                })
                .buildProcessor(processor);

        ReactorGraph<SingleInterfaceProcessorPayload> graph = graphBuilder.payload(SingleInterfaceProcessorPayload.class)
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


    @Reactored({
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
        GraphProcessor<IdProcessor, DeadBranchPayload> idProcessor0 = graphBuilder.describeProcessor(IdProcessor.class)
                .forPayload(DeadBranchPayload.class)
                .withHandler(IdProcessor::handle)
                .withMerger((pld, id) -> {
                    pld.getIdSequence().add(id);
                    pld.setThreeStateStatus(ThreeStateStatus.A);
                    return ThreeStateStatus.A;
                })
                .buildProcessor(new IdProcessor(0)).withId(0);


        GraphProcessor<IdProcessor, DeadBranchPayload> idProcessor1 = graphBuilder.describeProcessor(IdProcessor.class)
                .forPayload(DeadBranchPayload.class)
                .withHandler(IdProcessor::handle)
                .withMerger((pld, id) -> {
                    pld.getIdSequence().add(id);
                    return pld.getThreeStateStatus();
                })
                .buildProcessor(new IdProcessor(1)).withId(1);

        GraphProcessor<IdProcessor, DeadBranchPayload> idProcessor2 = graphBuilder.describeProcessor(IdProcessor.class)
                .forPayload(DeadBranchPayload.class)
                .withHandler(IdProcessor::handle)
                .withMerger((pld, id) -> {
                    pld.getIdSequence().add(id);
                    return Status.OK;
                })
                .buildProcessor(new IdProcessor(2)).withId(2);

        ReactorGraph<DeadBranchPayload> graph = graphBuilder.payload(DeadBranchPayload.class)
                .startPoint()
                .handleBy(idProcessor0)

                .singleMerge(idProcessor0)
                .on(ThreeStateStatus.A).handleBy(idProcessor1)
                .on(ThreeStateStatus.B).handleBy(idProcessor2)
                .on(ThreeStateStatus.AB).handleBy(idProcessor1)
                .on(ThreeStateStatus.AB).handleBy(idProcessor2)

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


    @Reactored({
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

        GraphProcessor<IdProcessor, IdListPaylaod> idProcessor0 = idListProcessorDescription.buildProcessor(new IdProcessor(0)).withId(0);
        GraphProcessor<IdProcessor, IdListPaylaod> idProcessor1 = idListProcessorDescription.buildProcessor(new IdProcessor(1)).withId(1);

        GraphMergePoint<DetachedMergePointFromStartPointPayload> mergePoint = graphBuilder.describeMergePoint()
                .forPayload(DetachedMergePointFromStartPointPayload.class)
                .withMerger(
                        "mergePointTitle",
                        new String[]{
                                "merge point documentation",
                                "here"},
                        pld -> {
                            pld.getIdSequence().add(MERGE_POINT_ID);
                            return Status.OK;
                        })
                .buildMergePoint()
                .withId(0);


        ReactorGraph<DetachedMergePointFromStartPointPayload> graph = graphBuilder.payload(DetachedMergePointFromStartPointPayload.class)
                .startPoint()
                .handleBy(idProcessor0)
                .handleBy(idProcessor1)
                .merge(mergePoint)

                .multiMerge()
                .merge(mergePoint)
                .onAny().merge(idProcessor1)

                .merge(idProcessor1)
                .onAny().merge(idProcessor0)

                .merge(idProcessor0)
                .onAny().complete()


                .coordinates()
                .start(107, 9)
                .proc("IdProcessor@0", 22, 78)
                .proc("IdProcessor@1", 211, 79)
                .merge("IdProcessor@0", 126, 215)
                .merge("IdProcessor@1", 267, 187)
                .merge("MergePoint@0", 424, 160)
                .complete("IdProcessor@0", 57, 274)

                .buildGraph();

        printGraph(graph);

        reactor.registerReactorGraph(graph);

        CompletableReactor.Execution<DetachedMergePointFromStartPointPayload> result = reactor.submit(new DetachedMergePointFromStartPointPayload());

        DetachedMergePointFromStartPointPayload resultPayload = result
                .getResultFuture()
                .get(10, TimeUnit.SECONDS);

        assertEquals(Arrays.asList(42, 1, 0), resultPayload.getIdSequence());
    }

    @Reactored({
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

        GraphProcessor<IdProcessor, IdListPaylaod> idProcessor0 = idListProcessorDescription.buildProcessor(new IdProcessor(0)).withId(0);
        GraphProcessor<IdProcessor, IdListPaylaod> idProcessor1 = idListProcessorDescription.buildProcessor(new IdProcessor(1)).withId(1);

        GraphMergePoint<DetachedMergePointFromProcessorsMergePointPayload> mergePoint = graphBuilder.describeMergePoint()
                .forPayload(DetachedMergePointFromProcessorsMergePointPayload.class)
                .withMerger(
                        "addMergePointId",
                        new String[]{
                                "Adds merge point id",
                                "to payload sequence"},
                        pld -> {
                            pld.getIdSequence().add(MERGE_POINT_ID);
                            return Status.OK;
                        })

                .buildMergePoint()
                .withId(0);

        ReactorGraph<DetachedMergePointFromProcessorsMergePointPayload> graph = graphBuilder.payload(DetachedMergePointFromProcessorsMergePointPayload.class)
                .startPoint()
                .handleBy(idProcessor0)
                .handleBy(idProcessor1)

                .multiMerge()
                .merge(idProcessor0)
                .onAny().merge(idProcessor1)

                .merge(idProcessor1)
                .onAny().merge(mergePoint)

                .merge(mergePoint)
                .onAny().complete()

                .coordinates()
                .start(95, 62)
                .proc("IdProcessor@0", 164, 131)
                .proc("IdProcessor@1", 330, 127)
                .merge("IdProcessor@0", 235, 224)
                .merge("IdProcessor@1", 357, 241)
                .merge("MergePoint@0", 461, 289)
                .complete("MergePoint@0", 406, 369)

                .buildGraph();

        printGraph(graph);

        reactor.registerReactorGraph(graph);

        CompletableReactor.Execution<DetachedMergePointFromProcessorsMergePointPayload> result = reactor.submit(new DetachedMergePointFromProcessorsMergePointPayload());

        DetachedMergePointFromProcessorsMergePointPayload resultPayload = result
                .getResultFuture()
                .get(10, TimeUnit.SECONDS);

        assertEquals(Arrays.asList(0, 1, 42), resultPayload.getIdSequence());

    }


    @Reactored({
            "OptionalProcessorExecution shows how to use detached merge point to avoid unnesessary processor execution"
    })

    @Accessors(chain = true)
    @Data
    static class OptionalProcessorExecutionPayload extends IdListPaylaod {
        OPTIONAL_DECISION whereToGo;
    }

    enum OPTIONAL_DECISION {
        LEFT, RIGHT
    }

    @Test
    public void optional_processor_execution() throws Exception {

        GraphProcessor<IdProcessor, IdListPaylaod> idProcessor1 = idListProcessorDescription.buildProcessor(new IdProcessor(1)).withId(1);
        GraphProcessor<IdProcessor, IdListPaylaod> idProcessor2 = idListProcessorDescription.buildProcessor(new IdProcessor(2)).withId(2);

        GraphMergePoint<OptionalProcessorExecutionPayload> mergePoint = graphBuilder.describeMergePoint()
                .forPayload(OptionalProcessorExecutionPayload.class)
                .withMerger(
                        "getWhereToGo",
                        new String[]{
                                "returns destination from payload"
                        },
                        pld -> {
                            return pld.getWhereToGo();
                        })
                .buildMergePoint()
                .withId(0);


        ReactorGraph<OptionalProcessorExecutionPayload> graph = graphBuilder.payload(OptionalProcessorExecutionPayload.class)
                .startPoint()
                .merge(mergePoint)

                .singleMerge(mergePoint)
                .on(OPTIONAL_DECISION.LEFT).handleBy(idProcessor2)
                .on(OPTIONAL_DECISION.RIGHT).handleBy(idProcessor1)

                .singleMerge(idProcessor1)
                .onAny().handleBy(idProcessor2)

                .singleMerge(idProcessor2)
                .onAny().complete()

                .coordinates()
                .start(0, 17)
                .proc("IdProcessor@1", 189, 164)
                .proc("IdProcessor@2", 46, 269)
                .merge("IdProcessor@1", 221, 233)
                .merge("IdProcessor@2", 81, 354)
                .merge("MergePoint@0", 114, 82)
                .complete("IdProcessor@2", 87, 432)

                .buildGraph();

        printGraph(graph);

        reactor.registerReactorGraph(graph);

        CompletableReactor.Execution<OptionalProcessorExecutionPayload> result = reactor.submit(new OptionalProcessorExecutionPayload()
                .setWhereToGo(OPTIONAL_DECISION.RIGHT));

        OptionalProcessorExecutionPayload resultPayload = result
                .getResultFuture()
                .get(10, TimeUnit.SECONDS);

        assertEquals(Arrays.asList(1, 2), resultPayload.getIdSequence());


        result = reactor.submit(new OptionalProcessorExecutionPayload()
                .setWhereToGo(OPTIONAL_DECISION.LEFT));


        resultPayload = result
                .getResultFuture()
                .get(10, TimeUnit.SECONDS);

        assertEquals(Arrays.asList(2), resultPayload.getIdSequence());
    }

}