package ru.fix.completable.reactor.example.processors;

import lombok.extern.slf4j.Slf4j;
import ru.fix.completable.reactor.api.HandlerDescription;
import ru.fix.completable.reactor.api.ProcessorDescription;

import java.util.concurrent.CompletableFuture;

/**
 * @author Kamil Asfandiyarov
 */
@ProcessorDescription(doc = {
        "Send notifications to external system",
        "Notification about events like purchase.",
        "We do not need to wait result of notification action for building correct response.",
        "So we attach notification completable future to async task manager and continue without waiting for its result"

})
@Slf4j
public class NotificationProcessor  {
    @HandlerDescription(doc = {
            "Send notification about purchase event"}
    )
    public CompletableFuture<Void> sendPurchaseNotification(Long userId) {

        return CompletableFuture.runAsync(() -> log.info("Notification for user with id = " + userId));
    }
}
