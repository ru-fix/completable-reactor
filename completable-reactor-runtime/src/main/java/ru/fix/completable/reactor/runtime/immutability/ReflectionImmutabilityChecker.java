package ru.fix.completable.reactor.runtime.immutability;

import java.util.Optional;

/**
 * @author Kamil Asfandiyarov
 */
public class ReflectionImmutabilityChecker implements ImmutabilityChecker {

    static class ReflectionSnapshot implements Snapshot{

    }

    @Override
    public <T> ReflectionSnapshot takeSnapshot(T pojo) {
        return new ReflectionSnapshot();
    }

    @Override
    public <T> Optional<String> diff(Snapshot pojoSnapshot, T pojo) {
        /**
         * Compare versions here
         */
        return Optional.empty();
    }
}
