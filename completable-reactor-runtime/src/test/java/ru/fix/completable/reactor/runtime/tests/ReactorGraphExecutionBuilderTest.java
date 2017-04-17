package ru.fix.completable.reactor.runtime.tests;

import lombok.val;
import org.junit.Test;
import ru.fix.completable.reactor.runtime.ReactorGraph;
import ru.fix.completable.reactor.runtime.ReactorGraphBuilder;
import ru.fix.completable.reactor.runtime.dsl.MergePoint;
import ru.fix.completable.reactor.runtime.dsl.Processor;
import ru.fix.completable.reactor.runtime.dsl.Subgraph;
import ru.fix.completable.reactor.runtime.internal.CRReactorGraph;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * @author Kamil Asfandiyarov
 */
public class ReactorGraphExecutionBuilderTest {

    ReactorGraphBuilder graphBuilder = new ReactorGraphBuilder();

    Processor<CompletableReactorTest.IdListPayload> buildProcessor(IdProcessor idProcessor) {
        return graphBuilder.processor()
                .forPayload(CompletableReactorTest.IdListPayload.class)
                .withHandler(idProcessor::handle)
                .withMerger((pld, id) -> {
                    pld.getIdSequence().add(id);
                    return CompletableReactorTest.Status.OK;
                })
                .buildProcessor();
    }

    @Test
    public void generate_merge_groups_for_graph_with_processors() throws Exception {

        Processor<CompletableReactorTest.IdListPayload> idProcessor1 = buildProcessor(new IdProcessor(1)).setId(1);
        Processor<CompletableReactorTest.IdListPayload> idProcessor2 = buildProcessor(new IdProcessor(2)).setId(2);
        Processor<CompletableReactorTest.IdListPayload> idProcessor3 = buildProcessor(new IdProcessor(3)).setId(3);

        ReactorGraph graph = graphBuilder.payload(CompletableReactorTest.PayloadWithMergeGroup.class)

                .handleBy(idProcessor1)
                .handleBy(idProcessor2)
                .handleBy(idProcessor3)

                .mergePoint(idProcessor1)
                .onAny().merge(idProcessor2)

                .mergePoint(idProcessor2)
                .onAny().merge(idProcessor3)

                .mergePoint(idProcessor3)
                .onAny().complete()

                .coordinates()
                .start(500, 100)
                .proc(IdProcessor.class, 1, 372, 174)
                .proc(IdProcessor.class, 2, 537, 180)
                .proc(IdProcessor.class, 3, 685, 175)
                .merge(IdProcessor.class, 1, 419, 275)
                .merge(IdProcessor.class, 2, 583, 305)
                .merge(IdProcessor.class, 3, 722, 330)
                .complete(IdProcessor.class, 3, 721, 434)

                .buildGraph();

        val crGraph = (CRReactorGraph) graph;
        assertEquals(1, crGraph.getMergeGroups().size());

        assertFalse(crGraph.getStartPointMergeGroup().isPresent());

        val groupItems = ((CRReactorGraph.MergeGroup) crGraph.getMergeGroups().get(0))
                .getMergePoints()
                .stream()
                .map(CRReactorGraph.MergePoint::asProcessingItem)
                .collect(Collectors.toList());

        assertEquals(3, groupItems.size());
        assertEquals(Arrays.asList(idProcessor1, idProcessor2, idProcessor3), groupItems);
    }

