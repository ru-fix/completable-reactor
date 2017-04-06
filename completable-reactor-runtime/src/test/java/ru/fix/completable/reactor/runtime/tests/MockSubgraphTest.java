package ru.fix.completable.reactor.runtime.tests;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.val;
import org.junit.Before;
import org.junit.Test;
import ru.fix.commons.profiler.Profiler;
import ru.fix.commons.profiler.impl.SimpleProfiler;
import ru.fix.completable.reactor.api.Reactored;
import ru.fix.completable.reactor.runtime.CompletableReactor;
import ru.fix.completable.reactor.runtime.ReactorGraphBuilder;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Kamil Asfandiyarov
 */
public class MockSubgraphTest {

    @Data()
    @Accessors(chain = true)
    static class MainPayload {
        String data;
    }

    @Data
    @Accessors(chain = true)
    static class SubgraphPayload {
        String data;
    }

    enum Status {OK}

    @Reactored()
    static class Processor1{
        public CompletableFuture<Void> handle(){
            return CompletableFuture.completedFuture(null);
        }
    }


    final Profiler profiler = new SimpleProfiler();
    final ReactorGraphBuilder graphBuilder = new ReactorGraphBuilder();

    CompletableReactor reactor;


    @Before
    public void before(){
        reactor = new CompletableReactor(profiler)
                .setDebugProcessingVertexGraphState(true);
    }

    @Test
    public void mock_subgraph() throws Exception {

        val processor1 = graphBuilder.processor()
                .forPayload(MainPayload.class)
                .withHandler(new Processor1()::handle)
                .withMerger((mainPayload, any) -> Status.OK)
                .buildProcessor();

        val subgraph = graphBuilder.subgraph(SubgraphPayload.class)
                .forPayload(MainPayload.class)
                .passArg(pld -> new SubgraphPayload().setData(pld.getData()))
                .withMerger((mainPayload, subgraphPayload) -> {
                    mainPayload.setData(subgraphPayload.getData());
                    return Status.OK;
                })
                .buildSubgraph();


        val graph = graphBuilder.payload(MainPayload.class)
                .handleBy(processor1)

                .mergePoint(processor1)
                .onAny().handleBy(subgraph)

                .mergePoint(subgraph)
                .onAny().complete()
                .coordinates()
                .buildGraph();

        CompletableReactor.write(graph);
        reactor.registerReactorGraph(graph);


        // mocking subgraph behaviour

        AtomicReference<SubgraphPayload> subgraphCapture = new AtomicReference<>();

        reactor.registerReactorGraph(SubgraphPayload.class, payload -> {
            subgraphCapture.set(payload);
            return CompletableFuture.completedFuture(new SubgraphPayload()
                    .setData("mock-subgraph-data"));
        });

        // override subgraph implementation by async method

        CompletableReactor.Execution<MainPayload> result = reactor.submit(new MainPayload().setData("main-payload-data"));
        MainPayload mainGraphResultPayload = result
                .getResultFuture()
                .get(10, TimeUnit.SECONDS);

        assertNotNull(subgraphCapture.get());
        assertEquals("main-payload-data", subgraphCapture.get().getData());

        assertEquals("mock-subgraph-data", mainGraphResultPayload.getData());

    }
}
