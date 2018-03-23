package ru.fix.completable.reactor.runtime.tests;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fix.commons.profiler.ProfiledCall;
import ru.fix.commons.profiler.Profiler;
import ru.fix.completable.reactor.graph.Graph;
import ru.fix.completable.reactor.graph.Vertex;
import ru.fix.completable.reactor.runtime.CompletableReactor;
import ru.fix.completable.reactor.runtime.ProfilerNames;

import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.mockito.Mockito.*;

/**
 * @author Kamil Asfandiyarov
 */
public class GlProfilingTest {
    private static final Logger log = LoggerFactory.getLogger(GlProfilingTest.class);

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

    enum Status {OK}


    static class SimpleGraph extends Graph<TracablePayload> {

        Vertex processor1 =
                handler(p -> completedFuture(null)
                ).withEmptyMerger();

        Vertex processor2 =
                handler(p -> completedFuture(null)
                ).withEmptyMerger();

        Vertex processor3 =
                handler(p -> completedFuture(null)
                ).withEmptyMerger();

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

        Profiler profiler = mock(Profiler.class);
        ProfiledCall profiledCall = mock(ProfiledCall.class);
        when(profiler.profiledCall(any())).thenReturn(profiledCall);
        when(profiledCall.start()).thenReturn(profiledCall);

        final CompletableReactor completableReactor = new CompletableReactor(profiler);


        SimpleGraph graph = new SimpleGraph();
        completableReactor.registerGraph(graph);

        for (int num = 0; num < 10; num++) {
            completableReactor.submit(new TracablePayload().setNumber(num));
        }

        completableReactor.close();


        verify(profiler, times(80)).profiledCall(any());
        verify(profiler, times(10)).profiledCall(prefix(ProfilerNames.PAYLOAD));
        verify(profiler, times(10)).profiledCall(prefix(ProfilerNames.EXECUTION));
        verify(profiler, times(10)).profiledCall(prefix(ProfilerNames.HANDLE) + ".processor1");
        verify(profiler, times(10)).profiledCall(prefix(ProfilerNames.HANDLE) + ".processor2");
        verify(profiler, times(10)).profiledCall(prefix(ProfilerNames.HANDLE) + ".processor3");
        verify(profiler, times(10)).profiledCall(prefix(ProfilerNames.MERGE) + ".processor1");
        verify(profiler, times(10)).profiledCall(prefix(ProfilerNames.MERGE) + ".processor2");
        verify(profiler, times(10)).profiledCall(prefix(ProfilerNames.MERGE) + ".processor3");

    }

    private static String prefix(String type){
        return "" +
                ProfilerNames.PROFILER +
                "." + type +
                "." + TracablePayload.class.getSimpleName();
    }
}
