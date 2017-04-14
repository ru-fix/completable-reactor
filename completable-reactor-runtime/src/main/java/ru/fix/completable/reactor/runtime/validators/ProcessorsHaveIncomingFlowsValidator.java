package ru.fix.completable.reactor.runtime.validators;


import lombok.val;
import ru.fix.completable.reactor.api.ReactorGraphModel;
import ru.fix.completable.reactor.api.ReactorGraphModel.MergeGroup;
import ru.fix.completable.reactor.api.ReactorGraphModel.Processor;
import ru.fix.completable.reactor.api.ReactorGraphModel.Subgraph;
import ru.fix.completable.reactor.api.ReactorGraphModel.Transition;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Kamil Asfandiyarov
 */
public class ProcessorsHaveIncomingFlowsValidator implements GraphValidator {

    @Override
    public void validateGraph(ReactorGraphModel graph) throws ValidationException {

        val processorsWithIncomingTransitions = graph.getMergeGroups().stream()
                .map(MergeGroup::getMergePoints)
                .flatMap(List::stream)
                .flatMap(mergePoint -> mergePoint.getTransitions().stream())
                .map(Transition::getHandleByProcessingItem)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        processorsWithIncomingTransitions.addAll(graph.getStartPoint().getProcessingItems());

        graph.getMergeGroups().stream()
                .map(MergeGroup::getMergePoints)
                .flatMap(List::stream)
                .flatMap(mergePoint -> mergePoint.getTransitions().stream())
                .map(Transition::getHandleByProcessingItem)
                .filter(Objects::nonNull)
                .forEach(processorsWithIncomingTransitions::add);

        val processorWithoutIncomingTransitions = Stream.concat(
                graph.getProcessors().stream().map(Processor::getIdentity),
                graph.getSubgraphs().stream().map(Subgraph::getIdentity))
                .filter(processor -> !processorsWithIncomingTransitions.contains(processor))
                .findAny();

        if (processorWithoutIncomingTransitions.isPresent()) {
            throw new ValidationException(String.format("ProcessingItem %s does not have incoming transitions.",
                    processorWithoutIncomingTransitions.get()));
        }
    }
}
