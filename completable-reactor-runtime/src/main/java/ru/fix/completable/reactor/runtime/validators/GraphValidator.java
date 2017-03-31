package ru.fix.completable.reactor.runtime.validators;

import ru.fix.completable.reactor.runtime.ReactorGraph;

/**
 * @author Kamil Asfandiyarov
 */
public interface GraphValidator {
    default void validateGraph(ReactorGraph<?> graph) throws ValidationException {

    }

    default void validateExecution(ReactorGraph<?> graphExecution) throws ValidationException {

    }
}
