package ru.fix.completable.reactor.runtime.dsl;

import java.io.Serializable;

/**
 * @author Kamil Asfandiyarov
 */
@FunctionalInterface
public interface SubgraphMerger<Payload, SubgraphResult> extends Serializable {
    Enum merge(Payload payload, SubgraphResult subgraphResult);
}
