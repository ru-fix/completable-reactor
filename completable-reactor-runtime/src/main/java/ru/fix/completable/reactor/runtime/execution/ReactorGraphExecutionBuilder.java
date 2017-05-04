package ru.fix.completable.reactor.runtime.execution;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import ru.fix.commons.profiler.ProfiledCall;
import ru.fix.commons.profiler.Profiler;
import ru.fix.completable.reactor.api.ReactorGraphModel;
import ru.fix.completable.reactor.runtime.ProfilerNames;
import ru.fix.completable.reactor.runtime.ReactorGraph;
import ru.fix.completable.reactor.runtime.cloning.ThreadsafeCopyMaker;
import ru.fix.completable.reactor.runtime.debug.DebugSerializer;
import ru.fix.completable.reactor.runtime.immutability.ImmutabilityChecker;
import ru.fix.completable.reactor.runtime.immutability.ImmutabilityControlLevel;
import ru.fix.completable.reactor.runtime.internal.CRProcessingItem;
import ru.fix.completable.reactor.runtime.internal.CRReactorGraph;
import ru.fix.completable.reactor.runtime.internal.dsl.CRProcessorDescription;
import ru.fix.completable.reactor.runtime.tracing.Tracer;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;


@Slf4j
public class ReactorGraphExecutionBuilder {

    private final Profiler profiler;
    private final ImmutabilityChecker immutabilityChecker;
    private final ThreadsafeCopyMaker threadsafeCopyMaker;
    private final DebugSerializer debugSerializer;
    private final Tracer tracer;

    private boolean debugProcessingVertexGraphState = false;

    volatile ImmutabilityControlLevel immutabilityControlLevel = ImmutabilityControlLevel.NO_CONTROL;


    private final SubgraphRunner subgraphRunner;

    @FunctionalInterface
    public interface SubgraphRunner {
        CompletableFuture<?> run(Object paylaod);
    }


    @Data
    @Accessors(chain = true)
    static class MergePayloadContext {
        Object payload;
        /**
         * Transition marked as dead when merge status does not match transition condition
         */
        boolean isDeadTransition = false;
        /**
         * Terminal graph state reached.
         * No further merging (or payload modification) is allowed.
         * All transitions except copy-transition for detached processors is allowed.
         */
        boolean isTerminal = false;
        Enum<?> mergeResult;
    }

    private static final MergePayloadContext INVALID_MERGE_PAYLOAD_CONTEXT = new MergePayloadContext();

    @Data
    @Accessors(chain = true)
    static class TransitionPayloadContext {
        Object payload;
        /**
         * Transition marked as dead when merge status does not match transition condition
         */
        boolean isDeadTransition = false;
        /**
         * Terminal graph state reached.
         * No further merging (or payload modification) is allowed.
         * All transitions except copy-transition for detached processors is allowed.
         */
        boolean isTerminal;
    }

    private static final TransitionPayloadContext INVALID_TRANSITION_PAYLOAD_CONTEXT = new TransitionPayloadContext();

    @Data
    @Accessors(chain = true)
    static class HandlePayloadContext {
        Object payload;
        /**
         * Transition marked as dead when merge status does not match transition condition
         */
        boolean isDeadTransition = false;
        /**
         * Terminal graph state reached.
         * No further merging (or payload modification) is allowed.
         * All transitions except copy-transition for detached processors is allowed.
         */
        boolean isTerminal;
        Object processorResult;
    }

    private static final HandlePayloadContext INVALID_HANDLE_PAYLOAD_CONTEXT = new HandlePayloadContext();

    @Data
    static class TransitionFuture<PayloadContextType> {
        final CompletableFuture<PayloadContextType> future;
    }

    /**
     * <img src="./doc-files/processing-item.png" alt="">
     */
    @Data
    @Accessors(chain = true)
    static class ProcessingVertex {

        CRProcessingItem processingItem;

        CRReactorGraph.ProcessingItemInfo processorInfo;

        CRReactorGraph.MergeGroup mergeGroup;

        boolean inMergeGroup;

        final List<TransitionFuture<TransitionPayloadContext>> incomingProcessorFlows = new ArrayList<>();

        final List<TransitionFuture<MergePayloadContext>> incomingMergeFlows = new ArrayList<>();

        final CompletableFuture<HandlePayloadContext> processorFuture = new CompletableFuture<>();

        final CompletableFuture<MergePayloadContext> mergePointFuture = new CompletableFuture<>();

        /**
         * Holds information about outgoing transitions from this merge point.
         * If during merging process we will receive merge status that match any of terminal transitions
         * then we should complete mergePointFuture with Terminal MergePayloadContext
         */
        final List<CRReactorGraph.Transition> mergePointTransition = new ArrayList<>();

        /**
         * Completes when all incoming transitions to merge group from processors is complete.
         * Merging process within merge group starts only after this future is completes.
         */
        CompletableFuture<Void> beforeMergeGroupMergingFuture;

        /**
         * Completes when all mergePoint merging within merge group is complete.
         * Outgoing transitions from merge points within merge group activates only after this future is completes.
         */
        CompletableFuture<Void> afterMergeGroupMergingFuture;
    }


    public ReactorGraphExecutionBuilder(
            Profiler profiler,
            ImmutabilityChecker immutabilityChecker,
            ThreadsafeCopyMaker threadsafeCopyMaker,
            SubgraphRunner subgraphRunner,
            DebugSerializer debugSerializer,
            Tracer tracer) {

        this.profiler = profiler;
        this.immutabilityChecker = immutabilityChecker;
        this.threadsafeCopyMaker = threadsafeCopyMaker;
        this.subgraphRunner = subgraphRunner;
        this.debugSerializer = debugSerializer;
        this.tracer = tracer;
    }

