package ru.fix.cpapsm.completable.reactor.example.processors;

import lombok.extern.slf4j.Slf4j;
import ru.fix.cpapsm.completable.reactor.api.ProcessorDescription;

import java.util.concurrent.CompletableFuture;


/**
 * @author Kamil Asfandiyarov
 */
@ProcessorDescription(doc = {
        "Описание процессора №4",
        "Тоже многострочное",
        "Тоже довольно подробное",
        "которое выглядит, например, как tooltip"

})
@Slf4j
public class TransactionLogProcessor  {

    public CompletableFuture<Void> logTransactioin(Long userId) {
        return CompletableFuture.runAsync(() ->
                log.info("Log transaction for user: {}", userId));
    }
}
