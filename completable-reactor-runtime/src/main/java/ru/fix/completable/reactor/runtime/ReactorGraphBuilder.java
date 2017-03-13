package ru.fix.completable.reactor.runtime;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.var;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import ru.fix.completable.reactor.api.*;
import ru.fix.completable.reactor.runtime.validators.ProcessorsHaveIncomingFlowsValidator;
import ru.fix.completable.reactor.runtime.validators.TerminalVertexExistValidator;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Provides fluent API for building {@link ReactorGraph}
 *
 * @author Kamil Asfandiyarov
 */
@Slf4j
public class ReactorGraphBuilder {

    final ReactorReflector reactorReflector = new ReactorReflector();

    final List<GraphValidator> graphValidators = new ArrayList<>();

    public ReactorGraphBuilder() {
        graphValidators.add(new TerminalVertexExistValidator());
        graphValidators.add(new ProcessorsHaveIncomingFlowsValidator());
    }

    private static <PayloadType> void ensureMergePointRegistered(GraphMergePoint graphMergePoint, ReactorGraph<PayloadType> graph) {
        Objects.requireNonNull(graphMergePoint);

        if (graph.processors.containsKey(graphMergePoint)) {
            return;
        }

        ReactorGraph.ProcessorInfo processorInfo = new ReactorGraph.ProcessorInfo()
                .setProcessorType(ReactorGraph.ProcessorType.DETACHED_MERGE_POINT)
                .setDetachedMergePointDescription(graphMergePoint.mergePointDescription);

        graph.processors.put(graphMergePoint, processorInfo);
    }


    private static <ProcessorType, PayloadType> void ensureProcessorRegistered(
            GraphProcessor<ProcessorType, ? super PayloadType> graphProcessor,
            ReactorGraph<PayloadType> graph) {

        Objects.requireNonNull(graphProcessor);

        if (graph.processors.containsKey(graphProcessor)) {
            return;
        }

        if (graph.processors.keySet().stream().anyMatch(processor -> processor.equals(graphProcessor))) {

            throw new IllegalArgumentException(
                    String.format("Processor of the same class %s and id %s already exist in the graph.",
                            graphProcessor.getProcessorClass(),
                            graphProcessor.getId()));
        }

        ReactorGraph.ProcessorInfo processorInfo = new ReactorGraph.ProcessorInfo();
        processorInfo.description = new GraphProcessorDescription();

        Optional.ofNullable(ReactorReflector.lookupProcessorDescription(graphProcessor.getProcessorClass()))
                .map(ProcessorDescription::doc)
                .ifPresent(doc -> processorInfo.processorDoc = doc);

        graph.processors.put(graphProcessor, processorInfo);
    }

    private static <SubgraphPayloadType, PayloadType> void ensureSubgraphRegistered(
            SubgraphProcessor<SubgraphPayloadType, ? super PayloadType> subgraphProcessor,
            ReactorGraph<PayloadType> graph) {

        Objects.requireNonNull(subgraphProcessor);

        if(graph.processors.containsKey(subgraphProcessor)){
            return;
        }

        ReactorGraph.ProcessorInfo processorInfo = new ReactorGraph.ProcessorInfo()
                .setProcessorType(ReactorGraph.ProcessorType.SUBGRAPH)
                .setSubgraphDescription(subgraphProcessor.subgraphDescription);

        Optional.ofNullable(subgraphProcessor.getPayloadClass().getAnnotation(PayloadDescription.class))
                .map(PayloadDescription::doc)
                .ifPresent(doc -> processorInfo.processorDoc = doc);

        graph.processors.put(subgraphProcessor, processorInfo);
    }


    public class BuilderPayload<PayloadType> {
        final Class<PayloadType> payloadClass;
        final ReactorGraph<PayloadType> graph;


        public BuilderPayload(Class<PayloadType> payloadClass, ReactorGraph<PayloadType> graph) {
            this.payloadClass = payloadClass;
            this.graph = graph;
        }


        public BuilderStartPoint<PayloadType> startPoint() {
            graph.startPoint = new ReactorGraph.StartPoint();
            return new BuilderStartPoint<>(graph, graph.startPoint);
        }

    }

