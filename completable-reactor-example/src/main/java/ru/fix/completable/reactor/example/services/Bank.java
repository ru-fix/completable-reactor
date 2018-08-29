package ru.fix.completable.reactor.example.services;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;


/**
 * @author Kamil Asfandiyarov
 */
public class Bank {


    /**
     * Emulating data in remote database
     */
    final Map<Long, AtomicReference<BigDecimal>> wallets = new ConcurrentHashMap<>();

    public Bank() {
        wallets.put(1L, new AtomicReference<>(BigDecimal.valueOf(1000.00)));
        wallets.put(2L, new AtomicReference<>(BigDecimal.valueOf(1000.00)));
    }


    public static class Withdraw {
        public enum Status {
            OK,
            USER_IS_BLOCKED,
            WALLET_NOT_FOUND;
        }

        Long userId;
        Status status;
        BigDecimal newAmount;

        public Long getUserId() {
            return userId;
        }

        public Withdraw setUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Status getStatus() {
            return status;
        }

        public Withdraw setStatus(Status status) {
            this.status = status;
            return this;
        }

        public BigDecimal getNewAmount() {
            return newAmount;
        }

        public Withdraw setNewAmount(BigDecimal newAmount) {
            this.newAmount = newAmount;
            return this;
        }
    }


    public CompletableFuture<Withdraw> withdrawMoney(UserProfile userProfile, ServiceInfo serviceInfo) {

        return CompletableFuture
                .supplyAsync(() -> {
                    AtomicReference<BigDecimal> wallet = wallets.get(userProfile.userId);
                    if (wallet == null) {
                        return new Withdraw().setStatus(Withdraw.Status.WALLET_NOT_FOUND);
                    }

                    if (userProfile.isBlocked) {
                        return new Withdraw().setStatus(Withdraw.Status.USER_IS_BLOCKED);
                    }

                    return new Withdraw()
                            .setNewAmount(wallet.accumulateAndGet(
                                    serviceInfo.servicePrice,
                                    (prevValue, arg) -> {
                                        BigDecimal newValue = prevValue.add(arg.negate());
                                        return newValue.compareTo(BigDecimal.ZERO) > 0 ? newValue : prevValue;
                                    }
                            ))
                            .setUserId(userProfile.userId)
                            .setStatus(Withdraw.Status.OK);
                });
    }

    public CompletableFuture<Withdraw> withdrawMoneyWithMinus(UserProfile userProfile, ServiceInfo serviceInfo) {

        return CompletableFuture
                .supplyAsync(() -> {
                    AtomicReference<BigDecimal> wallet = wallets.get(userProfile.userId);
                    if (wallet == null) {
                        return new Withdraw().setStatus(Withdraw.Status.WALLET_NOT_FOUND);
                    }

                    if (userProfile.isBlocked) {
                        return new Withdraw().setStatus(Withdraw.Status.USER_IS_BLOCKED);
                    }

                    return new Withdraw()
                            .setNewAmount(wallet.accumulateAndGet(
                                    serviceInfo.servicePrice,
                                    (prev, arg) -> prev.add(arg.negate())))
                            .setUserId(userProfile.userId)
                            .setStatus(Withdraw.Status.OK);
                });
    }


}

