package ru.fix.completable.reactor.example.processors;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.fix.completable.reactor.api.HandlerDescription;
import ru.fix.completable.reactor.api.ProcessorDescription;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * @author Kamil Asfandiyarov
 */
@ProcessorDescription(doc = {
        "Service information processor load data for given service from database.",
        "Service could be not active. In that case purchase request will be denied.",

})
public class ServiceInfoProcessor  {
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

    @Data
    @Accessors(chain = true)
    public static class ServiceInfoResult {
        Status status;
        ServiceInfo serviceInfo;
    }

    @HandlerDescription(doc = {
            "Load service information from database.",
    })
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
