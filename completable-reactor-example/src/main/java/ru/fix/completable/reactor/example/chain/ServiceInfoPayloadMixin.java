package ru.fix.completable.reactor.example.chain;

import ru.fix.completable.reactor.example.processors.ServiceInfo;

/**
 * @author Kamil Asfandiyarov
 */
public interface ServiceInfoPayloadMixin {
    Long getServiceId();
    ServiceInfo getServiceInfo();
    void setServiceInfo(ServiceInfo serviceInfo);
    void setStatus(Enum status);
    Enum getStatus();
}