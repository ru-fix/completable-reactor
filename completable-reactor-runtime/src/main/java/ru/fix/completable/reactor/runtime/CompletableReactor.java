package ru.fix.completable.reactor.runtime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fix.commons.profiler.PrefixedProfiler;
import ru.fix.commons.profiler.ProfiledCall;
import ru.fix.commons.profiler.Profiler;
import ru.fix.completable.reactor.graph.Graph;
import ru.fix.completable.reactor.graph.internal.GlGraph;
import ru.fix.completable.reactor.runtime.cloning.CopierThreadsafeCopyMaker;
import ru.fix.completable.reactor.runtime.cloning.ThreadsafeCopyMaker;
import ru.fix.completable.reactor.runtime.debug.DebugSerializer;
import ru.fix.completable.reactor.runtime.debug.ToStringDebugSerializer;
import ru.fix.completable.reactor.runtime.execution.ExecutionBuilder;
import ru.fix.completable.reactor.runtime.execution.ReactorGraphExecution;
import ru.fix.completable.reactor.runtime.execution.ReactorGraphExecutionBuilder;
import ru.fix.completable.reactor.runtime.immutability.ImmutabilityChecker;
import ru.fix.completable.reactor.runtime.immutability.ImmutabilityControlLevel;
import ru.fix.completable.reactor.runtime.immutability.ReflectionImmutabilityChecker;
import ru.fix.completable.reactor.runtime.internal.CRReactorGraph;
import ru.fix.completable.reactor.runtime.tracing.Tracer;

