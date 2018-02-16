package ru.fix.completable.reactor.example;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.fix.commons.profiler.impl.SimpleProfiler;
import ru.fix.completable.reactor.example.services.*;
import ru.fix.completable.reactor.runtime.CompletableReactor;
import ru.fix.completable.reactor.spring.CompletableReactorSpringSupportConfiguration;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * @author Kamil Asfandiyarov
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        PurchaseGraphTest.ServicesConfig.class,
        CompletableReactorSpringSupportConfiguration.class,
        PurchaseGraphTest.GraphsConfig.class
})
public class PurchaseGraphTest {

    @Configuration
    public static class ServicesConfig {
        @Bean
        Bank bank() {
            return new Bank();
        }

        @Bean
        MarketingService marketingService() {
            return new MarketingService();
        }

        @Bean
        Notifier notifier() {
            return new Notifier();
        }

        @Bean
        ServiceRegistry serviceRegistry() {
            return new ServiceRegistry();
        }

        @Bean
        TransactionLog transactionLog() {
            return new TransactionLog();
        }

        @Bean
        UserJournal userJournal() {
            return new UserJournal();
        }

        @Bean
        UserProfileManager userProfileManager() {
            return new UserProfileManager();
        }

        @Bean
        CompletableReactor reactor(){
            return new CompletableReactor(new SimpleProfiler());
        }
    }

    @Configuration
    public static class GraphsConfig {
        @Autowired
        CompletableReactor reactor;

        @Bean
        PurchaseGraph purchaseGraph(){
            PurchaseGraph purchaseGraph = new PurchaseGraph();
            reactor.registerIfAbsent(purchaseGraph);
            return purchaseGraph;
        }
    }


    @Autowired
    CompletableReactor reactor;



    //TODO: fix after implementing CR2
//    @Ignore
    @Test()
    public void purchase_invalid_user_and_service() throws Exception {


        PurchasePayload payload = new PurchasePayload();
        payload.request.setUserId(UserProfileManager.USER_ID_INVALID).setServiceId(ServiceRegistry.SERVICE_ID_INVALID);

        PurchasePayload result = reactor.submit(payload).getResultFuture().get(5, TimeUnit.SECONDS);

        assertEquals(UserProfileManager.Status.USER_NOT_FOUND, result.response.getStatus());

    }


    //TODO: fix after implementing CR2
    @Ignore
    @Test
    public void purchase_car_wash() throws Exception {
        CompletableReactor reactor = new CompletableReactor(new SimpleProfiler());
        reactor.registerIfAbsent(new PurchaseGraph());

        PurchasePayload payload = new PurchasePayload();
        payload.request.setUserId(UserProfileManager.USER_ID_JOHN).setServiceId(ServiceRegistry.SERVICE_ID_CAR_WASH);

        PurchasePayload result = reactor.submit(payload).getResultFuture().get(5, TimeUnit.SECONDS);

        assertEquals(Bank.Withdraw.Status.OK, result.response.getStatus());
    }
}