package ru.fix.completable.reactor.runtime;


import ru.fix.completable.reactor.runtime.dsl.config.PayloadDsl;

public class GraphConfig<Payload> {
    public PayloadDsl<Payload> payload(){
        throw new UnsupportedOperationException();
    }
}
