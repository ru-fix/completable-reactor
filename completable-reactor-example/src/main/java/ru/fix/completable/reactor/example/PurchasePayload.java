package ru.fix.completable.reactor.example;


import ru.fix.completable.reactor.example.services.ServiceInfo;
import ru.fix.completable.reactor.example.services.UserProfile;

import java.math.BigDecimal;

/**
 * @author Kamil Asfandiyarov
 */
public class PurchasePayload {

    public static class Request {
        Long userId;
        Long serviceId;

        public Long getUserId() {
            return userId;
        }

        public Request setUserId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Long getServiceId() {
            return serviceId;
        }

        public Request setServiceId(Long serviceId) {
            this.serviceId = serviceId;
            return this;
        }
    }

    public static class Response {
        Enum status;
        BigDecimal newAmount;
        boolean withdrawalWasInMinus;
        Enum bonusServiceStatus;
        boolean isPartnerService;

        public Enum getStatus() {
            return status;
        }

        public Response setStatus(Enum status) {
            this.status = status;
            return this;
        }

        public BigDecimal getNewAmount() {
            return newAmount;
        }

        public Response setNewAmount(BigDecimal newAmount) {
            this.newAmount = newAmount;
            return this;
        }

        public boolean isWithdrawalWasInMinus() {
            return withdrawalWasInMinus;
        }

        public Response setWithdrawalWasInMinus(boolean withdrawalWasInMinus) {
            this.withdrawalWasInMinus = withdrawalWasInMinus;
            return this;
        }

        public Enum getBonusServiceStatus() {
            return bonusServiceStatus;
        }

        public Response setBonusServiceStatus(Enum bonusServiceStatus) {
            this.bonusServiceStatus = bonusServiceStatus;
            return this;
        }

        public boolean isPartnerService() {
            return isPartnerService;
        }

        public Response setPartnerService(boolean partnerService) {
            isPartnerService = partnerService;
            return this;
        }
    }

    public static class IntermediateData {
        ServiceInfo serviceInfo;
        UserProfile userInfo;
        Long bonusService;

        public ServiceInfo getServiceInfo() {
            return serviceInfo;
        }

        public IntermediateData setServiceInfo(ServiceInfo serviceInfo) {
            this.serviceInfo = serviceInfo;
            return this;
        }

        public UserProfile getUserInfo() {
            return userInfo;
        }

        public IntermediateData setUserInfo(UserProfile userInfo) {
            this.userInfo = userInfo;
            return this;
        }

        public Long getBonusService() {
            return bonusService;
        }

        public IntermediateData setBonusService(Long bonusService) {
            this.bonusService = bonusService;
            return this;
        }


    }

    public final Request request = new Request();
    public final IntermediateData intermediateData = new IntermediateData();
    public final Response response = new Response();

}
