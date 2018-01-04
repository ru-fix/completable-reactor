package ru.fix.completable.reactor.runtime.dsl.config;

import ru.fix.completable.reactor.runtime.dsl.Processor;

public interface PayloadDsl<Payload> {
    void handleBy(Processor<Payload> processor);
}
