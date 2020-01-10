package ru.fix.completable.reactor.example;


import ru.fix.completable.reactor.graph.Submit;

import java.math.BigDecimal;

/**
 * @author Kamil Asfandiyarov
 */
public interface PurchaseSubmit extends Submit<PurchaseSubmit.Request, PurchaseSubmit.Response> {

    class Request {
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

    class Response {
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
}
