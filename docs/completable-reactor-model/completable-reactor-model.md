# Completable Reactor Model

# Table of Content
- [Completable Reactor Model](#completable-reactor-model)
- [Table of Content](#table-of-content)
- [Handler-Merger concurrent execution concept](#handler-merger-concurrent-execution-concept)
  * [Simple sequential model](#simple-sequential-model)
  * [Sequential asynchronous handler-merger model](#sequential-asynchronous-handler-merger-model)
  * [Parallel handler-merger model](#parallel-handler-merger-model)
  * [Handler-merger model with conditional transitions](#handler-merger-model-with-conditional-transitions)
  * [StartPoint and EndPoints](#startpoint-and-endpoints)
- [Completable Reactor Model](#completable-reactor-model-1)
  * [Payload and StartPoint](#payload-and-startpoint)
  * [Vertices](#vertices)
  * [Handlers and Mergers](#handlers-and-mergers)
    + [With Routing Merger](#with-routing-merger)
    + [With Merger](#with-merger)
    + [With Empty Merger](#with-empty-merger)
    + [Without Merger](#without-merger)
    + [Connect vertices into graph](#connect-vertices-into-graph)
    + [Transition](#transition)
    + [EndPoint](#endpoint)
  * [Subgraph](#subgraph)
  * [Routers and Mutators](#routers-and-mutators)
    + [Merger decision](#merger-decision)
    + [Parallel execution](#parallel-execution)
    + [Detached Handler without Merger](#detached--handler-without-merger)
    + [Detached Handler with Merger](#detached-handler-with-merger)


#  Handler-Merger concurrent execution concept

To describe and idea behind CompletableReactor let's start with simple model and then evolve it step by step.

## Simple sequential model

This model consists of two base elements: *Payload* and *Vertex*.
Payload is a simple [POJO](https://en.wikipedia.org/wiki/Plain_old_Java_object).
Vertex is active element of model that takes Payload, execute business logic based on that Payload, 
then modify Vertex, stores computation result inside this Payload and pass this Payload to the next Vertex. All 
Vertices linked in chain one after another. Modification of Payload is optional for Vertex.

![Alt simple-sequential-model](../completable-reactor-model/res/simple-sequential-model.png?raw=true "simple-sequential-model")
 
In given example we have two vertices and payload object. Payload consists of two fields: x and result. 
MultiplyVertex reads x from payload, multiplies it by 2 and stores computation result in result field.
StdoutVertex does not modify incoming payload and simply prints result field to stdout.   

## Sequential asynchronous handler-merger model
Now lets make our vertices work asynchronously. We will split computation logic of vertices in two parts.
First one, called `Handler`, will read input data from payload and perform computation.
Second one, called `Merger`, will store computation result inside payload.
Handler will be asynchronous function and Merger will be synchronous.  
Here is an example how to split MultiplyVertex logic to handler and merger functions: handler will read 
argument x from payload, then asynchronously perform multiplication and return CompletableFuture of this operation. 
Merger will get result of handler function as an argument and will store it inside payload in result filed.
```java
CompletableFuture<Integer> handle(Payload payload){
    return CompletableFuture.supplyAsync(()->{
        return payload.getX() * 2;
    });
}

void merge(Payload paylaod, int result){
    paylaod.setResult(result);
}
```
Visual representation:  
![Alt sequential-async-handler-merger-model](../completable-reactor-model/res/sequential-async-handler-merger-model.png?raw=true 
"sequential-asynchronous-handler-merger-model")

Visual representation of such computation contains several items:
 - *Handler* bar that represent asynchronous computation of handler function
 - *Merger* circle that represent synchronous computation of merger function
 - *Transition* between handler and merger that carries two objects: payload and computation result 
 of handler function.
 - *Transition* that goes into Handler and carries single object - Payload.
 - *Transition* that goes out of Merger and carries single object - Payload.  
 - *Payload* object that goes into Handler, then to Merger then out of Merger.
 
Now we can simplify visual notation and hide implicit Payload and handler function result.  
![Alt sequential-async-handler-merger-model-simplified.png](../completable-reactor-model/res/sequential-async-handler-merger-model-simplified.png?raw=true
 "sequential-asynchronous-handler-merger-model2")


## Parallel handler-merger model
Lets update Merger and allow it to have several outgoing transitions. When merger function 
completes merging process it will send same Payload through all of outgoing transitions in parallel.  
![Alt parallel-handler-merger-multiple-outgoing](../completable-reactor-model/res/parallel-handler-merger-multiple-outgoing.png?raw=true 
"parallel-handler-merger-merge-point")  
In given example origin Payload and vertex Result goes from Handler to Merger. Then Merger modifies 
Payload and puts result inside it. Then Merger sends this Payload instance that contains result 20 to all outgoing 
transitions. 

Also we need another feature in Merger - an ability to join two incoming transitions into single outgoing. When 
Merger have two incoming transitions: one from Vertex that carries Payload with handler Result and another 
with Payload only, Merger will chose Result from first transition and will merge it to Payload that it received
 from second transition. For activation Merger has to wait for both incoming transitions.   
![Alt parallel-handler-merger-join-flows.png](../completable-reactor-model/res/parallel-handler-merger-join-flows.png?raw=true)

In given illustration there are two incoming transitions into Merger. First incoming transition carries
Payload_2 and computation result of Handler2 - value 42. Second incoming transition  carries ony Payload_1.
Router ignores Payload_2 from first transition. It takes Payload_1 from second transition and takes Result from
First transition. Merges them together by merger function and passes Payload_1 to outgoing transition.
  
Now we are ready to make big step to parallel execution.  
As an example lets implement purchasing process. Suppose that customer with identifier `userId` wants to buy product 
with identifier `item`. During purchase process we have to reserve money on users account and reserve product in our 
storage. `BillingService` will provide asynchronous method to reserve money. And StorageFacility will provide 
asynchronous method to reserve product in the storage. We can require reservation in parallel in both services and 
then if both services successfully performed reservation we can return answer to the user which purchase accomplished 
successfully.  
Plain class `Purchase` will serve as payload object in our model. 
```java
class Purchase{
    Long userId;
    Long item;
    Boolean moneyReserved;
    Boolean productReserved;
    Boolean result;
}
```
`userId` and `item` will identify customer and requested product. `moneyReserved` and `productReserved` fields will 
keep information about reservation status returned from BillingService and StorageFacility.  
Lets visualize execution graph and discuss execution steps in detail. 
![Alt parallel-handler-merger-computation-example](../completable-reactor-model/res/parallel-handler-merger-computation-example.png?raw=true)
As a first step we create Purchase payload and populate userid and item.
Suppose that first Merger at the top simply sends this Payload into two places in parallel:
to BillingService Vertex and StorageFacility Vertex. 
StorageFacility runs reservation logic and send via outgoing transitions two  objects:
origin Purchase payload and product reservation status. 
BillingService runs reservation logic and send via outgoing transition two objects:
origin Purchase payload and money reservation status. 
Left Merger that belongs to BillingService Vertex waits for BillingService to complete.
After that Merger takes money reservation status and puts in into Purchase payload
(by using it's merger function) and sends this Payload through outgoing transition to Merger
on right side of the graph.  
     
```java
//BillingService Vertex Merger
void merge(Purchase paylaod, BillingServiceStatus billingStatus){
    if(billingStatus == RESERVED){
        paylaod.setMoneyReserved(true);
    }
}
```
Right Merger that belongs to StorageFacility Vertex waits two incoming transitions: with StorageFacility 
result and with Payload from BillingService. Then it takes StorageFacility product reservation status and puts it into 
Payload that came from BillingService processor. This payload already contains information about BillingService 
operation status. After that Merger checks both fields: `Purchase.moneyReserved` and `Purchase.productReserved`. 
If both fields are true, Merger sets `Purchase.result` to true. This means that purchase accomplished 
successfully. This process is done by using merge function of StorageFacility MergePoint. 
```java
//StorageFacility Merger
void merge(Purchase paylaod, StorageFacilityStatus storageStatus){
    if(storageStatus == RESERVED){
        paylaod.setProductReserved(true);
    }
    if(paylaod.getMoneyReserved() && payload.getProductReserved()){
        payload.setResult(true);
    } else {
        payload.setResult(false);
    }
}
```
Then StorageFacility Merger sends Payload with BillingService and StorageFacility results through outgoing transition
at the end of the given graph.  

Now we can simplify visualisation:  
![Alt parallel-handler-merger-computation-example-simplified](../completable-reactor-model/res/parallel-handler-merger-computation-example-simplified.png?raw=true)


## Handler-merger model with conditional transitions
We almost thee. Fasten seat belts. Lets enrich our Merger with last feature: conditional transitions:    
![Alt merger-conditional-outgoing-flows](../completable-reactor-model/res/merger-conditional-outgoing-flows.png?raw=true "MergePoint")

Each outgoing transitions now have enum value associated with it. In graph this enum value illustrated as text label 
near arrows.  
Merger function signature is changed too. Now merger should return enum that will control which outgoing transition 
will be activated and which is not.
```java
Enum merge(Payload payload, HandlerResult handlerResult) {...}
``` 
When Merger is activated by incoming transition it evaluates merger function and checks merger result. 
After that Merger marks all outgoing transitions which associated enum values does not match 
merger function result as dead and deactivates them.
Also Merger activates all outgoing transitions which associated with enum values that matches 
merger function result.  
If there are two or more outgoing transitions that matches enum function result then all of them activates and runs 
in parallel.   
In given illustration we can control how graph will execute.
If Merger function return FIRST then two outgoing transitions will run in parallel. 
If Merger function return SECOND then only single transition will run. 

It is important to mention that when at least one of incoming transition of Merger is marked as dead 
then Merger itself marked as dead, it does not execute and simply marks all outgoing transitions as dead too. 
When  Handler have several incoming transitions and one of them is marked as dead 
then there is nothing happens with Handler. Only if all incoming transitions marked as dead Handler dies too and 
marks outgoing transition as dead.  
In other worlds Handler waits result of all incoming transitions. 
If all incoming transitions dies - Handler dies. 
If single incoming transition survives - Handler executes. 
If there are more that one active incoming transitions survives - Handlers rise an error. 
This is inconsistent graph configuration.
Merger in same manner waits result of all incoming transitions. 
But if any of incoming transitions dies - Merger dies too. 
To execute Merger should have all incoming transitions to be alive.    
![Alt merger-conditional-outgoing-example](../completable-reactor-model/res/merger-conditional-outgoing-example.png?raw=true "MergePoint")   

In given example if Merger returns FIRST then:  
 * Vertex1 and Vertex2 execute in parallel, Vertex3 dies.
 * Then runs Merger1 and Merger3 dies.
 * Then runs Merger2
 * Then Vertex4 executes.
 * Then runs Merger4

If Merger returns SECOND then:
 * Vertex1 and Vertex2 dies. Vertex3 executes.
 * Then runs Merger3 and Merger1 with Merger2 dies.
 * Then Vertex4 executes.
 * Then runs Merger4.   

## StartPoint and EndPoints 
The difficult part is over. Now let discuss how to start execution and how to stop it.
StartPoint specify position where Payload start it trip over graph. There is only one StartPoint for each graph.
EndPoints defines places where execution of the graph is stops and current Payload is returned 
as a graph computation result.
![Alt start-point-and-end-point](../completable-reactor-model/res/start-point-and-end-point.png?raw=true "MergePoint")

In given illustration graph execution starts at StartPoint.
* Then Payload goes to Vertex1 and Vertex2 in parallel.
* Then Merger1 executes.
* Then Merger2 executes.
* Then if Merger2 returns FIRST  graph execution stops at left EndPoint
* Otherwise if Merger2 returns SECOND execution continues
* Vertex3 with Merger3 runs sequentially and after that graph execution stops ant bottom EndPoint

# Completable Reactor Model
Completable Reactor Model enriches Handler-Merger execution model with new components 
and determinates its execution on JVM.

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

### With Routing Merger

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

### With Merger

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

### With Empty Merger

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

### Without Merger

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

### Connect vertices into graph

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

### Conditional execution

* Handlers, Routers and Mutators works as logical OR `||`.
Vertex waits for all incoming transition to complete. 
If some transitions marked as dead and there is at least one alive transition - vertex will run
and send paylaod through outgoing transitions. 
Otherwise if all transitions are dead - vertex will not run.
* Mergers works as logical AND `&&`. 
Vertex waits for all incoming transition to complete. 
If at least one transition mareked as daed - vertex will not run. 
All outgoing transitions will be marked as dead. 
Otherwise if all incoming transition are alive - then vertex will run
and send payload through outgoing transitions.  

![](res/conditional-execution-or-and.png?raw=true)

Lets discuss an example where we have two cases in our graph.
First one: when we execute handler A, handler B and handler C in parallel.  
Second one: when we execute handler A, handler B and we do not need to run C at all.
We can implement this behaviour through three approaches:

* Use conditional transition with optional handling and merging

![](res/conditional-execution-1.png?raw=true)

* Use conditional transition with vertex cloning

![](res/conditional-execution-2.png?raw=true)

* Always run vertex C. Use if-else logic inside handler `C` 
and simply do nothing in handler itself.

![](res/conditional-execution-3.png?raw=true)