import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public class CompletableReactor implements AutoCloseable {
    private static final Logger log = LoggerFactory.getLogger(CompletableReactor.class);

    private final Profiler profiler;

    private final ImmutabilityChecker immutabilityChecker = new ReflectionImmutabilityChecker();

    private final ThreadsafeCopyMaker threadsafeCopyMaker = new CopierThreadsafeCopyMaker();

    private final DebugSerializer debugSerializer = new ToStringDebugSerializer();

    private final ReactorGraphExecutionBuilder executionBuilder;
    private final ExecutionBuilder glExecutionBuilder;

    private final Map<Class<?>, CRReactorGraph> payloadGraphs = new ConcurrentHashMap<>();

    /**
     * {@code Function<PayloadType, CompletableFuture<PayloadType>>}
     */
    private final Map<Class<?>, Function> inlinePayloadGraphs = new ConcurrentHashMap<>();

    private final ScheduledExecutorService timeoutExecutorService = newScheduledThreadPool(
            1,
            "completable-reactor-check-timeout-");

    private volatile long executionTimeoutMs = TimeUnit.MINUTES.toMillis(15);

    private AtomicBoolean isClosed = new AtomicBoolean();

    final Map<Class<?>, PayloadStatCounters> payloadStatCounters = new ConcurrentHashMap<>();

    private final AtomicLong maxPendingRequestCount = new AtomicLong(Long.MAX_VALUE);

    /**
     * Used as a synchronized monitor.
     * Notify when reach 0.
     */
    private final AtomicLong pendingRequestCount = new AtomicLong();

    private final AtomicLong closeTimeoutMs = new AtomicLong(120_000);

    private final ConcurrentHashMap<Class<?>, GlGraph> glPayloadGraphs = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Class<? extends Graph>, Boolean> glGraphConfigs = new ConcurrentHashMap<>();

    public <GraphConfigType extends Graph>
    void registerGraphIfAbsent(GraphConfigType graphConfig) {
        Objects.requireNonNull(graphConfig, "graphConfig");

        glGraphConfigs.computeIfAbsent(graphConfig.getClass(), type -> {

            Class payloadType = getPayloadTypeForGraphConfigBasedClass(graphConfig.getClass());
            GlGraph graph = graphBuilder.buildGraph(graphConfig, dependencyInjector);

            Object prevGraph = glPayloadGraphs.putIfAbsent(payloadType, graph);
            if (prevGraph != null) {
                throw new IllegalArgumentException(""
                        + "New graph config: " + graphConfig
                        + " of type " + graphConfig.getClass()
                        + " is registering graph for payload " + payloadType + "."
                        + " But this payload was already registered by another graph config.");
            }
            return true;
        });
    }

    private final GraphBuilder graphBuilder = new GraphBuilder();

    //TODO: execute validation during graph registration

    /**
     * Test graph configuration.
     * Check all execution paths.
     * Validated that there is no conflicts between merging vertices and all required endpoints exist.
     */
    public <PayloadType, GraphConfigType extends Graph>
    void registerGraphIfAbsent(Class<GraphConfigType> graphConfigClass) {
        Objects.requireNonNull(graphConfigClass, "graphConfigClass");

        glGraphConfigs.computeIfAbsent(graphConfigClass, type -> {
            try {

                Class payloadType = getPayloadTypeForGraphConfigBasedClass(graphConfigClass);


                Graph graphConfig;
                try {
                    Constructor<GraphConfigType> ctor = graphConfigClass.getDeclaredConstructor();
                    if (!ctor.isAccessible()) {
                        ctor.setAccessible(true);
                    }
                    graphConfig = ctor.newInstance();
                } catch (Exception exc) {
                    throw new IllegalArgumentException(""
                            + "Failed to instantiate graph config instance of class: " + graphConfigClass + "."
                            + " Graph config class should have default no arg constructor."
                            + " If it is inner class then it sould be static.",
                            exc);
                }
                GlGraph graph = graphBuilder.buildGraph(graphConfig, dependencyInjector);

                glPayloadGraphs.putIfAbsent(payloadType, graph);

            } catch (Exception exc) {
                throw new RuntimeException(
                        "Failed to register graph config in ReactorGraph: " + graphConfigClass,
                        exc);
            }
            return true;
        });
    }


    private Class getPayloadTypeForGraphConfigBasedClass(Class graphConfigClass) {

        if (graphConfigClass.getSuperclass() == null) {
            throw new IllegalArgumentException(""
                    + "Superclass of graph config class " + graphConfigClass + " is NULL."
                    + " Graph config class should extend GraphConfig<PayloadType>."
            );
        }

        if (!Graph.class.isAssignableFrom(graphConfigClass)) {
            throw new IllegalArgumentException(""
                    + "Superclass of graph config class " + graphConfigClass + " is not GraphConfig<PayloadType>."
                    + " Superclass of graph config class should be GraphConfig<PayloadType>."
                    + " Actual superclass of graph config is: " + graphConfigClass.getSuperclass());
        }

        if (graphConfigClass.getGenericSuperclass() == null) {
            throw new IllegalArgumentException(""
                    + "Generic Superclass of graph config class " + graphConfigClass + " is NULL."
                    + " Superclass of graph config class should be generic GraphConfig<PayloadType>.");
        }

        if (!(graphConfigClass.getGenericSuperclass() instanceof ParameterizedType)) {
            throw new IllegalArgumentException(""
                    + "Superclass of graph config class " + graphConfigClass + " is not ParameterizedType"
                    + " Superclass of graph config class should be GraphConfig<PayloadType>."
                    + " Actual generic superclass of graph config is: " + graphConfigClass.getGenericSuperclass());
        }

        Type[] genericConfigParams = ((ParameterizedType) graphConfigClass.getGenericSuperclass())
                .getActualTypeArguments();

        if (genericConfigParams.length != 1) {
            throw new IllegalArgumentException(""
                    + "Superclass of graph config class " + graphConfigClass + " should have 1 generic type parameter."
                    + " Superclass of graph config class should be GraphConfig<PayloadType>."
                    + " Actual super class is : " + graphConfigClass.getGenericSuperclass()
                    + " Actual parameter count of superclass is : " + genericConfigParams.length);
        }

        if (genericConfigParams[0] == null) {
            throw new IllegalArgumentException(""
                    + "Superclass of graph config class " + graphConfigClass
                    + " should have 1 non null generic type parameter."
                    + " Superclass of graph config class should be GraphConfig<PayloadType>."
                    + " Actual super class is : " + graphConfigClass.getGenericSuperclass()
                    + " Actual generic parameter of superclass is null.");
        }

        if (!(genericConfigParams[0] instanceof Class)) {
            throw new IllegalArgumentException(""
                    + "Superclass of graph config " + graphConfigClass
                    + " should have generic type parameter of type Class."
                    + " Superclass of graph config class should be GraphConfig<PayloadType>."
                    + " Actual super class is : " + graphConfigClass.getGenericSuperclass()
                    + " Actual generic parameter type is : " + genericConfigParams[0].getClass());
        }

        return (Class) genericConfigParams[0];

    }


    //TODO: fix injection
    private DependencyInjector dependencyInjector;

    public void registerDependecnyInjector(DependencyInjector dependencyInjector) {
        this.dependencyInjector = dependencyInjector;
    }


    private static class ReactorTracer implements Tracer {
        private static final Logger log = LoggerFactory.getLogger(ReactorTracer.class);

        private volatile Tracer tracer;

        @Override
        public boolean isTraceable(Object payload) {
            try {
                return tracer != null && tracer.isTraceable(payload);
            } catch (Exception exc) {
                log.error("Failed to call isTraceable method of tracer.", exc);
                return false;
            }
        }

        @Override
        public Object beforeHandle(String vertexName, Object payload) {
            try {
                return tracer.beforeHandle(vertexName, payload);
            } catch (Exception exc) {
                log.error("Failed to call beforeHandle method of tracer.", exc);
                return null;
            }
        }

        @Override
        public void afterHandle(Object tracingMarker,
                                String vertexName,
                                Object handlerResult,
                                Throwable throwable) {
            try {
                tracer.afterHandle(tracingMarker, vertexName, handlerResult, throwable);
            } catch (Exception exc) {
                log.error("Failed to call afterHandle method of tracer.", exc);
            }
        }

        @Override
        public Object beforeMerge(String vertexName, Object payload, Object handleResult) {
            try {
                return tracer.beforeMerge(vertexName, payload, handleResult);
            } catch (Exception exc) {
                log.error("Failed to call beforeMerge method of tracer.", exc);
                return null;
            }
        }

        @Override
        public void afterMerger(Object tracingMarker, String vertexName, Object payload) {
            try {
                tracer.afterMerger(tracingMarker, vertexName, payload);
            } catch (Exception exc) {
                log.error("Failed to call afterMerger method of tracer.", exc);
            }
        }

    }

    private final ReactorTracer reactorTracer = new ReactorTracer();

    public CompletableReactor(Profiler profiler) {
        this.profiler = new PrefixedProfiler(profiler, ProfilerNames.PROFILER_PREFIX);
        this.executionBuilder = new ReactorGraphExecutionBuilder(
                profiler,
                immutabilityChecker,
                threadsafeCopyMaker,
                payload -> {
                    try {
                        return this.internalSubmit(payload, executionTimeoutMs).getResultFuture();
                    } catch (Exception exc) {
                        CompletableFuture result = new CompletableFuture();
                        result.completeExceptionally(exc);
                        return result;
                    }
                },
                debugSerializer,
                reactorTracer);

        this.glExecutionBuilder = new ExecutionBuilder(
                profiler,
                immutabilityChecker,
                threadsafeCopyMaker,
                payload -> {
                    try {
                        return this.internalSubmit(payload, executionTimeoutMs).getResultFuture();
                    } catch (Exception exc) {
                        CompletableFuture result = new CompletableFuture();
                        result.completeExceptionally(exc);
                        return result;
                    }
                },
                debugSerializer,
                reactorTracer);
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
        glExecutionBuilder.setDebugProcessingVertexGraphState(debugProcessingVertexGraphState);
        return this;
    }

    public long getMaxPendingRequestCount() {
        return maxPendingRequestCount.get();
    }

    public CompletableReactor setMaxPendingRequestCount(long value) {
        maxPendingRequestCount.set(value);
        return this;
    }

    /**
     * @return timeout in millisecond reactor will wain pending flows during {@link #close()} method
     */
    public long getCloseTimeoutMs() {
        return closeTimeoutMs.get();
    }

    /**
     * @param value timeout in millisecond reactor will wain pending flows during {@link #close()} method
     */
    public void setCloseTimeoutMs(long value) {
        closeTimeoutMs.set(value);
    }

    /**
     * @return timeout in milliseconds reactor will wain for flow to execute until terminating
     * it with TimeoutException
     */
    public long getExecutionTimeoutMs() {
        return executionTimeoutMs;
    }

    /**
     * @param value timeout in milliseconds reactor will wain for flow to execute until terminating
     *              it with TimeoutException
     */
    public void setExecutionTimeoutMs(long value) {
        executionTimeoutMs = value;
    }

    public void setTracer(Tracer tracer) {
        this.reactorTracer.tracer = tracer;
    }

    public Tracer getTracer() {
        return this.reactorTracer.tracer;
    }

    public void removeTracer() {
        this.reactorTracer.tracer = null;
    }

    /**
     * Register reactor graph
     *
     * @param reactorGraph
     */
    public void registerReactorGraph(ReactorGraph reactorGraph) {
        CRReactorGraph crReactorGraph = (CRReactorGraph) reactorGraph;
        payloadGraphs.put(crReactorGraph.getPayloadClass(), crReactorGraph);
        inlinePayloadGraphs.remove(crReactorGraph.getPayloadClass());
    }

    /**
     * //TODO remove
     * Register functional graph implementation.
     * For test subgraph mocking purpose.
     * If graph with same payload already registered by {@link #registerReactorGraph(ReactorGraph)}
     * it will be unregistered.
     *
     * @param payloadType
     * @param payloadProcessingFunction
     * @param <PayloadType>
     */
    @Deprecated
    public <PayloadType> void registerReactorGraph(
            Class<PayloadType> payloadType,
            Function<PayloadType, CompletableFuture<PayloadType>> payloadProcessingFunction) {

        inlinePayloadGraphs.put(payloadType, payloadProcessingFunction);
        payloadGraphs.remove(payloadType);
    }


    /**
     * Register functional graph implementation.
     * For test subgraph mocking purpose.
     * If graph with same payload already registered by {@link #registerReactorGraph(ReactorGraph)}
     * it will be unregistered.
     *
     * @param payloadType
     * @param payloadProcessingFunction
     * @param <PayloadType>
     */
    public <PayloadType> void registerGraph(
            Class<PayloadType> payloadType,
            Function<PayloadType, CompletableFuture<PayloadType>> payloadProcessingFunction) {

        inlinePayloadGraphs.put(payloadType, payloadProcessingFunction);
        payloadGraphs.remove(payloadType);
    }

    /**
     * Register functional graph implementation.
     * For test subgraph mocking purpose.
     * If graph with same payload already registered by {@link #registerReactorGraph(ReactorGraph)}
     * it will be unregistered.
     *
     * @param payloadType
     * @param payloadProcessingFunction
     * @param <PayloadType>
     */
    public <PayloadType> void registerGraphSync(
            Class<PayloadType> payloadType,
            Function<PayloadType, PayloadType> payloadProcessingFunction) {

        registerGraph(
                payloadType,
                pld -> CompletableFuture.completedFuture(payloadProcessingFunction.apply(pld)));
    }

    //TODO extract statistics

    static class PayloadStatCounters {
        final LongAdder runningTotal = new LongAdder();
        final LongAdder runningWithoutResult = new LongAdder();

    }

    public static class PayloadStatisticsReport {
        final long runningTotal;
        final long runningWithoutResult;

        public PayloadStatisticsReport(long runningTotal, long runningWithoutResult) {
            this.runningTotal = runningTotal;
            this.runningWithoutResult = runningWithoutResult;
        }
    }


    public static class StatisticsReport {
        final Map<Class<?>, PayloadStatisticsReport> payloadStatisticsReports = new HashMap<>();
    }

    private static ScheduledThreadPoolExecutor newScheduledThreadPool(int poolSize, String threadsNamePrefix) {
        AtomicInteger counter = new AtomicInteger();
        ThreadFactory threadFactory = runnable -> new Thread(
                runnable,
                threadsNamePrefix + counter.getAndIncrement());

        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor =
                new ScheduledThreadPoolExecutor(poolSize, threadFactory);
        scheduledThreadPoolExecutor.setRemoveOnCancelPolicy(true);
        return scheduledThreadPoolExecutor;
    }


    public void setImmutabilityControlLevel(ImmutabilityControlLevel immutabilityControlLevel) {
        this.immutabilityControlLevel.set(immutabilityControlLevel);
    }

    final AtomicReference<ImmutabilityControlLevel> immutabilityControlLevel =
            new AtomicReference<>(ImmutabilityControlLevel.NO_CONTROL);

    public StatisticsReport buildStatisticsReport() {
        StatisticsReport report = new StatisticsReport();

        payloadStatCounters.forEach((payloadType, counters) -> {
            report.payloadStatisticsReports.put(
                    payloadType,
                    new PayloadStatisticsReport(
                            counters.runningTotal.sum(),
                            counters.runningWithoutResult.sum()));
        });

        return report;
    }

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
        private final Collection debugProcessingVertexGraphState;

        public Execution(
                CompletableFuture<PayloadType> resultFuture,
                CompletableFuture<Void> chainExecutionFuture,
                Collection debugProcessingVertexGraphState) {
            this.chainExecutionFuture = chainExecutionFuture;
            this.resultFuture = resultFuture;
            this.debugProcessingVertexGraphState = debugProcessingVertexGraphState;
        }

        public CompletableFuture<Void> getChainExecutionFuture() {
            return chainExecutionFuture;
        }

        public CompletableFuture<PayloadType> getResultFuture() {
            return resultFuture;
        }
    }


    public <PayloadType> Optional<Execution<PayloadType>> trySubmit(PayloadType payload) {
        return trySubmit(payload, executionTimeoutMs);
    }

    public <PayloadType> Optional<Execution<PayloadType>> trySubmit(PayloadType payload, long timeoutMs) {
        if (pendingRequestCount.get() > maxPendingRequestCount.get()) {
            return Optional.empty();
        }
        return Optional.of(submit(payload, timeoutMs));
    }

    public <PayloadType> Execution<PayloadType> submit(PayloadType payload) {
        return submit(payload, executionTimeoutMs);
    }

    public <PayloadType> Execution<PayloadType> submit(PayloadType payload, long timeoutMs) {
        if (isClosed.get()) {
            throw new IllegalStateException(String.format(
                    "CompletableReactor is closed. Payload %s is discarded.", payload));
        }
        return internalSubmit(payload, timeoutMs);
    }

    /**
     * Submit request without checking whether reactor closed or not.
     * If maxPendingRequestCount limit is reached prints error message and accepts request.
     *
     * @param payload
     * @param timeoutMs
     * @param <PayloadType>
     * @return
     */
    private <PayloadType> Execution<PayloadType> internalSubmit(PayloadType payload, long timeoutMs) {

        if (pendingRequestCount.get() > maxPendingRequestCount.get()) {
            log.error("Max pending request count is reached. Request will be accepted but there is a possibility of " +
                    "OOM or something wrong with back pressure logic in client code.\n" +
                    "Use trySubmit method that supports back pressure or correctly handle the load on " +
                    "CompletableReactor on client side.");
        }

        ProfiledCall payloadCall = profiler.profiledCall(ProfilerNames.PAYLOAD + payload.getClass().getSimpleName())
                .start();

        /**
         * Inline graph execution scenario
         */
        Function inlineGraphFunction = inlinePayloadGraphs.get(payload.getClass());
        if (inlineGraphFunction != null) {
            CompletableFuture<PayloadType> inlineGraphResult =
                    (CompletableFuture<PayloadType>) inlineGraphFunction.apply(payload);

            inlineGraphResult.thenAcceptAsync(any -> payloadCall.stop());

            return new Execution<>(
                    inlineGraphResult,
                    inlineGraphResult.thenAccept(any -> {/* do nothing */}),
                    null
            );
        }

        /**
         * Standard graph execution scenario
         */
        ReactorGraphExecution<PayloadType> execution;

        ReactorGraph<PayloadType> graph = payloadGraphs.get(payload.getClass());
        if (graph != null) {
            execution = executionBuilder.build(graph);
        } else {
            GlGraph glGraph = glPayloadGraphs.get(payload.getClass());
            if (glGraph != null) {
                execution = glExecutionBuilder.build(glGraph);

            } else {
                throw new IllegalArgumentException(String.format(
                        "Rector graph not found for payload %s", payload.getClass()));
            }
        }


        /**
         * Handling pending request counts
         */
        pendingRequestCount.incrementAndGet();

        PayloadStatCounters statistics = payloadStatCounters.computeIfAbsent(
                payload.getClass(),
                key -> new PayloadStatCounters());

        statistics.runningTotal.increment();

        execution.getChainExecutionFuture().handleAsync((result, thr) -> {
            statistics.runningTotal.decrement();
            return null;
        });

        execution.getResultFuture().handleAsync((result, thr) -> {
            statistics.runningWithoutResult.decrement();
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
                                        String.format(
                                                "Response for payload %s took more than %d ms.", payload, timeoutMs)));
                    }
                    if (!execution.getChainExecutionFuture().isDone()) {
                        execution.getChainExecutionFuture().completeExceptionally(
                                new TimeoutException(
                                        String.format(
                                                "Execution of payload %s took more than %d ms.", payload, timeoutMs)));
                    }
                },
                timeoutMs,
                TimeUnit.MILLISECONDS);

        execution.getChainExecutionFuture().handleAsync((result, throwable) -> {
            long count = pendingRequestCount.decrementAndGet();
            if (count == 0) {
                synchronized (pendingRequestCount) {
                    pendingRequestCount.notifyAll();
                }
            }

            schedule.cancel(false);

            return null;
        });

        execution.getResultFuture().thenRunAsync(payloadCall::stop);

        return new Execution<>(
                execution.getResultFuture(),
                execution.getChainExecutionFuture(),
                execution.getDebugProcessingVertexGraphState()
        );
    }

    /**
     * Blocks until all pending request is complete or {@link #getCloseTimeoutMs()} time elapsed
     *
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        isClosed.set(true);

        if (pendingRequestCount.get() > 0) {

            log.info("Closing Completable Reactor. Waiting for pending requests: {} for {} ms",
                    pendingRequestCount.get(),
                    closeTimeoutMs);

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


            if (pendingRequestCount.get() > 0) {
                log.error("Completable Reactor forced to be closed due to timeout." +
                        " There are {} pending request left.", pendingRequestCount.get());
            } else {
                log.info("Completable Reactor closed without any pending request left to process.");
            }
        }
    }
}