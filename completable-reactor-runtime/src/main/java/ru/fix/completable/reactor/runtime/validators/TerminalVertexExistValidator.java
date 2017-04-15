package ru.fix.completable.reactor.runtime.validators;

import ru.fix.completable.reactor.api.ReactorGraphModel;
import ru.fix.completable.reactor.api.ReactorGraphModel.Transition;

/**
 * @author Kamil Asfandiyarov
 */
public class TerminalVertexExistValidator implements GraphValidator {

    @Override
    public void validateGraph(ReactorGraphModel graph) throws ValidationException {
        if (graph.getMergePoints().stream()
                .flatMap(mergePoint -> mergePoint.getTransitions().stream())
                .noneMatch(Transition::isComplete)) {

            throw new ValidationException("Graph should contain at least one terminal vertex." +
                    " Add complete step to at least one merge point.");
        }
    }
}
