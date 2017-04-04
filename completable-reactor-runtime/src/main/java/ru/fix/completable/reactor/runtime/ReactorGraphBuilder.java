package ru.fix.completable.reactor.runtime;

import lombok.extern.slf4j.Slf4j;
import ru.fix.completable.reactor.api.Reactored;
import ru.fix.completable.reactor.runtime.dsl.*;
import ru.fix.completable.reactor.runtime.internal.CRReactorGraph;
import ru.fix.completable.reactor.runtime.internal.dsl.*;
import ru.fix.completable.reactor.runtime.validators.GraphValidator;
import ru.fix.completable.reactor.runtime.validators.ProcessorsHaveIncomingFlowsValidator;
import ru.fix.completable.reactor.runtime.validators.TerminalVertexExistValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Provides fluent API for building {@link ReactorGraph}
 *
 * @author Kamil Asfandiyarov
 */
@Slf4j
public class ReactorGraphBuilder {

    final List<GraphValidator> graphValidators = new ArrayList<>();

    public ReactorGraphBuilder() {
        graphValidators.add(new TerminalVertexExistValidator());
        graphValidators.add(new ProcessorsHaveIncomingFlowsValidator());
    }

    /**
     * Build ReactorGraph for given payload
     */

    public <PayloadType> PayloadBuilder<PayloadType> payload(Class<PayloadType> payloadClass) {
        CRReactorGraph<PayloadType> graph = new CRReactorGraph<>(payloadClass);
        return new CRPayloadBuilder<>(graph);
    }


    /**
     * Build ProcessorDescription
     */
    public ProcessorDescriptionBuilder processor() {
        return new ProcessorDescriptionBuilder() {
            @Override
            public <PayloadType> ru.fix.completable.reactor.runtime.dsl.HandlerBuilder0<PayloadType> forPayload(Class<PayloadType> payloadType) {
                return null;
            }
        };
    }

    /**
     * Build MergePointDescription
     */
    public MergePointDescriptionBuilder mergePoint() {
        return new MergePointDescriptionBuilder() {
            @Override
            public <PayloadType> MergePointMergerBuilder<PayloadType> forPayload(Class<PayloadType> payloadType) {

                CRMergePointDescription<PayloadType> mergePointDescription = new CRMergePointDescription<>();
                return new CRMergePointMergerBuilder<>(mergePointDescription);
            }
        };
    }

    /**
     * Build SubgraphDescription
     */
    public <SubgraphPayloadType> SubgraphBuilder<SubgraphPayloadType> subgraph(Class<SubgraphPayloadType> subgraphPayload) {
        return new SubgraphBuilder<SubgraphPayloadType>() {
            @Override
            public <PayloadType> SubgraphHandlerBuilder<SubgraphPayloadType, PayloadType> forPayload(Class<PayloadType> payloadType) {

                CRSubgraphDescription<PayloadType> subgraphDescription = new CRSubgraphDescription<>(subgraphPayload);

                subgraphDescription.setSubgraphTitle(subgraphPayload.getSimpleName());

                Optional.ofNullable(subgraphPayload.getAnnotation(Reactored.class))
                        .map(Reactored::value)
                        .ifPresent(subgraphDescription::setSubgraphDoc);

                return new CRSubgraphHandlerBuilder<>(subgraphDescription);
            }
        };
    }
}
