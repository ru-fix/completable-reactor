package ru.fix.completable.reactor.spring;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import ru.fix.completable.reactor.graph.Graph;
import ru.fix.completable.reactor.graph.Vertex;
import ru.fix.completable.reactor.runtime.CompletableReactor;

import javax.annotation.PostConstruct;

@Configuration
public class GraphAsSpringConfig extends Graph<TestPayload> {

    MyService myService;
    CompletableReactor completableReactor;

    @Autowired
    public GraphAsSpringConfig(MyService myService, CompletableReactor completableReactor) {
        this.myService = myService;
        this.completableReactor = completableReactor;
    }

    @PostConstruct
    public void initialize() {
        completableReactor.registerGraphIfAbsent(this);
    }

    enum Flow {OK}


    Vertex router =
            handler(p -> myService.foo())
                    .withMerger((payload, result) -> {
                        payload.result = result;
                        return Flow.OK;
                    });

    {
        payload().handleBy(router);

        router.onAny().complete();
    }

}
