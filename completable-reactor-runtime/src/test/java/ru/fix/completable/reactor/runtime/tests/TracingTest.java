package ru.fix.completable.reactor.runtime.tests;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.val;
import org.junit.Test;
import ru.fix.commons.profiler.impl.SimpleProfiler;
import ru.fix.completable.reactor.api.ReactorGraphModel;
import ru.fix.completable.reactor.api.Reactored;
import ru.fix.completable.reactor.runtime.CompletableReactor;
import ru.fix.completable.reactor.runtime.LogTracer;
import ru.fix.completable.reactor.runtime.ReactorGraph;
import ru.fix.completable.reactor.runtime.ReactorGraphBuilder;
import ru.fix.completable.reactor.runtime.dsl.Processor;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertTrue;

/**
 * @author Kamil Asfandiyarov
 */
public class TracingTest {


    @Reactored({
            "If tracable payload contain number that match particular condition",
            "this payload will be traced by tracer."
    })
    @Data
    @EqualsAndHashCode(callSuper = true)
    @Accessors(chain = true)
    static class TracablePayload extends CompletableReactorTest.IdListPayload {
        int number;
    }

    enum Status {OK}

    final CompletableReactor completableReactor = new CompletableReactor(new SimpleProfiler());

    @Test
    public void trace_payload_if_payload_contain_special_id() throws Exception {

        class Config {
            ReactorGraphBuilder graphBuilder = new ReactorGraphBuilder(this);

            Processor<TracablePayload> processor1 = graphBuilder.processor()
                    .forPayload(TracablePayload.class)
                    .withHandler(new IdProcessor(1)::handle)
                    .withMerger((payload, any) -> Status.OK)
                    .buildProcessor();

            Processor<TracablePayload> processor2 = graphBuilder.processor()
                    .forPayload(TracablePayload.class)
                    .withHandler(new IdProcessor(2)::handle)
                    .withMerger((payload, any) -> Status.OK)
                    .buildProcessor();

            Processor<TracablePayload> processor3 = graphBuilder.processor()
                    .forPayload(TracablePayload.class)
                    .withHandler(new IdProcessor(3)::handle)
                    .withMerger((payload, any) -> Status.OK)
                    .buildProcessor();

            ReactorGraph<TracablePayload> graph() {
                return graphBuilder.payload(TracablePayload.class)
                        .handle(processor1)
                        .handle(processor2)

                        .mergePoint(processor1)
                        .onAny().merge(processor2)

                        .mergePoint(processor2)
                        .onAny().handle(processor3)

                        .mergePoint(processor3)
                        .onAny().complete()

                        .coordinates()
                        .buildGraph();
            }

        }

        val graph = new Config().graph();
        completableReactor.registerReactorGraph(graph);

        val beforeHandle = new AtomicBoolean();
        val beforeMerge = new AtomicBoolean();

        val tracer = new LogTracer() {
            @Override
            public boolean isTraceable(Object payload) {
                if (payload instanceof TracablePayload) {
                    return ((TracablePayload) payload).getNumber() == 42;
                } else {
                    return false;
                }
            }

            @Override
            public Object beforeHandle(ReactorGraphModel.Identity identity, Object payload) {
                if (((TracablePayload) payload).getNumber() == 42) {
                    beforeHandle.set(true);
                }
                return super.beforeHandle(identity, payload);
            }

            @Override
            public Object beforeMerge(ReactorGraphModel.Identity identity, Object payload, Object handleResult) {
                if (((TracablePayload) payload).getNumber() == 42) {
                    beforeMerge.set(true);
                }
                return super.beforeMerge(identity, payload, handleResult);
            }
        };

        completableReactor.setTracer(tracer);

        for (int num = 0; num < 1000; num++) {
            completableReactor.submit(new TracablePayload().setNumber(num));
        }

        completableReactor.close();

        assertTrue(beforeHandle.get());
        assertTrue(beforeMerge.get());
    }
}
