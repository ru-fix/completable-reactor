package ru.fix.completable.reactor.spring;

import java.util.concurrent.CompletableFuture;

public class MyService {
    public CompletableFuture<String> foo(){
        return CompletableFuture.completedFuture("foo");
    }
}
