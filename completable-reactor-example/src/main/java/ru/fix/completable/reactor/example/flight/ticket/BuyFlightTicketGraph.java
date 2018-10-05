package ru.fix.completable.reactor.example.flight.ticket;

import lombok.Data;
import lombok.experimental.Accessors;
import ru.fix.aggregating.profiler.AggregatingProfiler;
import ru.fix.completable.reactor.graph.Graph;
import ru.fix.completable.reactor.graph.Vertex;
import ru.fix.completable.reactor.runtime.CompletableReactor;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;



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

public class BuyFlightTicketGraph extends Graph<BuyFightTicketPayload> {

    SalesDepartment salesDepartment = new SalesDepartment();
    Bank bank = new Bank();
    EmailClient emailClient = new EmailClient();
    TransactionJournal transactionJournal = new TransactionJournal();

    enum Flow{
        DENY_PURCHASE,
        CONTINUE
    }


    Vertex askForPrice =
            handler(
                    payload -> salesDepartment.calculateCurrentPrice(payload.request.destination)
            ).withMerger((payload, currentPrice) -> {
                payload.intermediateData.price = currentPrice;
            });

    Vertex withdrawMoney =
            handler(
                    payload -> bank.withdrawMoney(payload.intermediateData.price)
            ).withRoutingMerger((payload, reserveSuccessful) -> {
                if(reserveSuccessful){
                    payload.response.operationResult = "Successful purchase for " + payload.intermediateData.price;
                    return Flow.CONTINUE;
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

    {
        payload()
                .handleBy(askForPrice);
        askForPrice
                .onAny().handleBy(withdrawMoney);

        withdrawMoney
                .on(Flow.CONTINUE).handleBy(sendSuccessEmail)
                .on(Flow.CONTINUE).handleBy(logTransaction)
                .on(Flow.DENY_PURCHASE).handleBy(sendDenyEmail);

        logTransaction.onAny().complete();

        sendDenyEmail.onAny().complete();

    }


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