    public class BuilderStartPoint<PayloadType> {
        final ReactorGraph<PayloadType> graph;
        final ReactorGraph.StartPoint startPoint;

        public BuilderStartPoint(ReactorGraph<PayloadType> graph, ReactorGraph.StartPoint startPoint) {
            this.startPoint = startPoint;
            this.graph = graph;
        }

        public  BuilderStartPoint<PayloadType> handleBy(GraphProcessor<?, ? super PayloadType> processor){
            ensureProcessorRegistered(processor, this.graph);
            return handleByItem(processor);
        }

        public  BuilderStartPoint<PayloadType>  handleBy(SubgraphProcessor<?, ? super PayloadType> subgraph){
            ensureSubgraphRegistered(subgraph, this.graph);
            return handleByItem(subgraph);
        }

        public  BuilderStartPoint<PayloadType>  merge(GraphMergePoint<? super PayloadType> mergePoint){
            ensureMergePointRegistered(mergePoint, this.graph);
            return handleByItem(mergePoint);
        }


        private BuilderStartPoint<PayloadType> handleByItem(ProcessingGraphItem... processors) {
            if (Arrays.stream(processors).anyMatch(this.startPoint.processors::contains)) {
                throw new IllegalArgumentException(String.format(
                        "Could not add twice same processor to ReactorGraph." +
                                " Registered processors: %s. New processors: %s.",
                        this.startPoint.processors.stream()
                                .map(Object::toString)
                                .collect(Collectors.joining(",")),
                        Arrays.stream(processors)
                                .map(Object::toString)
                                .collect(Collectors.joining(","))));
            }

            Arrays.stream(processors).forEachOrdered(this.startPoint.processors::add);
            return this;
        }

        public BuilderSingleMerge<PayloadType> singleMerge(MergeableProcessingGraphItem processor) {
            if (!this.startPoint.processors.contains(processor)) {
                throw new IllegalArgumentException(String.format("Processor %s is not registered", processor.getClass()));
            }

            ReactorGraph.MergeGroup mergeGroup = new ReactorGraph.MergeGroup();
            this.graph.mergeGroups.add(mergeGroup);

            ReactorGraph.MergePoint mergePoint = new ReactorGraph.MergePoint();
            mergeGroup.mergePoints.add(mergePoint);

            return new BuilderSingleMerge<>(processor, graph, mergePoint);
        }

        public BuilderMultiMerge<PayloadType> multiMerge() {
            ReactorGraph.MergeGroup mergeGroup = new ReactorGraph.MergeGroup();
            this.graph.mergeGroups.add(mergeGroup);
            return new BuilderMultiMerge<>(graph, mergeGroup, null);
        }

    }

    public class BuilderMultiMerge<PayloadType> extends BuilderBaseMerge<PayloadType> {
        final ReactorGraph<PayloadType> graph;
        final ReactorGraph.MergeGroup mergeGroup;
        /**
         * Can be NULL
         */
        ReactorGraph.MergePoint mergePoint;


        public BuilderMultiMerge(ReactorGraph<PayloadType> graph, ReactorGraph.MergeGroup mergeGroup, ReactorGraph.MergePoint mergePoint) {
            super(graph);
            this.graph = graph;
            this.mergeGroup = mergeGroup;
            this.mergePoint = mergePoint;
        }

        public BuilderMultiMerge<PayloadType> merge(GraphMergePoint<? super PayloadType> mergePoint) {
            ensureMergePointRegistered(mergePoint, this.graph);

            return mergeItem(mergePoint);
        }

        public BuilderMultiMerge<PayloadType> merge(GraphProcessor<?, ? super PayloadType> processor) {
            ensureProcessorRegistered(processor, this.graph);
            return mergeItem(processor);
        }

        public BuilderMultiMerge<PayloadType> merge(SubgraphProcessor<?, ? super PayloadType> subgraph) {
            ensureSubgraphRegistered(subgraph, this.graph);
            return mergeItem(subgraph);
        }

