package ru.fix.cpapsm.completable.reactor.runtime.immutability;

import com.rits.cloning.Cloner;

import java.util.Optional;

/**
 * @author Kamil Asfandiyarov
 */
public class ClonerWithReflectionImmutabilityChecker implements ImmutabilityChecker {
    final Cloner cloner = new Cloner();

    @Override
    public <T> T clone(T pojo) {
        /**
         * cloner is thread-safe
         */
        return cloner.deepClone(pojo);
    }

    static class ReflectionSnapshot implements Snapshot{

    }

    @Override
    public <T> ReflectionSnapshot takeSnapshot(T firstVersionOfPojo) {
        return new ReflectionSnapshot();
    }

    @Override
    public <T> Optional<String> diff(Snapshot firstVersionOfPojo, T secondVersionOfPojo) {
        /**
         * Compare versions here
         */
        return Optional.empty();
    }
}
