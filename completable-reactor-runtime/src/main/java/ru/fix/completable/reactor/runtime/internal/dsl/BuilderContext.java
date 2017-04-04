package ru.fix.completable.reactor.runtime.internal.dsl;

import lombok.Data;
import ru.fix.completable.reactor.runtime.internal.CRReactorGraph;
import ru.fix.completable.reactor.runtime.validators.GraphValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Kamil Asfandiyarov
 */
@Data
public class BuilderContext<PayloadType> {
    CRReactorGraph<PayloadType> graph;
    final List<GraphValidator> graphValidators = new ArrayList<>();
}
