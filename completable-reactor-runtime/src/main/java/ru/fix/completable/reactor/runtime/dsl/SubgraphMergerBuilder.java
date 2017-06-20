package ru.fix.completable.reactor.runtime.dsl;

/**
 * @author Kamil Asfandiyarov
 */
public interface SubgraphMergerBuilder<PayloadType, SubgraphResult> {

    SubgraphDescription<PayloadType> withMerger(SubgraphMerger<PayloadType, SubgraphResult> subgraphMerger);

    SubgraphDescription<PayloadType> withMerger(String title,
                                                SubgraphMerger<PayloadType, SubgraphResult> subgraphMerger);

    SubgraphDescription<PayloadType> withMerger(String title,
                                                String doc,
                                                SubgraphMerger<PayloadType, SubgraphResult> subgraphMerger);

    SubgraphDescription<PayloadType> withMerger(String title,
                                                String[] docs,
                                                SubgraphMerger<PayloadType, SubgraphResult> subgraphMerger);

    SubgraphDescription<PayloadType> withoutMerger();
}
