package ru.fix.completable.reactor.example.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class Notifier {
    Logger log = LoggerFactory.getLogger(Notifier.class);

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
