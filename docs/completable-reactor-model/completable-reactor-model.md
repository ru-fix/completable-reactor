#  Completable Reactor Model
It is essential to read
[Handler-Merger execution model](../handler-merger-model/handler-merger-model.md)
first.

Completable Reactor Model enriches
[Handler-Merger execution model](../handler-merger-model/handler-merger-model.md)
with new components and determinate its execution on JVM.


## Table of Content
- [Completable Reactor Model](#completable-reactor-model)
  * [Payload and StartPoint](#payload-and-startpoint)
  * [Vertices](#vertices)
  * [Handlers and Mergers](#handlers-and-mergers)
    + [Transition](#transition)
    + [EndPoint](#endpoint)
  * [Subgraph](#subgraph)
  * [Routers and Mutators](#routers-and-mutators)
    + [Merger decision](#merger-decision)
    + [Parallel execution](#parallel-execution)
    + [Detached Handler without Merger](#detached--handler-without-merger)
    + [Detached Handler with Merger](#detached-handler-with-merger)

## Payload and StartPoint
Payload is a plain old java object that encapsulates request, response
and intermediate computation data required for request processing.
CompletableReactor receives payload as an argument.
Executes business flows, modifies payload during execution and returns it as a result.
```java
class Purchase{
    //request parameters
    //provided by user during graph launching
    Long userId;
    Long productId;

    //intermediate data, used to pass information
    //from one vertex to another vertex
    Boolean moneyReservationStatus;
    Boolean productReservationStatus;

    //results
    //that user receives when graph execution is complete
    Boolean purchaseStatus;
}
```
We can add little structure to payload to clarify purpose of each field
and make our graph code more readable:
```java
class Purchase{
    Request request;
    Intermediate intermediate;
    Response response;

    Purchase(Long userId, Long productId){
        this.request = new Request(userId, productId);
    }

    static class Request{
        Long userId;
        Long productId;

        Request(Long userId, Long productId){
            this.userId = userId;
            this.productId = productId;
        }
    }
    static class Intermediate{
        Boolean moneyReservationStatus;
        Boolean productReservationStatus;
    }
    static class Response{
        Boolean purchaseStatus;
    }
}
```
To launch graph user should create Payload instance and submit it to reactor:
```java

//launch graph
Execution<Purchase> execution = completableReactor.submit(new Purchase(107, 42));

//wait for graph to complete
Purchase purchase = execution.getResultFuture().get();

//check result
System.out.print( purchase.getResponse().getPurchaseStatus() );
```
Start point visualization:    
![Alt start-point](res/start-point.png?raw=true "start-point")

To attach vertices to StartPoint you can use `payload()` builder method of graph.
```java

class PurchaseGraph extends Graph<Purchase>{

    Vertex reserveMoney = ...
    Vertex reserveProduct = ...

    {
        payload()
                .handleBy(reserveMoney)
                .handleBy(reserveProduct);
    }
}
```
![Alt start-point-handle-by](res/start-point-handle-by.png?raw=true "start-point-handle-by")

## Vertices
Vertex is a visual graph item that represent a handler and optional merger invocation.
Vertex encapsulates coupled business logic that can be reused in different branches.
There are three types of vertices:
* Handlers with or without Mergers
```java
    Vertex vx = handler(...).withRoutingMerger(...);
    Vertex vx = handler(...).withMerger(...);
    Vertex vx = handler(...).withoutMerger();
```
* Subgraphs
```java
    Vertex vx = subgraph(...).withRoutingMerger(...);
    Vertex vx = subgraph(...).withMerger(...);
    Vertex vx = subgraph(...).withoutMerger();
```
* Routers and Mutators
```java
    Vertex vx = router(...);
    Vertex vx = mutator(...);
```

## Handlers and Mergers

Handler is an asynchronous function that takes information from Payload and returns computation result.
Handler implements asynchronous part of business logic of the execution flow.
It could be reused in different flows branches several time.
Handler `MUST NOT` change Payload because same instance of Payload passed to other handlers in parallel.
Is is ok to concurrently read payload from several handlers but not to modify payload.
The only point of payload modification is Merger.  
```java
Vertex reserveMoney =
        handler(
            // Reserve required amount of money for the purchase in the bank
            payload -> bank.reserve(payload.getAccountId(), payload.getAmount())
        )
        ...;
```
Merger is a synchronous function that takes Handlers computation result and uses it to update Payload.
It is safe to modify payload within merger.

There are several types of Mergers: RoutingMerger, Merger and EmptyMerger.
Routing Merger can change Payload and should always return enum value that defines outgoing transition.
Graph will continue to execute by this transition returned from RoutingMerger function.
In given example we send request to bank.
Then we saves reservation result in payload and return transition value depending on reservation result.
Graph will continue to execute by transition that will be selected based on enum value returned by RoutingMerger.

```java
Vertex reserveMoney =
        handler(
            // Reserve required amount of money for the purchase in the bank
            payload -> bank.reserve(payload.getAccountId(), payload.getAmount())

        ).withRoutingMerger((payload, result) -> {
            if(result.getStatus() == OK){
                payload.moneyReserved = true;
                return RESERVED;

            } if(result.getStatus() == NO_MONEY){
                payload.moneyReserved = false;
                payload.reservateionFailReason = NO_MONEY;
                return NO_MONEY;

            } else {
                payload.moneyReserved = false;
                payload.reservateionFailReason = OTHER_REASON;
                return OTHER_REASON;
            }
        });
}
```
![Alt routing-merger](res/routing-merger.png?raw=true)

If the only thing you need is to modify Payload and unconditionally continue to execute graph
by the next Vertex, then you need to use Merger.
Merger works in a same way as RoutingMerger but it is not required for Merger to return any value.
In given example we send notificatin and save it's result in payload.
But graph execution path does not depend on notification result.

```java
Vertex sendNotification =
        handler(
            payload -> notificatior.sendNotification(
                    payload.getUserId(),
                    "You are purchasing " + payload.getServiceTitle())

        ).withMerger((payload, result) -> {
            if(result.getStatus() == OK){
                payload.isNotificationSent = true;

            }
        });
}
```
![Alt merger](res/merger.png?raw=true)

Some times we even do not care about saving result of asynchronous operation in payload.
In that case we can use EmptyMerger.
EmptyMerger does not do anything.
It simply waits for handler to complete and the by pass execution down the flow though outgoing transition.

```java
Vertex trySendEmail =
        handler(
            payload -> mailer.sendMail(
                    payload.getUserId(),
                    "You purchased " + payload.getServiceTitle())

        ).withEmptyMerger();
}
```
![Alt empty-merger](res/empty-merger.png?raw=true)

Last option with mergers is not to use them at all!
The differences with EmptyMerger is that handler will be executed in parallel with other vertices but will
not trigger any outgoing transitions because such vertex does not have any.  


```java
Vertex fireStatisticEvent =
        handler(
            payload -> statistic.fireEvent(
                    new PurchaseEvent(
                        payload.getUserId(),
                        payload.getServiceId())
                    )

        ).withoutMerger();
}
```
![Alt without-merger](res/without-merger.png?raw=true)

No we can connect described vertices into graph.
```java
{
    payload()
        .handleBy(reserveMoney)
        .handleBy(sendNotification)
        .handleBy(fireStatisticEvent);

    sendNotification
        .onAny().mergeBy(reserveMoney);

    reserveMoney
        .on(RESERVED).handleBy(trySendEmail)
        .on(NO_MONEY).complete()
        .on(OTHER_REASON).complete();

    trySendEmail
        .onAny().complete()
}
```
![Alt mergers-example](res/mergers-example.png?raw=true)


### Transition
Transition is a Enum instance that represent jumps between graph items during flow execution.
RoutingMerger returns instance of Enum.
Outgoing transition will be activated according to this value.
If merger returns status PLAN_B, then all outgoing transitions with condition status PLAN_B
will be activated and all transitions without PLAN_B status will be deactivated
or marked as dead transition.
Unconditional transitions `onAny` will be always activated regardless of the Routing Merger result.
EmptyMerger and Merger work as a RoutingMerger that returns internal default merge status that
could participate only in `onAny` transitions.

```java
enum MyTransitions{
    ONE_WAY,
    ANOTHER_WAY
}

{
    vx1
        .on(ON_WAY).handleBy(vx2)
        .on(ANOTHER_WAY).handleBy(vx3);

    vx2
        .onAny().handleBy(vx4);
}
```
* `handleBy` transition connect Merers, Routers and Mutators to Handlers, Subgraphs, Routers and Mutators.  
* `mergeBy` transition connect Merers, Routers and Mutators to Mergers.



### EndPoint
EndPoint is a visual graph item that indicates end of flow execution.
When graph execution reaches EndPoint then all transitions marked as terminated
and CompletableReactor immediately returns graph result.
The only exception is Handlers and Subgraphs without mergers.
They will continue to execute but their execution result will be ignored.
```java
Execution<Purchase> execution = reactor.submit(new Purchase(100, 42));

//chain execution future completes when all vertices, including
//Handlers and Subgraphs without mergers are complete and graph reached EndPoint.
execution.getChainExecutionFuture()

//chain result future completes when graph reaches EndPoint
execution.getResultFutureFuture()

```
Yuy can attach EndPoint to any of transition withing reactor graph.
```java
    vx3.onAny().complete()
```    

![Alt endpoint.png](res/endpoint.png?raw=true)


## Subgraph
Subgraph - is a Vertex that allows to invoke one graph from another.
It simply submits constructed child Payload to CompletableReactor.
Subgraph has implicit Handler that takes Payload as argument and returns Payload
as computation result.
Subgraphs allows us to reuse graphs.
```java
Vertex bonusPurchase =
        subgraph(
                Purchase.class,
                paylaod -> new Purchase(payload.userId, payload.bonusServiceId)
        ).withMerger(...)

```

![Alt subgraph.png](res/subgraph.png?raw=true)


## Routers and Mutators
* Router is a synchronous function that works as RoutingMerger.
It can modify payload.
Outgoing transition activated depending ot Router result.  
* Mutator works as Merger.
It can only modify payload and does not return any value.

```java
Vertex vx1 = router(
        paylaod -> {
            payload.data = ...;
            return ONE_WAY;

        });

Vertex vx2 = mutator(
        paylaod -> {
            payload.data = ...;
        });


```
![](res/routers-and-mutators.png?raw=true)

### Merger decision

If all of outgoing transitions from Merger have distinct statuses
then flow will continue to execute only one of transitions.

![](res/merger-decision.png?raw=true)

If Merger will return FAIL status then execution completes immediately.
In case of FIRST status flow will continue and Handler2 will be invoked.
In case of SECOND status - Handler3 will be invoked.
In given example there could be three options:  
* Payload -> Handler1 -> Merger1 -> End (if merger returns FAIL status)
* Payload -> Handler1 -> Merger1 -> Handler2 -> Merger2 -> End (if merger returns FIRST status)
* Payload -> Handler1 -> Merger1 -> Handler3 -> Merger3 -> End (if merger returns SECOND status)

At first payload goes to Handler1.
Then framework waits when CompletableFuture returned by Handler1 completes.
After that Merger will be invoked and Payload with Handler1 result will be passed to this merger.
Merger will check Handler1 result, modifies Payload if needed and returns one of statuses:
FAIL, FIRST or SECOND.
After that execution will continue along with one of transitions:
FAIL leads to END, FIRST - to Handler1, SECOND - to Handler3.

### Parallel execution

If two transitions have same condition Status then flow will continue execution in parallel
and both transitions will be activated.

![](res/parallel-execution.png?raw=true)


In given example there could be three options:
* merger1 returns FAIL status:  
Payload -> Handler1 -> Merger1 -> End

* merger1 returns OK, merger3 returns FAIL:  
Payload -> Handler1 -> Merger1 -> Handler2, Handler3 -> Merger3 -> End

* merger1 returns OK, merger3 returns OK:
Payload -> Handler1 -> Merger1 -> Handler2, Handler3 -> Merger3 -> Merger 2 -> continue

CompletableReactor will wait until result of Handler1 is ready.
Then it sends Handler1 result to Merger1.
Merger1 analyzes Handler1 result, mutates Payload instance if needed
and then returns merge status.
If this status is FAIL then flow execution stops.
If this status is OK then framework launches Handler2 and Handler3 in parallel.  
Merger2 have two incoming transitions.
It will wait until all of them is complete and only then will launch merging function.
Suppose Handler2 will finish first.
Merger2 will have to wait for second incoming transition from Merger3.
When Handler3 result is complete, Merger3 will be executed.
If Merger3 returns FAIL then all flows stops.
If Merger3 returns OK then transition from Merger3 to Merger2 activates.
Then Merger2 executes.  
This will leads us to deterministic order of Payload modification and Processors execution.
Handlers could be launched in parallel and provided with same reference to Payload.
But no two mergers can runs concurrently.
Payload always modified by mergers graph sequentially.  


### Detached  Handler without Merger

Some times we are not interesting in computation result of Handler.
We simply need to invoke async method and does not care about it's result.
It could be Notification service that sends message to external system and
current flow business is not depend on notification result.

![](res/detached-without-merger.png?raw=true)

We passing Payload to Hanlder6 by reference.
In that case there could be concurrent reading data by Handler6
and data reading or modification in one of Vertices that will be triggered
after Merger2 is complete.
To exclude problems with concurrency Handler6 should only read and use data
that will not be modified by last part of the graph.
Or Handler6 could simply copy data and continue to work with local copy.  
Merger2 will launch Handler6 function first, and only after receiving CompletableFuture of
computation result of Hanlder6 Merger2 will trigger outgoing transition to Merger1.   
That behaviour will secure Handler 6 invocation and allows it to safely read payload
without expecting of concurrent modification of payload by Merger1.
```java
Vertex handler6 =
        handler( payload -> {

            // It is safe to read data from payload even if they are going to be modified by Merger1
            // Merger1 will be invoked after this method returns CompletableFuture result.

            MutableData data = payload.getMutableData().clone()
            CompletableFuture<ServiceResult> result = asyncService.doAction(data);

            // After we return result Merger1 will be invoked.
            // But asyncService will continue to run with it's own copy of MutableData.
            return result;
            }
        ).withoutMerger();
}
```
We can avoid this difficulties if we will follow simple rule:
Populate Payload with new data and handlers results and do not modify this results if you are using them
in detached handlers.
```java
Vertex handler6 =
        handler(
            // It is safe to pass data without copying if we know That
            // now vertices after current one  in the graph will not try to modify this data.
            // Or even better: when Data is simply immutable.
            payload -> asyncService.doAction(paylaod.getData());
        ).withoutMerger();
}
```


### Detached Handler with Merger

Some times we want to launch Handler execution in parallel with main flow and we interested in result.
In that case we also have to pass copy of Payload data to async function of Handler6
or use immutable arguments to prevent concurrent reading by Handler6  async function and data modification by
other Vertices of the graph like we did in Detached Handler without Merger scenario.
Also we have to use Merger to bring Handler6 result back to main flow.

![](res/detached-with-merger.png?raw=true)
