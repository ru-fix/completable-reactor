package ru.fix.completable.reactor.runtime.dsl;

import java.io.Serializable;

/**
 * @author Kamil Asfandiyarov
 */
@FunctionalInterface
public interface MergePointMerger<Payload> extends Serializable {
    Enum merge(Payload payload);
}