package ru.fix.completable.reactor.example;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.fix.completable.reactor.api.Reactored;
import ru.fix.completable.reactor.example.services.ServiceInfo;
import ru.fix.completable.reactor.example.services.UserProfile;

import java.math.BigDecimal;

/**
 * @author Kamil Asfandiyarov
 */
@Reactored({
        "Purchase procedure when user buys goods in a shop"
})
public class PurchasePayload {

    @Data
    @Accessors(chain = true)
    public static class Request {
        Long userId;
        Long serviceId;
    }

    @Data
    @Accessors(chain = true)
    public static class Response {
        Enum status;
        BigDecimal newAmount;
        boolean withdrawalWasInMinus;
        Enum bonusServiceStatus;

        public Response setStatusIfNull(Enum status) {
            this.status = status;
            return this;
        }
    }

    @Data
    @Accessors
    public static class IntermediateData {
        ServiceInfo serviceInfo;
        UserProfile userInfo;
        Long bonusService;
    }

    public final Request request = new Request();
    public final IntermediateData intermediateData = new IntermediateData();
    public final Response response = new Response();

}
