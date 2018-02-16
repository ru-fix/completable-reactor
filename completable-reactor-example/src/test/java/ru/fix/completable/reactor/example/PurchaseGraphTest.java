package ru.fix.completable.reactor.example;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.fix.commons.profiler.impl.SimpleProfiler;
import ru.fix.completable.reactor.example.services.*;
import ru.fix.completable.reactor.graph.Graph;
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
//        CompletableReactorSpringSupportConfiguration.class,
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
        TransactionLog txLog() {
            return new TransactionLog();
        }

        @Bean
        UserJournal userJournal() {
            return new UserJournal();
        }

        @Bean
        UserProfileManager userProfile() {
            return new UserProfileManager();
        }

        @Bean
        CompletableReactor reactor() {
            return new CompletableReactor(new SimpleProfiler());
        }
    }

    @Configuration
    public static class GraphsConfig {
        @Autowired
        CompletableReactor reactor;

        @Bean
        PurchaseGraph purchaseGraph() {
            PurchaseGraph purchaseGraph = new PurchaseGraph();
            return purchaseGraph;
        }

        @Bean
        ReactorBeanFactoryPostProcessor reactorBeanFactoryPostProcessor() {
            return new ReactorBeanFactoryPostProcessor();
        }

        @Bean
        ReactorBeanPostProcessor reactorBeanPostProcessor(){
            return new ReactorBeanPostProcessor();
        }

    }

    static class ReactorBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
            System.out.println(beanFactory);

        }
    }

    static class ReactorBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {
        private ApplicationContext applicationContext;

        @Autowired
        CompletableReactor completableReactor;

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext = applicationContext;
        }

        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            if (Graph.class.isAssignableFrom(bean.getClass())) {
//                System.out.println("graph bean: " + beanName);
//                System.out.println(applicationContext.getBean("bank"));

                completableReactor.registerDependecnyInjector(applicationContext::getBean);
                completableReactor.registerIfAbsent((Graph)bean);
            }
            return bean;
        }

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            return bean;
        }
    }

    @Autowired
    PurchaseGraph purchaseGraph;

    @Autowired
    CompletableReactor reactor;


    //TODO: fix after implementing CR2
//    @Ignore
    @Test()
    public void purchase_invalid_user_and_service() throws Exception {

        reactor.registerIfAbsent(purchaseGraph);

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