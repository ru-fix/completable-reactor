package ru.fix.completable.reactor.runtime;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import ru.fix.completable.reactor.api.Reactored;
import ru.fix.completable.reactor.runtime.dsl.*;
import ru.fix.completable.reactor.runtime.internal.CRReactorGraph;
import ru.fix.completable.reactor.runtime.internal.ReactorReflector;
import ru.fix.completable.reactor.runtime.internal.dsl.*;
import ru.fix.completable.reactor.runtime.validators.GraphValidator;
import ru.fix.completable.reactor.runtime.validators.ProcessorsHaveIncomingFlowsValidator;
import ru.fix.completable.reactor.runtime.validators.TerminalVertexExistValidator;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        val builderContext = new BuilderContext<PayloadType>();
        builderContext.setGraph(new CRReactorGraph<>(payloadClass));
        builderContext.getGraphValidators().addAll(graphValidators);

        builderContext.getGraph().getStartPoint().setBuilderPayloadSource(
                ReactorReflector.getMethodInvocationPoint().orElse(null));

        return new CRPayloadBuilder<>(builderContext);
    }


    /**
     * Build ProcessorDescription
     */
    public ProcessorDescriptionBuilder processor() {
        return new ProcessorDescriptionBuilder() {
            @Override
            public <PayloadType> ru.fix.completable.reactor.runtime.dsl.HandlerBuilder0<PayloadType> forPayload(
                    Class<PayloadType> payloadType) {

                val processorDescription = new CRProcessorDescription<PayloadType>();
                return new CRHandlerBuilder0<>(processorDescription);
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
    public <SubgraphPayloadType> SubgraphBuilder<SubgraphPayloadType> subgraph(
            Class<SubgraphPayloadType> subgraphPayload) {

        return new SubgraphBuilder<SubgraphPayloadType>() {
            @Override
            public <PayloadType> SubgraphHandlerBuilder<SubgraphPayloadType, PayloadType> forPayload(
                    Class<PayloadType> payloadType) {

                CRSubgraphDescription<PayloadType> subgraphDescription = new CRSubgraphDescription<>(subgraphPayload);
                subgraphDescription.setBuildSource(ReactorReflector.getMethodInvocationPoint().orElse(null));

                subgraphDescription.setSubgraphTitle(subgraphPayload.getSimpleName());

                Optional.ofNullable(subgraphPayload.getAnnotation(Reactored.class))
                        .map(Reactored::value)
                        .ifPresent(subgraphDescription::setSubgraphDoc);

                return new CRSubgraphHandlerBuilder<>(subgraphDescription);
            }
        };
    }


    /**
     * Write *.rg file representation of graph structure
     * *.rg files used by IDE and Graph Viewer to visualize graph
     *
     * @param graphs
     * @throws Exception
     */
    public static void write(ReactorGraph<?>... graphs) throws Exception {
        for (ReactorGraph<?> graph : graphs) {

            val model = graph.serialize();
            model.serializationPointSource = ReactorReflector.getMethodInvocationPoint().orElse(null);

            val content = ReactorMarshaller.marshall(model);

            val path = Paths.get(((CRReactorGraph) graph).getPayloadClass().getName() + ".rg");
            Files.write(path, content.getBytes(StandardCharsets.UTF_8));
        }
    }
}
