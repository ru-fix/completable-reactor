package ru.fix.completable.reactor.spring;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import ru.fix.aggregating.profiler.AggregatingProfiler;
import ru.fix.completable.reactor.runtime.CompletableReactor;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        SpringJUnitTest.TestConfig.class,
        GraphAsSpringConfig.class,
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestExecutionListeners({
        DirtiesContextTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class
})
public class SpringJUnitTest {

    @Configuration
    static class TestConfig{
        @Bean
        public MyService myService(){
            return new MyService();
        }

        @Bean
        public CompletableReactor completableReactor(){
            return new CompletableReactor(new AggregatingProfiler());
        }
    }


    @Autowired
    private CompletableReactor completableReactor;


    @Test
    public void run_test_graph() throws Exception {
        TestPayload result = completableReactor.submit(new TestPayload()).getResultFuture().get(5, TimeUnit.MINUTES);
        assertEquals("foo", result.result);
    }
}
