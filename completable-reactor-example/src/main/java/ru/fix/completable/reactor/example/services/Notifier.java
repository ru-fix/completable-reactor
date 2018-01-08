package ru.fix.completable.reactor.example.services;

import lombok.extern.slf4j.Slf4j;
import ru.fix.completable.reactor.api.Reactored;

import java.util.concurrent.CompletableFuture;

/**
 * @author Kamil Asfandiyarov
 */
@Reactored({
        "Send notifications to external systems",
        "Notification about events like purchase.",
        "We do not need to wait result of notification action for building correct response.",
})
@Slf4j
public class Notifier {
    @Reactored({
            "Send web notification about purchase event"}
    )
    public CompletableFuture<Void> sendHttpPurchaseNotification(Long userId) {
        return CompletableFuture.runAsync(() -> log.info("Web notification for user with id = " + userId));
    }

    @Reactored({
            "Send sms notification about purchase event"}
    )
    public CompletableFuture<Void> sendSmsPurchaseNotification(Long userId) {
        return CompletableFuture.runAsync(() -> log.info("Sms notification for user with id = " + userId));
    }

}
