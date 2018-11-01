package ru.fix.completable.reactor.example.flight.ticket;

import ru.fix.aggregating.profiler.AggregatingProfiler;
import ru.fix.completable.reactor.runtime.CompletableReactor;

import java.util.concurrent.CompletableFuture;

public class BuyFlightTicketApplication {
    // tag::main[]
    //
    //  Single instance of completable reactor created for application.
    //  Graph registered withing reactor.
    //  Payload submitted to reactor and received as a result of computation.
    //
    public static void main(String[] args) {
        CompletableReactor completableReactor = new CompletableReactor(new AggregatingProfiler());
        completableReactor.registerGraph(new BuyFlightTicketGraph());

        BuyFightTicketPayload payload = new BuyFightTicketPayload();
        payload.request
                .setAge(30)
                .setName("John Smith")
                .setDestination("New York");

        CompletableFuture<BuyFightTicketPayload> future = completableReactor.submit(payload).getResultFuture();

        BuyFightTicketPayload resultPayload = future.join();

        System.out.println("Result: " + resultPayload.response.operationResult);
    }
    // end::main[]
}
