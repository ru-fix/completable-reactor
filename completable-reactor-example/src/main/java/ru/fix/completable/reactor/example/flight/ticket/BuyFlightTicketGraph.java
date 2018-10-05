package ru.fix.completable.reactor.example.flight.ticket;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.fix.aggregating.profiler.AggregatingProfiler;
import ru.fix.completable.reactor.graph.Graph;
import ru.fix.completable.reactor.graph.Vertex;
import ru.fix.completable.reactor.runtime.CompletableReactor;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;


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

//
// All graph classes extends base Graph<Payload>
//
public class BuyFlightTicketGraph extends Graph<BuyFightTicketPayload> {
    //
    // During execution graph uses external async services as a building blocks to create complex business process.
    //
    SalesDepartment salesDepartment = new SalesDepartment();
    Bank bank = new Bank();
    EmailClient emailClient = new EmailClient();
    TransactionJournal transactionJournal = new TransactionJournal();

    //
    //  Enum values identifies transitions in graph
    //
    enum Flow {
        DENY_PURCHASE,
        SUCCESS_WITHDRAW
    }

    //
    //  Vertex represent step in business process.
    //  Vertex encapsulates async method invocation and merging result of invocation into payload.
    //
    Vertex askForPrice =
            handler(
                    payload -> salesDepartment.calculateCurrentPrice(payload.request.destination)
            ).withMerger((payload, currentPrice) -> {
                payload.intermediateData.price = currentPrice;
            });

    Vertex withdrawMoney =
            handler(
                    // Withdraw money from user account to purchase flight ticket
                    payload -> bank.withdrawMoney(payload.intermediateData.price)
            ).withRoutingMerger(
                    //# Is withdraw successful?
                    (payload, withdrawSuccessful) -> {
                        if (withdrawSuccessful) {
                            payload.response.operationResult = "Successful purchase for " + payload.intermediateData.price;
                            return Flow.SUCCESS_WITHDRAW;
                        } else {
                            payload.response.operationResult = "Money withdraw failed";
                            return Flow.DENY_PURCHASE;
                        }
                    });

    Vertex sendDenyEmail =
            handler(
                    payload -> emailClient.sendEmail("Sorry, can not purchase a ticket.")
            ).withoutMerger();

    Vertex sendSuccessEmail =
            handler(
                    payload -> emailClient.sendEmail("Congratulations, you have purchased a ticket.")
            ).withoutMerger();

    Vertex logTransaction =
            handler(
                    payload -> transactionJournal.logTransaction(
                            payload.request.name + " purchased a ticket, price: " + payload.intermediateData.price))
                    .withoutMerger();

    //
    // To build graph we join vertices with transitions.
    // Some transitions could be conditional.
    //
    {
        payload()
                .handleBy(askForPrice);

        askForPrice
                .onAny().handleBy(withdrawMoney);

        withdrawMoney
                .on(Flow.SUCCESS_WITHDRAW).handleBy(sendSuccessEmail)
                .on(Flow.SUCCESS_WITHDRAW).handleBy(logTransaction)
                .on(Flow.DENY_PURCHASE).handleBy(sendDenyEmail);

        logTransaction
                .onAny().complete();

        sendDenyEmail
                .onAny().complete();

        coordinates()
                .pd(50, 0)
                .vx(logTransaction, 375, 315)
                .vx(sendDenyEmail, -183, 344)
                .vx(sendSuccessEmail, 183, 352)
                .vx(askForPrice, 96, 49, 133, 112)
                .vx(withdrawMoney, 82, 164, 80, 239);
    }

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

}

//
//  Simple implementation of external async services that being used by the graph.
//
class SalesDepartment {
    CompletableFuture<BigDecimal> calculateCurrentPrice(String destination) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("SalesDepartment: calculateCurrentPrice for " + destination);
            return BigDecimal.valueOf(12.0);
        });
    }
}

class Bank {
    CompletableFuture<Boolean> withdrawMoney(BigDecimal amount) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Bank: withdrawMoney " + amount);
            return true;
        });
    }
}

class EmailClient {
    CompletableFuture<Void> sendEmail(String message) {
        return CompletableFuture.runAsync(() -> System.out.println("EmailClient: " + message));
    }
}

class TransactionJournal {
    CompletableFuture<Void> logTransaction(String message) {
        return CompletableFuture.runAsync(() -> System.out.println("TransactionJournal: " + message));
    }
}