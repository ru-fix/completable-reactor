package ru.fix.cpapsm.completable.reactor.runtime.immutability;

import java.util.Optional;

/**
 * @author Kamil Asfandiyarov
 */
public interface ImmutabilityChecker {
    /**
     * Make deep copy of pojo
     */
    <T> T clone(T pojo);


    interface Snapshot{

    }

    <T> Snapshot takeSnapshot(T firstVersionOfPojo);


    /**
     * @return EMPTY if new version of pojo is equal to previous one. Otherwise return human readable description of difference.
     */
    <T> Optional<String> diff(Snapshot firstVersionOfPojo, T secondVersionOfPojo);
}