        private BuilderMultiMerge<PayloadType> mergeItem(MergeableProcessingGraphItem processor) {


            if (processor instanceof GraphProcessor || processor instanceof SubgraphProcessor) {
                if (!Optional.ofNullable(this.graph.processors.get(processor))
                        .map(processorInfo -> processorInfo.processorType == ReactorGraph.ProcessorType.PLAIN ?
                                processorInfo.description.merger :
                                processorInfo.subgraphDescription.merger)
                        .isPresent()) {

                    throw new IllegalArgumentException(String.format(
                            "Merge point added to processor %s, but it does not have merger." +
                                    " Use 'withMerger' clause to attach merger to processor.",
                            processor));
                }
            }

            this.mergePoint = new ReactorGraph.MergePoint()
                    .setProcessor(processor);

            if (processor instanceof GraphMergePoint) {
                this.mergePoint.setType(ReactorGraph.MergePoint.Type.DETACHED);
            }

            mergeGroup.mergePoints.add(mergePoint);
            return this;
        }

        public BuilderMultiMerge<PayloadType> multiMerge() {
            ReactorGraph.MergeGroup newMergeGroup = new ReactorGraph.MergeGroup();
            this.graph.mergeGroups.add(newMergeGroup);
            return new BuilderMultiMerge<>(this.graph, newMergeGroup, null);
        }

        public MergePointTransition<BuilderMultiMerge<PayloadType>, PayloadType> on(Enum<?>... mergeStatuses) {
            if (mergeStatuses.length <= 0) {
                throw new IllegalArgumentException("You should provide at least one merge status or use onAny predicate.");
            }

            ReactorGraphModel.Source onSource = ReactorReflector.getMethodInvocationPoint().orElse(null);

            return new MergePointTransition<>(graph, this.mergePoint, () -> {
                val transition = new ReactorGraph.Transition(mergeStatuses);
                var map = transition.getTransitionOnStatusSource();
                if (map == null) {
                    map = new HashMap<>();
                }
                for (Enum<?> status : mergeStatuses) {
                    map.put(status.name(), onSource);
                }
                transition.setTransitionOnStatusSource(map);

                return transition;

            }, this);
        }

        public MergePointTransition<BuilderMultiMerge<PayloadType>, PayloadType> onAny() {
            ReactorGraphModel.Source onAnySource = ReactorReflector.getMethodInvocationPoint().orElse(null);

            return new MergePointTransition<>(
                    graph,
                    this.mergePoint,
                    () -> new ReactorGraph.Transition().setOnAny(true).setTransitionOnAnySource(onAnySource),
                    this);
        }

        public BuilderSingleMerge<PayloadType> singleMerge(MergeableProcessingGraphItem<PayloadType> processor) {

            if (!Optional.ofNullable(this.graph.processors.get(processor))
                    .map(processorInfo -> processorInfo.processorType == ReactorGraph.ProcessorType.PLAIN ?
                            processorInfo.description.merger :
                            processorInfo.subgraphDescription.merger)
                    .isPresent()) {
                throw new IllegalArgumentException(String.format(
                        "Merge point added to processor %s, but it does not have merger." +
                                " Use 'withMerger' clause to attach merger to processor.",
                        processor));
            }

            ReactorGraph.MergeGroup newMergeGroup = new ReactorGraph.MergeGroup();
            this.graph.mergeGroups.add(newMergeGroup);

            ReactorGraph.MergePoint newMergePoint = new ReactorGraph.MergePoint();
            newMergeGroup.mergePoints.add(newMergePoint);

            return new BuilderSingleMerge<>(processor, graph, newMergePoint);
        }
    }


    public class BuilderSingleMerge<PayloadType> extends BuilderBaseMerge<PayloadType> {
        final ReactorGraph<PayloadType> graph;
        final ReactorGraph.MergePoint mergePoint;

        public BuilderSingleMerge(ProcessingGraphItem processor, ReactorGraph<PayloadType> graph, ReactorGraph.MergePoint mergePoint) {
            super(graph);
            this.graph = graph;
            this.mergePoint = mergePoint;
            this.mergePoint.processor = processor;
        }

