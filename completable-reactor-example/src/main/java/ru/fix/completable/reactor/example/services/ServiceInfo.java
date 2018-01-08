package ru.fix.completable.reactor.example.services;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * Created by swarmshine on 01.11.2016.
 */
@Data
@Accessors(chain = true)
public class ServiceInfo {
    Long serviceId;
    String serviceName;
    boolean isActive;
    BigDecimal servicePrice;
    boolean isSupportTrialPeriod;

    Long bonusMarketing;
    Long bonusService;

}
