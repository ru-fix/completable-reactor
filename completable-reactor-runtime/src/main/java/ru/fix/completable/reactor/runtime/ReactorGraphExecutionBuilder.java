package ru.fix.completable.reactor.runtime;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import ru.fix.commons.profiler.ProfiledCall;
import ru.fix.commons.profiler.Profiler;
import ru.fix.completable.reactor.runtime.immutability.ImmutabilityChecker;
import ru.fix.completable.reactor.runtime.immutability.ImmutabilityControlLevel;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;


@Slf4j
public class ReactorGraphExecutionBuilder {

    private final Profiler profiler;
    private final ImmutabilityChecker immutabilityChecker;
    private final ThreadsafeCopyMaker threadsafeCopyMaker;

    private boolean debugProcessingVertexGraphState = false;

    volatile ImmutabilityControlLevel immutabilityControlLevel = ImmutabilityControlLevel.NO_CONTROL;


    private final SubgraphRunner subgraphRunner;

    @FunctionalInterface
    interface SubgraphRunner {
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
        ProcessingGraphItem processor;
        ReactorGraph.ProcessorInfo processorInfo;
        ReactorGraph.MergeGroup mergeGroup;
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
        final List<ReactorGraph.Transition> mergePointTransition = new ArrayList<>();

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


