package ru.fix.completable.reactor.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CompletableReactorSpringSupportConfiguration {

    @Bean
    public CompletableReactorSpringSupportBean completableReactorSpringSupportBean(){
        return new CompletableReactorSpringSupportBean();
    }
}
