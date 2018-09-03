package ru.fix.completable.reactor.runtime.tests;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.val;
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



    @Data
    @EqualsAndHashCode(callSuper = true)
    @Accessors(chain = true)
    static class TracablePayload extends GlCompletableReactorTest.IdListPayload {
        int number;
    }

    enum Status {OK}

    final CompletableReactor completableReactor = new CompletableReactor(new AggregatingProfiler());

    @Test
    public void trace_payload_if_payload_contain_special_id() throws Exception {

        /**
         * If traceable payload contains number that matches particular condition
         * payload will be traced by tracer.
         */
        class Config extends Graph<TracablePayload> {

            Vertex processor1 =
                    handler(new IdProcessor(1)::handle)
                    .withRoutingMerger((payload, any) -> Status.OK);

            Vertex processor2 =
                    handler(new IdProcessor(2)::handle)
                    .withRoutingMerger((payload, any) -> Status.OK);

            Vertex processor3 =
                    handler(new IdProcessor(3)::handle)
                    .withRoutingMerger((payload, any) -> Status.OK);

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

                        coordinates();
            }

        }

        val graph = new Config();
        completableReactor.registerGraph(graph);

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
