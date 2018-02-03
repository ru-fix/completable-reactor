package ru.fix.completable.reactor.runtime;

public interface DependencyInjector {
    <T> T inject(String name, Class<T> type);
}

