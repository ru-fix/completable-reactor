package ru.fix.completable.reactor.runtime.internal.dsl;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import ru.fix.completable.reactor.runtime.dsl.MergePoint;
import ru.fix.completable.reactor.runtime.dsl.Processor;
import ru.fix.completable.reactor.runtime.dsl.Subgraph;
import ru.fix.completable.reactor.runtime.internal.CRReactorGraph;
import ru.fix.completable.reactor.runtime.internal.gl.ConfigFieldNameResolver;
import ru.fix.completable.reactor.runtime.validators.GraphValidator;

import java.util.*;

/**
 * @author Kamil Asfandiyarov
 */
@Slf4j
@Data
@Accessors(chain = true)
public class BuilderContext<PayloadType> {
    final List<GraphValidator> graphValidators = new ArrayList<>();
    final Object graphConfig;
    final CRReactorGraph<PayloadType> graph;
    final ConfigFieldNameResolver configFieldNameResolver;

    public BuilderContext(Object graphConfig, CRReactorGraph<PayloadType> graph) {
        this.graphConfig = graphConfig;
        this.graph = graph;
        configFieldNameResolver = new ConfigFieldNameResolver(
                graphConfig,
                Arrays.asList(Processor.class, Subgraph.class, MergePoint.class));
    }

    public String resolveConfigField(Object configField) {
        return configFieldNameResolver.resolveFieldName(configField);
    }
}
