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
Transition is a Enum instance that represent jumps between graph items during flow execution. MergePoint merger 
returns instance of Enum. Outgoing transition will be activated according to this value. If merger returns status 
PLAN_B, then all outgoing transitions with condition status PLAN_B will be activated and all transitions without 
PLAN_B status will be deactivated or marked as dead transition. For unconditional transitions (onAny) there is no 
distinct status. That transitions will be always activated regardless of the merger result.

```java
enum MyTransitions{
    @Reactored("OneWay transition description")
    ONE_WAY,
    @Reactored("AnotherWay transition description")
    ANOTHER_WAY
}
```
### EndPoint
EndPoint is a visual graph item that indicates end of flow execution. When graph execution reaches EndPoint then all 
transitions marked as terminated  and CompletableReactor immediately returns graph result. The only exception
 is detached processors or subgraphs. They will continue to execute but their execution result will be ignored.   

![Alt end-point.png](docs/end-point.png?raw=true "EndPoint")


### Processor with MergePoint

![Alt processor-with-merge-point.png](docs/processor-with-merge-point.png?raw=true "Processor with MergePoint")

Processor uses Handler to make asynchronous computation, MergePoint uses Merger to apply Handler computation result 
on Payload. Origin payload is passed to Processors handler, then after handler computation origin payload is passed 
together with handler result to merge point. Inside merge point origin payload is modified and became Payload*. 
Outgoing transitions from merge point pass this new Payload* to next nodes.  

## Subgraphs

## Routers and Mutators