    public ReactorGraphExecutionBuilder setImmutabilityControlLevel(ImmutabilityControlLevel immutabilityControlLevel) {
        Objects.requireNonNull(immutabilityControlLevel);
        this.immutabilityControlLevel = immutabilityControlLevel;
        return this;
    }

    /**
     * If this flag is enabled then internal processing graph state will be attached to Execution result.
     * This allows easy access to execution state during debug.
     * One of drawbacks of this future is that GC will be prevented form removing internal state objects
     * until all reference to execution result dies.
     * <p>
     * By default this flag is disabled.
     *
     * @param debugProcessingVertexGraphState
     * @return
     */
    public ReactorGraphExecutionBuilder setDebugProcessingVertexGraphState(boolean debugProcessingVertexGraphState) {
        this.debugProcessingVertexGraphState = debugProcessingVertexGraphState;
        return this;
    }

    /**
     * @param reactorGraph
     * @param <PayloadType>
     * @return
     */
    public <PayloadType> ReactorGraphExecution<PayloadType> build(ReactorGraph<PayloadType> reactorGraph) {

        val crReactorGraph = (CRReactorGraph<PayloadType>) reactorGraph;

        /**
         * Internal representation of processing graph based on processing vertices.
         */
        final Map<CRProcessingItem, ProcessingVertex> processingVertices = new HashMap<>();


        final CompletableFuture<PayloadType> submitFuture = new CompletableFuture<>();

        /**
         * Will be completed on payload submission to processor chain
         */
        final CompletableFuture<ReactorGraphExecutionBuilder.TransitionPayloadContext> startPointTransitionFuture =
                submitFuture.thenApplyAsync(payload -> new TransitionPayloadContext().setPayload(payload));

        /**
         * Will be completed with payload when terminal graph state would be reached.
         */
        final CompletableFuture<PayloadType> executionResultFuture = new CompletableFuture<>();

        /**
         * Init Processing Vertices.
         */
        crReactorGraph.getProcessingItems().forEach((proc, info) -> {
            ProcessingVertex vertex = new ProcessingVertex()
                    .setProcessingItem(proc)
                    .setProcessorInfo(info);

            if (info.getProcessingItemType() == CRReactorGraph.ProcessingItemType.MERGE_POINT) {
                /**
                 * Detached merge point does not uses {@code {@link ProcessingVertex#getProcessorFuture()}
                 */
                vertex.getProcessorFuture().completeExceptionally(new IllegalStateException(
                        String.format("Detached Merge Point %s should not use processorFuture.",
                                vertex.getProcessingItem().getDebugName())));
            }

            processingVertices.put(proc, vertex);
        });

        /**
         * Populate start point transition
         *
         * If there is any detached merge point linked with start point
         * we have to execute its merge group first and only after merging process is done
         * can launch other outgoing from start point transitions
         *
         * startPointAfterMergeGroupMergingFuture will be triggered right after startPointTransitionFuture if there
         * is no MergeGroup for startPoint. Otherwise it will be triggered only after its MergeGroup evaluation.
         */
        CompletableFuture<ReactorGraphExecutionBuilder.TransitionPayloadContext> startPointAfterMergeGroupMergingFuture
                = new CompletableFuture<>();

        for (CRProcessingItem processor : crReactorGraph.getStartPoint().getProcessingItems()) {
            ProcessingVertex vertex = processingVertices.get(processor);

            if (vertex.getProcessorInfo().getProcessingItemType() == CRReactorGraph.ProcessingItemType.MERGE_POINT) {
                /**
                 * In case of Detached merge point transition from start point is being converted
                 * to a {@link MergePayloadContext}
                 * And we do no have to wait for startPointAfterMergeGroupMergingFuture
                 */
                vertex.getIncomingMergeFlows().add(new TransitionFuture<>(
                        startPointTransitionFuture.thenApplyAsync(
                                transitionPayloadContext ->
                                        new MergePayloadContext()
                                                .setDeadTransition(transitionPayloadContext.isDeadTransition())
                                                .setTerminal(transitionPayloadContext.isTerminal())
                                                .setPayload(transitionPayloadContext.getPayload())
                                                .setMergeResult(null))));


            } else {

                vertex.getIncomingProcessorFlows()
                        .add(new TransitionFuture<>(startPointAfterMergeGroupMergingFuture));
            }
        }

        /**
         * Populate MergePoints without MergeGroup
         */
        crReactorGraph.getMergePoints().stream()
                .filter(mergePoint -> !crReactorGraph.getMergeGroups().stream()
                        .anyMatch(mergeGroup -> mergeGroup.getMergePoints().contains(mergePoint)))

                .forEach(mergePoint -> {
                    ProcessingVertex mergePointVertex = processingVertices.get(mergePoint.asProcessingItem());
                    mergePointVertex.setInMergeGroup(false);

                    mergePoint.getTransitions().forEach(mergePointVertex.getMergePointTransition()::add);
                });

        /**
         * Populate MergePoints within MergeGroups
         */
        for (CRReactorGraph.MergeGroup mergeGroup : crReactorGraph.getMergeGroups()) {


            /**
             * Future that waits when all processors completes.
             */
            CompletableFuture<Void> beforeMergeGroupMergingFuture = CompletableFuture.allOf(
                    mergeGroup.getMergePoints().stream()
                            .map(mergePoint -> processingVertices.get(mergePoint.asProcessingItem()))
                            .filter(vertex -> vertex.getProcessorInfo().getProcessingItemType() !=
                                    CRReactorGraph.ProcessingItemType.MERGE_POINT)
                            .map(ProcessingVertex::getProcessorFuture)
                            .toArray(CompletableFuture[]::new)
            );
            /**
             * Future that waits when all merging completes.
             */
            CompletableFuture<Void> afterMergeGroupMergingFuture = CompletableFuture.allOf(
                    mergeGroup.getMergePoints().stream()
                            .map(mergePoint -> processingVertices.get(mergePoint.asProcessingItem()))
                            .map(ProcessingVertex::getMergePointFuture)
                            .toArray(CompletableFuture[]::new)
            );

            /**
             * Populate merge group information in vertexes
             */
            mergeGroup.getMergePoints().stream()
                    .map(mergePoint -> processingVertices.get(mergePoint.asProcessingItem()))
                    .forEach(vertex -> {
                        vertex.setInMergeGroup(true);
                        vertex.setMergeGroup(mergeGroup);
                        vertex.beforeMergeGroupMergingFuture = beforeMergeGroupMergingFuture;
                        vertex.afterMergeGroupMergingFuture = afterMergeGroupMergingFuture;
                    });
        }

        /**
         * Check if StartPoint have MergeGroup and initialize startPointAfterMergeGroupMergingFuture
         */
        if (crReactorGraph.getStartPointMergeGroup().isPresent()) {
            for (val processingItem : crReactorGraph.getStartPoint().getProcessingItems()) {
                ProcessingVertex vertex = processingVertices.get(processingItem);
                if (vertex.getProcessorInfo().getProcessingItemType() == CRReactorGraph.ProcessingItemType.MERGE_POINT) {
                    startPointTransitionFuture.thenCombine(vertex.getAfterMergeGroupMergingFuture(),
                            (transitionPayloadContext, none) -> transitionPayloadContext)
                            .thenApply(startPointAfterMergeGroupMergingFuture::complete);
                    break;
                }
            }
        } else {
            startPointTransitionFuture.thenApply(startPointAfterMergeGroupMergingFuture::complete);
        }

        /**
         * Populate transitions to all MergePoints
         */
        crReactorGraph.getMergePoints().forEach(mergePoint -> {
            ProcessingVertex mergePointVertex = processingVertices.get(mergePoint.asProcessingItem());
            mergePoint.getTransitions().forEach(mergePointVertex.getMergePointTransition()::add);
        });


        /**
         * Populate outgoing flows
         */
        for (CRReactorGraph.MergePoint mergePoint : crReactorGraph.getMergePoints()) {

            /**
             * MergeGroup can contain more that one MergePoint, so we have to wait all merge points to complete before
             * invoke processors.
             * We should not wait all merge point to complete if we are invoking other merge point within mergeGroup.
             */
            ProcessingVertex mergePointVertex = processingVertices.get(mergePoint.asProcessingItem());

            CompletableFuture<MergePayloadContext> mergePointFuture = mergePointVertex.getMergePointFuture();
            CompletableFuture<MergePayloadContext> groupedMergePointFuture = mergePointVertex.isInMergeGroup() ?
                    mergePointVertex.getAfterMergeGroupMergingFuture().
                            thenComposeAsync(any -> mergePointVertex.getMergePointFuture()) :
                    mergePointVertex.getMergePointFuture();

            for (CRReactorGraph.Transition transition : mergePoint.getTransitions()) {

                /**
                 * Terminal merge point vertex handled synchronously with merging process
                 * Skip that kind of transition during pre-processing.
                 */
                if (!transition.isComplete()) {
                    /**
                     * Populate outgoing processor flows
                     * activates when all merge points within merge group is completed
                     */
                    if (transition.getHandleBy() != null) {
                        CRProcessingItem proc = transition.getHandleBy();

                        processingVertices.get(proc).getIncomingProcessorFlows().add(
                                new TransitionFuture<>(
                                        groupedMergePointFuture.thenApplyAsync(
                                                context -> {
                                                    if (context.isTerminal()) {
                                                        return new TransitionPayloadContext().setTerminal(true);

                                                    } else if (context.isDeadTransition()) {
                                                        return new TransitionPayloadContext().setDeadTransition(true);

                                                    } else if (transition.isOnAny() || transition.getMergeStatuses().contains(context
                                                            .mergeResult)) {
                                                        return new TransitionPayloadContext()
                                                                .setPayload(context.payload);
                                                    } else {
                                                        return new TransitionPayloadContext()
                                                                .setPayload(context.payload)
                                                                .setDeadTransition(true);
                                                    }
                                                }
                                        )
                                )
                        );

                    }

                    /**
                     * Populate outgoing merge flow
                     * if merge point in same merge group activate it as soon as merge point completes
                     * if merge point does not belong to same merge group - activate it only whole merge group is complete
                     */
                    if (transition.getMerge() != null) {
                        CRProcessingItem proc = transition.getMerge();

                        ProcessingVertex transitionDestinationVertex = processingVertices.get(proc);

                        CompletableFuture<MergePayloadContext> startTransitionFuture;

                        if (mergePointVertex.isInMergeGroup()
                                && transitionDestinationVertex.isInMergeGroup()
                                && mergePointVertex.getMergeGroup().equals(transitionDestinationVertex.getMergeGroup())) {

                            startTransitionFuture = mergePointFuture;
                        } else {
                            startTransitionFuture = groupedMergePointFuture;
                        }

                        transitionDestinationVertex.getIncomingMergeFlows().add(
                                new TransitionFuture<>(
                                        startTransitionFuture.thenApplyAsync(
                                                context -> {
                                                    if (context.isTerminal()) {
                                                        return new MergePayloadContext().setTerminal(true);

                                                    } else if (context.isDeadTransition()) {
                                                        return new MergePayloadContext().setDeadTransition(true);

                                                    } else if (transition.isOnAny() ||
                                                            transition.getMergeStatuses().contains(context.mergeResult)) {

                                                        return new MergePayloadContext()
                                                                .setPayload(context.payload)
                                                                .setMergeResult(context.mergeResult);
                                                    } else {
                                                        return new MergePayloadContext()
                                                                .setDeadTransition(true);
                                                    }
                                                }
                                        )

                                )
                        );

                    }
                }
            }
        }

        /**
         * Join incoming processor flows to single processor invocation
         */
        processingVertices.forEach((processor, processingItem) -> {

            if (processingItem.getProcessorInfo().getProcessingItemType() == CRReactorGraph.ProcessingItemType.MERGE_POINT) {
                /**
                 * Detached merge point does not have graph processor, only merge point.
                 * No processor invocation is needed
                 */
                if (processingItem.getIncomingProcessorFlows().size() != 0) {
                    throw new IllegalStateException(String.format(
                            "Invalid graph state. Detached merge point %s have more than 0 incoming flows.",
                            processor.getDebugName()));
                }
                return;
            }

            if (processingItem.getIncomingProcessorFlows().size() <= 0) {
                throw new IllegalArgumentException(String.format(
                        "Invalid graph descriptor. Processor %s does not have incoming flows." +
                                " Probably missing handleBy directive for this processor.",
                        processor.getDebugName()));
            }

            CompletableFuture.allOf(
                    processingItem.getIncomingProcessorFlows().stream()
                            .map(TransitionFuture::getFuture)
                            .toArray(CompletableFuture[]::new)
            )

                    .thenRunAsync(() -> {

                                List<TransitionPayloadContext> incomingFlows = processingItem.getIncomingProcessorFlows().stream()
                                        .map(future -> {
                                            try {
                                                /**
                                                 * Future should be already complete
                                                 */
                                                if (!future.getFuture().isDone()) {
                                                    Exception resultException = new Exception(String.format(
                                                            "Illegal graph execution state. Future is not completed. Processor: %s",
                                                            processingItem.getProcessingItem().getDebugName()));

                                                    log.error(resultException.getMessage(), resultException);
                                                    executionResultFuture.completeExceptionally(resultException);
                                                    return INVALID_TRANSITION_PAYLOAD_CONTEXT;
                                                } else {
                                                    return future.getFuture().get();
                                                }
                                            } catch (Exception exc) {
                                                Exception resultException = new Exception(String.format(
                                                        "Failed to get incoming processor flow future result for processor: %s",
                                                        processingItem.getProcessingItem().getDebugName()), exc);
                                                log.error(resultException.getMessage(), resultException);
                                                executionResultFuture.completeExceptionally(resultException);

                                                return INVALID_TRANSITION_PAYLOAD_CONTEXT;
                                            }
                                        })
                                        .collect(Collectors.toList());

                                if (incomingFlows.stream().anyMatch(context -> context == INVALID_TRANSITION_PAYLOAD_CONTEXT)) {
                                    /**
                                     * Invalid graph execution state
                                     * Mark as terminal all outgoing flows from processor
                                     */
                                    processingItem.getProcessorFuture().complete(new HandlePayloadContext().setTerminal(true));

                                } else if (incomingFlows.stream().anyMatch(TransitionPayloadContext::isTerminal)) {
                                    /**
                                     * Terminal state reached.
                                     * Mark as terminal all outgoing flows from processor
                                     */
                                    processingItem.getProcessorFuture().complete(new HandlePayloadContext().setTerminal(true));

                                } else {
                                    List<TransitionPayloadContext> activeIncomingFlows = incomingFlows.stream()
                                            .filter(context -> !context.isDeadTransition())
                                            .collect(Collectors.toList());

                                    if (activeIncomingFlows.size() <= 0) {
                                        /**
                                         * There is no active incoming flow for given processor.
                                         * Processor will not be invoked.
                                         * All outgoing flows from processor will be marked as dead.
                                         */
                                        processingItem.getProcessorFuture().complete(new HandlePayloadContext().setDeadTransition(true));
                                    } else {
                                        if (activeIncomingFlows.size() > 1) {
                                            log.warn("There is more than one active incoming processor flow for processor {}." +
                                                            " Payload context will be used only from one of active flows." +
                                                            " Other active flows will be ignored." +
                                                            " Possible loss of computation results. ",
                                                    processingItem.getProcessingItem().getDebugName());
                                        }

                                        handle(processingItem, activeIncomingFlows.get(0), executionResultFuture);
                                    }
                                }
                            }
                    )
                    .exceptionally(throwable -> {
                        log.error("Join incoming processor flows failed.", throwable);
                        return null;
                    });

        });//processingVertices

        /**
         * Join incoming merge flows and processor handing future with single merging invocation
         */
        processingVertices.forEach((processor, vertex) -> {

            List<CompletableFuture> incomingFlows = new ArrayList<>();
            vertex.getIncomingMergeFlows()
                    .stream()
                    .map(TransitionFuture::getFuture)
                    .forEach(incomingFlows::add);
            if (vertex.isInMergeGroup()) {
                incomingFlows.add(vertex.beforeMergeGroupMergingFuture);
            }

            if (vertex.getProcessorInfo().getProcessingItemType() != CRReactorGraph.ProcessingItemType.MERGE_POINT) {
                /**
                 * Ignore processor future for detached merge point
                 * And use it for all other cases
                 */
                incomingFlows.add(vertex.getProcessorFuture());
            }

            CompletableFuture.allOf(incomingFlows.toArray(new CompletableFuture[incomingFlows.size()]))
                    .thenRunAsync(() -> {

                        /**
                         * Processor result, could be INVALID_HANDLE_PAYLOAD_CONTEXT in case of exception
                         * Could be NULL in case of detached merge point
                         */
                        HandlePayloadContext handlePayloadContext = null;

                        if (vertex.getProcessorInfo().getProcessingItemType() != CRReactorGraph.ProcessingItemType.MERGE_POINT) {

                            handlePayloadContext = Optional.of(vertex.getProcessorFuture())
                                    .map(future -> {
                                        try {
                                            if (!future.isDone()) {
                                                RuntimeException resultException = new RuntimeException(String.format(
                                                        "Illegal graph execution state. Processor future is not completed. Processor %s",
                                                        vertex.getProcessingItem().getDebugName()));
                                                log.error(resultException.getMessage(), resultException);
                                                executionResultFuture.completeExceptionally(resultException);

                                                return INVALID_HANDLE_PAYLOAD_CONTEXT;
                                            } else {
                                                return future.get();
                                            }
                                        } catch (Exception exc) {
                                            RuntimeException resultException = new RuntimeException(String.format(
                                                    "Failed to get processor future result for processor: %s",
                                                    vertex.getProcessingItem().getDebugName()), exc);

                                            log.error(resultException.getMessage(), resultException);
                                            executionResultFuture.completeExceptionally(resultException);

                                            return INVALID_HANDLE_PAYLOAD_CONTEXT;
                                        }
                                    })
                                    .orElse(INVALID_HANDLE_PAYLOAD_CONTEXT);

                            if (handlePayloadContext == INVALID_HANDLE_PAYLOAD_CONTEXT) {
                                /**
                                 * Failed to get processor result.
                                 * Merging will not be applied to payload.
                                 * All outgoing flows from merge point will be marked as terminal.
                                 * executionResult completed by exception
                                 */
                                vertex.getMergePointFuture().complete(new MergePayloadContext().setTerminal(true));
                                return;

                            } else if (handlePayloadContext.isTerminal()) {
                                /**
                                 * Processor was marked as terminal during flow by terminal transition.
                                 * Merging will not be applied to payload.
                                 * All outgoing flows from merge point will be marked as terminal.
                                 */
                                vertex.getMergePointFuture().complete(new MergePayloadContext().setTerminal(true));
                                return;

                            } else if (handlePayloadContext.isDeadTransition()) {
                                /**
                                 * Processor was disabled during flow by dead transition.
                                 * Merging will not be applied to payload.
                                 * All outgoing flows from merge point will be marked as dead.
                                 */
                                vertex.getMergePointFuture().complete(new MergePayloadContext().setDeadTransition(true));
                                return;
                            }
                        }

                        /**
                         * Active incoming merge flows, could be empty
                         */
                        List<MergePayloadContext> incomingMergeFlows = vertex.getIncomingMergeFlows().stream()
                                .map(future -> {
                                    try {
                                        if (!future.getFuture().isDone()) {

                                            RuntimeException resultException = new RuntimeException(String.format(
                                                    "Illegal graph execution state. Incoming merge future is not complete." +
                                                            " ProcessingVertex: %s", vertex));
                                            log.error(resultException.getMessage(), resultException);
                                            executionResultFuture.completeExceptionally(resultException);
                                            return INVALID_MERGE_PAYLOAD_CONTEXT;
                                        } else {
                                            return future.getFuture().get();
                                        }
                                    } catch (Exception exc) {
                                        RuntimeException resultException = new RuntimeException(String.format(
                                                "Failed to get incoming merge flow future result for processor: %s",
                                                vertex.getProcessingItem().getDebugName()), exc);

                                        log.error(resultException.getMessage(), resultException);
                                        executionResultFuture.completeExceptionally(resultException);
                                        return INVALID_MERGE_PAYLOAD_CONTEXT;
                                    }
                                }).collect(Collectors.toList());

                        if (incomingMergeFlows.stream().anyMatch(context -> context == INVALID_MERGE_PAYLOAD_CONTEXT)) {
                            /**
                             * Exception during merging
                             * Mark as terminal all outgoing flows from merge point
                             */
                            vertex.getMergePointFuture().complete(new MergePayloadContext().setTerminal(true));
                        }
                        if (incomingMergeFlows.stream().anyMatch(MergePayloadContext::isTerminal)) {
                            /**
                             * Terminal state reached.
                             * Mark as terminal all outgoing flows from merge point
                             */
                            vertex.getMergePointFuture().complete(new MergePayloadContext().setTerminal(true));

                        } else {

                            final List<MergePayloadContext> activeIncomingMergeFlows = incomingMergeFlows.stream()
                                    .filter(context -> !context.isDeadTransition())
                                    .collect(Collectors.toList());

                            if (vertex.getProcessorInfo().getProcessingItemType() == CRReactorGraph.ProcessingItemType.MERGE_POINT) {
                                /**
                                 * Detached merge point
                                 */
                                if (activeIncomingMergeFlows.size() == 0) {

                                    /**
                                     * Check that there are at least on incoming transition that marked as dead
                                     */
                                    if (incomingMergeFlows.stream().anyMatch(MergePayloadContext::isDeadTransition)) {
                                        /**
                                         * Detached MergePoint marked as Dead, because there are no active incoming flows and
                                         * there is at least one incoming dead transition
                                         * Mark as dead all outgoing flows from merge point
                                         */
                                        vertex.getMergePointFuture().complete(new MergePayloadContext().setDeadTransition(true));
                                    } else {
                                        throw new IllegalStateException(String.format(
                                                "There is no incoming merge flows for detached merge point %s." +
                                                        " At least dead incoming transition expected.",
                                                vertex.getProcessingItem().getDebugName()));
                                    }

                                } else {
                                    if (activeIncomingMergeFlows.size() > 1) {
                                        log.warn("There is more than one active incoming flow for detached merge point for processor {}." +
                                                        " Payload context will be used only from one of active flows." +
                                                        " Other active flows will be ignored." +
                                                        " Possible loss of computation results." +
                                                        " Possible concurrent modifications of payload.",
                                                vertex.getProcessingItem().getDebugName());
                                    }

                                    merge(vertex, Optional.empty(), activeIncomingMergeFlows.get(0).getPayload(), executionResultFuture);
                                }
                            } else {
                                if (activeIncomingMergeFlows.size() == 0) {
                                    /**
                                     * There is no active incoming merge flow for given merge point.
                                     */
                                    merge(vertex,
                                            handlePayloadContext.getProcessorResult(),
                                            handlePayloadContext.getPayload(),
                                            executionResultFuture);

                                } else {
                                    if (activeIncomingMergeFlows.size() > 1) {
                                        log.warn("There is more than one active incoming flow for merge point for processor {}." +
                                                        " Payload context will be used only from one of active flows." +
                                                        " Other active flows will be ignored." +
                                                        " Possible loss of computation results." +
                                                        " Possible concurrent modifications of payload.",
                                                vertex.getProcessingItem().getDebugName());
                                    }

                                    merge(vertex,
                                            handlePayloadContext.getProcessorResult(),
                                            activeIncomingMergeFlows.get(0).getPayload(),
                                            executionResultFuture);
                                }
                            }
                        }
                    })
                    .exceptionally(throwable -> {
                        log.error("Joining incoming merge flows failed.", throwable);
                        return null;
                    });

        });//processingVertices

        /**
         * Handle terminal vertices.
         * When execution reaches 'complete' vertex all transitions should be marked dead and complete.
         */
        executionResultFuture.thenRunAsync(() -> {
            processingVertices.entrySet().stream()
                    .map(Map.Entry::getValue)
                    .map(ProcessingVertex::getIncomingProcessorFlows)
                    .flatMap(Collection::stream)
                    .map(TransitionFuture::getFuture)
                    .forEach(future -> future.complete(new TransitionPayloadContext().setDeadTransition(true)));

            processingVertices.entrySet().stream()
                    .map(Map.Entry::getValue)
                    .map(ProcessingVertex::getIncomingProcessorFlows)
                    .flatMap(Collection::stream)
                    .map(TransitionFuture::getFuture)
                    .forEach(future -> future.complete(new TransitionPayloadContext().setDeadTransition(true)));
        }).exceptionally(throwable -> {
            log.error("Marking transitions as dead is failed.", throwable);
            return null;
        });

        /**
         * Collect chain execution processor handling futures
         * Processors futures holds handle invocation result or dead context.
         * Then all processors futures completes chainExecutionFuture completes too.
         */
        CompletableFuture<Void> chainExecutionFuture = CompletableFuture.allOf(
                processingVertices.entrySet().stream()
                        .map(Map.Entry::getValue)
                        .map(ProcessingVertex::getProcessorFuture)
                        .toArray(CompletableFuture[]::new)
        );

        return ReactorGraphExecution.<PayloadType>builder()
                .resultFuture(executionResultFuture)
                .submitFuture(submitFuture)
                .chainExecutionFuture(chainExecutionFuture)
                .debugProcessingVertexGraphState(debugProcessingVertexGraphState ? processingVertices.values() : null)
                .build();
    }

