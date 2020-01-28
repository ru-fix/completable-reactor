package ru.fix.completable.reactor.runtime.tests;


import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import ru.fix.aggregating.profiler.Identity;
import ru.fix.aggregating.profiler.ProfiledCall;
import ru.fix.aggregating.profiler.Profiler;
import ru.fix.completable.reactor.graph.Graph;
import ru.fix.completable.reactor.graph.Vertex;
import ru.fix.completable.reactor.runtime.CompletableReactor;
import ru.fix.completable.reactor.runtime.Metrics;
import ru.fix.completable.reactor.runtime.Tags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.concurrent.CompletableFuture.completedFuture;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * @author Kamil Asfandiyarov
 */
public class ProfilingTest {
    /**
     * If tracable payload contain number that match particular condition
     * this payload will be traced by tracer
     */
    static class TracablePayload extends CompletableReactorTest.IdListPayload {
        int number;

        public TracablePayload setNumber(int number) {
            this.number = number;
            return this;
        }
    }

    static class SimpleGraph extends Graph<TracablePayload> {

        Vertex processor1 =
                handler(p -> completedFuture(null)
                ).withoutMerger();

        Vertex processor2 =
                handler(p -> completedFuture(null)
                ).withoutMerger();

        Vertex processor3 =
                handler(p -> completedFuture(null)
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

        Profiler profiler = mock(Profiler.class);
        ProfiledCall profiledCall = mock(ProfiledCall.class);

        doNothing().when(profiler).attachIndicator(eq(Metrics.PENDING_REQUEST), any());

        when(profiler.profiledCall(ArgumentMatchers.<String>any())).thenReturn(profiledCall);
        when(profiler.profiledCall(ArgumentMatchers.<Identity>any())).thenReturn(profiledCall);

        when(profiledCall.start()).thenReturn(profiledCall);

        CompletableReactor completableReactor = new CompletableReactor(profiler);
        SimpleGraph graph = new SimpleGraph();
        completableReactor.registerGraph(graph);
        for (int num = 0; num < 10; num++) {
            completableReactor.submit(new TracablePayload().setNumber(num));
        }
        completableReactor.close();



        verify(profiler, times(1)).attachIndicator(eq(Metrics.PENDING_REQUEST), any());

        ArgumentCaptor<Identity> identityCapture = ArgumentCaptor.forClass(Identity.class);
        verify(profiler, times(80)).profiledCall(identityCapture.capture());

        List<Identity> identities = identityCapture.getAllValues();
        assertEquals(80, identities.size());

        List<Identity> expected = new ArrayList<>();
        expected.addAll(vertexIdentities(Tags.HANDLER, "processor1", 10));
        expected.addAll(vertexIdentities(Tags.HANDLER,"processor2", 10));
        expected.addAll(vertexIdentities(Tags.HANDLER,"processor3", 10));
        expected.addAll(vertexIdentities(Tags.MERGER, "processor1", 10));
        expected.addAll(vertexIdentities(Tags.MERGER,"processor2", 10));
        expected.addAll(vertexIdentities(Tags.MERGER,"processor3", 10));
        expected.addAll(executionIdentity(Tags.SUBMIT, 10));
        expected.addAll(executionIdentity(Tags.EXECUTION, 10));

        assertThat(identities, containsInAnyOrder(expected.toArray()));
    }

    private static List<Identity> vertexIdentities(String operation, String vertex, int number) {
        return IntStream.range(0, number)
                .mapToObj(i -> {
                    Map<String, String> tags = new HashMap<>();
                    tags.put("payload", TracablePayload.class.getName());
                    tags.put("operation", operation);
                    tags.put("vertex", vertex);
                    return new Identity(Metrics.COMPLETABLE_REACTOR_GRAPH, tags);
                })
                .collect(Collectors.toList());
    }

    private static List<Identity> executionIdentity(String operation, int number) {
        return IntStream.range(0, number)
                .mapToObj(i -> {
                    Map<String, String> tags = new HashMap<>();
                    tags.put("payload", TracablePayload.class.getName());
                    tags.put("operation", operation);
                    return new Identity(Metrics.COMPLETABLE_REACTOR, tags);
                })
                .collect(Collectors.toList());
    }
}
