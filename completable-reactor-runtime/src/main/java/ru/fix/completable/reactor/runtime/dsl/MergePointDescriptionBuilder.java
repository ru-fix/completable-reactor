package ru.fix.completable.reactor.runtime.dsl;

/**
 * @author Kamil Asfandiyarov
 */
public interface MergePointDescriptionBuilder {

    <PayloadType> MergePointMergerBuilder<PayloadType> forPayload(
            Class<PayloadType> payloadType
    );

}