        public BuilderSingleMerge<PayloadType> singleMerge(MergeableProcessingGraphItem processor) {

            if (!Optional.ofNullable(this.graph.processors.get(processor))
                    .map(processorInfo -> processorInfo.processorType == ReactorGraph.ProcessorType.PLAIN ?
                            processorInfo.description.merger :
                            processorInfo.subgraphDescription.merger)
                    .isPresent()) {

                throw new IllegalArgumentException(String.format(
                        "Merge point added to processor %s, but it does not have merger." +
                                " Use 'withMerger' clause to attach merger to processor.",
                        processor));
            }

            ReactorGraph.MergeGroup mergeGroup = new ReactorGraph.MergeGroup();
            this.graph.mergeGroups.add(mergeGroup);

            ReactorGraph.MergePoint newMergePoint = new ReactorGraph.MergePoint();
            mergeGroup.mergePoints.add(newMergePoint);

            return new BuilderSingleMerge<>(processor, graph, newMergePoint);
        }

        public BuilderMultiMerge<PayloadType> multiMerge() {
            ReactorGraph.MergeGroup mergeGroup = new ReactorGraph.MergeGroup();
            this.graph.mergeGroups.add(mergeGroup);
            return new BuilderMultiMerge<>(this.graph, mergeGroup, null);
        }


        public MergePointTransition<BuilderSingleMerge<PayloadType>, PayloadType> on(Enum<?>... mergeStatuses) {
            if (mergeStatuses.length <= 0) {
                throw new IllegalArgumentException("You should provide at least one merge status or use onAny predicate.");
            }

            ReactorGraphModel.Source onSource = ReactorReflector.getMethodInvocationPoint().orElse(null);


            return new MergePointTransition<>(
                    graph,
                    mergePoint,
                    () -> {
                        val transition = new ReactorGraph.Transition(mergeStatuses);
                        var map = transition.getTransitionOnStatusSource();
                        if (map == null) {
                            map = new HashMap<>();
                        }
                        for (Enum<?> status : mergeStatuses) {
                            map.put(status.name(), onSource);
                        }
                        transition.setTransitionOnStatusSource(map);
                        return transition;
                    },
                    this);
        }

        public MergePointTransition<BuilderSingleMerge<PayloadType>, PayloadType> onAny() {
            ReactorGraphModel.Source onAnySource = ReactorReflector.getMethodInvocationPoint().orElse(null);
            return new MergePointTransition<>(
                    graph,
                    this.mergePoint,
                    () -> new ReactorGraph.Transition().setOnAny(true).setTransitionOnAnySource(onAnySource),
                    this);
        }
    }

    class BuilderBaseMerge<PayloadType> {
        final ReactorGraph<PayloadType> graph;

        public BuilderBaseMerge(ReactorGraph<PayloadType> graph) {
            this.graph = graph;
        }

        public BuilderCoordinates<PayloadType> coordinates() {
            this.graph.coordinatesSource = ReactorReflector.getMethodInvocationPoint().orElse(null);
            return new BuilderCoordinates<>(graph);
        }
    }

    public class BuilderCoordinates<PaylaodType> extends BuilderBuilder<PaylaodType> {
        final ReactorGraph<PaylaodType> graph;

        public BuilderCoordinates(ReactorGraph<PaylaodType> graph) {
            super(graph);
            this.graph = graph;
        }

        public BuilderCoordinates<PaylaodType> proc(String processorId, int x, int y) {
            List<ProcessingGraphItem> matchedProcessors = graph.getProcessors().keySet().stream()
                    .filter(processor -> ReactorGraph.serialize(processor).equals(processorId))
                    .collect(Collectors.toList());


            if (matchedProcessors.size() > 1) {
                String matchedProcessorIds = matchedProcessors.stream()
                        .map(ReactorGraph::serialize)
                        .collect(Collectors.joining(","));
                throw new IllegalArgumentException(String.format("Found multiple processors that matches %s. Available processors: %s", processorId, matchedProcessorIds));

            } else if (matchedProcessors.size() <= 0) {
                throw new IllegalArgumentException(String.format("Could not find processor that matches %s. Registered processors: %s",
                        processorId,
                        graph.getProcessors().keySet().stream()
                                .map(ReactorGraph::serialize)
                                .collect(Collectors.joining(","))
                ));
            }

            ReactorGraph.ProcessorInfo processorInfo = graph.getProcessors().get(matchedProcessors.get(0));
            processorInfo.coordinates = new ReactorGraphModel.Coordinates(x, y);
            processorInfo.coordinatesSource = ReactorReflector.getMethodInvocationPoint().orElse(null);

            return this;
        }

