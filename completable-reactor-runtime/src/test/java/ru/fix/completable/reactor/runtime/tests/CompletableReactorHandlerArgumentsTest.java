package ru.fix.completable.reactor.runtime.tests;

import lombok.Data;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import ru.fix.commons.profiler.impl.SimpleProfiler;
import ru.fix.completable.reactor.api.Reactored;
import ru.fix.completable.reactor.runtime.CompletableReactor;
import ru.fix.completable.reactor.runtime.ReactorGraph;
import ru.fix.completable.reactor.runtime.ReactorGraphBuilder;
import ru.fix.completable.reactor.runtime.dsl.Processor;

import java.math.BigInteger;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * @author Kamil Asfandiyarov
 */
public class CompletableReactorHandlerArgumentsTest {

    private SimpleProfiler profiler;
    private CompletableReactor reactor;

    enum Status {OK}

    @Before
    public void before() throws Exception {
        profiler = new SimpleProfiler();
        reactor = new CompletableReactor(profiler)
                .setDebugProcessingVertexGraphState(true);

    }

    @Test
    public void arguments_test_0() throws Exception {

        @Reactored("Payload with string")
        @Data
        class Payload {
            String data;
        }

        class Service {
            @Reactored("method with 0 arguments")
            public CompletableFuture<String> foo() {
                return CompletableFuture.completedFuture("");
            }
        }

        final Service service = new Service();

        class Config {
            ReactorGraphBuilder graphBuilder = new ReactorGraphBuilder(this);

            Processor<Payload> processor = graphBuilder.processor()
                    .forPayload(Payload.class)
                    .withHandler(service::foo)
                    .withMerger((payload, result) -> {
                        payload.data = result;
                        return CompletableReactorTest.Status.OK;
                    })
                    .buildProcessor();


            ReactorGraph<Payload> graph() {
                return graphBuilder.payload(Payload.class)
                        .handle(processor)

                        .mergePoint(processor)
                        .onAny()
                        .complete()
                        .coordinates()
                        .buildGraph();
            }
        }

        val graph = new Config().graph();
        reactor.registerReactorGraph(graph);

        assertEquals("", reactor.submit(new Payload())
                .getResultFuture()
                .get(5, TimeUnit.SECONDS)
                .getData());
    }


    @Test
    public void arguments_test_1() throws Exception {

        @Reactored("Payload with string")
        @Data
        class Payload {
            String data;
        }

        class Service {
            @Reactored("method with 6 arguments")
            public CompletableFuture<String> foo(
                    long arg) {
                return CompletableFuture.completedFuture("" + arg);
            }
        }

        final Service service = new Service();

        class Config {
            ReactorGraphBuilder graphBuilder = new ReactorGraphBuilder(this);

            Processor<Payload> processor = graphBuilder.processor()
                    .forPayload(Payload.class)
                    .passArg(pld -> 7L)
                    .withHandler(service::foo)
                    .withMerger((payload, result) -> {
                        payload.data = result;
                        return CompletableReactorTest.Status.OK;
                    })
                    .buildProcessor();


            ReactorGraph<Payload> graph(){
                return graphBuilder.payload(Payload.class)
                        .handle(processor)

                        .mergePoint(processor)
                        .onAny()
                        .complete()
                        .coordinates()
                        .buildGraph();
            }
        }

        reactor.registerReactorGraph(new Config().graph());

        assertEquals("7", reactor.submit(new Payload())
                .getResultFuture()
                .get(5, TimeUnit.SECONDS)
                .getData());
    }


    @Test
    public void arguments_test_2() throws Exception {

        @Reactored("Payload with string")
        @Data
        class Payload {
            String data;
        }

        class Service {
            @Reactored("method with 6 arguments")
            public CompletableFuture<String> foo(
                    BigInteger arg1,
                    long arg2) {
                return CompletableFuture.completedFuture("" + arg1 + arg2);
            }
        }

        final Service service = new Service();

        class Config {
            ReactorGraphBuilder graphBuilder = new ReactorGraphBuilder(this);
            Processor<Payload> processor = graphBuilder.processor()
                    .forPayload(Payload.class)
                    .passArg(pld -> BigInteger.valueOf(6L))
                    .passArg(pld -> 7L)
                    .withHandler(service::foo)
                    .withMerger((payload, result) -> {
                        payload.data = result;
                        return CompletableReactorTest.Status.OK;
                    })
                    .buildProcessor();


            ReactorGraph<Payload> graph() {
                return graphBuilder.payload(Payload.class)
                        .handle(processor)

                        .mergePoint(processor)
                        .onAny()
                        .complete()
                        .coordinates()
                        .buildGraph();

            }
        }
        val graph = new Config().graph();
        reactor.registerReactorGraph(graph);

        assertEquals("67", reactor.submit(new Payload())
                .getResultFuture()
                .get(5, TimeUnit.SECONDS)
                .getData());
    }

