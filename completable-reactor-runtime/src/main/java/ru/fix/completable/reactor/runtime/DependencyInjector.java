package ru.fix.completable.reactor.runtime;

public interface DependencyInjector {
    <T> T resolve(String name, Class<T> type);
}

