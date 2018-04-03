#  Completable Reactor Model
It is essential to read 
[Handler-Merger execution model](../handler-merger-model/handler-merger-model.md)
first.

Completable Reactor Model enriches 
[Handler-Merger execution model](../handler-merger-model/handler-merger-model.md)
with new components and determinate its execution on JVM.

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

### MergePoint decision

If all of outgoing transitions from MergePoint have distinct statuses then flow will continue to execute only by one 
of transitions.

![Alt merge-point-decision.png](docs/merge-point-decision.png?raw=true "MergePoint decision")

If MergePoint merger will return FAIL status then execution completes immediately. In case of FIRST status flow will 
continue and Processor2 will be invoked. In case of SECOND status - Processor3
In given example there could be three options:  
* Payload -> Processor1 -> End (if merger returns FAIL status)
* Payload -> Processor1 -> Processor2 -> End (if merger returns FIRST status)
* Payload -> Processor1 -> Processor3 -> End (if merger returns SECOND status)

First payload goes to Processor1 and passed to Processor1 handler. Then framework waits when CompletableFuture 
returned by Processor1 handler completes. After that MergePoint merger will be invoked and Payload with Processor1 
handler result will be passed to this merger.  
Merger will check Processor1 handler result, modify Payload if needed and returns one of statuses: FAIL, FIRST, 
SECOND.  After that execution will continue along with one of transitions:
FAIL leads to END, FIRST - to Processor1, SECOND - to Processor3.

### Parallel execution

If two transitions have same condition Status then flow will continue execution in parallel and both transitions will be activated.

![Alt parallel-execution.png](docs/parallel-execution.png?raw=true "Parallel execution")


In given example there could be two options: 
* Payload -> Processor1 -> End (if merger returns FAIL status)
* Payload -> Processor2, Processor3 -> End (if merger returns OK status)

CompletableReactor will way until result of Processor1 is ready. The it send Processor1 result to MergePoint. MergePoint analyzes 
Processor1 result, mutates Payload instance if needed and then returns merge status. If this status is END then flow execution stops. If 
this status is OK then framework launches Processor2 and Processor3 in parallel.  
MergePoint of Processor2 have two incoming transitions. It will wait until all of them is complete and only then will launch merger. 
Suppose Processor2 will finish first. MergePoint of Processor2 will have to wait to second incoming transition from MergePoint of 
Processor3. When Processor3 result is complete, MergePoint of Processor3 will be executed. If MergePoint of Processor3 returns  FAIL then
all flow stops. If MergePoint of Processor3 returns OK then transition from Processor3 MergePoint to Processor2 MergePoint activates. 
Then MergePoint of Processor2 executed.  This will leads us to deterministic order of Payload modification and Processors execution. 


### Detached Processor without MergePoint

Until now we pass by reference Payload instances to Processors. But some times we are not interesting in computation result of processor.
It could be NotificationService that will send message to external system and current business flow is not dependent on notification 
result. We can not simply pass Payload to Processor6 by reference due to concurrent reading data by Processor6 and data modification in 
one of MergePoints. Problem could be solved by passing copy of Payload to Processor6 or by using Immutable arguments in Processor6.

![Alt detached-without-merge-point.png](docs/detached-without-merge-point.png?raw=true "Parallel execution")

### Detached Processor with MergePoint

Some times we want to launch Processor execution in parallel with main flow. In that case we have to pass copy of Payload to Processor6 
or use immutable arguments to prevent concurrent reading by Processor6 and modification by MergePoints like in Detached Processor without
 MergePoint scenario. We have to use MergePoint to bring Processor6 result back to main flow. 

![Alt detached-with-merge-point.png](docs/detached-with-merge-point.png?raw=true "Parallel execution")



### How MergePoint decides which incoming Payload to chose
Processors MergePoint receives Processors computation Result and reference to Payload passed to Processor. If there are incoming 
transitions to Processors MergePoint then MergePoint also receives Payload instances through this transitions. Detached MergePoint does 
not have transition from Processor with Processors Result. It have only incoming transitions. Both Processors MergePoint and Detached 
Merge Point uses same rules to chose Payload for merging.
* If there is single incoming transition (from Processor or other MergePoint) then Payload of this transitions will be used.
* If there are incoming transition from Processor and incoming transition from other MergePoint then Payload of Processors transitions 
 will be used.
* Case when there is no Processors transition and more than one incoming transition is not allowed.
* Case when there is more that one Processors transition is not allowed.

![Alt merge-point-chose-payload.png](docs/merge-point-chose-payload.png?raw=true "MergePoint chose payload")
