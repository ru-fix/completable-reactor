package ru.fix.completable.reactor.example.services;


import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author Kamil Asfandiyarov
 */
public class ServiceRegistry {
    public static final long SERVICE_ID_CAR_WASH = 1L;
    public static final long SERVICE_ID_HAIR_CUT = 2L;
    public static final long SERVICE_ID_INVALID = 3L;
    /**
     * Emulating data in remote database
     */
    final Map<Long, ServiceInfo> services = Stream.of(
            new ServiceInfo()
                    .setServiceId(SERVICE_ID_CAR_WASH)
                    .setServiceName("Car wash")
                    .setServicePrice(BigDecimal.valueOf(10.00))
                    .setActive(true),

            new ServiceInfo()
                    .setServiceId(SERVICE_ID_HAIR_CUT)
                    .setServiceName("Heir cut")
                    .setServicePrice(BigDecimal.valueOf(3.15))
                    .setActive(false))
            .collect(Collectors.toMap(service -> service.serviceId, Function.identity()));

    public enum Status {
        SERVICE_NOT_FOUND, OK

    }

    public static class ServiceInfoResult {
        Status status;
        ServiceInfo serviceInfo;

        public Status getStatus() {
            return status;
        }

        public ServiceInfoResult setStatus(Status status) {
            this.status = status;
            return this;
        }

        public ServiceInfo getServiceInfo() {
            return serviceInfo;
        }

        public ServiceInfoResult setServiceInfo(ServiceInfo serviceInfo) {
            this.serviceInfo = serviceInfo;
            return this;
        }
    }

    /**
     * Load service information from database.
     */
    public CompletableFuture<ServiceInfoResult> loadServiceInformation(Long serviceId) {

        return CompletableFuture
                .supplyAsync(() ->
                        /**
                         * loading data from remote database
                         */
                        services.get(serviceId)
                )
                .thenApplyAsync(service -> {
                    if (service == null) {
                        return new ServiceInfoResult().setStatus(Status.SERVICE_NOT_FOUND);

                    } else {
                        return new ServiceInfoResult().setServiceInfo(service).setStatus(Status.OK);
                    }
                });
    }
}
