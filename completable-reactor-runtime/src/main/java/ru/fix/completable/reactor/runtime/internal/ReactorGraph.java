package ru.fix.completable.reactor.runtime.internal;

import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.val;
import ru.fix.completable.reactor.api.ReactorGraphModel;
import ru.fix.completable.reactor.api.Reactored;
import ru.fix.completable.reactor.runtime.ReactorMarshaller;
import ru.fix.completable.reactor.runtime.dsl.Coordinates;
import ru.fix.completable.reactor.runtime.internal.dsl.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Model of graph used to generate execution chain.
 *
 * @author Kamil Asfandiyarov
 */
@Data
public class ReactorGraph<PayloadType> {


    @Data
    @Accessors(chain = true)
    public static class MergeGroup {

        final List<MergePoint> mergePoints = new ArrayList<>();

        public ReactorGraphModel.MergeGroup serialize(Function<CRProcessingItem, ProcessorInfo> processorInfoSupplier) {
            ReactorGraphModel.MergeGroup model = new ReactorGraphModel.MergeGroup();
            model.mergePoints = new ArrayList<>();

            mergePoints.forEach(mergePoint -> {
                ReactorGraphModel.MergePoint mergePointModel = mergePoint.serialize();
                ProcessorInfo processorInfo = processorInfoSupplier.apply(mergePoint.getProcessor());


                String mergerTitle = null;
                String[] mergerDocs = null;
                ReactorGraphModel.Source mergerSource = null;

                switch (processorInfo.getProcessorType()){
                    case PLAIN:
                        mergerDocs = processorInfo.description.getMergerDocs();
                        mergerTitle = processorInfo.description.getMergerTitle();
                        mergerSource = processorInfo.description.getMergeSource();
                        break;
                    case SUBGRAPH:
                        mergerDocs = processorInfo.subgraphDescription.getMergerDocs();
                        mergerTitle = processorInfo.subgraphDescription.getMergerTitle();
                        mergerSource = processorInfo.subgraphDescription.getMergeSource();
                        break;
                    case DETACHED_MERGE_POINT:
                        mergerDocs = processorInfo.detachedMergePointDescription.getMergerDocs();
                        mergerTitle = processorInfo.detachedMergePointDescription.getMergerTitle();
                        mergerSource = processorInfo.detachedMergePointDescription.getMergerSource();
                        break;
                }

                mergePointModel.mergeSource = mergerSource;
                mergePointModel.mergerDocs = mergerDocs;
                mergePointModel.mergerTitle = mergerTitle;

                model.mergePoints.add(mergePointModel);
            });

            return model;
        }

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
            model.processor = ReactorGraph.serialize(this.processor);
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

            model.mergeProcessor = Optional.ofNullable(merge).map(ReactorGraph::serialize).orElse(null);

            model.handleByProcessor = Optional.ofNullable(handleBy).map(ReactorGraph::serialize).orElse(null);

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

    public enum ProcessorType {
        PLAIN, SUBGRAPH, DETACHED_MERGE_POINT;

        public ReactorGraphModel.ProcessorType serialize() {
            switch (this) {
                case PLAIN:
                    return ReactorGraphModel.ProcessorType.PLAIN;
                case SUBGRAPH:
                    return ReactorGraphModel.ProcessorType.SUBGRAPH;
                case DETACHED_MERGE_POINT:
                    return ReactorGraphModel.ProcessorType.DETACHED_MERGE_POINT;
                default:
                    throw new IllegalStateException(String.format("Invalid processor type: %s", this.name()));
            }
        }

    }

    @Data
    @Accessors(chain = true)
    public static class ProcessorInfo {

        ProcessorType processorType = ProcessorType.PLAIN;

        CRProcessorDescription description;
        CRSubgraphDescription subgraphDescription;
        CRMergePointDescription detachedMergePointDescription;

        ReactorGraphModel.Coordinates coordinates;


//        String[] processorDoc;
//        String handlerName;
//        String[] handlerDoc;

        ReactorGraphModel.Source coordinatesSource;

        boolean isMergerExist(){
            switch (processorType){
                case PLAIN:
                    return description.getMerger() != null;
                case SUBGRAPH:
                    return subgraphDescription.getMerger() != null;
                case DETACHED_MERGE_POINT:
                    return detachedMergePointDescription.getMerger() != null;
                default:
                    throw new IllegalArgumentException(String.format("Invalid processing item type: %s", processorType));
            }
        }


        public ReactorGraphModel.ProcessorInfo serialize() {
            ReactorGraphModel.ProcessorInfo model = new ReactorGraphModel.ProcessorInfo();
            model.coordinates = coordinates != null ? coordinates : new ReactorGraphModel.Coordinates(100, 100);
            model.coordinatesSource = this.coordinatesSource;
            model.processorDoc = processorDoc;
            model.requestDoc = handlerDoc;
            model.handlerName = handlerName;
            model.processorType = this.processorType.serialize();
            model.handleBySource = Optional.ofNullable(this.description)
                    .map(CRProcessorDescription::getHandleBySource)
                    .orElse(null);
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
                    .map(ReactorGraph::serialize)
                    .sorted()
                    .forEach(model.processors::add);
            return model;
        }

    }

    Class<PayloadType> payloadClass;


    ReactorGraphModel.Source coordinatesSource;

    /**
     * Location where {@link Coordinates#buildGraph()} was invoked.
     */
    ReactorGraphModel.Source buildGraphSource;

    @Getter
    final Map<CRProcessingItem, ProcessorInfo> processors = new HashMap<>();

    @Getter
    final List<MergeGroup> mergeGroups = new ArrayList<>();

    @Getter
    final StartPoint startPoint = new ReactorGraph.StartPoint();

    public ReactorGraph(Class<PayloadType> payloadClass) {
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

    public ReactorGraphModel serialize() {
        ReactorGraphModel model = new ReactorGraphModel();

        model.payload = serializePayload();

        model.processors = new LinkedHashMap<>();

        model.coordinatesSource = this.coordinatesSource;
        model.buildGraphSource = this.buildGraphSource;

        processors.entrySet().stream()
                .sorted(Comparator.comparing(entry -> serialize(entry.getKey())))
                .forEach(entry -> model.processors.put(serialize(entry.getKey()), entry.getValue().serialize()));

        model.startPoint = startPoint.serialize();
        model.mergeGroups = new ArrayList<>();

        this.mergeGroups.forEach(mergeGroup -> model.mergeGroups.add(mergeGroup.serialize(this.processors::get)));

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

    public static void write(ReactorGraph<?>... graphs) throws Exception {
        for (ReactorGraph<?> graph : graphs) {
            val model = graph.serialize();
            model.serializationPointSource = ReactorReflector.getMethodInvocationPoint().orElse(null);

            val content = ReactorMarshaller.marshall(model);
            val path = Paths.get(graph.getPayloadClass().getName() + ".rg");
            Files.write(path, content.getBytes(StandardCharsets.UTF_8));
        }
    }
}