        public BuilderCoordinates<PaylaodType> merge(String processorId, int x, int y) {
            List<ReactorGraph.MergePoint> matchedMergePoints = graph.getMergeGroups().stream()
                    .map(ReactorGraph.MergeGroup::getMergePoints)
                    .flatMap(List::stream)
                    .filter(mergePoint -> ReactorGraph.serialize(mergePoint.getProcessor()).equals(processorId))
                    .collect(Collectors.toList());


            if (matchedMergePoints.size() > 1) {
                String matchedMergePointIds = matchedMergePoints.stream()
                        .map(mergePoint -> ReactorGraph.serialize(mergePoint.getProcessor()))
                        .collect(Collectors.joining(","));
                throw new IllegalArgumentException(String.format("Found multiple merge points that matches %s. Merge points: %s", processorId, matchedMergePointIds));

            } else if (matchedMergePoints.size() <= 0) {
                throw new IllegalArgumentException(String.format("Could not find merge point that matches %s", processorId));
            }
            ReactorGraph.MergePoint mergePoint = matchedMergePoints.get(0);
            mergePoint.coordinates = new ReactorGraphModel.Coordinates(x, y);
            return this;
        }

        public BuilderCoordinates<PaylaodType> start(int x, int y) {
            graph.startPoint.coordinates = new ReactorGraphModel.Coordinates(x, y);
            graph.startPoint.coordinatesSource = ReactorReflector.getMethodInvocationPoint().orElse(null);
            return this;
        }

        public BuilderCoordinates<PaylaodType> complete(String processorId, int x, int y) {
            List<ReactorGraph.MergePoint> matchedMergePoints = graph.getMergeGroups().stream()
                    .map(ReactorGraph.MergeGroup::getMergePoints)
                    .flatMap(List::stream)
                    .filter(mergePoint -> ReactorGraph.serialize(mergePoint.getProcessor()).equals(processorId))
                    .collect(Collectors.toList());

            if (matchedMergePoints.size() > 1) {
                String matchedMergePointIds = matchedMergePoints.stream()
                        .map(mergePoint -> ReactorGraph.serialize(mergePoint.getProcessor()))
                        .collect(Collectors.joining(","));

                throw new IllegalArgumentException(String.format("Found multiple merge points that matches %s. Available merge points: %s", processorId, matchedMergePointIds));
            } else if (matchedMergePoints.size() <= 0) {
                throw new IllegalArgumentException(String.format("Could not find merge point that matches %s", processorId));
            }

            List<ReactorGraph.Transition> transitions = matchedMergePoints.get(0).transitions.stream()
                    .filter(transition -> transition.isComplete())
                    .collect(Collectors.toList());

            if (transitions.size() > 1) {
                throw new IllegalArgumentException(String.format("Found multiple complete transitions that matches %s", processorId));
            } else if (transitions.size() <= 0) {
                throw new IllegalArgumentException(String.format("Could not find complete transition that matches %s", processorId));
            }

            ReactorGraph.Transition transition = transitions.get(0);
            transition.completeCoordinates = new ReactorGraphModel.Coordinates(x, y);
            transition.completeCoordinatesSource = ReactorReflector.getMethodInvocationPoint().orElse(null);

            return this;
        }
    }

    @Data
    @Accessors(chain = true)
    public static class HandlerDocumentation {
        String[] documentation;
        String handlerName;
    }

    public class BuilderBuilder<PayloadType> {
        final ReactorGraph<PayloadType> graph;

        public BuilderBuilder(ReactorGraph<PayloadType> graph) {
            this.graph = graph;
        }


