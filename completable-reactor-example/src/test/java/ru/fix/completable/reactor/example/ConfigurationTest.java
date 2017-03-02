package ru.fix.completable.reactor.example;

import org.junit.Test;
import ru.fix.commons.profiler.impl.SimpleProfiler;
import ru.fix.completable.reactor.api.ReactorGraphModel;
import ru.fix.completable.reactor.example.chain.PurchasePayload;
import ru.fix.completable.reactor.example.processors.BankProcessor;
import ru.fix.completable.reactor.example.processors.ServiceInfoProcessor;
import ru.fix.completable.reactor.example.processors.UserProfileProcessor;
import ru.fix.completable.reactor.runtime.CompletableReactor;
import ru.fix.completable.reactor.runtime.ReactorGraph;
import ru.fix.completable.reactor.ui.ReactorHtmlUIBuilder;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * @author Kamil Asfandiyarov
 */
public class ConfigurationTest {

    /**
     * Test generate html view of graphs.
     * @throws Exception
     */
    @Test
    public void print_html_for_debug() throws Exception {
        Configuration configuration = new Configuration();

        List<ReactorGraphModel> graphModels = Arrays.asList(
                configuration.purchaseGraph().serialize(),
                configuration.subscribeGraph().serialize()
        );

        Path path = Paths.get("continuous-reactor-example-graph.html");
        ReactorHtmlUIBuilder.write(graphModels, path);
        ReactorGraph.write(configuration.purchaseGraph());
        ReactorGraph.write(configuration.subscribeGraph());

        System.out.printf("Continuous reactor example graph available at:\n%s\n", path.toUri().toURL());
    }

    @Test
    public void purchase_invalid_user_and_service() throws Exception {

        Configuration configuration = new Configuration();

        CompletableReactor reactor = new CompletableReactor(new SimpleProfiler());
        reactor.registerReactorGraph(configuration.purchaseGraph());

        PurchasePayload payload = new PurchasePayload();
        payload.request.setUserId(UserProfileProcessor.USER_ID_INVALID).setServiceId(ServiceInfoProcessor.SERVICE_ID_INVALID);

        PurchasePayload result = reactor.submit(payload).getResultFuture().get(5, TimeUnit.SECONDS);

        assertEquals(UserProfileProcessor.Status.USER_NOT_FOUND, result.getStatus());
    }


    @Test
    public void purchase_car_wash() throws Exception {

        Configuration configuration = new Configuration();

        CompletableReactor reactor = new CompletableReactor(new SimpleProfiler());
        reactor.registerReactorGraph(configuration.purchaseGraph());

        PurchasePayload payload = new PurchasePayload();
        payload.request.setUserId(UserProfileProcessor.USER_ID_JOHN).setServiceId(ServiceInfoProcessor.SERVICE_ID_CAR_WASH);

        PurchasePayload result = reactor.submit(payload).getResultFuture().get(5, TimeUnit.SECONDS);

        assertEquals(BankProcessor.Withdraw.Status.OK, result.getStatus());
    }

}