package ru.fix.completable.reactor.graph.kotlin;

import java.util.concurrent.CompletableFuture;

public class NullResultHandler {

    CompletableFuture<String> handleWithNullResult(){
        return null;
    }

}