        public ReactorGraph<PayloadType> buildGraph() throws Exception {
            this.graph.buildGraphSource = ReactorReflector.getMethodInvocationPoint().orElse(null);
            /**
             * Populate request documentation from handling method
             */
            for (Map.Entry<ProcessingGraphItem, ReactorGraph.ProcessorInfo> entry : graph.processors.entrySet()) {

                if (entry.getKey() instanceof GraphProcessor) {

                    Object processor = ((GraphProcessor) entry.getKey()).getProcessor();
                    ReactorGraph.ProcessorInfo processorInfo = entry.getValue();

                    try {
                        HandlerDocumentation documentation = lookupHandlerDocumentation(processor, processorInfo);
                        processorInfo.requestDoc = documentation.getDocumentation();
                        processorInfo.handlerName = documentation.getHandlerName();
                    } catch (Exception exc) {
                        throw new Exception(String.format(
                                "Failed to lookup handler documentation for processor %s",
                                processor.getClass()),
                                exc);
                    }
                } else if (entry.getKey() instanceof SubgraphProcessor) {
                    Class<?> subgraphPayloadClass = ((SubgraphProcessor) entry.getKey()).getPayloadClass();
                    ReactorGraph.ProcessorInfo processorInfo = entry.getValue();

                    processorInfo.processorDoc = Optional.ofNullable(subgraphPayloadClass.getDeclaredAnnotation(PayloadDescription.class))
                            .map(PayloadDescription::doc)
                            .orElse(null);

                } else if (entry.getKey() instanceof GraphMergePoint) {
                    //do nothing

                } else {
                    throw new IllegalStateException(String.format("ProcessingGraphItem of type %s not supported.", entry.getKey().getClass()));
                }

            }

            /**
             * Populate transitions documentation
             */
            graph.mergeGroups.stream()
                    .map(ReactorGraph.MergeGroup::getMergePoints)
                    .flatMap(List::stream)
                    .map(ReactorGraph.MergePoint::getTransitions)
                    .flatMap(List::stream)
                    .forEach(transition -> transition.setTransitionsDoc(lookupTransitionDocumentation(transition)));

            /**
             * Validate graph
             */
            graphValidators.forEach(validator -> {
                validator.validateGraph(graph);
            });

            /**
             * Generate possible graph executions and validate each execution
             * will be implemented supposedly by {@link CombinatoricGraphValidator} that will call {@link GraphValidator#validateExecution(ReactorGraph)}
             */
            return graph;
        }

        /**
         * @param transition
         * @return NULL if no documentation provided
         */
        Map<String, String[]> lookupTransitionDocumentation(ReactorGraph.Transition transition) {
            if (transition.getMergeStatuses() != null) {
                Map<String, String[]> transitionsDoc = new HashMap<>();

                transition.getMergeStatuses().forEach(status -> {

                    Field mergeStatusEnumField = null;
                    try {
                        mergeStatusEnumField = status.getClass().getField(status.name());
                    } catch (NoSuchFieldException illegalStateExc) {
                        log.error("Illegal state. Can not get field of enum %s", status, illegalStateExc);
                    }

                    transitionsDoc.put(status.name(),
                            Optional.ofNullable(mergeStatusEnumField)
                                    .map(field -> field.getAnnotation(TransitionDescription.class))
                                    .map(TransitionDescription::doc)
                                    .orElse(null));
                });

                return transitionsDoc;
            } else {
                return null;
            }
        }

