package ru.fix.completable.reactor.runtime.internal.dsl;

import ru.fix.completable.reactor.runtime.dsl.SubgraphHandlerBuilder;
import ru.fix.completable.reactor.runtime.dsl.SubgraphMergerBuilder;

import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public class CRSubgraphHandlerBuilder<SubgraphPayloadType, PayloadType> implements SubgraphHandlerBuilder<SubgraphPayloadType, PayloadType> {
    final CRSubgraphDescription<PayloadType> subgraphDescription;

    public CRSubgraphHandlerBuilder(CRSubgraphDescription<PayloadType> subgraphDescription) {
        this.subgraphDescription = subgraphDescription;
    }

    @Override
    public SubgraphMergerBuilder<PayloadType, SubgraphPayloadType> passArg(Function<PayloadType, SubgraphPayloadType> arg) {
        this.subgraphDescription.arg = arg;
        return new CRSubgraphMergerBuilder<>(subgraphDescription);
    }

    @Override
    public SubgraphMergerBuilder<PayloadType, SubgraphPayloadType> copyArg(Function<PayloadType, SubgraphPayloadType> arg) {
        this.subgraphDescription.arg = arg;
        this.subgraphDescription.isCopyArg = true;

        return new CRSubgraphMergerBuilder<>(subgraphDescription);
    }
}
