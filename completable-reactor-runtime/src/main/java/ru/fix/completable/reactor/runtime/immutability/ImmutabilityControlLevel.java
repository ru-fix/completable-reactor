package ru.fix.completable.reactor.runtime.immutability;

/**
 * Immutability control uses reflection to copy payload before forwarding it to handler.
 *
 * @author Kamil Asfandiyarov
 */
public enum ImmutabilityControlLevel {
    /**
     * Fastest execution mode.
     * Payload is not copied and no payload modification check is applied.
     */
    NO_CONTROL,
    /**
     * In case payload modification inside processor handler method or outside reactor
     * warning will be logged
     */
    LOG_WARN,
    /**
     * In case payload modification inside processor handler method or outside reactor
     * error will be logged
     */
    LOG_ERROR,
    /**
     * In case payload modification inside processor handler method or outside reactor
     * exception will be raised
     */
    EXCEPTION
}
