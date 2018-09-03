package ru.fix.completable.reactor.example.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

/**
 * @author Kamil Asfandiyarov
 */
public class Notifier {
    Logger log = LoggerFactory.getLogger(Notifier.class);

    public CompletableFuture<Void> sendHttpPurchaseNotification(Long userId) {
        return CompletableFuture.runAsync(() -> log.info("Web notification for user with id = " + userId));
    }

    public CompletableFuture<Void> sendSmsPurchaseNotification(Long userId) {
        return CompletableFuture.runAsync(() -> log.info("Sms notification for user with id = " + userId));
    }

}
