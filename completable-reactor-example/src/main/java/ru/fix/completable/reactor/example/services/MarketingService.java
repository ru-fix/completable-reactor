package ru.fix.completable.reactor.example.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class MarketingService {
    private static final Logger log = LoggerFactory.getLogger(MarketingService.class);

    /**
     * Method deliberately synchronous. In many cases there is no need to use CompletableFuture api since computation
     * is simple, uses local data and do not require access to external systems
     */
    public Optional<Long> checkBonuses(long userId, long purchaseService){
        log.info("Check bonus for user id: {} and service: {}", userId, purchaseService);
        return Optional.of(123L);
    }

}
