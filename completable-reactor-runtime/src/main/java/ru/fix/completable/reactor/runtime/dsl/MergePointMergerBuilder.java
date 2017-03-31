package ru.fix.completable.reactor.runtime.dsl;

/**
 * @author Kamil Asfandiyarov
 */
public interface MergePointMergerBuilder<PayloadType> {

    MergePointDescription<PayloadType> withMerger(
            MergePointMerger<PayloadType> mergePointMerger
    );

    MergePointDescription<PayloadType> withMerger(
            String title, MergePointMerger<PayloadType> mergePointMerger
    );

    MergePointDescription<PayloadType> withMerger(
            String title,
            String[] docs,
            MergePointMerger<PayloadType> mergePointMerger
    );
}