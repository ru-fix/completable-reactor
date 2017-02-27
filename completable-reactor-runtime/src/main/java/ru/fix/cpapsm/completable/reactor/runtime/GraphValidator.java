package ru.fix.cpapsm.completable.reactor.runtime;

/**
 * @author Kamil Asfandiyarov
 */
public interface GraphValidator {
    default void validateGraph(ReactorGraph<?> graph) throws ValidationException {

    }

    default void validateExecution(ReactorGraph<?> graphExecution) throws ValidationException {

    }
}
