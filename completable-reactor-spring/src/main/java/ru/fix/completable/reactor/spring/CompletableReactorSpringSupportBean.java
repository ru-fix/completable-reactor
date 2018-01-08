package ru.fix.completable.reactor.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import ru.fix.completable.reactor.runtime.GraphConfig;

public class CompletableReactorSpringSupportBean implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        GraphConfig.registerDependecnyInjector(applicationContext::getBean);
    }
}
