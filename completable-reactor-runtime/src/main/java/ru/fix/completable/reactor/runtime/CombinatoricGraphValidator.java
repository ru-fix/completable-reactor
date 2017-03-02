package ru.fix.completable.reactor.runtime;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Kamil Asfandiyarov
 */
public class CombinatoricGraphValidator {

    @Data
    @Accessors(chain = true)
    static class ConditionalMergeVertex {
        final ProcessingGraphItem processor;
        final Map<ConditionalMergeVertex, List<ReactorGraph.Transition>> incomingTransitions = new HashMap<>();
        boolean reached;
    }

    @Data
    static class UnconditionalMergeVertex {
        final Object processor;
        final List<UnconditionalMergeVertex> incomingTransitions = new ArrayList<>();
        boolean reached;
    }

    final ReactorGraph<?> graph;

    public CombinatoricGraphValidator(ReactorGraph<?> graph) {
        this.graph = graph;
    }

    void validateNoConcurrentMergePoints() {

        /**
         * Build graph that contains only merge points and their connections
         */
        List<ConditionalMergeVertex> conditionalMergeGraph = buildConditionalMergeGraph();

        /**
         * Go through all possible combination of merge statuses on each of merge points
         * that could happen during runtime.
         * Check that there is no concurrent merge point exist for each of combination.
         */
        processUnconditionalMergeGraphCombinations(conditionalMergeGraph, combination -> {

            List<UnconditionalMergeVertex> vertices;
            do {
                /**
                 * Get merge points that could be executed:
                 */
                vertices = combination.stream()
                        .filter(vertex -> !vertex.isReached())
                        .filter(vertex -> vertex.getIncomingTransitions().stream().allMatch(UnconditionalMergeVertex::isReached))
                        .collect(Collectors.toList());
                /**
                 * Check that there is no contention between multiple vertices (only single vertex is reached)
                 */
                if (vertices.size() > 1) {
                    throw new IllegalStateException(String.format("Concurrent merge points is forbidden. " +
                                    "Possible concurrent modification of payload by merge points: %s",
                            vertices.stream()
                                    .map(UnconditionalMergeVertex::getProcessor)
                                    .map(Object::getClass)
                                    .map(Class::toString)
                                    .collect(Collectors.joining(","))));
                } else if (vertices.size() == 1) {
                    /**
                     * Reach out next portion of vertices
                     */
                    vertices.get(0).setReached(true);
                }
            } while (vertices.size() > 0);
        });
    }

    List<ConditionalMergeVertex> buildConditionalMergeGraph() {

        Map<ProcessingGraphItem, ConditionalMergeVertex> vertices = new HashMap<>();

        /**
         * Create unconditional vertex from start point.
         * Mark it as reached.
         */
        final ConditionalMergeVertex startPointVertex = new ConditionalMergeVertex(null).setReached(true);

        graph.getMergeGroups().stream().map(ReactorGraph.MergeGroup::getMergePoints).flatMap(List::stream).forEach(mergePoint -> {
            ConditionalMergeVertex vertex = new ConditionalMergeVertex(mergePoint.getProcessor());
            vertices.put(vertex.processor, vertex);
        });
        // populate incoming transitions and then

        /**
         * Add merge points connections
         */
        graph.getMergeGroups().stream().map(ReactorGraph.MergeGroup::getMergePoints).flatMap(List::stream).forEach(mergePoint -> {
            ConditionalMergeVertex source = vertices.get(mergePoint.processor);
            mergePoint.transitions.forEach(transition -> {
                if (transition.handleBy != null) {
                    vertices.get(transition.handleBy)
                            .getIncomingTransitions()
                            .computeIfAbsent(source, key -> new ArrayList<>())
                            .add(transition);
                }
                if (transition.merge != null) {
                    vertices.get(transition.merge)
                            .getIncomingTransitions()
                            .computeIfAbsent(source, key -> new ArrayList<>())
                            .add(transition);

                }
            });
        });

        List<ConditionalMergeVertex> result = new ArrayList<>();
        result.add(startPointVertex);
        vertices.values().forEach(result::add);
        return result;
    }

    enum TransitionType {
        ANY
    }

    void processUnconditionalMergeGraphCombinations(List<ConditionalMergeVertex> vertices, Consumer<List<UnconditionalMergeVertex>> consumer) {
        List<List<Enum>> possibleStatuses = new ArrayList<>(vertices.size());
        for (int index = 0; index < vertices.size(); index++) {
            possibleStatuses.add(index,
                    Stream.concat(
                            vertices.get(index)
                                    .getIncomingTransitions()
                                    .values()
                                    .stream()
                                    .flatMap(Collection::stream)
                                    .map(ReactorGraph.Transition::getMergeStatuses)
                                    .filter(Objects::nonNull)
                                    .flatMap(Set::stream),
                            Stream.of(TransitionType.ANY))
                            .collect(Collectors.toList()));
        }

        List<Integer> state = new ArrayList<>(vertices.size());
        for (int index = 0; index < vertices.size(); index++) {
            state.add(index, 0);
        }

        while (true) {
            /**
             * build combination that represent given state
             */
            List<UnconditionalMergeVertex> combination = new ArrayList<>(vertices.size());
            //Build  conditions and iterate throug possible variants..
            consumer.accept(combination);
        }
    }
}
