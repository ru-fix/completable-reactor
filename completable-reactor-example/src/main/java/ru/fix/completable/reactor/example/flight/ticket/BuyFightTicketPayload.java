package ru.fix.completable.reactor.example.flight.ticket;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

// tag::payload[]
//
// In given example flight ticket purchase process implemented as a reactor graph.
// Payload contains request, response and intermediate data for computation.
//
@Data
class BuyFightTicketPayload {
    @Data
    @Accessors(chain = true)
    class Request {
        String destination;
        String name;
        Integer age;
    }

    @Data
    class IntermediateData {
        BigDecimal price;
    }

    @Data
    class Response {
        String operationResult;
    }

    final Request request = new Request();
    final IntermediateData intermediateData = new IntermediateData();
    final Response response = new Response();
}
// end::payload[]