    private CompletableFuture<?> invokeHandlingMethod(
            CRReactorGraph.ProcessingItemInfo processorInfo,
            CRProcessingItem processingItem,
            Object payload) {

        switch (processorInfo.getProcessingItemType()) {
            case PROCESSOR:
                return invokeProcessorHandlingMethod(processorInfo, processingItem, payload);
            case SUBGRAPH:
                return invokeSubgraphHandlingMethod(processorInfo, payload);
            default:
                throw new IllegalStateException(
                        String.format("Processing item %s of type %s not supported",
                                processingItem.getDebugName(),
                                processorInfo.getProcessingItemType()));
        }
    }

    private CompletableFuture<?> invokeSubgraphHandlingMethod(
            CRReactorGraph.ProcessingItemInfo processorInfo,
            Object payload) {

        Object param = processorInfo.getSubgraphDescription().getArg().apply(payload);
        if (processorInfo.getSubgraphDescription().isCopyArg()) {
            param = threadsafeCopyMaker.makeThreadsafeCopy(param);
        }

        return subgraphRunner.run(param);
    }

    private CompletableFuture<?> invokeProcessorHandlingMethod(
            CRReactorGraph.ProcessingItemInfo processorInfo,
            CRProcessingItem processingItem,
            Object payload) {

        CRProcessorDescription description = processorInfo.getDescription();

        try {

            Object param1 = null;
            Object param2 = null;
            Object param3 = null;
            Object param4 = null;
            Object param5 = null;
            Object param6 = null;

            if (description.getArg1() != null) {
                param1 = description.getArg1().apply(payload);
                if (description.isCopyArg1()) {
                    param1 = threadsafeCopyMaker.makeThreadsafeCopy(param1);
                }
            }
            if (description.getArg2() != null) {
                param2 = description.getArg2().apply(payload);
                if (description.isCopyArg2()) {
                    param2 = threadsafeCopyMaker.makeThreadsafeCopy(param2);
                }
            }
            if (description.getArg3() != null) {
                param3 = description.getArg3().apply(payload);
                if (description.isCopyArg3()) {
                    param3 = threadsafeCopyMaker.makeThreadsafeCopy(param3);
                }
            }
            if (description.getArg4() != null) {
                param4 = description.getArg4().apply(payload);
                if (description.isCopyArg4()) {
                    param4 = threadsafeCopyMaker.makeThreadsafeCopy(param4);
                }
            }
            if (description.getArg5() != null) {
                param5 = description.getArg5().apply(payload);
                if (description.isCopyArg5()) {
                    param5 = threadsafeCopyMaker.makeThreadsafeCopy(param5);
                }
            }
            if (description.getArg6() != null) {
                param6 = description.getArg6().apply(payload);
                if (description.isCopyArg6()) {
                    param6 = threadsafeCopyMaker.makeThreadsafeCopy(param6);
                }
            }


            if (description.getHandler0() != null) {
                return (CompletableFuture) description.getHandler0().handle();

            } else if (description.getHandler1() != null) {
                return (CompletableFuture) description.getHandler1().handle(param1);


            } else if (description.getHandler2() != null) {
                return (CompletableFuture) description.getHandler2().handle(
                        param1,
                        param2
                );
            } else if (description.getHandler3() != null) {
                return (CompletableFuture) description.getHandler3().handle(
                        param1,
                        param2,
                        param3
                );

            } else if (description.getHandler4() != null) {
                return (CompletableFuture) description.getHandler4().handle(
                        param1,
                        param2,
                        param3,
                        param4
                );

            } else if (description.getHandler5() != null) {
                return (CompletableFuture) description.getHandler5().handle(
                        param1,
                        param2,
                        param3,
                        param4,
                        param5
                );
            } else if (description.getHandler6() != null) {
                return (CompletableFuture) description.getHandler6().handle(
                        param1,
                        param2,
                        param3,
                        param4,
                        param5,
                        param6
                );

            } else {
                CompletableFuture result = new CompletableFuture();
                result.completeExceptionally(
                        new IllegalArgumentException(
                                String.format("There is no handler in processor %s for payload %s %s",
                                        processingItem.getDebugName(),
                                        payload.getClass(),
                                        debugSerializer.dumpObject(payload))));
                return result;
            }

        } catch (Exception exc) {
            CompletableFuture result = new CompletableFuture();
            result.completeExceptionally(
                    new IllegalArgumentException(
                            String.format("Exception during handling in processor %s for payload %s %s",
                                    processingItem.getDebugName(),
                                    payload.getClass(),
                                    debugSerializer.dumpObject(payload)),
                            exc));
            return result;
        }
    }