        HandlerDocumentation lookupHandlerDocumentation(Object processor, ReactorGraph.ProcessorInfo processorInfo) throws Exception {
            ReactorReflector reflector = ReactorGraphBuilder.this.reactorReflector;

            Method method;

            if (processorInfo.description.handler0 != null) {
                method = reflector.getMethodFromMethodReference(
                        processor.getClass(),
                        processorInfo.description.handler0::apply);

            } else if (processorInfo.description.handler1 != null) {
                method = reflector.getMethodFromMethodReference(
                        processor.getClass(),
                        processorInfo.description.handler1::apply);

            } else if (processorInfo.description.handler2 != null) {
                method = reflector.getMethodFromMethodReference(
                        processor.getClass(),
                        processorInfo.description.handler2::apply);

            } else if (processorInfo.description.handler3 != null) {
                method = reflector.getMethodFromMethodReference(
                        processor.getClass(),
                        processorInfo.description.handler3::apply);

            } else if (processorInfo.description.handler4 != null) {
                method = reflector.getMethodFromMethodReference(
                        processor.getClass(),
                        processorInfo.description.handler4::apply);
            } else if (processorInfo.description.handler5 != null) {
                method = reflector.getMethodFromMethodReference(
                        processor.getClass(),
                        processorInfo.description.handler5::apply);
            } else {
                throw new IllegalStateException(String.format("Can not find handler for processor %s", processor.getClass()));
            }

            return new HandlerDocumentation()
                    .setHandlerName(method.getName())
                    .setDocumentation(Optional.ofNullable(method.getAnnotation(HandlerDescription.class))
                            .map(HandlerDescription::doc)
                            .orElse(null)
                    );
        }
    }

    public static class MergePointTransition<BuilderContext, PayloadType> {
        final ReactorGraph<PayloadType> graph;
        final ReactorGraph.MergePoint mergePoint;
        final Supplier<ReactorGraph.Transition> transitionSupplier;
        final BuilderContext builderContext;


        public MergePointTransition(ReactorGraph<PayloadType> graph, ReactorGraph.MergePoint mergePoint, Supplier<ReactorGraph.Transition> transitionSupplier, BuilderContext
                builderContext) {
            this.graph = graph;
            this.mergePoint = mergePoint;
            this.transitionSupplier = transitionSupplier;
            this.builderContext = builderContext;
        }

        public BuilderContext complete() {

            if (this.mergePoint.getTransitions().stream().anyMatch(ReactorGraph.Transition::isComplete)) {
                throw new IllegalArgumentException(String.format(
                        "Complete transition already present in merge point for processor %s. " +
                                "Join condition into single complete transition.",
                        mergePoint.getProcessor()));
            }


            this.mergePoint.getTransitions().add(
                    transitionSupplier.get()
                            .setComplete(true).setCompleteSource(ReactorReflector.getMethodInvocationPoint().orElse(null)));

            return this.builderContext;
        }


        enum TransitionType {
            HANDLE, MERGE
        }

        /**
         * Check if transition between mergePoint and given processor already exist.
         * Check if new transition is redundant.
         * Try to merge new transition into existing one.
         * If transition does not exist one - add new transition to mergePoint.
         */
        void checkMergeOrAddNewTransition(ReactorGraph.Transition newTransition, ReactorGraph.MergePoint mergePoint, TransitionType transitionType) {

            Function<ReactorGraph.Transition, ProcessingGraphItem> processorAccessor = transitionType == TransitionType.HANDLE ?
                    ReactorGraph.Transition::getHandleBy :
                    ReactorGraph.Transition::getMerge;


            ProcessingGraphItem processor = processorAccessor.apply(newTransition);

            List<ReactorGraph.Transition> existingTransitions = this.mergePoint.getTransitions().stream()
                    .filter(existingTransition -> processorAccessor.apply(existingTransition) != null)
                    .filter(existingTransition -> processor.equals(processorAccessor.apply(existingTransition)))
                    .collect(Collectors.toList());

            if (existingTransitions.size() > 0) {
                if (existingTransitions.size() > 1) {
                    throw new IllegalArgumentException(String.format(
                            "More that one transition exit from merge point %s to processor %s: %s. New transition: %s",
                            mergePoint.getProcessor(),
                            processor,
                            existingTransitions.stream().map(ReactorGraph.Transition::toString).collect(Collectors.joining(",")),
                            newTransition
                    ));
                }

                ReactorGraph.Transition existingTransition = existingTransitions.get(0);
                if (existingTransition.isOnAny) {
                    throw new IllegalArgumentException(String.format(
                            "Unconditional transition from merge point %s to processor %s already exist." +
                                    " Conditional transition is redundant. Existing transition: %s. New transition: %s",
                            mergePoint.getProcessor(),
                            processor,
                            existingTransition,
                            newTransition));
                }
                if (existingTransition.getMergeStatuses().containsAll(newTransition.getMergeStatuses())) {
                    throw new IllegalArgumentException(String.format(
                            "Transition that suite given condition already exist in route from merge point %s to processor %s. Existing transition: %s. New transition: %s" +
                                    " Conditional transition is redundant.",
                            mergePoint.getProcessor(),
                            processor,
                            existingTransition,
                            newTransition));
                }
                existingTransition.getMergeStatuses().addAll(newTransition.getMergeStatuses());
            } else {
                this.mergePoint.getTransitions().add(newTransition);
            }
        }

