package ru.fix.completable.reactor.runtime;

/**
 * @author Kamil Asfandiyarov
 */
public class PendingOperationsLimitOverflowException extends Exception {
    public PendingOperationsLimitOverflowException(String message) {
        super(message);
    }
}
