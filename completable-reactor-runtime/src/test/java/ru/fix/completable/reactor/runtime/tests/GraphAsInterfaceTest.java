package ru.fix.completable.reactor.runtime.tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import ru.fix.aggregating.profiler.NoopProfiler;
import ru.fix.completable.reactor.graph.Vertex;
import ru.fix.completable.reactor.runtime.CompletableReactor;
import ru.fix.completable.reactor.graph.mapped.Graph;

public class GraphAsInterfaceTest {

    final CompletableReactor reactor = new CompletableReactor(new NoopProfiler());

    /**
     * Interface Part
     */

    interface SumService{
        class Request {
            String arg1;
            String arg2;
            Request(String arg1, String arg2) {
                this.arg1 = arg1;
                this.arg2 = arg2;
            }
        }
        class Response{
            String result;
        }

        Response sum(Request request);
    }

    /**
     * Implementation Part
     */

    class SumGraphContext{
        String upperCaseArg1;
        String upperCaseArg2;
    }
    class SumGraph extends Graph<SumService.Request, SumService.Response, SumGraphContext>{

        Vertex argsToUpperCase = mutator(p -> {
            p.context.upperCaseArg1 = p.request.arg1.toUpperCase();
            p.context.upperCaseArg2 = p.request.arg2.toUpperCase();
        });

        Vertex sum = mutator(p -> {
            p.response.result = p.context.upperCaseArg1 + p.context.upperCaseArg2;
        });

        {
            payload().handleBy(argsToUpperCase);
            argsToUpperCase.onAny().handleBy(sum);
        }
    }




    @Test
    void testInterface(){
        SumService service = reactor.registerGraph(new SumGraph(), SumService.class);

        SumService.Response response = service.sum(new SumService.Request("one", "two"));
        assertEquals("OneTwo", response.result);

    }
}
