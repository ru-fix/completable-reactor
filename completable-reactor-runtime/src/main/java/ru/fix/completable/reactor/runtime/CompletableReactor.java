package ru.fix.completable.reactor.runtime;

import lombok.*;
import lombok.experimental.Accessors;
import ru.fix.commons.profiler.PrefixedProfiler;
import ru.fix.commons.profiler.ProfiledCall;
import ru.fix.commons.profiler.Profiler;
import ru.fix.completable.reactor.runtime.cloning.CopierThreadsafeCopyMaker;
import ru.fix.completable.reactor.runtime.cloning.ThreadsafeCopyMaker;
import ru.fix.completable.reactor.runtime.execution.ReactorGraphExecution;
import ru.fix.completable.reactor.runtime.execution.ReactorGraphExecutionBuilder;
import ru.fix.completable.reactor.runtime.immutability.ClonerWithReflectionImmutabilityChecker;
import ru.fix.completable.reactor.runtime.immutability.ImmutabilityChecker;
import ru.fix.completable.reactor.runtime.immutability.ImmutabilityControlLevel;
import ru.fix.completable.reactor.runtime.internal.CRReactorGraph;
import ru.fix.completable.reactor.runtime.internal.ReactorReflector;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public class CompletableReactor implements AutoCloseable {

    private final Profiler profiler;

    private final ImmutabilityChecker immutabilityChecker = new ClonerWithReflectionImmutabilityChecker();

    private final ThreadsafeCopyMaker threadsafeCopyMaker = new CopierThreadsafeCopyMaker();

    private final ReactorGraphExecutionBuilder executionBuilder;

    private final Map<Class<?>, CRReactorGraph> payloadGraphs = new ConcurrentHashMap<>();

    /**
     * {@code Function<PayloadType, CompletableFuture<PayloadType>>}
     */
    private final Map<Class<?>, Function> inlinePayloadGraphs = new ConcurrentHashMap<>();

    private final ScheduledExecutorService timeoutExecutorService = newScheduledThreadPool(
            1,
            "completable-reactor-check-timeout-");

    private volatile long executionTimeout = TimeUnit.MINUTES.toMillis(15);

    private AtomicBoolean isClosed = new AtomicBoolean();

    final Map<Class<?>, PayloadStatCounters> payloadStatCounters = new ConcurrentHashMap<>();

    private final AtomicLong maxPendingRequestCount = new AtomicLong(100_000);

    /**
     * Used as a synchronized monitor.
     * Notify when reach 0.
     */
    private final AtomicLong pendingRequestCount = new AtomicLong();

    private final AtomicLong closeTimeoutMs = new AtomicLong(120_000);

    public CompletableReactor(Profiler profiler) {
        this.profiler = new PrefixedProfiler(profiler, ProfilerNames.PROFILER_PREFIX);
        this.executionBuilder = new ReactorGraphExecutionBuilder(
                profiler,
                immutabilityChecker,
                threadsafeCopyMaker,
                payload -> {
                    try {
                        return this.submit(payload).getResultFuture();
                    } catch (Exception exc) {
                        CompletableFuture result = new CompletableFuture();
                        result.completeExceptionally(exc);
                        return result;
                    }
                });
    }

    /**
     * If this flag is enabled then internal processing graph state will be attached to Execution result.
     * This allows easy access to execution state during debug.
     * One of drawbacks of this future is that GC will be prevented form removing internal state objects
     * until all reference to execution result dies.
     *
     * @param debugProcessingVertexGraphState By default this flag is disabled.
     */
    public CompletableReactor setDebugProcessingVertexGraphState(boolean debugProcessingVertexGraphState) {
        executionBuilder.setDebugProcessingVertexGraphState(debugProcessingVertexGraphState);
        return this;
    }

    public long getMaxPendingRequestCount() {
        return maxPendingRequestCount.get();
    }

    public CompletableReactor setMaxPendingRequestCount(long value) {
        maxPendingRequestCount.set(value);
        return this;
    }

    public long getCloseTimeoutMs() {
        return closeTimeoutMs.get();
    }

    public void setCloseTimeoutMs(long value) {
        closeTimeoutMs.set(value);
    }

    public long getExecutionTimeout() {
        return executionTimeout;
    }

    public void setExecutionTimeout(long value) {
        executionTimeout = value;
    }

    /**
     * Register reactor graph
     * @param reactorGraph
     */
    public void registerReactorGraph(ReactorGraph reactorGraph) {
        val crReactorGraph = (CRReactorGraph) reactorGraph;
        payloadGraphs.put(crReactorGraph.getPayloadClass(), crReactorGraph);
        inlinePayloadGraphs.remove(crReactorGraph.getPayloadClass());
    }

    /**
     * Register functional graph implementation.
     * For test subgraph mocking purpose.
     * If graph with same payload already registered by {@link #registerReactorGraph(ReactorGraph)} it will be unregistered.
     *
     * @param payloadType
     * @param payloadProcessingFunction
     * @param <PayloadType>
     */
    public <PayloadType> void registerReactorGraph(
            Class<PayloadType> payloadType,
            Function<PayloadType, CompletableFuture<PayloadType>> payloadProcessingFunction) {

        inlinePayloadGraphs.put(payloadType, payloadProcessingFunction);
        payloadGraphs.remove(payloadType);
    }

    @Data
    @Accessors(chain = true)
    static class PayloadStatCounters {
        final LongAdder runningTotal = new LongAdder();
        final LongAdder runningWithoutResult = new LongAdder();

    }


    @Data
    @Accessors(chain = true)
    public static class PayloadStatisticsReport {
        final long runningTotal;
        final long runningWithoutResult;
    }

    @Data
    @Accessors(chain = true)
    public static class StatisticsReport {
        final Map<Class<?>, PayloadStatisticsReport> payloadStatisticsReports = new HashMap<>();
    }

    private static ScheduledThreadPoolExecutor newScheduledThreadPool(int poolSize, String threadsNamePrefix) {
        AtomicInteger counter = new AtomicInteger();
        ThreadFactory threadFactory = runnable -> new Thread(runnable, threadsNamePrefix + counter.getAndIncrement());
        return new ScheduledThreadPoolExecutor(poolSize, threadFactory);
    }


    public void setImmutabilityControlLevel(ImmutabilityControlLevel immutabilityControlLevel) {
        this.immutabilityControlLevel.set(immutabilityControlLevel);
    }

    final AtomicReference<ImmutabilityControlLevel> immutabilityControlLevel = new AtomicReference<>(ImmutabilityControlLevel.NO_CONTROL);

    public StatisticsReport buildStatisticsReport() {
        StatisticsReport report = new StatisticsReport();

        payloadStatCounters.forEach((payloadType, counters) -> {
            report.getPayloadStatisticsReports().put(
                    payloadType,
                    new PayloadStatisticsReport(
                            counters.getRunningTotal().sum(),
                            counters.getRunningWithoutResult().sum()));
        });

        return report;
    }

    @Data
    @Builder
    public static class Execution<PayloadType> {
        /**
         * Completes when all processes within given execution of single request is complete
         */
        final CompletableFuture<Void> chainExecutionFuture;
        /**
         * Will be completed with payload when terminal graph state would be reached.
         */
        final CompletableFuture<PayloadType> resultFuture;

        /**
         * Debug purpose field that allows to check internal execution graph state
         */
        @Getter(AccessLevel.NONE)
        private final Collection debugProcessingVertexGraphState;
    }


    public <PayloadType> Execution<PayloadType> submit(PayloadType payload) throws PendingOperationsLimitOverflowException {
        return submit(payload, executionTimeout);
    }

    public <PayloadType> Execution<PayloadType> submit(PayloadType payload, long timeoutMs) throws PendingOperationsLimitOverflowException {
        return trySubmit(payload, timeoutMs)
                .orElseThrow(() ->
                        new PendingOperationsLimitOverflowException(String.format(
                                "Limit pending operations has been exceeded." +
                                        " Max pending operations: %d." +
                                        " Pending operations: %d. " +
                                        " Payload %s is rejected.",
                                maxPendingRequestCount.get(),
                                pendingRequestCount.get(),
                                payload)));
    }

    public <PayloadType> Optional<Execution<PayloadType>> trySubmit(PayloadType payload) {
        return trySubmit(payload, executionTimeout);
    }

    public <PayloadType> Optional<Execution<PayloadType>> trySubmit(PayloadType payload, long timeoutMs) {

        if (isClosed.get()) {
            throw new IllegalStateException(String.format("CompletableReactor is closed. Payload %s is discarded.", payload));
        }

        if (pendingRequestCount.get() > maxPendingRequestCount.get()) {
            return Optional.empty();
        }

        ProfiledCall payloadCall = profiler.profiledCall(ProfilerNames.PAYLOAD + payload.getClass().getSimpleName())
                .start();

        /**
         * Inline graph execution scenario
         */
        Function inlineGraphFunction = inlinePayloadGraphs.get(payload.getClass());
        if(inlineGraphFunction != null){
            CompletableFuture<PayloadType> inlineGraphResult = (CompletableFuture<PayloadType>) inlineGraphFunction.apply(payload);
            inlineGraphResult.thenAcceptAsync(any -> payloadCall.stop());

            return Optional.of(
                    Execution.<PayloadType>builder()
                            .chainExecutionFuture(inlineGraphResult.thenAccept(any -> {/* do nothing */}))
                            .resultFuture(inlineGraphResult)
                            .build());

        }

        /**
         * Standard graph execution scenario
         */

        ReactorGraph<PayloadType> graph = payloadGraphs.get(payload.getClass());
        if (graph == null) {
            throw new IllegalArgumentException(String.format("Rector graph not found for payload %s", payload.getClass()));
        }

        ReactorGraphExecution<PayloadType> execution = executionBuilder.build(graph);


        /**
         * Handling pending request counts
         */
        pendingRequestCount.incrementAndGet();

        PayloadStatCounters statistics = payloadStatCounters.computeIfAbsent(payload.getClass(), key -> new PayloadStatCounters());
        statistics.getRunningTotal().increment();

        execution.getChainExecutionFuture().handleAsync((result, thr) -> {
            statistics.getRunningTotal().decrement();
            return null;
        });

        execution.getResultFuture().handleAsync((result, thr) -> {
            statistics.getRunningWithoutResult().decrement();
            return null;
        });

        /**
         * Launching chain execution
         */
        execution.getSubmitFuture().complete(payload);


        /**
         * Add timeout protection to execution
         */
        ScheduledFuture<?> schedule = timeoutExecutorService.schedule(
                () -> {
                    /**
                     * Temporary solution.
                     * Should be fixed by completing all futures in processor chain
                     */
                    if (!execution.getResultFuture().isDone()) {
                        execution.getResultFuture().completeExceptionally(
                                new TimeoutException(
                                        String.format("Response for payload %s took more than %d ms.", payload, timeoutMs)));
                    }
                    if (!execution.getChainExecutionFuture().isDone()) {
                        execution.getChainExecutionFuture().completeExceptionally(
                                new TimeoutException(
                                        String.format("Execution of payload %s took more than %d ms.", payload, timeoutMs)));
                    }
                },
                timeoutMs,
                TimeUnit.MILLISECONDS);

        execution.getChainExecutionFuture().thenRunAsync(() -> {
            long count = pendingRequestCount.decrementAndGet();
            if (count == 0) {
                synchronized (pendingRequestCount) {
                    pendingRequestCount.notifyAll();
                }
            }

            schedule.cancel(false);
        });

        execution.getResultFuture().thenRunAsync(payloadCall::stop);

        return Optional.of(
                Execution.<PayloadType>builder()
                        .chainExecutionFuture(execution.getChainExecutionFuture())
                        .resultFuture(execution.getResultFuture())
                        .debugProcessingVertexGraphState(execution.getDebugProcessingVertexGraphState())
                        .build());
    }

    /**
     * Blocks until all pending request is complete
     *
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        isClosed.set(true);

        if (pendingRequestCount.get() > 0) {
            long deadline = System.currentTimeMillis() + closeTimeoutMs.get();

            synchronized (pendingRequestCount) {
                while (pendingRequestCount.get() > 0) {
                    long timeoutLeft = deadline - System.currentTimeMillis();
                    if (timeoutLeft <= 0) {
                        break;
                    }
                    pendingRequestCount.wait(timeoutLeft);
                }
            }
        }
    }

    /**
     * Write *.rg file representation of graph structure
     * *.rg files used by IDE and Graph Viewer to visualize graph
     * @param graphs
     * @throws Exception
     */
    public static void write(ReactorGraph<?>... graphs) throws Exception {
        for (ReactorGraph<?> graph : graphs) {

            val model = graph.serialize();
            model.serializationPointSource = ReactorReflector.getMethodInvocationPoint().orElse(null);

            val content = ReactorMarshaller.marshall(model);

            val path = Paths.get(((CRReactorGraph)graph).getPayloadClass().getName() + ".rg");
            Files.write(path, content.getBytes(StandardCharsets.UTF_8));
        }
    }
}