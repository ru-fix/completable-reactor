package ru.fix.completable.reactor.example.services;

import java.math.BigDecimal;

/**
 * Created by swarmshine on 01.11.2016.
 */
public class ServiceInfo {
    Long serviceId;
    String serviceName;
    boolean isActive;
    BigDecimal servicePrice;
    boolean isSupportTrialPeriod;

    Long bonusMarketing;
    Long bonusService;

    public Long getServiceId() {
        return serviceId;
    }

    public ServiceInfo setServiceId(Long serviceId) {
        this.serviceId = serviceId;
        return this;
    }

    public String getServiceName() {
        return serviceName;
    }

    public ServiceInfo setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public ServiceInfo setActive(boolean active) {
        isActive = active;
        return this;
    }

    public BigDecimal getServicePrice() {
        return servicePrice;
    }

    public ServiceInfo setServicePrice(BigDecimal servicePrice) {
        this.servicePrice = servicePrice;
        return this;
    }

    public boolean isSupportTrialPeriod() {
        return isSupportTrialPeriod;
    }

    public ServiceInfo setSupportTrialPeriod(boolean supportTrialPeriod) {
        isSupportTrialPeriod = supportTrialPeriod;
        return this;
    }

    public Long getBonusMarketing() {
        return bonusMarketing;
    }

    public ServiceInfo setBonusMarketing(Long bonusMarketing) {
        this.bonusMarketing = bonusMarketing;
        return this;
    }

    public Long getBonusService() {
        return bonusService;
    }

    public ServiceInfo setBonusService(Long bonusService) {
        this.bonusService = bonusService;
        return this;
    }
}
