package ru.fix.completable.reactor.runtime.internal;

import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.val;
import ru.fix.completable.reactor.api.ReactorGraphModel;
import ru.fix.completable.reactor.api.Reactored;
import ru.fix.completable.reactor.runtime.ReactorGraph;
import ru.fix.completable.reactor.runtime.ReactorGraphBuilder;
import ru.fix.completable.reactor.runtime.dsl.Coordinates;
import ru.fix.completable.reactor.runtime.internal.dsl.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Model of graph used to generate execution chain.
 *
 * @author Kamil Asfandiyarov
 */
@Data
public class CRReactorGraph<PayloadType> implements ReactorGraph<PayloadType> {

    @Data
    @Accessors(chain = true)
    public static class MergeGroup {
        final List<MergePoint> mergePoints = new ArrayList<>();
    }

    public ReactorGraphModel.MergeGroup serialize(MergeGroup mergeGroup) {
        ReactorGraphModel.MergeGroup model = new ReactorGraphModel.MergeGroup();
        model.mergePoints = new ArrayList<>();

        mergeGroup.mergePoints.forEach(mergePoint -> {
            ReactorGraphModel.MergePoint mergePointModel = mergePoint.serialize();
            ProcessingItemInfo processorInfo = this.processingItems.get(mergePoint.getProcessor());

            String mergerTitle = null;
            String[] mergerDocs = null;
            ReactorGraphModel.Source mergerSource = null;

            switch (processorInfo.getProcessingItemType()){
                case PROCESSOR:
                    mergerDocs = processorInfo.description.getMergerDocs();
                    mergerTitle = processorInfo.description.getMergerTitle();
                    mergerSource = processorInfo.description.getMergeSource();
                    break;
                case SUBGRAPH:
                    mergerDocs = processorInfo.subgraphDescription.getMergerDocs();
                    mergerTitle = processorInfo.subgraphDescription.getMergerTitle();
                    mergerSource = processorInfo.subgraphDescription.getMergeSource();
                    break;
                case MERGE_POINT:
                    mergerDocs = processorInfo.detachedMergePointDescription.getMergerDocs();
                    mergerTitle = processorInfo.detachedMergePointDescription.getMergerTitle();
                    mergerSource = processorInfo.detachedMergePointDescription.getMergerSource();
                    break;
            }

            mergePointModel.mergeSource = mergerSource;
            mergePointModel.mergerTitle = mergerTitle;
            mergePointModel.mergerDocs = mergerDocs;

            model.mergePoints.add(mergePointModel);
        });

        return model;
    }


    @Data
    @Accessors(chain = true)
    public static class MergePoint {
        enum Type {
            PROCESSOR,
            DETACHED
        }

        Type type = Type.PROCESSOR;

        CRProcessingItem processor;

        ReactorGraphModel.Coordinates coordinates;
        ReactorGraphModel.Source coordinatesSource;


        final List<Transition> transitions = new ArrayList<>();

        public ReactorGraphModel.MergePoint serialize() {
            ReactorGraphModel.MergePoint model = new ReactorGraphModel.MergePoint();
            model.coordinates = this.coordinates != null ? this.coordinates : new ReactorGraphModel.Coordinates(100,100);
            model.coordinatesSource = coordinatesSource;
            model.isDetached = type == Type.DETACHED;
            model.processor = CRReactorGraph.serialize(this.processor);
            model.transitions = new ArrayList<>();
            this.transitions.forEach(transition -> model.transitions.add(transition.serialize()));
            return model;
        }
    }

    @Data
    @Accessors(chain = true)
    public static class Transition {

        final Set<Enum<?>> mergeStatuses;

        boolean isOnAny;
        boolean isComplete = false;
        CRProcessingItem merge;
        CRProcessingItem handleBy;

        ReactorGraphModel.Coordinates completeCoordinates;
        ReactorGraphModel.Source completeCoordinatesSource;
        ReactorGraphModel.Source completeSource;

        ReactorGraphModel.Source transitionOnAnySource;
        Map<String, ReactorGraphModel.Source> transitionOnStatusSource;
        Map<String, ReactorGraphModel.Source> mergeStatusSources;


        private Map<String, String[]> transitionsDoc;

