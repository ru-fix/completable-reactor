package ru.fix.completable.reactor.runtime.internal.dsl;

import lombok.extern.slf4j.Slf4j;
import ru.fix.completable.reactor.api.ReactorGraphModel;
import ru.fix.completable.reactor.runtime.ReactorGraph;
import ru.fix.completable.reactor.runtime.dsl.Coordinates;
import ru.fix.completable.reactor.runtime.dsl.MergePoint;
import ru.fix.completable.reactor.runtime.dsl.Processor;
import ru.fix.completable.reactor.runtime.dsl.Subgraph;
import ru.fix.completable.reactor.runtime.internal.CRProcessingItem;
import ru.fix.completable.reactor.runtime.internal.CRReactorGraph;
import ru.fix.completable.reactor.runtime.internal.ReactorReflector;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Kamil Asfandiyarov
 */

@Slf4j
public class CRCoordinates<PayloadType> implements Coordinates<PayloadType> {

    final BuilderContext<PayloadType> builderContext;
    final CRReactorGraph<PayloadType> graph;
    final CRGraphBuilder<PayloadType> crGraphBuilder;


    public CRCoordinates(BuilderContext<PayloadType> builderContext) {
        this.builderContext = builderContext;
        this.graph = builderContext.graph;
        this.crGraphBuilder = new CRGraphBuilder<>(builderContext);
    }

    @Override
    public Coordinates<PayloadType> start(int x, int y) {
        graph.getStartPoint().setCoordinates(new ReactorGraphModel.Coordinates(x, y));
        graph.getStartPoint().setCoordinatesSource(ReactorReflector.getMethodInvocationPoint().orElse(null));
        return this;
    }

    @Override
    public Coordinates<PayloadType> proc(Processor<? super PayloadType> processor, int x, int y) {
        return crProc((CRProcessor) processor, x, y);
    }

    @Override
    public Coordinates<PayloadType> proc(Subgraph<? super PayloadType> subgraph, int x, int y) {
        return crProc((CRSubgraph) subgraph, x, y);
    }

    Coordinates<PayloadType> crProc(CRProcessingItem processingItem, int x, int y) {
        CRReactorGraph.ProcessingItemInfo processorInfo = graph.getProcessingItems().get(processingItem);
        if (processorInfo == null) {
            log.warn(String.format("Could not find graph item that matches %s. Registered items: %s",
                    processingItem,
                    graph.getProcessingItems().keySet().stream()
                            .map(CRReactorGraph::serialize)
                            .collect(Collectors.joining(","))
            ));
        } else {
            processorInfo.setCoordinates(new ReactorGraphModel.Coordinates(x, y));
            processorInfo.setCoordinatesSource(ReactorReflector.getMethodInvocationPoint().orElse(null));
        }
        return this;
    }

    @Override
    public Coordinates<PayloadType> merge(Processor<? super PayloadType> processor, int x, int y) {
        return crMerge((CRProcessor) processor, x, y);
    }

    @Override
    public Coordinates<PayloadType> merge(Subgraph<? super PayloadType> subgraph, int x, int y) {
        return crMerge((CRSubgraph) subgraph, x, y);
    }

    @Override
    public Coordinates<PayloadType> merge(MergePoint<? super PayloadType> mergePoint, int x, int y) {
        return crMerge((CRMergePoint) mergePoint, x, y);
    }

    Coordinates<PayloadType> crMerge(CRProcessingItem processingItem, int x, int y) {
        Optional<CRReactorGraph.MergePoint> matchedMergePoint = graph.getMergePoints().stream()
                .filter(mergePoint -> mergePoint.asProcessingItem() == processingItem)
                .findAny();

        if (!matchedMergePoint.isPresent()) {
            log.warn(String.format(
                    "Could not find merge point that matches %s",
                    processingItem));
        } else {
            CRReactorGraph.MergePoint mergePoint = matchedMergePoint.get();
            mergePoint.setCoordinates(new ReactorGraphModel.Coordinates(x, y));
        }

        return this;
    }

    @Override
    public Coordinates<PayloadType> complete(Processor<? super PayloadType> processor, int x, int y) {
        return crComplete((CRProcessor) processor, x, y);
    }

    @Override
    public Coordinates<PayloadType> complete(Subgraph<? super PayloadType> subgraph, int x, int y) {
        return crComplete((CRSubgraph) subgraph, x, y);
    }

    @Override
    public Coordinates<PayloadType> complete(MergePoint<? super PayloadType> mergePoint, int x, int y) {
        return crComplete((CRMergePoint) mergePoint, x, y);
    }

    Coordinates<PayloadType> crComplete(CRProcessingItem processingItem, int x, int y) {
        Optional<CRReactorGraph.MergePoint> matchedMergePoint = graph.getMergePoints().stream()
                .filter(mergePoint -> mergePoint.asProcessingItem() == processingItem)
                .findAny();

        if (!matchedMergePoint.isPresent()) {
            log.warn("Could not find merge point that matches {}", processingItem);
        } else {

            List<CRReactorGraph.Transition> transitions = matchedMergePoint.get().getTransitions().stream()
                    .filter(CRReactorGraph.Transition::isComplete)
                    .collect(Collectors.toList());

            if (transitions.size() > 0) {

                if (transitions.size() > 1) {
                    log.warn("Found multiple complete transitions that matches {}", processingItem);
                }

                CRReactorGraph.Transition transition = transitions.get(0);
                transition.setCompleteCoordinates(new ReactorGraphModel.Coordinates(x, y));
                transition.setCompleteCoordinatesSource(ReactorReflector.getMethodInvocationPoint().orElse(null));
            } else {
                log.warn("Could not find complete transitions that matches {}", processingItem);
            }
        }

        return this;
    }


    @Override
    public ReactorGraph<PayloadType> buildGraph() {
        return crGraphBuilder.buildGraph();
    }
}
