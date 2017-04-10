package ru.fix.completable.reactor.runtime.validators;

import ru.fix.completable.reactor.api.ReactorGraphModel;

/**
 * @author Kamil Asfandiyarov
 */
public interface GraphValidator {
    void validateGraph(ReactorGraphModel graph) throws ValidationException;
}