        public Transition(Enum<?>... mergeStatuses) {
            this.mergeStatuses = new HashSet<>(Arrays.asList(mergeStatuses));

            mergeStatusSources = new HashMap<>();
            for (Enum<?> status : mergeStatuses) {
                mergeStatusSources.put(status.name(), new ReactorGraphModel.Source().setClassName(status.getClass().getName()));
            }
        }

        public ReactorGraphModel.MergePointTransition serialize() {
            ReactorGraphModel.MergePointTransition model = new ReactorGraphModel.MergePointTransition();
            model.mergeStatuses = Optional.ofNullable(mergeStatuses)
                    .map(Set::stream)
                    .map(stream -> stream.map(Enum::toString).collect(Collectors.toList()))
                    .orElse(null);
            model.isComplete = isComplete;
            model.isOnAny = isOnAny;

            model.mergeProcessor = Optional.ofNullable(merge).map(CRReactorGraph::serialize).orElse(null);

            model.handleByProcessor = Optional.ofNullable(handleBy).map(CRReactorGraph::serialize).orElse(null);

            model.completeCoordinates = completeCoordinates != null ? completeCoordinates : new ReactorGraphModel.Coordinates(100, 100);

            model.completeCoordinatesSource = completeCoordinatesSource;

            model.completeSource = completeSource;

            model.transitionOnAnySource = transitionOnAnySource;
            model.transitionOnStatusSource = transitionOnStatusSource;
            model.mergeStatusSources = mergeStatusSources;

            if (this.transitionsDoc != null) {
                model.transitionsDoc = new ArrayList<>();
                this.transitionsDoc.forEach((status, docs) -> {
                    ReactorGraphModel.TransitionDocumentation doc = new ReactorGraphModel.TransitionDocumentation();
                    doc.mergeStatus = status;
                    doc.docs = docs;
                    model.transitionsDoc.add(doc);
                });
            }

            return model;
        }

    }

    public enum ProcessingItemType {
        PROCESSOR, SUBGRAPH, MERGE_POINT
    }

    @Data
    @Accessors(chain = true)
    public static class ProcessingItemInfo {

        ProcessingItemType processingItemType = ProcessingItemType.PROCESSOR;

        CRProcessorDescription description;
        CRSubgraphDescription subgraphDescription;
        CRMergePointDescription detachedMergePointDescription;

        ReactorGraphModel.Coordinates coordinates;

        ReactorGraphModel.Source coordinatesSource;

        boolean isMergerExist(){
            switch (processingItemType){
                case PROCESSOR:
                    return description.getMerger() != null;
                case SUBGRAPH:
                    return subgraphDescription.getMerger() != null;
                case MERGE_POINT:
                    return detachedMergePointDescription.getMerger() != null;
                default:
                    throw new IllegalArgumentException(String.format("Invalid processing item type: %s", processingItemType));
            }
        }

        public ReactorGraphModel.Processor serializeProcessor(){
            if (processingItemType != ProcessingItemType.PROCESSOR) {
                throw new IllegalArgumentException("Invalid type: " + processingItemType);
            }

            ReactorGraphModel.Processor model = new ReactorGraphModel.Processor();
            model.coordinates = coordinates != null ? coordinates : new ReactorGraphModel.Coordinates(100, 100);
            model.coordinatesSource = this.coordinatesSource;

            model.processorDoc = description.getProcessorDoc();
            model.handlerDoc = description.getHandlerDocs();
            model.handlerTitle = description.getHandlerTitle();

            model.withHandlerSource = Optional.ofNullable(this.description)
                    .map(CRProcessorDescription::getWithHandlerSource)
                    .orElse(null);

            return model;
        }

        public ReactorGraphModel.Subgraph serializeSubgraph() {
            ReactorGraphModel.Subgraph model = new ReactorGraphModel.Subgraph();
            model.coordinates = coordinates != null ? coordinates : new ReactorGraphModel.Coordinates(100, 100);
            model.coordinatesSource = this.coordinatesSource;

            model.subgraphDoc = subgraphDescription.getSubgraphDoc();
            model.subgraphTitle = subgraphDescription.getSubgraphTitle();

            return model;
        }
    }


    @Data
    @Accessors(chain = true)
    public static class StartPoint {

        ReactorGraphModel.Coordinates coordinates;

        ReactorGraphModel.Source coordinatesSource;
        final List<CRProcessingItem> processors = new ArrayList<>();

