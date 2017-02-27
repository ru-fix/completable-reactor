package ru.fix.cpapsm.completable.reactor.example.chain;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.fix.cpapsm.completable.reactor.example.processors.AccountInfo;
import ru.fix.cpapsm.completable.reactor.example.processors.ServiceInfo;
import ru.fix.cpapsm.completable.reactor.example.processors.UserProfile;

/**
 * Created by swarmshine on 16.10.2016.
 */
public class SubscribePayload implements ServiceInfoPayloadMixin, UserProfilePayloadMixin {

    @Data
    @Accessors()
    public static class Request {
        Long userId;
        Long serviceId;
    }

    @Data
    @Accessors()
    public static class Response {
        Enum status;
        Integer moneyWithdrawed;
    }

    @Data
    @Accessors()
    public static class IntermediateData{
        ServiceInfo serviceInfo;
        AccountInfo accountInfo;
        UserProfile userInfo;
    }

    public final PurchasePayload.Request request = new PurchasePayload.Request();
    public final PurchasePayload.IntermediateData intermediateData = new PurchasePayload.IntermediateData();
    public final PurchasePayload.Response response = new PurchasePayload.Response();



    @Override
    public Long getServiceId() {
        return request.serviceId;
    }

    @Override
    public ServiceInfo getServiceInfo() {
        return intermediateData.serviceInfo;
    }

    @Override
    public void setServiceInfo(ServiceInfo serviceInfo) {
        intermediateData.serviceInfo = serviceInfo;
    }

    @Override
    public Long getUserId() {
        return request.userId;
    }

    @Override
    public UserProfile getUserProfile() {
        return intermediateData.userInfo;
    }

    @Override
    public void setUserProfile(UserProfile userProfile) {
        intermediateData.userInfo = userProfile;
    }

    @Override
    public void setStatus(Enum status) {
        response.status = status;
    }

    @Override
    public Enum getStatus() {
        return response.status;
    }


}
