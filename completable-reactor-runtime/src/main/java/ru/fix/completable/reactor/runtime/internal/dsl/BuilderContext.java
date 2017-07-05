package ru.fix.completable.reactor.runtime.internal.dsl;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import ru.fix.completable.reactor.runtime.dsl.MergePoint;
import ru.fix.completable.reactor.runtime.dsl.Processor;
import ru.fix.completable.reactor.runtime.dsl.Subgraph;
import ru.fix.completable.reactor.runtime.internal.CRReactorGraph;
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
    Object graphConfig;

    CRReactorGraph<PayloadType> graph;

    Map<Object, String> graphConfigFields;

    final List<Class> graphItems = Arrays.asList(Processor.class, Subgraph.class, MergePoint.class);

    public String resolveConfigField(Object configField) {
        if (graphConfigFields == null) {
            graphConfigFields = new HashMap<>();

            for (Class clazz = graphConfig.getClass(); clazz != null; clazz = clazz.getSuperclass()) {

                Arrays.stream(clazz.getDeclaredFields())
                        .filter(field ->
                                graphItems.stream().anyMatch(graphItemClass -> graphItemClass.isAssignableFrom(field
                                        .getDeclaringClass())))
                        .forEach(field -> {
                            try {
                                Object value = field.get(graphConfig);
                                graphConfigFields.put(value, field.getName());
                            } catch (Exception exc) {
                                log.warn(String.format(
                                        "Failed to access field %s of class %s in object %s",
                                        field,
                                        configField.getClass().getName(),
                                        configField));
                            }
                        });
            }
        }

        String name = graphConfigFields.get(configField);
        if (name == null) {
            throw new IllegalArgumentException(String.format("" +
                    "Failed to resolve field within class %s that reference given object %s" +
                    "Than could happen if you are passing local variable instead of class field to reactor builder.",
                    graphConfig.getClass(),
                    configField));
        }
        return name;
    }
}
