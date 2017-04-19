package ru.fix.completable.reactor.runtime.tests;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.val;
import org.junit.Test;
import ru.fix.commons.profiler.impl.SimpleProfiler;
import ru.fix.completable.reactor.api.Reactored;
import ru.fix.completable.reactor.runtime.CompletableReactor;
import ru.fix.completable.reactor.runtime.LogTracer;
import ru.fix.completable.reactor.runtime.ReactorGraphBuilder;

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
    static class TracablePayload extends CompletableReactorTest.IdListPayload{
        int number;
    }

    enum Status{OK}

    final ReactorGraphBuilder graphBuilder = new ReactorGraphBuilder();
    final CompletableReactor completableReactor = new CompletableReactor(new SimpleProfiler());

    @Test
    public void trace_payload_if_payload_contain_special_id() throws Exception {

        val processor1 = graphBuilder.processor()
                .forPayload(TracablePayload.class)
                .withHandler(new IdProcessor(1)::handle)
                .withMerger((payload, any) -> Status.OK)
                .buildProcessor()
                .setId(1);

        val processor2 = graphBuilder.processor()
                .forPayload(TracablePayload.class)
                .withHandler(new IdProcessor(2)::handle)
                .withMerger((payload, any) -> Status.OK)
                .buildProcessor()
                .setId(2);

        val processor3 = graphBuilder.processor()
                .forPayload(TracablePayload.class)
                .withHandler(new IdProcessor(3)::handle)
                .withMerger((payload, any) -> Status.OK)
                .buildProcessor()
                .setId(3);

        val graph = graphBuilder.payload(TracablePayload.class)
                .handleBy(processor1)
                .handleBy(processor2)

                .mergePoint(processor1)
                .onAny().merge(processor2)

                .mergePoint(processor2)
                .onAny().handleBy(processor3)

                .mergePoint(processor3)
                .onAny().complete()

                .coordinates()
                .buildGraph();

        completableReactor.registerReactorGraph(graph);

        val tracer = new LogTracer(){
            @Override
            public boolean isTraceable(Object payload) {
                if(payload instanceof TracablePayload){
                    return ((TracablePayload)payload).getNumber() == 42;
                } else {
                    return false;
                }
            }
        };

        completableReactor.setTracer(tracer);

        for (int num = 0; num < 1000; num++) {
            completableReactor.submit(new TracablePayload().setNumber(num));
        }

        completableReactor.close();
    }
}
