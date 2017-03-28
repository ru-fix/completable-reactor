package ru.fix.completable.reactor.example.processors;

import lombok.extern.slf4j.Slf4j;
import ru.fix.completable.reactor.api.Reactored;

import java.util.concurrent.CompletableFuture;


/**
 * @author Kamil Asfandiyarov
 */
@Reactored({
        "Store information about transactions.",
        "Each transaction have user id and money amount"

})
@Slf4j
public class TransactionLogProcessor  {

    public CompletableFuture<Void> logTransactioin(Long userId) {
        return CompletableFuture.runAsync(() ->
                log.info("Log transaction for user: {}", userId));
    }
}
