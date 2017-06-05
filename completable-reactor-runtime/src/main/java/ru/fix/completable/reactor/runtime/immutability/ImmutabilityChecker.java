package ru.fix.completable.reactor.runtime.immutability;

import java.util.Optional;

/**
 * @author Kamil Asfandiyarov
 */
public interface ImmutabilityChecker {

    /**
     * Snapshot of given object state.
     * Will be used to detect whether object was modified or not.
     */
    interface Snapshot{
    }

    /**
     * Reads object state and save it to Snapshot
     */
    <T> Snapshot takeSnapshot(T pojo);


    /**
     * Compares pojo with give snapshot.
     * @return EMPTY if new version of pojo is equal to previous one. Otherwise return human readable description of difference.
     */
    <T> Optional<String> diff(Snapshot pojoSnapshot, T pojo);
}