        public ReactorGraphModel.StartPoint serialize() {
            ReactorGraphModel.StartPoint model = new ReactorGraphModel.StartPoint();
            model.coordinates = this.coordinates != null ? this.coordinates : new ReactorGraphModel.Coordinates(500,100);
            model.coordinatesSource = this.coordinatesSource;
            model.processors = new ArrayList<>();
            this.processors.stream()
                    .map(CRReactorGraph::serialize)
                    .sorted()
                    .forEach(model.processors::add);
            return model;
        }
    }

    /**
     * Graph Payload Type
     */
    Class<PayloadType> payloadClass;

    /**
     * Location where {@link ReactorGraphBuilder.BuilderBaseMerge#coordinates()} was invoked.
     */
    ReactorGraphModel.Source coordinatesSource;

    /**
     * Location where {@link Coordinates#buildGraph()} was invoked.
     */
    ReactorGraphModel.Source buildGraphSource;

    @Getter
    final Map<CRProcessingItem, ProcessingItemInfo> processingItems = new HashMap<>();

    @Getter
    final List<MergeGroup> mergeGroups = new ArrayList<>();

    @Getter
    final StartPoint startPoint = new CRReactorGraph.StartPoint();

    public CRReactorGraph(Class<PayloadType> payloadClass) {
        this.payloadClass = payloadClass;
    }

    ReactorGraphModel.Payload serializePayload() {
        ReactorGraphModel.Payload payload = new ReactorGraphModel.Payload();
        payload.payloadClass = payloadClass.getName();
        payload.payloadName = payloadClass.getSimpleName();
        payload.payloadDoc = Optional.ofNullable(payloadClass.getDeclaredAnnotation(Reactored.class))
                .map(Reactored::value)
                .orElse(null);
        return payload;
    }

    @Override
    public ReactorGraphModel serialize() {
        ReactorGraphModel model = new ReactorGraphModel();

        model.payload = serializePayload();

        model.coordinatesSource = this.coordinatesSource;
        model.buildGraphSource = this.buildGraphSource;

        processingItems.entrySet().stream()
                .sorted(Comparator.comparing(entry -> serialize(entry.getKey())))
                .forEach(entry -> {
                    switch (entry.getValue().getProcessingItemType()) {
                        case PROCESSOR:
                            val processor = entry.getValue().serializeProcessor();
                            processor.id = serialize(entry.getKey());
                            model.processors.add(processor);
                            break;
                        case SUBGRAPH:
                            val subgraph = entry.getValue().serializeSubgraph();
                            subgraph.id = serialize(entry.getKey());
                            model.subgraphs.add(subgraph);
                            break;
                        case MERGE_POINT:
                            break;
                        default:
                            throw new IllegalStateException("Invalid type: " + entry.getValue().getProcessingItemType());


                    }
                });

        model.startPoint = startPoint.serialize();
        model.mergeGroups = new ArrayList<>();

        this.mergeGroups.forEach(mergeGroup -> model.mergeGroups.add(serialize(mergeGroup)));

        return model;
    }


    static String serialize(CRProcessingItem CRProcessingItem) {
        if (CRProcessingItem instanceof CRProcessor) {
            return serialize((CRProcessor) CRProcessingItem);
        }
        if (CRProcessingItem instanceof CRSubgraph) {
            return serialize((CRSubgraph) CRProcessingItem);
        }
        if (CRProcessingItem instanceof CRMergePoint){
            return serialize((CRMergePoint) CRProcessingItem);
        }
        throw new IllegalArgumentException(String.format("Instance of class %s not supported.", CRProcessingItem.getClass()));
    }

    static String serialize(CRProcessor graphProcessor) {
        if (graphProcessor == null) {
            return null;
        }

        return String.format("%s@%d",
                graphProcessor.getProcessorDescription().getProcessorType().getSimpleName(),
                graphProcessor.getId());
    }

    static String serialize(CRMergePoint graphMergePoint) {
        return String.format("MergePoint@%d", graphMergePoint.getId());
    }


    static String serialize(CRSubgraph subgraphProcessor) {
        return subgraphProcessor == null ? null :
                String.format("%s@%d",
                        subgraphProcessor.getPayloadClass().getSimpleName(),
                        subgraphProcessor.getId());
    }


}
