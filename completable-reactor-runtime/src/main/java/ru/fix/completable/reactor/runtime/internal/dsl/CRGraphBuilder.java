package ru.fix.completable.reactor.runtime.internal.dsl;

import lombok.extern.slf4j.Slf4j;
import ru.fix.completable.reactor.api.Reactored;
import ru.fix.completable.reactor.runtime.ReactorGraph;
import ru.fix.completable.reactor.runtime.internal.CRReactorGraph;
import ru.fix.completable.reactor.runtime.internal.ReactorReflector;
import ru.fix.completable.reactor.runtime.validators.GraphValidator;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
         * Populate transitions documentation
         */
        graph.getMergePoints().stream()
                .map(CRReactorGraph.MergePoint::getTransitions)
                .flatMap(List::stream)
                .forEach(transition -> transition.setTransitionsDoc(lookupTransitionDocumentation(transition)));

        /**
         * Validate graph
         */
        builderContext.getGraphValidators().forEach(validator -> {
            validator.validateGraph(graph.serialize());
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
