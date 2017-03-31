package ru.fix.completable.reactor.runtime.validators;

/**
 * @author Kamil Asfandiyarov
 */
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}
