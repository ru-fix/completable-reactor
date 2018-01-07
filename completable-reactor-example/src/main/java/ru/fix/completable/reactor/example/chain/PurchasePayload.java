package ru.fix.completable.reactor.example.chain;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.fix.completable.reactor.api.Reactored;
import ru.fix.completable.reactor.example.processors.ServiceInfo;
import ru.fix.completable.reactor.example.processors.UserProfile;

import java.math.BigDecimal;

/**
 * @author Kamil Asfandiyarov
 */
@Reactored({
        "Represent purchase request"
})
public class PurchasePayload{

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

        public Response setStatusIfNull(Enum status){
            this.status = status;
            return this;
        }
    }


    @Data
    @Accessors
    public static class IntermediateData{
        ServiceInfo serviceInfo;
        UserProfile userInfo;

    }

    public final Request request = new Request();
    public final IntermediateData intermediateData = new IntermediateData();
    public final Response response = new Response();


    public Long getServiceId() {
        return request.serviceId;
    }

    public ServiceInfo getServiceInfo() {
        return intermediateData.serviceInfo;
    }

    public void setServiceInfo(ServiceInfo serviceInfo) {
        intermediateData.serviceInfo = serviceInfo;
    }

    public Long getUserId() {
        return request.userId;
    }

    public UserProfile getUserProfile() {
        return intermediateData.userInfo;
    }

    public void setUserProfile(UserProfile userProfile) {
        intermediateData.userInfo = userProfile;
    }

    public void setStatus(Enum status) {
        this.response.status = status;
    }

    public Enum getStatus() {
        return response.status;
    }
}
