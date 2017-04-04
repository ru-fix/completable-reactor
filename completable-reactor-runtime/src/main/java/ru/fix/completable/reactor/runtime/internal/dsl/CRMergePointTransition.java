package ru.fix.completable.reactor.runtime.internal.dsl;

import lombok.val;
import ru.fix.completable.reactor.runtime.dsl.*;
import ru.fix.completable.reactor.runtime.internal.CRProcessingItem;
import ru.fix.completable.reactor.runtime.internal.CRReactorGraph;
import ru.fix.completable.reactor.runtime.internal.ReactorReflector;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author Kamil Asfandiyarov
 */
public class CRMergePointTransition<PayloadType> implements MergePointTransition<PayloadType> {

    final BuilderContext<PayloadType> builderContext;
    final CRReactorGraph<PayloadType> graph;
    final CRReactorGraph.MergePoint mergePoint;
    final Supplier<CRReactorGraph.Transition> transitionSupplier;

    public CRMergePointTransition(
            BuilderContext<PayloadType> builderContext,
            CRReactorGraph.MergePoint mergePoint,
            Supplier<CRReactorGraph.Transition> transitionSupplier) {
        this.builderContext = builderContext;
        this.graph = builderContext.graph;
        this.mergePoint = mergePoint;
        this.transitionSupplier = transitionSupplier;
    }

    @Override
    public MergePointBuilder<PayloadType> complete() {

        if (this.mergePoint.getTransitions().stream().anyMatch(CRReactorGraph.Transition::isComplete)) {
            switch (mergePoint.getType()) {
                case DETACHED:
                    throw new IllegalArgumentException(String.format(
                            "Complete transition already present in merge point for processor %s. " +
                                    "Join condition into single complete transition.",
                            mergePoint.getProcessor().getDebugName()));
                case PROCESSOR:
                    throw new IllegalArgumentException(String.format(
                            "Complete transition already present in merge point %s. " +
                                    "Join condition into single complete transition.",
                            mergePoint.getMergePoint().getDebugName()));
                default:
                    throw new IllegalArgumentException(String.format("Invalid merge point type: %s", mergePoint.getType()));
            }
        }

        this.mergePoint.getTransitions().add(
                transitionSupplier.get()
                        .setComplete(true).setCompleteSource(ReactorReflector.getMethodInvocationPoint().orElse(null)));

        return new CRMergePointBuilder<>(builderContext, this.mergePoint);
    }

    @Override
    public MergePointBuilder<PayloadType> handleBy(Processor<? super PayloadType> processor) {
        CRProcessor<?> crProcessor = (CRProcessor<?>) processor;
        graph.ensureProcessingItemRegistered(crProcessor);

        val newTransition = transitionSupplier.get()
                .setHandleBy(crProcessor);

        checkMergeOrAddNewTransition(newTransition, this.mergePoint, TransitionType.HANDLE);

        return new CRMergePointBuilder<>(builderContext, this.mergePoint);
    }

    @Override
    public MergePointBuilder<PayloadType> handleBy(Subgraph<? super PayloadType> subgraph) {
        CRSubgraph<?> crSubgraph = (CRSubgraph<?>) subgraph;
        graph.ensureProcessingItemRegistered(crSubgraph);

        val newTransition = transitionSupplier.get()
                .setHandleBy(crSubgraph);

        checkMergeOrAddNewTransition(newTransition, this.mergePoint, TransitionType.HANDLE);

        return new CRMergePointBuilder<>(builderContext, this.mergePoint);
    }

    @Override
    public MergePointBuilder<PayloadType> merge(Processor<? super PayloadType> processor) {
        CRProcessor<?> crProcessor = (CRProcessor<?>) processor;
        graph.ensureProcessingItemRegistered(crProcessor);

        val newTransition = transitionSupplier.get()
                .setMerge(crProcessor);

        checkMergeOrAddNewTransition(newTransition, this.mergePoint, TransitionType.MERGE);

        return new CRMergePointBuilder<>(builderContext, this.mergePoint);
    }