        public BuilderContext merge(GraphMergePoint<? super PayloadType> mergePoint) {
            ensureMergePointRegistered(mergePoint, this.graph);
            return mergeItem(mergePoint);
        }

        public BuilderContext merge(GraphProcessor<?, ? super PayloadType> processor) {
            return mergeItem(processor);
        }

        public BuilderContext merge(SubgraphProcessor<?, ? super PayloadType> subgraph) {
            return mergeItem(subgraph);
        }

        private BuilderContext mergeItem(MergeableProcessingGraphItem<? super PayloadType> processor) {

            if (!graph.processors.containsKey(processor)) {
                throw new IllegalArgumentException(String.format("Processor %s not registered for payload %s", processor, graph.getPayloadClass()));
            }

            ReactorGraph.Transition newTransition = transitionSupplier.get()
                    .setMerge(processor);

            checkMergeOrAddNewTransition(newTransition, this.mergePoint, TransitionType.MERGE);


            return this.builderContext;
        }

        public  BuilderContext handleBy(GraphProcessor<?, ? super PayloadType> processor){
            ensureProcessorRegistered(processor, this.graph);
            return handleByItem(processor);
        }

        public  BuilderContext handleBy(SubgraphProcessor<?, ? super PayloadType> subgraph){
            ensureSubgraphRegistered(subgraph, this.graph);
            return handleByItem(subgraph);
        }

        private BuilderContext handleByItem(HandleableProcessingGraphItem... processors) {

            for (ProcessingGraphItem processor : processors) {

                ReactorGraph.Transition newTransition = transitionSupplier.get()
                        .setHandleBy(processor);

                checkMergeOrAddNewTransition(newTransition, this.mergePoint, TransitionType.HANDLE);
            }
            return this.builderContext;
        }
    }

    public ReactorGraphBuilder registerValidator(GraphValidator validator) {
        graphValidators.add(validator);
        return this;
    }

    public <PayloadType> BuilderPayload<PayloadType> payload(Class<PayloadType> payloadClass) {
        ReactorGraph<PayloadType> graph = new ReactorGraph<>(payloadClass);
        return new BuilderPayload<>(payloadClass, graph);
    }

    public <ProcessorType, PayloadType> ArgMethodHandler0<GraphProcessorDescription<ProcessorType, PayloadType>, PayloadType, ProcessorType> describeProcessor(Class<ProcessorType> processorType, Class<PayloadType> payloadType) {

        GraphProcessorDescription<ProcessorType, PayloadType> description = new GraphProcessorDescription<>();

        return new ArgMethodHandler0<>(description, description);
    }

    public <PayloadType> MergePointArgMethodMerger<GraphMergePointDescription<PayloadType>, PayloadType> describeMergePoint(Class<PayloadType> payloadType) {
        GraphMergePointDescription<PayloadType> description = new GraphMergePointDescription<>();
        return new MergePointArgMethodMerger<>(description, description);
    }


    public <SubgraphPayloadType, PayloadType> SubgraphHandler<SubgraphProcessorDescription<SubgraphPayloadType, PayloadType>, PayloadType, SubgraphPayloadType> describeSubgraph(Class<SubgraphPayloadType> subgraphPayload,
                                                                                                                                                                                 Class<PayloadType> payloadType){
        SubgraphProcessorDescription<SubgraphPayloadType, PayloadType> description = new SubgraphProcessorDescription<>(subgraphPayload);
        return new SubgraphHandler<>(description, description);

    }
}
