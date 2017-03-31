package ru.fix.completable.reactor.runtime.validators;

import ru.fix.completable.reactor.runtime.ReactorGraph;

import java.util.List;

/**
 * @author Kamil Asfandiyarov
 */
public class TerminalVertexExistValidator implements GraphValidator {

    @Override
    public void validateGraph(ReactorGraph<?> graph) throws ValidationException {
        if (graph.getMergeGroups().stream()
                .map(ReactorGraph.MergeGroup::getMergePoints)
                .flatMap(List::stream)
                .flatMap(mergePoint -> mergePoint.getTransitions().stream())
                .noneMatch(ReactorGraph.Transition::isComplete)) {

            throw new ValidationException("Graph should contain at least one terminal vertex." +
                    " Add complete step to at least one merge point.");
        }
    }
}
