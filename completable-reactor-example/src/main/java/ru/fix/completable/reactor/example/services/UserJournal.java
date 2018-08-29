package ru.fix.completable.reactor.example.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;


/**
 * @author Kamil Asfandiyarov
 */
public class UserJournal {
    private static final Logger log = LoggerFactory.getLogger(UserJournal.class);

    public CompletableFuture<Void> logAction(Long userId, String message) {
        log.info("Userlog id: {} message: {}", userId, message);
        return CompletableFuture.completedFuture(null);
    }
}
