package ru.fix.completable.reactor.runtime.dsl;

import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public interface SubgraphHandlerBuilder<SubgraphPayloadType, PayloadType> {

    SubgraphMergerBuilder<PayloadType, SubgraphPayloadType> passArg(Function<PayloadType, SubgraphPayloadType> arg);

    SubgraphMergerBuilder<PayloadType, SubgraphPayloadType> copyArg(Function<PayloadType, SubgraphPayloadType> arg);
}