    private <PayloadType> void handle(ProcessingVertex processingVertex,
                                      TransitionPayloadContext payloadContext,
                                      CompletableFuture<PayloadType> executionResultFuture) {

        CRReactorGraph.ProcessingItemInfo processorInfo = processingVertex.getProcessorInfo();
        Object payload = payloadContext.getPayload();

        /**
         * In case of detached merge point processor should not have incoming handling transition.
         */
        if (processorInfo.getProcessingItemType() == CRReactorGraph.ProcessingItemType.MERGE_POINT) {
            throw new IllegalStateException(String.format("Processor %s is of type %s and should not have any incoming handling transition",
                    processingVertex.getProcessingItem().getDebugName(),
                    processorInfo.getProcessingItemType()));
        }

        ProfiledCall handleCall = profiler.profiledCall(
                ProfilerNames.PROCESSOR_HANDLE + processingVertex.getProcessingItem().getProfilingName())
                .start();

        boolean isTraceablePayload = tracer.isTraceable(payload);
        Object handleTracingMarker = isTraceablePayload ?
                tracer.beforeHandle(processingVertex.getProcessingItem().getIdentity(), payload) :
                null;
        ReactorGraphModel.Identity handleTracingIdentity = isTraceablePayload ?
                processingVertex.getProcessingItem().getIdentity() :
                null;

        CompletableFuture<?> handlingResult;

        /**
         * Immutability check ensures that there is no payload modification during handling.
         */
        final ImmutabilityControlLevel controlLevel = this.immutabilityControlLevel;

        Object payloadClone;
        ImmutabilityChecker.Snapshot payloadCloneSnapshot;

        try {
            if (controlLevel != ImmutabilityControlLevel.NO_CONTROL) {
                /**
                 * Invoke handling with immutability check.
                 * Do not use origin payload, make two copies of payload.
                 */
                payloadClone = immutabilityChecker.clone(payload);
                payloadCloneSnapshot = immutabilityChecker.takeSnapshot(payloadClone);

                handlingResult = invokeHandlingMethod(processorInfo, processingVertex.getProcessingItem(), payloadClone);

            } else {
                /**
                 * Invoke handling without immutability check.
                 */
                payloadClone = null;
                payloadCloneSnapshot = null;

                handlingResult = invokeHandlingMethod(processorInfo, processingVertex.getProcessingItem(), payload);
            }
        } catch (Exception handlingException) {
            RuntimeException exc = new RuntimeException(
                    String.format(
                            "Failed handling by processor %s for payload %s %s. Handling method raised exception: %s.",
                            processingVertex.getProcessingItem().getDebugName(),
                            payload.getClass(),
                            debugSerializer.dumpObject(payload),
                            handlingException),
                    handlingException);

            log.error(exc.getMessage(), exc);
            executionResultFuture.completeExceptionally(exc);
            processingVertex.getProcessorFuture().complete(new HandlePayloadContext().setTerminal(true));
            return;
        }

        if (handlingResult == null) {
            RuntimeException exc = new RuntimeException(
                    String.format(
                            "Failed handling by processor %s for payload %s %s. Handling method returned NULL." +
                                    " Instance of CompletableFuture expected.",
                            processingVertex.getProcessingItem().getDebugName(),
                            payload.getClass(),
                            debugSerializer.dumpObject(payload)));

            log.error(exc.getMessage(), exc);
            executionResultFuture.completeExceptionally(exc);
            processingVertex.getProcessorFuture().complete(new HandlePayloadContext().setTerminal(true));
            return;
        }

        handlingResult.handleAsync((res, thr) -> {
            handleCall.stop();

            if (isTraceablePayload) {
                tracer.afterHandle(handleTracingMarker, handleTracingIdentity, res, thr);
            }

            if (controlLevel != ImmutabilityControlLevel.NO_CONTROL) {

                Optional<String> diff = immutabilityChecker.diff(payloadCloneSnapshot, payloadClone);
                if (diff.isPresent()) {
                    String message = String.format("Concurrent modification of payload %s. Diff: %s.",
                            debugSerializer.dumpObject(payload),
                            diff.get());

                    switch (controlLevel) {
                        case LOG_ERROR:
                            log.error(message);
                            break;
                        case LOG_WARN:
                            log.warn(message);
                            break;
                        case EXCEPTION:
                            RuntimeException immutabilityException = new RuntimeException(message);
                            log.error(message, immutabilityException);

                            if (thr == null) {
                                log.error(
                                        "Overwriting execution exception {} by immutability check exception {}.",
                                        thr,
                                        immutabilityException,
                                        thr);
                            }
                            thr = immutabilityException;
                            break;
                    }
                }
            }

            if (thr != null) {
                RuntimeException exc = new RuntimeException(
                        String.format(
                                "Failed handling by processor %s for payload %s %s",
                                processingVertex.getProcessingItem().getDebugName(),
                                payload.getClass(),
                                debugSerializer.dumpObject(payload)),
                        thr);

                log.error(exc.getMessage(), exc);
                executionResultFuture.completeExceptionally(exc);

                processingVertex.getProcessorFuture().complete(new HandlePayloadContext().setTerminal(true));
            } else {
                processingVertex.getProcessorFuture().complete(new HandlePayloadContext().setPayload(payload).setProcessorResult(res));
            }
            return null;
        });
    }

