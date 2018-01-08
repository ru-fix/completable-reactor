package ru.fix.completable.reactor.example;

import org.junit.Test;
import ru.fix.commons.profiler.impl.SimpleProfiler;
import ru.fix.completable.reactor.example.services.Bank;
import ru.fix.completable.reactor.example.services.ServiceRegistry;
import ru.fix.completable.reactor.example.services.UserProfileManager;
import ru.fix.completable.reactor.runtime.CompletableReactor;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * @author Kamil Asfandiyarov
 */
public class ConfigurationTest {

    @Test
    public void purchase_invalid_user_and_service() throws Exception {
        PurchaseGraphConfig configuration = new PurchaseGraphConfig();

        CompletableReactor reactor = new CompletableReactor(new SimpleProfiler());
        reactor.registerReactorGraph(configuration.purchaseGraph());

        PurchasePayload payload = new PurchasePayload();
        payload.request.setUserId(UserProfileManager.USER_ID_INVALID).setServiceId(ServiceRegistry.SERVICE_ID_INVALID);

        PurchasePayload result = reactor.submit(payload).getResultFuture().get(5, TimeUnit.SECONDS);

        assertEquals(UserProfileManager.Status.USER_NOT_FOUND, result.response.getStatus());
    }


    @Test
    public void purchase_car_wash() throws Exception {
        PurchaseGraphConfig configuration = new PurchaseGraphConfig();

        CompletableReactor reactor = new CompletableReactor(new SimpleProfiler());
        reactor.registerReactorGraph(configuration.purchaseGraph());

        PurchasePayload payload = new PurchasePayload();
        payload.request.setUserId(UserProfileManager.USER_ID_JOHN).setServiceId(ServiceRegistry.SERVICE_ID_CAR_WASH);

        PurchasePayload result = reactor.submit(payload).getResultFuture().get(5, TimeUnit.SECONDS);

        assertEquals(Bank.Withdraw.Status.OK, result.response.getStatus());
    }
}