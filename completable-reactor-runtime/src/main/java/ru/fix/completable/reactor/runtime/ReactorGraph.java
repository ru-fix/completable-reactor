package ru.fix.completable.reactor.runtime;

import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;
import lombok.val;
import ru.fix.completable.reactor.api.PayloadDescription;
import ru.fix.completable.reactor.api.ReactorGraphModel;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Model of graph used to generate execution chain.
 *
 * @author Kamil Asfandiyarov
 */
public class ReactorGraph<PayloadType> {


    @Data
    @Accessors(chain = true)
    public static class MergeGroup {

        final List<MergePoint> mergePoints = new ArrayList<>();

        public ReactorGraphModel.MergeGroup serialize(Function<ProcessingGraphItem, ProcessorInfo> processorInfoSupplier) {
            ReactorGraphModel.MergeGroup model = new ReactorGraphModel.MergeGroup();
            model.mergePoints = new ArrayList<>();

            mergePoints.forEach(mergePoint -> {
                ReactorGraphModel.MergePoint mergePointModel = mergePoint.serialize();
                ProcessorInfo processorInfo = processorInfoSupplier.apply(mergePoint.getProcessor());

                mergePointModel.mergeSource = Optional.ofNullable(processorInfo.description)
                        .map(GraphProcessorDescription::getMergeSource)
                        .orElse(null);

                mergePointModel.mergerDocs = Optional.ofNullable(processorInfo.description)
                        .map(GraphProcessorDescription::getMergerDocs)
                        .orElse(null);

                model.mergePoints.add(mergePointModel);
            });

            return model;
        }

    }

    @Data
    @Accessors(chain = true)
    public static class MergePoint {

        ProcessingGraphItem processor;

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
        ProcessingGraphItem merge;
        ProcessingGraphItem handleBy;

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
        PLAIN, SUBGRAPH;

        public ReactorGraphModel.ProcessorType serialize() {
            switch (this) {
                case PLAIN:
                    return ReactorGraphModel.ProcessorType.PLAIN;
                case SUBGRAPH:
                    return ReactorGraphModel.ProcessorType.SUBGRAPH;
                default:
                    throw new IllegalStateException(String.format("Invalid processor type: %s", this.name()));
            }
        }

    }

    @Data
    @Accessors(chain = true)
    public static class ProcessorInfo {

        ProcessorType processorType = ProcessorType.PLAIN;

        GraphProcessorDescription description;
        SubgraphProcessorDescription subgraphDescription;

        ReactorGraphModel.Coordinates coordinates;

        String[] processorDoc;

        String[] requestDoc;
        String handlerName;
        ReactorGraphModel.Source coordinatesSource;
        BiFunction getMerger() {
            switch (processorType) {
                case PLAIN:
                    return description.merger;
                case SUBGRAPH:
                    return subgraphDescription.merger;
            }
            throw new IllegalStateException();
        }

        public ReactorGraphModel.ProcessorInfo serialize() {
            ReactorGraphModel.ProcessorInfo model = new ReactorGraphModel.ProcessorInfo();
            model.coordinates = coordinates != null ? coordinates : new ReactorGraphModel.Coordinates(100, 100);
            model.coordinatesSource = this.coordinatesSource;
            model.processorDoc = processorDoc;
            model.requestDoc = requestDoc;
            model.handlerName = handlerName;
            model.processorType = this.processorType.serialize();
            model.handleBySource = Optional.ofNullable(this.description)
                    .map(GraphProcessorDescription::getHandleBySource)
                    .orElse(null);
            return model;
        }

    }


    @Data
    @Accessors(chain = true)
    public static class StartPoint {

        ReactorGraphModel.Coordinates coordinates;

        ReactorGraphModel.Source coordinatesSource;
        final List<ProcessingGraphItem> processors = new ArrayList<>();
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


    @Getter
    final Class<PayloadType> payloadClass;

    ReactorGraphModel.Source coordinatesSource;
    ReactorGraphModel.Source buildGraphSource;


    @Getter
    final Map<ProcessingGraphItem, ProcessorInfo> processors = new HashMap<>();

    @Getter
    final List<MergeGroup> mergeGroups = new ArrayList<>();

    @Getter
    StartPoint startPoint;

    public ReactorGraph(Class<PayloadType> payloadClass) {
        this.payloadClass = payloadClass;
    }

    ReactorGraphModel.Payload serializePayload() {
        ReactorGraphModel.Payload payload = new ReactorGraphModel.Payload();
        payload.payloadClass = payloadClass.getName();
        payload.payloadName = payloadClass.getSimpleName();
        payload.payloadDoc = Optional.ofNullable(payloadClass.getDeclaredAnnotation(PayloadDescription.class))
                .map(PayloadDescription::doc)
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


    static String serialize(ProcessingGraphItem processingGraphItem) {
        if (processingGraphItem instanceof GraphProcessor) {
            return serialize((GraphProcessor) processingGraphItem);
        }
        if (processingGraphItem instanceof SubgraphProcessor) {
            return serialize((SubgraphProcessor) processingGraphItem);
        }
        throw new IllegalArgumentException(String.format("Instance of class %s not supported.", processingGraphItem.getClass()));
    }

    static String serialize(GraphProcessor graphProcessor) {
        if (graphProcessor == null) {
            return null;
        }

        ReactorReflector reflector = new ReactorReflector();


        return String.format("%s@%d",
                reflector.lookupProcessorIdentityClass(graphProcessor.getProcessorClass()).getSimpleName(),
                graphProcessor.getId());
    }

    static String serialize(SubgraphProcessor subgraphProcessor) {
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
