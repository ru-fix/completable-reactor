package ru.fix.completable.reactor.runtime.validators;


import ru.fix.completable.reactor.runtime.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Kamil Asfandiyarov
 */
public class ProcessorsHaveIncomingFlowsValidator implements GraphValidator {
    @Override
    public void validateGraph(ReactorGraph<?> graph) throws ValidationException {
        Set<ProcessingGraphItem> processorsWithIncomingTransitions = graph.getMergeGroups().stream()
                .map(ReactorGraph.MergeGroup::getMergePoints)
                .flatMap(List::stream)
                .flatMap(mergePoint -> mergePoint.getTransitions().stream())
                .map(ReactorGraph.Transition::getHandleBy)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        processorsWithIncomingTransitions.addAll(graph.getStartPoint().getProcessors());

        graph.getMergeGroups().stream()
                .map(ReactorGraph.MergeGroup::getMergePoints)
                .flatMap(List::stream)
                .flatMap(mergePoint -> mergePoint.getTransitions().stream())
                .map(ReactorGraph.Transition::getMerge)
                .filter(Objects::nonNull)
                .filter(processingItem -> processingItem instanceof GraphMergePoint<?>)
                .forEach(processorsWithIncomingTransitions::add);

        Optional<ProcessingGraphItem> processorWithoutIncomingTransitions = graph.getProcessors().keySet().stream()
                .filter(processor -> !processorsWithIncomingTransitions.contains(processor))
                .findAny();

        if (processorWithoutIncomingTransitions.isPresent()) {
            throw new ValidationException(String.format("Processor %s does not have incoming transitions.",
                    processorWithoutIncomingTransitions.get()));
        }
    }
}
