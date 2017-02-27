package ru.fix.cpapsm.completable.reactor.runtime;

/**
 * @author Kamil Asfandiyarov
 */
public class PendingOperationsLimitOverflowException extends Exception {
    public PendingOperationsLimitOverflowException(String message) {
        super(message);
    }
}