    /**
     * @param processingVertex
     * @param processorResult       empty in case of detached merge point
     * @param payload
     * @param executionResultFuture
     * @param <PayloadType>
     */
    private <PayloadType> void merge(ProcessingVertex processingVertex,
                                     Object processorResult,
                                     Object payload,
                                     CompletableFuture<PayloadType> executionResultFuture) {

        CRReactorGraph.ProcessingItemInfo processorInfo = processingVertex.getProcessorInfo();

        Supplier<Enum> mergerInvocation;

        switch (processorInfo.getProcessingItemType()) {
            case PROCESSOR:
                if (processorInfo.getDescription().getMerger() == null) {
                    /**
                     * This Processor does not have merger
                     */
                    processingVertex.getMergePointFuture().complete(new MergePayloadContext().setDeadTransition(true));
                    return;
                } else {
                    mergerInvocation = () -> (Enum) processorInfo.getDescription().getMerger().merge(
                            payload,
                            processorResult);
                }
                break;
            case SUBGRAPH:
                if (processorInfo.getSubgraphDescription().getMerger() == null) {
                    /**
                     * This Subgraph does not have merger
                     */
                    processingVertex.getMergePointFuture().complete(new MergePayloadContext().setDeadTransition(true));
                    return;
                } else {
                    mergerInvocation = () -> (Enum) processorInfo.getSubgraphDescription().getMerger().merge(
                            payload,
                            processorResult);
                }
                break;
            case MERGE_POINT:
                mergerInvocation = () -> (Enum) processorInfo.getDetachedMergePointDescription().getMerger().merge(payload);
                break;

            default:
                throw new IllegalArgumentException(String.format("Unknown processor type: %s", processorInfo.getProcessingItemType()));
        }


        try {
            ProfiledCall mergeCall = profiler.profiledCall(
                    ProfilerNames.PROCESSOR_MERGE + processingVertex.getProcessingItem().getProfilingName())
                    .start();
            boolean isTraceablePayload = tracer.isTraceable(payload);
            Object mergeTracingMarker = isTraceablePayload ?
                    tracer.beforeMerge(processingVertex.getProcessingItem().getIdentity(), payload, processorResult) :
                    null;

            Enum mergeStatus = mergerInvocation.get();

            mergeCall.stop();

            if (isTraceablePayload) {
                tracer.afterMerger(mergeTracingMarker, processingVertex.getProcessingItem().getIdentity(), payload);
            }

            /**
             * Select outgoing transitions that matches mergeStatus
             */
            List<CRReactorGraph.Transition> activeTransitions = processingVertex.getMergePointTransition().stream()
                    .filter(transition -> transition.isOnAny() || transition.getMergeStatuses().contains(mergeStatus))
                    .collect(Collectors.toList());

            if (activeTransitions.size() <= 0) {
                throw new IllegalStateException(String.format("Merging process returned %s status." +
                                " But merge point of processor %s does not have matching transition for this status.",
                        mergeStatus,
                        processingVertex.getProcessingItem().getDebugName()));
            }

            /**
             * check if this merge point have terminal transitions that matches merge status
             */
            if (activeTransitions.stream().anyMatch(CRReactorGraph.Transition::isComplete)) {

                /**
                 * Handle terminal transition by completing execution result
                 */
                if (!executionResultFuture.complete((PayloadType) payload)) {

                    Object previousResult = null;
                    try {
                        if (executionResultFuture.isDone()) {
                            previousResult = executionResultFuture.get();
                        } else {
                            log.error("Illegal graph execution state." +
                                    " Completion failed for new result, but execution result from previous terminal step is not complete.");
                        }
                    } catch (Exception exc) {
                        log.error("Failed to get completed execution result from previous terminal step.", exc);
                    }

                    log.error("Processing chain was completed by at least two different terminal steps." +
                                    " Already completed with result {}." +
                                    " New completion result {} in merge point for processor {}",
                            debugSerializer.dumpObject(previousResult),
                            debugSerializer.dumpObject(payload),
                            processingVertex.getProcessingItem().getDebugName());
                }

                /**
                 * Terminal state reached. Execution result completed.
                 * Throw poison pill - terminal context. All following merge points should be deactivated.
                 */
                processingVertex.getMergePointFuture().complete(new MergePayloadContext()
                        .setPayload(null)
                        .setMergeResult(mergeStatus)
                        .setTerminal(true));
            } else {
                /**
                 * There is no terminal state reached after merging.
                 */
                processingVertex.getMergePointFuture().complete(new MergePayloadContext().setPayload(payload).setMergeResult(mergeStatus));
            }

        } catch (Exception exc) {
            log.error("Failed to merge payload {} {} by processing item {} for result {}",
                    payload.getClass(),
                    debugSerializer.dumpObject(payload),
                    processingVertex.getProcessingItem().getDebugName(),
                    debugSerializer.dumpObject(processorResult),
                    exc);

            executionResultFuture.completeExceptionally(exc);

            processingVertex.getMergePointFuture().complete(new MergePayloadContext().setDeadTransition(true));
        }
    }
}
