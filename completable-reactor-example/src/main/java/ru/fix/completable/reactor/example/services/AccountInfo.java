package ru.fix.completable.reactor.example.services;

import java.math.BigDecimal;

/**
 * Created by swarmshine on 01.11.2016.
 */
public class AccountInfo {
    public Long userId;
    public BigDecimal money;

    public AccountInfo setMoney(BigDecimal money) {
        this.money = money;
        return this;
    }

    public AccountInfo setUserId(Long userId) {
        this.userId = userId;
        return this;
    }
}
