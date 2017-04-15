package ru.fix.completable.reactor.runtime.internal.dsl;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import ru.fix.completable.reactor.api.Reactored;
import ru.fix.completable.reactor.runtime.ReactorGraph;
import ru.fix.completable.reactor.runtime.internal.CRReactorGraph;
import ru.fix.completable.reactor.runtime.internal.ReactorReflector;
import ru.fix.completable.reactor.runtime.validators.GraphValidator;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Kamil Asfandiyarov
 */
@Slf4j
public class CRGraphBuilder<PayloadType> {
    final BuilderContext<PayloadType> builderContext;
    final CRReactorGraph<PayloadType> graph;

    public CRGraphBuilder(BuilderContext<PayloadType> builderContext) {
        this.builderContext = builderContext;
        this.graph = builderContext.graph;
    }

    public ReactorGraph<PayloadType> buildGraph() {

        this.graph.setBuildGraphSource(ReactorReflector.getMethodInvocationPoint().orElse(null));
        /**
         * Generate explicit MergeGroups from linked MergePoints to make life easier for ReactorGraphExecutionBuilder.
         * MergeGroup based of StartPoint and detached MergePoint will stay implicit and ReactorGraphExecutionBuilder
         * will take care of that
         */
        val groupedMergePoints = new HashMap<CRReactorGraph.MergePoint, CRReactorGraph.MergeGroup>();
        graph.getMergePoints().forEach(mergePoint -> {
            if (!groupedMergePoints.containsKey(mergePoint)) {

                val mergeGroup = new CRReactorGraph.MergeGroup(mergePoint);
                groupedMergePoints.put(mergePoint, mergeGroup);

                mergePoint.getTransitions().stream()
                        .filter(transition -> transition.getMerge() != null)
                        .map(CRReactorGraph.Transition::getMerge)
                        .map(item -> graph.getMergePoints().stream()
                                .filter(point -> point.asProcessingItem().equals(item))
                                .findAny()
                                .orElseThrow(() -> new IllegalStateException(
                                        String.format("MergePoint does not registered for processing item %s",
                                                item.getDebugName()))))
                        .forEach(linkedMergePoint -> {
                            if (!groupedMergePoints.containsKey(linkedMergePoint)) {
                                /**
                                 * Linked merge point not in the group.
                                 */
                                mergeGroup.getMergePoints().add(linkedMergePoint);
                            } else {
                                /**
                                 * Linked merge point already in the group.
                                 * Merge it's group to this one.
                                 */
                                val linkedGroup = groupedMergePoints.get(linkedMergePoint);
                                if (linkedGroup != mergeGroup) {
                                    linkedGroup.getMergePoints().forEach(point -> {
                                        mergeGroup.getMergePoints().add(point);
                                        groupedMergePoints.put(point, mergeGroup);
                                    });
                                }
                            }
                        });
            }
        });
        /**
         * If there are several mergePoints that linked directly with start point, we have to join theirs group together
         * Start point in terms of mergeGroup joining process works like a mergePoint.
         */
        CRReactorGraph.MergeGroup mergeGroupLinkedWithStartPoint = null;
        for (Iterator<CRMergePoint> iter = this.graph.getStartPoint().getProcessingItems().stream()
                .filter(item -> item instanceof CRMergePoint)
                .map(item -> (CRMergePoint) item)
                .iterator(); iter.hasNext(); ) {

            val point = iter.next();
            val group = groupedMergePoints.get(point);

            if (mergeGroupLinkedWithStartPoint == null) {
                mergeGroupLinkedWithStartPoint = group;
            } else {
                if (group != mergeGroupLinkedWithStartPoint) {
                    for (val pointInGroup : group.getMergePoints()) {
                        mergeGroupLinkedWithStartPoint.getMergePoints().add(pointInGroup);
                        groupedMergePoints.put(pointInGroup, mergeGroupLinkedWithStartPoint);
                    }
                }
            }
        }
        /**
         * If mergeGroupLinkedWithStartPoint contains only detached merge points when we should include
         * start point itself to this merge group.
         */
        if (mergeGroupLinkedWithStartPoint != null) {
            if (mergeGroupLinkedWithStartPoint.getMergePoints().stream()
                    .allMatch(mergePoint -> mergePoint.getType() != CRReactorGraph.MergePoint.Type.DETACHED)) {
                graph.setStartPointMergeGroup(Optional.of(mergeGroupLinkedWithStartPoint));
            } else {
                graph.setStartPointMergeGroup(Optional.empty());
            }
        }

        /**
         * Remove redundant merge groups that contain only single merge point
         * If merge group contains start point - leave it as is, even if it includes single merge point.
         */
        this.graph.getMergeGroups().removeIf(mergeGroup -> {
            if (graph.getStartPointMergeGroup().isPresent()) {
                return graph.getStartPointMergeGroup().get() != mergeGroup && mergeGroup.getMergePoints().size() <= 1;
            } else {
                return mergeGroup.getMergePoints().size() <= 1;
            }
        });

        /**
         * Populate transitions documentation
         */
        graph.getMergePoints().stream()
                .map(CRReactorGraph.MergePoint::getTransitions)
                .flatMap(List::stream)
                .forEach(transition -> transition.setTransitionsDoc(lookupTransitionDocumentation(transition)));

        /**
         * Validate graph
         */
        builderContext.getGraphValidators().forEach(validator -> validator.validateGraph(graph.serialize()));

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
    Map<String, String[]> lookupTransitionDocumentation(CRReactorGraph.Transition transition) {
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
                                .map(field -> field.getAnnotation(Reactored.class))
                                .map(Reactored::value)
                                .orElse(null));
            });

            return transitionsDoc;
        } else {
            return null;
        }
    }
}