    @Override
    public MergePointBuilder<PayloadType> merge(Subgraph<? super PayloadType> subgraph) {
        CRSubgraph<?> crSubgraph = (CRSubgraph<?>) subgraph;
        graph.ensureProcessingItemRegistered(crSubgraph);

        val newTransition = transitionSupplier.get()
                .setMerge(crSubgraph);

        checkMergeOrAddNewTransition(newTransition, this.mergePoint, TransitionType.MERGE);

        return new CRMergePointBuilder<>(builderContext, this.mergePoint);
    }

    @Override
    public MergePointBuilder<PayloadType> merge(MergePoint<? super PayloadType> mergePoint) {
        CRMergePoint<?> crMergePoint = (CRMergePoint<?>) mergePoint;
        graph.ensureProcessingItemRegistered(crMergePoint);

        val newTransition = transitionSupplier.get()
                .setMerge(crMergePoint);

        checkMergeOrAddNewTransition(newTransition, this.mergePoint, TransitionType.MERGE);

        return new CRMergePointBuilder<>(builderContext, this.mergePoint);
    }

    enum TransitionType {
        HANDLE, MERGE
    }

    /**
     * Check if transition between mergePoint and given processor already exist.
     * Check if new transition is redundant.
     * Try to merge new transition into existing one.
     * If transition does not exist add new transition to mergePoint.
     */
    void checkMergeOrAddNewTransition(
            CRReactorGraph.Transition newTransition,
            CRReactorGraph.MergePoint mergePoint,
            TransitionType transitionType) {

        Function<CRReactorGraph.Transition, CRProcessingItem> processorAccessor =
                transitionType == TransitionType.HANDLE ?
                        CRReactorGraph.Transition::getHandleBy :
                        CRReactorGraph.Transition::getMerge;

        CRProcessingItem targetProcessingItem = processorAccessor.apply(newTransition);

        List<CRReactorGraph.Transition> existingTransitions = this.mergePoint.getTransitions().stream()
                .filter(existingTransition -> processorAccessor.apply(existingTransition) != null)
                .filter(existingTransition -> targetProcessingItem.equals(processorAccessor.apply(existingTransition)))
                .collect(Collectors.toList());

        if (existingTransitions.size() > 0) {
            if (existingTransitions.size() > 1) {
                throw new IllegalArgumentException(String.format(
                        "More that one transition exit from merge point %s to processing item %s." +
                                " Current transitions: %s. New transition: %s",
                        mergePoint.asProcessingItem().getDebugName(),
                        targetProcessingItem.getDebugName(),
                        existingTransitions.stream().map(CRReactorGraph.Transition::toString).collect(Collectors.joining(",")),
                        newTransition
                ));
            }

            CRReactorGraph.Transition existingTransition = existingTransitions.get(0);
            if (existingTransition.isOnAny()) {
                throw new IllegalArgumentException(String.format(
                        "Unconditional transition from merge point %s to processing item %s already exist." +
                                " Conditional transition is redundant. Existing transition: %s. New transition: %s",
                        mergePoint.asProcessingItem().getDebugName(),
                        targetProcessingItem.getDebugName(),
                        existingTransition,
                        newTransition));
            }

            if (existingTransition.getMergeStatuses().containsAll(newTransition.getMergeStatuses())) {
                throw new IllegalArgumentException(String.format(
                        "Transition that suite given condition already exist in route from merge point %s to processing item %s." +
                                " Existing transition: %s. New transition: %s" +
                                " Conditional transition is redundant.",
                        mergePoint.asProcessingItem().getDebugName(),
                        targetProcessingItem.getDebugName(),
                        existingTransition,
                        newTransition));
            }
            existingTransition.getMergeStatuses().addAll(newTransition.getMergeStatuses());
        } else {
            this.mergePoint.getTransitions().add(newTransition);
        }
    }
}
