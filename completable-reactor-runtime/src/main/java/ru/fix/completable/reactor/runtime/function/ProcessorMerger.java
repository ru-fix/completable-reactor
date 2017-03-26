package ru.fix.completable.reactor.runtime.function;

import java.io.Serializable;

/**
 * @author Kamil Asfandiyarov
 */
@FunctionalInterface
public interface ProcessorMerger<Payload, HandlerResult> extends Serializable {
    Enum merge(Payload payload, HandlerResult handlerResult);
}
