package ru.fix.completable.reactor.runtime.tests;


import org.junit.jupiter.api.Test;
import ru.fix.aggregating.profiler.AggregatingProfiler;
import ru.fix.completable.reactor.graph.Graph;
import ru.fix.completable.reactor.graph.Vertex;
import ru.fix.completable.reactor.runtime.CompletableReactor;
import ru.fix.completable.reactor.runtime.LogTracer;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Kamil Asfandiyarov
 */
public class TracingTest {

    /**
     * If tracable payload contain number that match particular condition
     * this payload will be traced by tracer
     */
    static class TracablePayload extends CompletableReactorTest.IdListPayload {
        int number;

        public int getNumber() {
            return number;
        }

        public TracablePayload setNumber(int number) {
            this.number = number;
            return this;
        }
    }


    private final AggregatingProfiler profiler = new AggregatingProfiler();
    final CompletableReactor completableReactor = new CompletableReactor(profiler);


    static class SimpleGraph extends Graph<TracablePayload> {

        Vertex processor1 =
                handler(
                        new IdProcessor(1)::handle
                ).withoutMerger();

        Vertex processor2 =
                handler(new IdProcessor(2)::handle
                ).withoutMerger();

        Vertex processor3 =
                handler(new IdProcessor(3)::handle
                ).withoutMerger();

        {
            payload()
                    .handleBy(processor1)
                    .handleBy(processor2);

            processor1
                    .onAny().mergeBy(processor2);

            processor2
                    .onAny().handleBy(processor3);

            processor3
                    .onAny().complete();
        }

    }


    @Test
    public void trace_payload_if_payload_contain_special_id() throws Exception {


        SimpleGraph graph = new SimpleGraph();
        completableReactor.registerGraph(graph);

        AtomicBoolean beforeHandle = new AtomicBoolean();
        AtomicBoolean beforeMerge = new AtomicBoolean();

        LogTracer tracer = new LogTracer() {
            @Override
            public boolean isTraceable(Object payload) {
                if (payload instanceof TracablePayload) {
                    return ((TracablePayload) payload).getNumber() == 42;
                } else {
                    return false;
                }
            }

            @Override
            public Object beforeHandle(String identity, Object payload) {
                if (((TracablePayload) payload).getNumber() == 42) {
                    beforeHandle.set(true);
                }
                return super.beforeHandle(identity, payload);
            }

            @Override
            public Object beforeMerge(String identity, Object payload, Object handleResult) {
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
