package ru.fix.completable.reactor.example.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fix.completable.reactor.api.Reactored;

import java.util.concurrent.CompletableFuture;


/**
 * @author Kamil Asfandiyarov
 */
@Reactored({
        "Store information about transactions.",
        "Each transaction have user id and money amount"

})
public class TransactionLog {
    Logger log = LoggerFactory.getLogger(TransactionLog.class);

    public CompletableFuture<Void> logTransactioin(Long userId) {
        return CompletableFuture.runAsync(() ->
                log.info("Log transaction for user: {}", userId));
    }
}
