# CompletableReactor
[![Maven Central](https://img.shields.io/maven-central/v/ru.fix/completable-reactor-runtime.svg)](https://search.maven.org/search?q=g:ru.fix%20and%20a:completable-reactor-runtime)

CompletableReactor framework makes it easier to create business flows that have concurrently running parts
 and complex execution branching.

CompletableReactor provides DSL-like builder-style API to describe business flows and visualization plugins
that parse code and displays it as an execution graph. CompletableReactor follows code-first approach when developer
writes code and IDE visualizes it in plugin window and provides ability to navigate from graph to code and backward.

Framework built on top of Fork Join Pool and CompletableFuture API. Different JVM based languages use extension
to support more suitable DSL-like API. Currently supported DSLs are for `Java` and `Kotlin` languages.  

## Table of contents
- [CompletableReactor](#completablereactor)
  * [Table of contents](#table-of-contents)
  * [Motivation](#motivation)
  * [Getting Started](#getting-started)
  * [Completable Reactor model](#completable-reactor-model)
    + [Guide](docs/completable-reactor-model/completable-reactor-model.md)
  * [Validation](#validation)
  * [Intellij Idea Plugin](#intellij-idea-plugin)
  * [Examples](#examples)
  * [Best Practice and Code Convention](#best-practice-and-code-convention)


## Motivation
[CompletableFuture API](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html)
with [ForkJoinPool](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ForkJoinPool.html)
provides ability to write asynchronous code. But sometimes business logic not as straight forward as we would like.
This leads to complex `thenApply`/`thenCompose` CompletableFuture chains that hard to read and maintain. Kotlin
[`suspend` methods and coroutines](https://kotlinlang.org/docs/reference/coroutines.html)
slightly simplify sequential chains, but they are still fail to clarify complexity of
unobvious conditional branching with scenarios executed in parallel.  

Complex business logic with lots of branching and concurrently executing parts hard to describe using regular coding
 approach without proper visualization. One of ways to represent that branching is a graph with nodes and
 transitions.  
CompletableReactor API tries to keep API as simple and possible, saves benefits of statically typed languages and in
same time provides ability for fast code parsing and visualization as a graph of execution flows.

## Getting Started

#### Add CompletableReactor dependencies into your project.
  
For Java:
* completable-reactor-runtime [![](https://img.shields.io/maven-central/v/ru.fix/completable-reactor-runtime.svg)](https://search.maven.org/search?q=g:ru.fix%20and%20a:completable-reactor-runtime)
* completable-reactor-graph [![](https://img.shields.io/maven-central/v/ru.fix/completable-reactor-runtime.svg)](https://search.maven.org/search?q=g:ru.fix%20and%20a:completable-reactor-graph)

For Kotlin:
* completable-reactor-runtime [![](https://img.shields.io/maven-central/v/ru.fix/completable-reactor-runtime.svg)](https://search.maven.org/search?q=g:ru.fix%20and%20a:completable-reactor-runtime)
* completable-reactor-graph-kotlin [![](https://img.shields.io/maven-central/v/ru.fix/completable-reactor-runtime.svg)](https://search.maven.org/search?q=g:ru.fix%20and%20a:completable-reactor-graph-kotlin)

#### Install plugin into your IDE
* [Intellij Idea Plugin](https://plugins.jetbrains.com/plugin/9599-completable-reactor)
* Eclipse Plugin (TODO)
* Standalone application completable-reactor-graph-viewer-app [![](https://img.shields.io/maven-central/v/ru.fix/completable-reactor-runtime.svg)](https://search.maven.org/search?q=g:ru.fix%20AND%20a:completable-reactor-graph-viewer-app)


#### Write simple graph application
In given example flight ticket purchase process implemented as a reactor graph.  
Payload contains request, response and intermediate data for computation.  
```java
class BuyFightTicketPayload {
    class Request {
        String destination;
        String name;
        Integer age;
    }
    class IntermediateData {
        BigDecimal price;
    }
    class Response {
        String operationResult;
    }
    final Request request = new Request();
    final IntermediateData intermediateData = new IntermediateData();
    final Response response = new Response();
}
```
All graph classes extends base `Graph<Payload>`
```java
public class BuyFlightTicketGraph extends Graph<BuyFightTicketPayload> {
    //
    // During execution graph uses external async services as a building blocks to create complex business process.
    //
    SalesDepartment salesDepartment = new SalesDepartment();
    Bank bank = new Bank();
    EmailClient emailClient = new EmailClient();
    FlightPlanner flightPlanner = new FlightPlanner();

    //
    //  Enum values identifies transitions in graph
    //
    enum Flow {
        DENY_PURCHASE,
        SEAT_RESERVED,
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

    Vertex reserveSeat =
            handler(
                    payload -> flightPlanner.reserveSeat()
            ).withRoutingMerger((payload, isSeatReserved) -> {
                if(!isSeatReserved){
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
    }
}
```
Single instance of completable reactor created for application.
Graph registered withing reactor.
Payload submitted to reactor and received as a result of computation.
```java
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
``` 
Simple implementation of external async services that being used by the graph.
```java
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
```
Console output: 
```
SalesDepartment: calculate current price for New York
FlightPlanner: reserve seat
Bank: withdraw money: 12.0
EmailClient: Congratulations, you have purchased a ticket.
Result: Successful purchase for 12.0
```

#### View visual representation of the Graph
* Display visual graph directly from code by `Ctrl+R` shortcut `(Tools->ReactorGraph)` 
* Navigate from graph to code by double-clicking on the nodes
* Read graph description in nodes menu generated directly from code comments 
![](docs/flight-graph.png?raw=true)

#### Monitor graph execution
* Monitor graph and individual vertex performance   
* Trace how graph execute through individual vertex by enabling tracing for particular payloads 
* Check reactor reports about vertices that executed too long, did not complete on timeout or hang


## Completable Reactor model
Describes concept behind CompletbleReactor.  
Explains how we can decompose concurrent conditional execution into base graph components.  
Describes how reactor components could be described in code and how they visualized.

[Completable Reactor model](docs/completable-reactor-model/completable-reactor-model.md)


## Validation

During ReactorGraph building process CompletableReactorBuilder apply validation procedures on ReactorGraph instance. Validators checks
that graph is consistent, have complete nodes and correct transitions etc.

## Intellij Idea Plugin
https://plugins.jetbrains.com/plugin/9599-completable-reactor

Completable Reactor Intellij Idea plugin provides graph visualization and source code navigation within IDE.  
You can jump to code using double click on graph item or context menu.

## Examples

## Best Practice and Code Convention

Paragraph explains handy rules that will keep your code clear

[Best Practice and Code Convention](docs/best-practice-code-convention/best-practice-code-convention.md)  

# How to build this project
Intellij plugin located at `completable-reactor-plugin-idea` directory.  
In order to include plugin module into Intellij Idea you have to manually add line   
`<module fileurl="file://$PROJECT_DIR$/completable-reactor-plugin-idea/completable-reactor-plugin-idea.iml" filepath="$PROJECT_DIR$/completable-reactor-plugin-idea/completable-reactor-plugin-idea.iml" />`      
into `.idea/modules.xml` configuration file.  

