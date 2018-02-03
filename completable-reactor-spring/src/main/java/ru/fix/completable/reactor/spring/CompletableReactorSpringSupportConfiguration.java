package ru.fix.completable.reactor.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.fix.completable.reactor.runtime.CompletableReactor;

@Configuration
public class CompletableReactorSpringSupportConfiguration {

    @Bean
    public CompletableReactorSpringSupportBean completableReactorSpringSupportBean(
            CompletableReactor completableReactor
    ) {
        return new CompletableReactorSpringSupportBean(completableReactor);
    }
}