    @Test
    public void arguments_test_3() throws Exception {

        @Reactored("Payload with string")
        @Data
        class Payload {
            String data;
        }


        class Service {
            @Reactored("method with 6 arguments")
            public CompletableFuture<String> foo(
                    boolean arg1,
                    BigInteger arg2,
                    long arg3) {
                return CompletableFuture.completedFuture("" + arg1 + arg2 + arg3);
            }
        }

        final Service service = new Service();

        class Config {
            ReactorGraphBuilder graphBuilder = new ReactorGraphBuilder(this);

            Processor<Payload> processor = graphBuilder.processor()
                    .forPayload(Payload.class)
                    .passArg(pld -> true)
                    .passArg(pld -> BigInteger.valueOf(6L))
                    .passArg(pld -> 7L)
                    .withHandler(service::foo)
                    .withMerger((payload, result) -> {
                        payload.data = result;
                        return CompletableReactorTest.Status.OK;
                    })
                    .buildProcessor();


            ReactorGraph<Payload> graph() {
                return graphBuilder.payload(Payload.class)
                        .handle(processor)

                        .mergePoint(processor)
                        .onAny()
                        .complete()
                        .coordinates()
                        .buildGraph();
            }
        }

        reactor.registerReactorGraph(new Config().graph());

        assertEquals("true67", reactor.submit(new Payload())
                .getResultFuture()
                .get(5, TimeUnit.SECONDS)
                .getData());
    }


    @Test
    public void arguments_test_4() throws Exception {

        @Reactored("Payload with string")
        @Data
        class Payload {
            String data;
        }


        class Service {
            @Reactored("method with 6 arguments")
            public CompletableFuture<String> foo(
                    short arg1,
                    boolean arg2,
                    BigInteger arg3,
                    long arg4) {
                return CompletableFuture.completedFuture("" + arg1 + arg2 + arg3 + arg4);
            }
        }

        final Service service = new Service();

        class Config {
            ReactorGraphBuilder graphBuilder = new ReactorGraphBuilder(this);

            Processor<Payload> processor = graphBuilder.processor()
                    .forPayload(Payload.class)
                    .passArg(pld -> (short) 3)
                    .passArg(pld -> true)
                    .passArg(pld -> BigInteger.valueOf(6L))
                    .passArg(pld -> 7L)
                    .withHandler(service::foo)
                    .withMerger((payload, result) -> {
                        payload.data = result;
                        return CompletableReactorTest.Status.OK;
                    })
                    .buildProcessor();


            ReactorGraph<Payload> graph() {
                return graphBuilder.payload(Payload.class)
                        .handle(processor)

                        .mergePoint(processor)
                        .onAny()
                        .complete()
                        .coordinates()
                        .buildGraph();
            }
        }
        val graph = new Config().graph();
        reactor.registerReactorGraph(graph);

        assertEquals("3true67", reactor.submit(new Payload())
                .getResultFuture()
                .get(5, TimeUnit.SECONDS)
                .getData());
    }


