package ru.fix.completable.reactor.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import ru.fix.completable.reactor.runtime.CompletableReactor;

public class CompletableReactorSpringSupportBean implements ApplicationContextAware {

    private final CompletableReactor completableReactor;

    public CompletableReactorSpringSupportBean(CompletableReactor completableReactor) {
        this.completableReactor = completableReactor;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        completableReactor.registerDependecnyInjector(applicationContext::getBean);
    }
}