    @Test
    public void generate_merge_group_for_graph_with_subgraph() throws Exception {
        Processor<CompletableReactorTest.IdListPayload> idProcessor1 = buildProcessor(new IdProcessor(1)).setId(1);
        Processor<CompletableReactorTest.IdListPayload> idProcessor2 = buildProcessor(new IdProcessor(2)).setId(2);
        Processor<CompletableReactorTest.IdListPayload> idProcessor3 = buildProcessor(new IdProcessor(3)).setId(3);

        Subgraph<CompletableReactorTest.ParentGraphPayload> subgraphProcessor = graphBuilder.subgraph(CompletableReactorTest.SubgraphPayload.class)
                .forPayload(CompletableReactorTest.ParentGraphPayload.class)
                .passArg(payload -> new CompletableReactorTest.SubgraphPayload())
                .withMerger((payload, result) -> {
                    payload.getIdSequence().addAll(result.getIdSequence());
                    return CompletableReactorTest.Status.OK;
                })
                .buildSubgraph();

        ReactorGraph<CompletableReactorTest.ParentGraphPayload> graph = graphBuilder.payload(CompletableReactorTest.ParentGraphPayload.class)

                .handleBy(idProcessor1)

                .mergePoint(idProcessor1)
                .onAny().handleBy(idProcessor2)
                .onAny().handleBy(subgraphProcessor)

                .mergePoint(subgraphProcessor).onAny().merge(idProcessor2)

                .mergePoint(idProcessor2).onAny().handleBy(idProcessor3)

                .mergePoint(idProcessor3).onAny().complete()
                .coordinates()

                .proc(IdProcessor.class, 1, 406, 228)
                .proc(IdProcessor.class, 2, 626, 408)
                .proc(IdProcessor.class, 3, 415, 730)
                .proc(CompletableReactorTest.SubgraphPayload.class, 0, 195, 418)
                .merge(IdProcessor.class, 1, 475, 342)
                .merge(CompletableReactorTest.SubgraphPayload.class, 0, 341, 565)
                .merge(IdProcessor.class, 2, 488, 620)
                .merge(IdProcessor.class, 3, 490, 840)
                .start(460, 120)
                .complete(IdProcessor.class, 3, 460, 930)

                .buildGraph();


        val crGraph = (CRReactorGraph) graph;
        assertEquals(1, crGraph.getMergeGroups().size());

        assertFalse(crGraph.getStartPointMergeGroup().isPresent());

        val groupItems = ((CRReactorGraph.MergeGroup) crGraph.getMergeGroups().get(0))
                .getMergePoints()
                .stream()
                .map(CRReactorGraph.MergePoint::asProcessingItem)
                .collect(Collectors.toList());

        assertEquals(Arrays.asList(subgraphProcessor, idProcessor2), groupItems);
    }

    @Test
    public void generate_merge_group_for_graph_with_detached_merge_point_connected_to_start_point() throws Exception {
        Processor<CompletableReactorTest.IdListPayload> idProcessor0 = buildProcessor(new IdProcessor(0)).setId(0);
        Processor<CompletableReactorTest.IdListPayload> idProcessor1 = buildProcessor(new IdProcessor(1)).setId(1);

        MergePoint<CompletableReactorTest.DetachedMergePointFromStartPointPayload> mergePoint = graphBuilder.mergePoint()
                .forPayload(CompletableReactorTest.DetachedMergePointFromStartPointPayload.class)
                .withMerger(
                        "mergePointTitle",
                        new String[]{
                                "merge point documentation",
                                "here"},
                        pld -> {
                            pld.getIdSequence().add(2);
                            return CompletableReactorTest.Status.OK;
                        })
                .buildMergePoint()
                .setId(0);

        ReactorGraph<CompletableReactorTest.DetachedMergePointFromStartPointPayload> graph = graphBuilder.payload(CompletableReactorTest.DetachedMergePointFromStartPointPayload.class)

                .handleBy(idProcessor0)
                .handleBy(idProcessor1)
                .merge(mergePoint)

                .mergePoint(mergePoint)
                .onAny().merge(idProcessor1)

                .mergePoint(idProcessor1)
                .onAny().merge(idProcessor0)

                .mergePoint(idProcessor0)
                .onAny().complete()

                .coordinates()
                .start(107, 9)
                .proc(IdProcessor.class, 0, 22, 78)
                .proc(IdProcessor.class, 1, 211, 79)
                .merge(IdProcessor.class, 0, 126, 215)
                .merge(IdProcessor.class, 1, 267, 187)
                .merge(0, 424, 160)
                .complete(IdProcessor.class, 0, 57, 274)

                .buildGraph();

        val crGraph = (CRReactorGraph) graph;
        assertEquals(1, crGraph.getMergeGroups().size());

        assertFalse(crGraph.getStartPointMergeGroup().isPresent());

        val groupItems = ((CRReactorGraph.MergeGroup) crGraph.getMergeGroups().get(0))
                .getMergePoints()
                .stream()
                .map(CRReactorGraph.MergePoint::asProcessingItem)
                .collect(Collectors.toSet());

        assertEquals(3, groupItems.size());
        assertEquals(new HashSet<>(Arrays.asList(idProcessor0, idProcessor1, mergePoint)), groupItems);
    }

