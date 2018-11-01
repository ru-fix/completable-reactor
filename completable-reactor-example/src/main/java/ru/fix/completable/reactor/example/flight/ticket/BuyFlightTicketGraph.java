package ru.fix.completable.reactor.example.flight.ticket;

import ru.fix.completable.reactor.graph.Graph;
import ru.fix.completable.reactor.graph.Vertex;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

// tag::graph[]
//
// All graph classes extends base Graph<Payload>
//
public class BuyFlightTicketGraph extends Graph<BuyFightTicketPayload> {
    //
    // During execution graph uses external async services as a building blocks
    // to create complex business process.
    //
    SalesDepartment salesDepartment = new SalesDepartment();
    Bank bank = new Bank();
    EmailClient emailClient = new EmailClient();
    FlightPlanner flightPlanner = new FlightPlanner();

    //
    // Enum values identifies transitions in graph
    //
    enum Flow {
        DENY_PURCHASE,
        SEAT_RESERVED,
        SUCCESS_WITHDRAW
    }

    //
    // Vertex represent step in business process.
    // Vertex encapsulates async method invocation and merging result of invocation
    // into payload.
    //
    Vertex askForPrice =
            handler(
                    payload -> salesDepartment.calculateCurrentPrice(payload.request.destination)
            ).withMerger((payload, currentPrice) -> {
                payload.intermediateData.price = currentPrice;
            });

    Vertex reserveSeat =
            handler(
                    payload -> flightPlanner.reserveSeat()
            ).withRoutingMerger((payload, isSeatReserved) -> {
                if (!isSeatReserved) {
                    payload.response.operationResult = "Seat reservation failed";
                    return Flow.DENY_PURCHASE;
                } else {
                    return Flow.SEAT_RESERVED;
                }
            });

    Vertex withdrawMoney =
            handler(
                    // Withdraw money from user account to purchase flight ticket
                    payload -> bank.withdrawMoney(payload.intermediateData.price)
            ).withRoutingMerger(
                    //# Is withdraw successful?
                    (payload, withdrawSuccessful) -> {
                        if (withdrawSuccessful) {
                            payload.response.operationResult = "Successful purchase for " +
                                    payload.intermediateData.price;
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

    //
    // To build graph we join vertices with transitions.
    // Some transitions could be conditional.
    //
    {
        payload()
                .handleBy(askForPrice)
                .handleBy(reserveSeat);

        reserveSeat
                .on(Flow.DENY_PURCHASE).complete()
                .on(Flow.SEAT_RESERVED).mergeBy(askForPrice);

        askForPrice
                .onAny().handleBy(withdrawMoney);

        withdrawMoney
                .on(Flow.SUCCESS_WITHDRAW).handleBy(sendSuccessEmail)
                .on(Flow.DENY_PURCHASE).handleBy(sendDenyEmail);

        sendSuccessEmail
                .onAny().complete();

        sendDenyEmail
                .onAny().complete();

        coordinates()
                .pd(89, -126)
                .vx(askForPrice, 219, -60, 205, 45)
                .vx(reserveSeat, 18, -60, 90, 12)
                .vx(sendDenyEmail, 6, 304, 54, 368)
                .vx(sendSuccessEmail, 248, 309, 312, 369)
                .vx(withdrawMoney, 116, 145, 113, 218)
                .ct(reserveSeat, -67, 66)
                .ct(sendDenyEmail, 47, 420)
                .ct(sendSuccessEmail, 308, 419);
    }
}
// end::graph[]

// tag::services[]
//
// Simple implementation of external async services
// that used by the graph.
//
class SalesDepartment {
    CompletableFuture<BigDecimal> calculateCurrentPrice(String destination) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("SalesDepartment: calculate current price for " + destination);
            return BigDecimal.valueOf(12.0);
        });
    }
}

class Bank {
    CompletableFuture<Boolean> withdrawMoney(BigDecimal amount) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Bank: withdraw money: " + amount);
            return true;
        });
    }
}

class EmailClient {
    CompletableFuture<Void> sendEmail(String message) {
        return CompletableFuture.runAsync(() -> System.out.println("EmailClient: " + message));
    }
}

class FlightPlanner {
    CompletableFuture<Boolean> reserveSeat() {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("FlightPlanner: reserve seat");
            return true;
        });
    }
}
// end::services[]