    public ReactorGraphExecutionBuilder(Profiler profiler, ImmutabilityChecker immutabilityChecker, ThreadsafeCopyMaker threadsafeCopyMaker, SubgraphRunner subgraphRunner) {
        this.profiler = profiler;
        this.immutabilityChecker = immutabilityChecker;
        this.threadsafeCopyMaker = threadsafeCopyMaker;
        this.subgraphRunner = subgraphRunner;
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

        /**
         * Internal representation of processing graph based on processing vertices.
         */
        final Map<ProcessingGraphItem, ProcessingVertex> processingVertices = new HashMap<>();


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
        reactorGraph.processors.forEach((proc, info) -> {
            ProcessingVertex vertex = new ProcessingVertex()
                    .setProcessor(proc)
                    .setProcessorInfo(info);

            if (info.getProcessorType() == ReactorGraph.ProcessorType.DETACHED_MERGE_POINT) {
                /**
                 * Detached merge point does not uses {@code {@link ProcessingVertex#getProcessorFuture()}
                 */
                vertex.getProcessorFuture().completeExceptionally(new IllegalStateException(
                        String.format("Detached Merge Point %s should not use processorFuture.", vertex.getProcessor())));
            }

            processingVertices.put(proc, vertex);
        });

        /**
         * Populate start point transition
         */
        for (ProcessingGraphItem processor : reactorGraph.startPoint.processors) {
            ProcessingVertex vertex = processingVertices.get(processor);

            if (vertex.getProcessorInfo().getProcessorType() == ReactorGraph.ProcessorType.DETACHED_MERGE_POINT) {
                /**
                 * In case of Detached merge point transition from start point is being converted to a {@link MergePayloadContext}
                 */
                vertex.incomingMergeFlows.add(new TransitionFuture<>(
                        startPointTransitionFuture.thenApplyAsync(
                                transitionPayloadContext ->
                                        new MergePayloadContext()
                                                .setDeadTransition(transitionPayloadContext.isDeadTransition())
                                                .setTerminal(transitionPayloadContext.isTerminal())
                                                .setPayload(transitionPayloadContext.getPayload())
                                                .setMergeResult(null))));


            } else {
                vertex.getIncomingProcessorFlows()
                        .add(new TransitionFuture<>(startPointTransitionFuture));
            }
        }

        /**
         * Populate MergeGroups and MergePoints
         */
        for (ReactorGraph.MergeGroup mergeGroup : reactorGraph.mergeGroups) {

            if (mergeGroup.getMergePoints().size() == 1) {
                /**
                 * Disable merge group if there is only one merge point.
                 */
                ReactorGraph.MergePoint mergePoint = mergeGroup.getMergePoints().get(0);
                ProcessingVertex mergePointVertex = processingVertices.get(mergePoint.processor);
                mergePointVertex.setInMergeGroup(false);

                mergePoint.getTransitions().forEach(mergePointVertex.getMergePointTransition()::add);


            } else {
                /**
                 * Future that waits when all processors completes.
                 */
                CompletableFuture<Void> beforeMergeGroupMergingFuture = CompletableFuture.allOf(
                        mergeGroup.getMergePoints().stream()
                                .map(mergePoint -> processingVertices.get(mergePoint.getProcessor()))
                                .filter(vertex -> vertex.getProcessorInfo().getProcessorType() != ReactorGraph.ProcessorType.DETACHED_MERGE_POINT)
                                .map(ProcessingVertex::getProcessorFuture)
                                .toArray(CompletableFuture[]::new)
                );
                /**
                 * Future that waits when all merging completes.
                 */
                CompletableFuture<Void> afterMergeGroupMergingFuture = CompletableFuture.allOf(
                        mergeGroup.getMergePoints().stream()
                                .map(mergePoint -> processingVertices.get(mergePoint.getProcessor()))
                                .map(ProcessingVertex::getMergePointFuture)
                                .toArray(CompletableFuture[]::new)
                );

                /**
                 * Populate merge group information in vertexes
                 */
                mergeGroup.getMergePoints().stream()
                        .map(mergePoint -> processingVertices.get(mergePoint.getProcessor()))
                        .forEach(vertex -> {
                            vertex.setInMergeGroup(true);
                            vertex.setMergeGroup(mergeGroup);
                            vertex.beforeMergeGroupMergingFuture = beforeMergeGroupMergingFuture;
                            vertex.afterMergeGroupMergingFuture = afterMergeGroupMergingFuture;
                        });

                /**
                 * Populate terminal transitions to merge point vertex
                 */
                mergeGroup.getMergePoints().forEach(mergePoint -> {
                    ProcessingVertex mergePointVertex = processingVertices.get(mergePoint.getProcessor());
                    mergePoint.getTransitions().forEach(mergePointVertex.getMergePointTransition()::add);
                });
            }
        }

        /**
         * Populate outgoing flows
         */
        for (ReactorGraph.MergePoint mergePoint : reactorGraph.mergeGroups.stream().map(ReactorGraph.MergeGroup::getMergePoints).flatMap(List::stream).collect(Collectors.toList())) {

            /**
             * MergeGroup can contain more that one MergePoint, so we have to wait all merge points to complete before
             * invoke processors.
             * We should not wait all merge point to complete if we are invoking other merge point within mergeGroup.
             */
            ProcessingVertex mergePointVertex = processingVertices.get(mergePoint.processor);

            CompletableFuture<MergePayloadContext> mergePointFuture = mergePointVertex.getMergePointFuture();
            CompletableFuture<MergePayloadContext> groupedMergePointFuture = mergePointVertex.isInMergeGroup() ?
                    mergePointVertex.getAfterMergeGroupMergingFuture().
                            thenComposeAsync(any -> mergePointVertex.getMergePointFuture()) :
                    mergePointVertex.getMergePointFuture();

            for (ReactorGraph.Transition transition : mergePoint.transitions) {

                /**
                 * Terminal merge point vertex handled synchronously with merging process
                 * Skip that kind of transition during pre-processing.
                 */
                if (!transition.isComplete) {
                    /**
                     * Populate outgoing processor flows
                     * activates when all merge points within merge group is completed
                     */
                    if (transition.handleBy != null) {
                        ProcessingGraphItem proc = transition.handleBy;

                        processingVertices.get(proc).incomingProcessorFlows.add(
                                new TransitionFuture<>(
                                        groupedMergePointFuture.thenApplyAsync(
                                                context -> {
                                                    if (context.isTerminal()) {
                                                        return new TransitionPayloadContext().setTerminal(true);

                                                    } else if (context.isDeadTransition()) {
                                                        return new TransitionPayloadContext().setDeadTransition(true);

                                                    } else if (transition.isOnAny || transition.getMergeStatuses().contains(context.mergeResult)) {
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
                    if (transition.merge != null) {
                        ProcessingGraphItem proc = transition.merge;

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

                                                    } else if (transition.isOnAny || transition.getMergeStatuses().contains(context.mergeResult)) {
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

            if (processingItem.getProcessorInfo().getProcessorType() == ReactorGraph.ProcessorType.DETACHED_MERGE_POINT) {
                /**
                 * Detached merge point does not have graph processor, only merge point.
                 * No processor invocation is needed
                 */
                if (processingItem.getIncomingProcessorFlows().size() != 0) {
                    throw new IllegalStateException(String.format("Invalid graph state. Detached merge point %s have more than 0 incoming flows.",
                            processor));
                }
                return;
            }

            if (processingItem.getIncomingProcessorFlows().size() <= 0) {
                throw new IllegalArgumentException(String.format(
                        "Invalid graph descriptor. Processor %s does not have incoming flows.",
                        processor));
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
                                                    log.error("Illegal graph execution state. Future is not completed. Processor: {}",
                                                            processingItem.getProcessor());

                                                    return INVALID_TRANSITION_PAYLOAD_CONTEXT;
                                                } else {
                                                    return future.getFuture().get();
                                                }
                                            } catch (Exception exc) {
                                                log.warn("Failed to get incoming processor flow future result for processor: {}",
                                                        processingItem.getProcessor(), exc);

                                                return INVALID_TRANSITION_PAYLOAD_CONTEXT;
                                            }
                                        })
                                        .collect(Collectors.toList());

                                if (incomingFlows.stream().anyMatch(TransitionPayloadContext::isTerminal)) {
                                    /**
                                     * Terminal state reached.
                                     * Mark as terminal all outgoing flows from processor
                                     */
                                    processingItem.getProcessorFuture().complete(new HandlePayloadContext().setTerminal(true));

                                } else {
                                    List<TransitionPayloadContext> activeIncomingFlows = incomingFlows.stream()
                                            .filter(context -> context != INVALID_TRANSITION_PAYLOAD_CONTEXT)
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
                                                    processingItem.getProcessor());
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

            if (vertex.getProcessorInfo().getProcessorType() != ReactorGraph.ProcessorType.DETACHED_MERGE_POINT) {
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

                        if (vertex.getProcessorInfo().getProcessorType() != ReactorGraph.ProcessorType.DETACHED_MERGE_POINT) {

                            handlePayloadContext = Optional.of(vertex.getProcessorFuture())
                                    .map(future -> {
                                        try {
                                            if (!future.isDone()) {
                                                log.error("Illegal graph execution state. Processor future is not completed. Processor {}",
                                                        vertex.getProcessor());
                                                return INVALID_HANDLE_PAYLOAD_CONTEXT;
                                            } else {
                                                return future.get();
                                            }
                                        } catch (Exception exc) {
                                            RuntimeException resultException = new RuntimeException(String.format(
                                                    "Failed to get processor future result for processor: %s",
                                                    vertex.getProcessor()), exc);

                                            log.error(resultException.getMessage(), resultException);
                                            executionResultFuture.completeExceptionally(resultException);

                                            return INVALID_HANDLE_PAYLOAD_CONTEXT;
                                        }
                                    })
                                    .orElseGet(() -> INVALID_HANDLE_PAYLOAD_CONTEXT);

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
                                            log.error("Illegal graph execution state. Incoming merge future is not complete. ProcessingVertex: {}", vertex);
                                            return INVALID_MERGE_PAYLOAD_CONTEXT;
                                        } else {
                                            return future.getFuture().get();
                                        }
                                    } catch (Exception exc) {
                                        RuntimeException resultException = new RuntimeException(String.format(
                                                "Failed to get incoming merge flow future result for processor: %s",
                                                vertex.getProcessor()), exc);

                                        log.error(resultException.getMessage(), resultException);
                                        executionResultFuture.completeExceptionally(resultException);
                                        return INVALID_MERGE_PAYLOAD_CONTEXT;
                                    }
                                }).collect(Collectors.toList());

                        if (incomingMergeFlows.stream().anyMatch(MergePayloadContext::isTerminal)) {
                            /**
                             * Terminal state reached.
                             * Mark as terminal all outgoing flows from merge point
                             */
                            vertex.getMergePointFuture().complete(new MergePayloadContext().setTerminal(true));

                        } else {

                            final List<MergePayloadContext> activeIncomingMergeFlows = incomingMergeFlows.stream()
                                    .filter(context -> context != INVALID_MERGE_PAYLOAD_CONTEXT)
                                    .filter(context -> !context.isDeadTransition())
                                    .collect(Collectors.toList());

                            if (vertex.getProcessorInfo().getProcessorType() == ReactorGraph.ProcessorType.DETACHED_MERGE_POINT) {
                                /**
                                 * Detached merge point
                                 */
                                if(activeIncomingMergeFlows.size() == 0){
                                    throw new IllegalStateException(String.format("There is no incoming merge flows for detached merge point %s", vertex.getProcessor()));

                                } else {
                                    if (activeIncomingMergeFlows.size() > 1) {
                                        log.warn("There is more than one active incoming flow for detached merge point for processor {}." +
                                                        " Payload context will be used only from one of active flows." +
                                                        " Other active flows will be ignored." +
                                                        " Possible loss of computation results." +
                                                        " Possible concurrent modifications of payload.",
                                                vertex.getProcessor());
                                    }

                                    merge(vertex, Optional.empty(), activeIncomingMergeFlows.get(0).getPayload(), executionResultFuture);
                                }
                            } else {
                                if (activeIncomingMergeFlows.size() == 0) {
                                    /**
                                     * There is no active incoming merge flow for given merge point.
                                     */
                                    merge(vertex, handlePayloadContext.getProcessorResult(), handlePayloadContext.getPayload(), executionResultFuture);

                                } else {
                                    if (activeIncomingMergeFlows.size() > 1) {
                                        log.warn("There is more than one active incoming flow for merge point for processor {}." +
                                                        " Payload context will be used only from one of active flows." +
                                                        " Other active flows will be ignored." +
                                                        " Possible loss of computation results." +
                                                        " Possible concurrent modifications of payload.",
                                                vertex.getProcessor());
                                    }

                                    merge(vertex, handlePayloadContext.getProcessorResult(), activeIncomingMergeFlows.get(0).getPayload(), executionResultFuture);
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
            ReactorGraph.ProcessorInfo processorInfo,
            ProcessingGraphItem processingItem,
            Object payload) {

        if (processingItem instanceof GraphProcessor) {
            return invokeHandlingMethod(processorInfo,
                    (GraphProcessor) processingItem,
                    payload);
        } else if (processingItem instanceof SubgraphProcessor) {
            return invokeHandlingMethod(processorInfo,
                    (SubgraphProcessor) processingItem,
                    payload);
        } else {
            throw new IllegalStateException(String.format("processing item of type %s not supported", processingItem.getClass()));
        }
    }

    private CompletableFuture<?> invokeHandlingMethod(
            ReactorGraph.ProcessorInfo processorInfo,
            SubgraphProcessor graphProcessor,
            Object payload) {

        Object param = processorInfo.subgraphDescription.arg.apply(payload);
        if (processorInfo.subgraphDescription.isCopyArg) {
            param = threadsafeCopyMaker.makeThreadsafeCopy(param);
        }

        return subgraphRunner.run(param);
    }

    private CompletableFuture<?> invokeHandlingMethod(
            ReactorGraph.ProcessorInfo processorInfo,
            GraphProcessor graphProcessor,
            Object payload) {

        Object processor = graphProcessor.getProcessor();
        GraphProcessorDescription description = processorInfo.description;

        try {

            Object param1 = null;
            Object param2 = null;
            Object param3 = null;
            Object param4 = null;
            Object param5 = null;

            if (description.arg1 != null) {
                param1 = processorInfo.description.arg1.apply(payload);
                if (description.isCopyArg1) {
                    param1 = threadsafeCopyMaker.makeThreadsafeCopy(param1);
                }
            }
            if (description.arg2 != null) {
                param2 = processorInfo.description.arg2.apply(payload);
                if (description.isCopyArg2) {
                    param2 = threadsafeCopyMaker.makeThreadsafeCopy(param2);
                }
            }
            if (description.arg3 != null) {
                param3 = processorInfo.description.arg3.apply(payload);
                if (description.isCopyArg3) {
                    param3 = threadsafeCopyMaker.makeThreadsafeCopy(param3);
                }
            }
            if (description.arg4 != null) {
                param4 = processorInfo.description.arg4.apply(payload);
                if (description.isCopyArg4) {
                    param4 = threadsafeCopyMaker.makeThreadsafeCopy(param4);
                }
            }
            if (description.arg5 != null) {
                param5 = processorInfo.description.arg5.apply(payload);
                if (description.isCopyArg5) {
                    param5 = threadsafeCopyMaker.makeThreadsafeCopy(param5);
                }
            }


            if (processorInfo.description.handler0 != null) {
                return (CompletableFuture) processorInfo.description.handler0.apply(
                        processor
                );

            } else if (processorInfo.description.handler1 != null) {
                return (CompletableFuture) processorInfo.description.handler1.apply(
                        processor,
                        param1);


            } else if (processorInfo.description.handler2 != null) {
                return (CompletableFuture) processorInfo.description.handler2.apply(
                        processor,
                        param1,
                        param2
                );
            } else if (processorInfo.description.handler3 != null) {
                return (CompletableFuture) processorInfo.description.handler3.apply(
                        processor,
                        param1,
                        param2,
                        param3
                );

            } else if (processorInfo.description.handler4 != null) {
                return (CompletableFuture) processorInfo.description.handler4.apply(
                        processor,
                        param1,
                        param2,
                        param3,
                        param4
                );

            } else if (processorInfo.description.handler5 != null) {
                return (CompletableFuture) processorInfo.description.handler5.apply(
                        processor,
                        param1,
                        param2,
                        param3,
                        param4,
                        param5
                );

            } else {
                CompletableFuture result = new CompletableFuture();
                result.completeExceptionally(
                        new IllegalArgumentException(
                                String.format("There is no handler in processor %s for payload %s %s",
                                        processor.getClass(),
                                        payload.getClass(),
                                        payload)));
                return result;
            }

        } catch (Exception exc) {
            CompletableFuture result = new CompletableFuture();
            result.completeExceptionally(
                    new IllegalArgumentException(
                            String.format("Exception during handling in processor %s for payload %s %s",
                                    processor.getClass(),
                                    payload.getClass(),
                                    payload),
                            exc));
            return result;
        }
    }


    private <PayloadType> void handle(ProcessingVertex processingVertex,
                                      TransitionPayloadContext payloadContext,
                                      CompletableFuture<PayloadType> executionResultFuture) {

        ReactorGraph.ProcessorInfo processorInfo = processingVertex.getProcessorInfo();
        Object payload = payloadContext.getPayload();

        /**
         * In case of detached merge point processor should not have incoming handling transition.
         */
        if (processorInfo.getProcessorType() == ReactorGraph.ProcessorType.DETACHED_MERGE_POINT) {
            throw new IllegalStateException(String.format("Processor %s is of type %s and should not have any incoming handling transition",
                    processingVertex.getProcessor(),
                    processorInfo.getProcessorType()));
        }

        ProfiledCall handleCall = profiler.profiledCall(ProfilerNames.PROCESSOR_HANDLE +
                processingVertex.getProcessor().getProfilingName())
                .start();

        CompletableFuture<?> handlingResult;

        /**
         * Immutability check ensures that there is no payload modification during handling.
         */
        final ImmutabilityControlLevel controlLevel = this.immutabilityControlLevel;

        Object payloadClone;
        ImmutabilityChecker.Snapshot payloadCloneSnapshot;

        if (controlLevel != ImmutabilityControlLevel.NO_CONTROL) {
            /**
             * Invoke handling with immutability check.
             * Do not use origin payload, make two copies of payload.
             */
            payloadClone = immutabilityChecker.clone(payload);
            payloadCloneSnapshot = immutabilityChecker.takeSnapshot(payloadClone);

            handlingResult = invokeHandlingMethod(processorInfo, processingVertex.getProcessor(), payloadClone);

        } else {
            /**
             * Invoke handling without immutability check.
             */
            payloadClone = null;
            payloadCloneSnapshot = null;

            handlingResult = invokeHandlingMethod(processorInfo, processingVertex.getProcessor(), payload);
        }

        handlingResult.handleAsync((res, thr) -> {
            handleCall.stop();

            if (controlLevel != ImmutabilityControlLevel.NO_CONTROL) {

                Optional<String> diff = immutabilityChecker.diff(payloadCloneSnapshot, payloadClone);
                if (diff.isPresent()) {
                    String message = String.format("Concurrent modification of payload %s. Diff: %s.", payload, diff.get());

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
                                log.error("Overwriting execution exception {} by immutability check exception {}.", thr, immutabilityException, thr);
                            }
                            thr = immutabilityException;
                            break;
                    }
                }
            }

            if (thr != null) {
                RuntimeException exc = new RuntimeException(String.format(
                        "Failed handling by processor %s for payload %s %s",
                        processingVertex.getProcessor().getClass(), payload.getClass(), payload), thr);

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
     *
     * @param processingVertex
     * @param processorResult empty in case of detached merge point
     * @param payload
     * @param executionResultFuture
     * @param <PayloadType>
     */
    private <PayloadType> void merge(ProcessingVertex processingVertex,
                                     Object processorResult,
                                     Object payload,
                                     CompletableFuture<PayloadType> executionResultFuture) {

        ReactorGraph.ProcessorInfo processorInfo = processingVertex.getProcessorInfo();

        Supplier<Enum> mergerInvocation;

        switch (processorInfo.getProcessorType()) {
            case SUBGRAPH:
            case PLAIN:
                if (processorInfo.getMerger() == null) {
                    /**
                     * This is GraphProcessor or SubgraphProcessor without merger
                     */
                    processingVertex.getMergePointFuture().complete(new MergePayloadContext().setDeadTransition(true));
                    return;
                } else {
                    mergerInvocation = () -> (Enum) processorInfo.getMerger().merge(payload, processorResult);
                }
                break;

            case DETACHED_MERGE_POINT:
                mergerInvocation = () -> (Enum) processorInfo.getDetachedMerger().merge(payload);
                break;

            default:
                throw new IllegalArgumentException(String.format("Unknown processor type: %s", processorInfo.getProcessorType()));
        }


        try {
            ProfiledCall mergeCall = profiler.profiledCall(ProfilerNames.PROCESSOR_MERGE + processingVertex.getProcessor().getProfilingName())
                    .start();

            Enum mergeStatus = mergerInvocation.get();

            mergeCall.stop();

            /**
             * Select outgoing transitions that matches mergeStatus
             */
            List<ReactorGraph.Transition> activeTransitions = processingVertex.getMergePointTransition().stream()
                    .filter(transition -> transition.isOnAny() || transition.getMergeStatuses().contains(mergeStatus))
                    .collect(Collectors.toList());

            if (activeTransitions.size() <= 0) {
                throw new IllegalStateException(String.format("Merging process returned %s status." +
                                " But merge point of processor %s does not have matching transition for this status.",
                        mergeStatus,
                        processingVertex.getProcessor()));
            }

            /**
             * check if this merge point have terminal transitions that matches merge status
             */
            if (activeTransitions.stream().anyMatch(ReactorGraph.Transition::isComplete)) {

                /**
                 * Handle terminal transition by completing execution result
                 */
                if (!executionResultFuture.complete((PayloadType) payload)) {

                    Object previousResult = null;
                    try {
                        if (executionResultFuture.isDone()) {
                            previousResult = executionResultFuture.get();
                        } else {
                            log.error("Illegal graph execution state. Completion failed for new result, but execution result from previous terminal step is not complete.");
                        }
                    } catch (Exception exc) {
                        log.error("Failed to get completed execution result from previous terminal step.", exc);
                    }

                    log.error("Processing chain was completed by at least two different terminal steps." +
                                    " Already completed with result {}." +
                                    " New completion result {} in merge point for processor {}",
                            previousResult,
                            payload,
                            processingVertex.getProcessor());
                }

                /**
                 * Terminal state reached. Execution result completed.
                 * Throw poison pill - terminal context. All following merge points should be deactivated.
                 */
                processingVertex.getMergePointFuture().complete(new MergePayloadContext().setPayload(null).setMergeResult(mergeStatus).setTerminal(true));
            } else {
                /**
                 * There is no terminal state reached after merging.
                 */
                processingVertex.getMergePointFuture().complete(new MergePayloadContext().setPayload(payload).setMergeResult(mergeStatus));
            }

        } catch (Exception exc) {
            log.error("Failed to merge payload {} {} by processor {}  for result {}",
                    payload.getClass(),
                    payload,
                    processingVertex.getProcessor(),
                    processorResult,
                    exc);

            executionResultFuture.completeExceptionally(exc);

            processingVertex.getMergePointFuture().complete(new MergePayloadContext().setDeadTransition(true));
        }
    }
}
