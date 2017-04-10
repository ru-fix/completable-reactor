package ru.fix.completable.reactor.example;

import org.junit.Test;
import ru.fix.commons.profiler.impl.SimpleProfiler;
import ru.fix.completable.reactor.example.chain.PurchasePayload;
import ru.fix.completable.reactor.example.processors.BankProcessor;
import ru.fix.completable.reactor.example.processors.ServiceInfoProcessor;
import ru.fix.completable.reactor.example.processors.UserProfileService;
import ru.fix.completable.reactor.runtime.CompletableReactor;
import ru.fix.completable.reactor.runtime.ReactorGraphBuilder;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * @author Kamil Asfandiyarov
 */
public class ConfigurationTest {

    /**
     * Test generate view of graphs.
     *
     * @throws Exception
     */
    @Test
    public void print_html_for_debug() throws Exception {
        Configuration configuration = new Configuration();
        ReactorGraphBuilder.write(
                configuration.purchaseGraph(),
                configuration.subscribeGraph());
    }

    @Test
    public void purchase_invalid_user_and_service() throws Exception {

        Configuration configuration = new Configuration();

        CompletableReactor reactor = new CompletableReactor(new SimpleProfiler());
        reactor.registerReactorGraph(configuration.purchaseGraph());

        PurchasePayload payload = new PurchasePayload();
        payload.request.setUserId(UserProfileService.USER_ID_INVALID).setServiceId(ServiceInfoProcessor.SERVICE_ID_INVALID);

        PurchasePayload result = reactor.submit(payload).getResultFuture().get(5, TimeUnit.SECONDS);

        assertEquals(UserProfileService.Status.USER_NOT_FOUND, result.getStatus());


    }


    @Test
    public void purchase_car_wash() throws Exception {

        Configuration configuration = new Configuration();

        CompletableReactor reactor = new CompletableReactor(new SimpleProfiler());
        reactor.registerReactorGraph(configuration.purchaseGraph());

        PurchasePayload payload = new PurchasePayload();
        payload.request.setUserId(UserProfileService.USER_ID_JOHN).setServiceId(ServiceInfoProcessor.SERVICE_ID_CAR_WASH);

        PurchasePayload result = reactor.submit(payload).getResultFuture().get(5, TimeUnit.SECONDS);

        assertEquals(BankProcessor.Withdraw.Status.OK, result.getStatus());
    }

}