    @Test
    public void generate_merge_group_for_graph_with_detached_merge_point_in_the_middle_of_the_graph() throws Exception {

        Processor<CompletableReactorTest.IdListPayload> idProcessor0 = buildProcessor(new IdProcessor(0)).setId(0);
        Processor<CompletableReactorTest.IdListPayload> idProcessor1 = buildProcessor(new IdProcessor(1)).setId(1);

        MergePoint<CompletableReactorTest.DetachedMergePointFromProcessorsMergePointPayload> mergePoint = graphBuilder.mergePoint()
                .forPayload(CompletableReactorTest.DetachedMergePointFromProcessorsMergePointPayload.class)
                .withMerger(
                        "addMergePointId",
                        new String[]{
                                "Adds merge point id",
                                "to payload sequence"},
                        pld -> {
                            pld.getIdSequence().add(2);
                            return CompletableReactorTest.Status.OK;
                        })

                .buildMergePoint()
                .setId(0);

        ReactorGraph<CompletableReactorTest.DetachedMergePointFromProcessorsMergePointPayload> graph = graphBuilder.payload(CompletableReactorTest.DetachedMergePointFromProcessorsMergePointPayload.class)

                .handleBy(idProcessor0)
                .handleBy(idProcessor1)

                .mergePoint(idProcessor0)
                .onAny().merge(idProcessor1)

                .mergePoint(idProcessor1)
                .onAny().merge(mergePoint)

                .mergePoint(mergePoint)
                .onAny().complete()

                .coordinates()
                .start(95, 62)
                .proc(IdProcessor.class, 0, 164, 131)
                .proc(IdProcessor.class, 1, 330, 127)
                .merge(IdProcessor.class, 0, 235, 224)
                .merge(IdProcessor.class, 1, 357, 241)
                .merge(0, 461, 289)
                .complete(0, 406, 369)

                .buildGraph();

        val crGraph = (CRReactorGraph) graph;
        assertEquals(1, crGraph.getMergeGroups().size());

        assertFalse(crGraph.getStartPointMergeGroup().isPresent());

        val groupItems = ((CRReactorGraph.MergeGroup) crGraph.getMergeGroups().get(0))
                .getMergePoints()
                .stream()
                .map(CRReactorGraph.MergePoint::asProcessingItem)
                .collect(Collectors.toSet());

        assertEquals(3, groupItems.size());
        assertEquals(new HashSet<>(Arrays.asList(idProcessor0, idProcessor1, mergePoint)), groupItems);
    }


    @Test
    public void merge_group_for_graph_with_detached_merge_point_connected_to_start_point_with_start_point_merge_group()
            throws Exception {

        val idProcessor0 = buildProcessor(new IdProcessor(0)).setId(0);
        val idProcessor1 = buildProcessor(new IdProcessor(1)).setId(1);

        val mergePoint2 = graphBuilder.mergePoint()
                .forPayload(CompletableReactorTest.StartPointMergeGroupPayload.class)
                .withMerger(
                        "mergePoint-2",
                        pld -> {
                            pld.getIdSequence().add(2);
                            return CompletableReactorTest.Status.OK;
                        })
                .buildMergePoint()
                .setId(0);

        val idProcessor3 = buildProcessor(new IdProcessor(3)).setId(3);

        val graph = graphBuilder.payload(CompletableReactorTest.StartPointMergeGroupPayload.class)

                .handleBy(idProcessor0)
                .handleBy(idProcessor1)
                .merge(mergePoint2)

                .mergePoint(idProcessor0)
                .onAny().merge(idProcessor1)

                .mergePoint(idProcessor1)
                .onAny().merge(idProcessor3)

                .mergePoint(mergePoint2)
                .onAny().handleBy(idProcessor3)

                .mergePoint(idProcessor3)
                .onAny().complete()

                .coordinates()
                .buildGraph();

        val crGraph = (CRReactorGraph) graph;
        assertEquals(2, crGraph.getMergeGroups().size());

        assertTrue(crGraph.getStartPointMergeGroup().isPresent());

        val startPointGroupItems = ((List<CRReactorGraph.MergeGroup>) crGraph.getMergeGroups())
                .stream()
                .filter(group -> group.getMergePoints().stream().anyMatch(point -> point.asProcessingItem().equals(mergePoint2)))
                .findAny()
                .get()
                .getMergePoints()
                .stream()
                .map(CRReactorGraph.MergePoint::asProcessingItem)
                .collect(Collectors.toSet());

        assertEquals(1, startPointGroupItems.size());
        assertEquals(new HashSet<>(Arrays.asList(mergePoint2)), startPointGroupItems);


        val groupItems = ((List<CRReactorGraph.MergeGroup>) crGraph.getMergeGroups())
                .stream()
                .filter(group -> group.getMergePoints().stream().noneMatch(point -> point.asProcessingItem().equals(mergePoint2)))
                .findAny()
                .get()
                .getMergePoints()
                .stream()
                .map(CRReactorGraph.MergePoint::asProcessingItem)
                .collect(Collectors.toSet());

        assertEquals(3, groupItems.size());
        assertEquals(new HashSet<>(Arrays.asList(idProcessor0, idProcessor1, idProcessor3)), groupItems);
    }
}