    @Test
    public void arguments_test_5() throws Exception {

        @Reactored("Payload with string")
        @Data
        class Payload {
            String data;
        }


        class Service {
            @Reactored("method with 6 arguments")
            public CompletableFuture<String> foo(
                    String arg1,
                    short arg2,
                    boolean arg3,
                    BigInteger arg4,
                    long arg5) {
                return CompletableFuture.completedFuture(arg1 + arg2 + arg3 + arg4 + arg5);
            }
        }

        final Service service = new Service();

        class Config {
            ReactorGraphBuilder graphBuilder = new ReactorGraphBuilder(this);

            Processor<Payload> processor = graphBuilder.processor()
                    .forPayload(Payload.class)
                    .passArg(pld -> "1")
                    .passArg(pld -> (short) 3)
                    .passArg(pld -> true)
                    .passArg(pld -> BigInteger.valueOf(6L))
                    .passArg(pld -> 7L)
                    .withHandler(service::foo)
                    .withMerger((payload, result) -> {
                        payload.data = result;
                        return CompletableReactorTest.Status.OK;
                    })
                    .buildProcessor();


            ReactorGraph<Payload> graph() {
                return graphBuilder.payload(Payload.class)
                        .handle(processor)

                        .mergePoint(processor)
                        .onAny()
                        .complete()
                        .coordinates()
                        .buildGraph();
            }
        }
        val graph = new Config().graph();

        reactor.registerReactorGraph(graph);

        assertEquals("13true67", reactor.submit(new Payload())
                .getResultFuture()
                .get(5, TimeUnit.SECONDS)
                .getData());
    }


    @Test
    public void arguments_test_6() throws Exception {

        @Reactored("Payload with string")
        @Data
        class Payload {
            String data;
        }


        class Service {
            @Reactored("method with 6 arguments")
            public CompletableFuture<String> foo(
                    String arg1,
                    short arg2,
                    boolean arg3,
                    Object arg4,
                    BigInteger arg5,
                    long arg6) {
                return CompletableFuture.completedFuture(arg1 + arg2 + arg3 + arg4 + arg5 + arg6);
            }
        }

        final Service service = new Service();

        class Config {
            ReactorGraphBuilder graphBuilder = new ReactorGraphBuilder(this);

            Processor<Payload> processor = graphBuilder.processor()
                    .forPayload(Payload.class)
                    .passArg(pld -> "1")
                    .passArg(pld -> (short) 3)
                    .passArg(pld -> true)
                    .passArg(pld -> 5)
                    .passArg(pld -> BigInteger.valueOf(6L))
                    .passArg(pld -> 7L)
                    .withHandler(service::foo)
                    .withMerger((payload, result) -> {
                        payload.data = result;
                        return CompletableReactorTest.Status.OK;
                    })
                    .buildProcessor();


            ReactorGraph<Payload> graph() {
                return graphBuilder.payload(Payload.class)
                        .handle(processor)

                        .mergePoint(processor)
                        .onAny()
                        .complete()
                        .coordinates()
                        .buildGraph();
            }
        }

        val graph = new Config().graph();
        reactor.registerReactorGraph(graph);

        assertEquals("13true567", reactor.submit(new Payload())
                .getResultFuture()
                .get(5, TimeUnit.SECONDS)
                .getData());
    }


    @Test
    public void arguments_test_7() throws Exception {

        @Reactored("Payload with string")
        @Data
        class Payload {
            String data;
        }


        class Service {
            @Reactored("method with 7 arguments")
            public CompletableFuture<String> foo(
                    String arg1,
                    int arg2,
                    short arg3,
                    boolean arg4,
                    Object arg5,
                    BigInteger arg6,
                    long arg7) {
                return CompletableFuture.completedFuture(arg1 + arg2 + arg3 + arg4 + arg5 + arg6 + arg7);
            }
        }

        final Service service = new Service();

        class Config {
            ReactorGraphBuilder graphBuilder = new ReactorGraphBuilder(this);

            Processor<Payload> processor = graphBuilder.processor()
                    .forPayload(Payload.class)
                    .passArg(pld -> "1")
                    .passArg(pld -> 2)
                    .passArg(pld -> (short) 3)
                    .passArg(pld -> true)
                    .passArg(pld -> 5)
                    .passArg(pld -> BigInteger.valueOf(6L))
                    .passArg(pld -> 7L)
                    .withHandler(service::foo)
                    .withMerger((payload, result) -> {
                        payload.data = result;
                        return CompletableReactorTest.Status.OK;
                    })
                    .buildProcessor();


            ReactorGraph<Payload> graph() {
                return graphBuilder.payload(Payload.class)
                        .handle(processor)

                        .mergePoint(processor)
                        .onAny()
                        .complete()
                        .coordinates()
                        .buildGraph();
            }
        }
        reactor.registerReactorGraph(new Config().graph());

        assertEquals("123true567", reactor.submit(new Payload())
                .getResultFuture()
                .get(5, TimeUnit.SECONDS)
                .getData());
    }
}
