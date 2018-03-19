# CompletableReactor
CompletableReactor framework makes it easier to create business flows that have concurrently running parts
 and complex execution branching.

CompletableReactor provides DSL-like builder-style API to describe business flows and visualization plugin 
that parse code and displays it as an execution graph. CompletableReactor follows code-first approach when developer 
writes code and IDE visualize it in plugin window and provides ability to navigate from graph to code and backward.       

Framework built on top of Fork Join Pool and CompletableFuture API. An extension exist to support Kotlin suspendable 
functions. 

## Motivation
[CompletableFuture API](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html) 
with [ForkJoinPool](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ForkJoinPool.html) 
provides ability to write asynchronous code. But sometimes business logic not as straight forward as we would like.
This leads to complex `thenApply`/`thenCompose` CompletableFuture chains that hard to read and maintain.
Complex business logic with lots of branching and concurrently executing parts hard to describe using regular coding
 approach without proper visualization. And best way to represent that branching is graph with nodes.   
CompletableReactor API tries to keep API as simple and possible in same time provides ability to visualize 
code as graph of execution flows.
 
## Concept
To describe and idea behind CompletableReactor let's start with simple model and then evolve it step by step.

### Simple sequential model
This model consists of two base elements: *Payload* and *Vertex*. Payload is a simple POJO. Vertex is 
active element of model that takes Payload, execute business logic based on that Payload, then modify Vertex, stores 
computation result inside this Payload and pass this Payload to the next Vertex. All Vertices linked in chain one 
after another. Modification of Payload is optional for Vertex.

![Alt sequential-model.png](docs/sequential-model.png?raw=true "sequential-model")
 
In given example we have two vertices and payload object. Payload consists of two fields: x and result. 
MultiplyVertex reads x from payload, multiplies it by 2 store computation result in result field. StdoutVertex
does not modify incoming payload and simply prints result field to stdout.   

### Sequential asynchronous handler-merger model
Now lets make our vertices work asynchronously. We will split computation logic of vertices in two parts. First 
one, called *Handler*, will read input data from payload and perform computation. Second one, called *Merger*, will 
store computation result inside payload. Handler will be asynchronous function and Merger will be synchronous.  
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
![Alt sequential-asynchronous-handler-merger-model.png](docs/sequential-asynchronous-handler-merger-model.png?raw=true "sequential-asynchronous-handler-merger-model")

Visual representation of such computation contains several items:
 - *Handler* bar that represent asynchronous computation of handler function
 - *Merger* circle that represent synchronous computation of merger function
 - *Transition* between handler and merger that carries two objects: payload and computation result of handler 
 function.
 - *Transition* that goes into Handler and carries single object - Payload.
 - *Transition* that goes out of Merger and carries single object - Payload.  
 - *Payload* object that goes into Handlers, then to Merger then out of Merger.
 
Now we can simplify visual notation and hide implicit Payload and handler function result.  
![Alt sequential-asynchronous-handler-merger-model2.png](docs/sequential-asynchronous-handler-merger-model2.png?raw=true "sequential-asynchronous-handler-merger-model2")


### Parallel handler-merger model
Lets update MergePoint and allow it to have several outgoing transitions. When merger function inside MergePoint 
completes merging process it will send Payload through all of outgoing transitions in parallel.  
![Alt parallel-handler-merger-merge-point.png](docs/parallel-handler-merger-merge-point.png?raw=true 
"parallel-handler-merger-merge-point")  
In given example origin Payload and processors Result goes from Processor to MergePoint. Then MergePoint modifies 
Payload and puts result inside it. Then MergePoint sends this Payload that contains result 42 to all outgoing 
transitions. 

Also we need another feature in MergePoint - an ability to join two incoming transitions into single outgoing. When 
MergePoint have two incoming transitions: one from Processor that carries Payload with handler Result and another 
with simply Payload, MergePoint will chose Result from first transition and will merge it to Payload that it received
 from second transition. For activation MergePoint has to wait for both incoming transitions.   
![Alt parallel-handler-merger-merge-point2.png](docs/parallel-handler-merger-merge-point2.png?raw=true)
In given illustration there are two incoming transitions into MergePoint. First incoming transition carries Payload2 
and computation result of Processor2 - value 42. Second incoming transition  carries ony Payload1. MergePoint ignores
Payload2 from first transitions. It takes Payload1 from second transition and takes Result from First transition. 
Merges them together by merger function and passes Payload1 to outgoing transition.
  
Now we are ready to make big step to parallel execution.  
As an example lets implement purchasing process. Suppose that customer with identifier `userId` want to buy product 
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
![Alt parallel-handler-merer-computation.png](docs/parallel-handler-merer-computation.png?raw=true)
As a first step we create Purchase payload and populate userid and item. After that this payload first MergePoint 
that does not modify payload but simply sends same Payload instance into two places in parallel: to BillingService 
Processor and StorageFacility Processor. StorageFacility runs reservation logic and send via outgoing transitions two
 objects: origin Purchase payload and product reservation status. BillingService runs reservation logic and send via 
 outgoing transition two objects: origin Purchase payload and money reservation status.  
Left MergePoint that belongs to BillingService Processor waits for BillingService to complete. Then MergePoint takes 
money reservation status and puts in into Purchase payload (by using it's merger function) and sends this Payload 
through outgoing transition to MergePoint on right side of the graph.       
```java
//BillingService MergePoint merger
void merge(Purchase paylaod, BillingServiceStatus billingStatus){
    if(billingStatus == MONEY_RESERVED){
        paylaod.setMoneyReserved(true);
    }
}
```
Right MergePoint that belongs to StorageFacility Processor waits two incoming transitions: with StorageFacility 
result and with Payload from BillingService. Then it takes StorageFacility product reservation status and puts it into 
Payload that came from BillingService processor. This payload already contains information about BillingService 
operation status. After that MergePoint checks both fields: `Purchase.moneyReserved` and `Purchase.productReserved`. 
If both fields are true, MergePoint sets `Purchase.result` to true. This means that purchase accomplished 
successfully. This process is done by using merge function of StorageFacility MergePoint. 
```java
//StorageFacility MergePoint merger
void merge(Purchase paylaod, StorageFacilityStatus storageStatus){
    if(storageStatus == PRODUCT_RESERVED){
        paylaod.setProductReserved(true);
    }
    if(paylaod.getMoneyReserved() && payload.getProductReserved()){
        payload.setResult(true);
    }
}
```
Then StorageFacility MergePoint sends Payload with BillingService and StorageFacility results through outgoing transition at the end of 
the given graph.  

Now we can simplify visualisation:  
![Alt parallel-handler-merer-computation2.png](docs/parallel-handler-merer-computation2.png?raw=true)
   
### Handler-merger model with conditional transitions
We almost thee. Fasten seat belts. Lets enrich our MergePoint with last feature: conditional transitions:    
![Alt merge-point.png](docs/merge-point.png?raw=true "MergePoint")

Each outgoing transitions now have enum value associated with it. In graph this enum value illustrated as text label 
near arrow.  
Merger function signature is changed too. Now merger should return enum that will control which outgoing transition 
will be activated and which is not.
```java
Enum merge(Payload payload, HandlerResult handlerResult) {...}
``` 
When MergePoint is activated by incoming transition it evaluates merger function and checks merger result status. 
After that MergePoint deactivates all outgoing transitions which associated enum values does not match the one 
returned by merger function. After that MergePoint activates all outgoing transitions which associated enum values  
matches enum value returned by merger function.  
If there are two or more outgoing transitions that matches enum function result then all of them activates and runs 
in parallel.   
In given illustration we can control by which way execution flow goes by returning enum value FIRST or SECOND from 
merger function of the MergePoint.    

## Completable Reactor model
It is essential to read Concept paragraph to better understanding CompletableReactor model.

### Payload
Payload is a plain old java object that encapsulate request, response and intermediate computation data required for 
request processing. CompletableReactor receive payload as an argument. Execute business flows, modify payload during 
execution and returns it as a computation result.
```java
class Purchase{
    //request parameters
    Long userId;
    Long productId;
    
    //intermediate data
    Boolean moneyReservationStatus;
    Boolean productReservationStatus;
    
    //execution results
    Boolean purchaseStatus;
}
```
```java
Execution<Purchase> execution = completableReactor.submit(
        new Purchase()
            .setUserId(107)
            .setProductId(42));

Purchase purchase = execution.getResultFuture().get();
System.out.print( purchase.getPurchaseStatus() );
```
Start point of each graph represented by graphs Paylaod:    
![Alt payload.png](docs/payload.png?raw=true "Payload")


### Handler
Handler is an asynchronous function that takes information from Payload and returns computation result. Handler 
implements atomic business logic of the execution flow. It could be reused in different flows several time. Handler 
MUST NOT change Payload because same instance of Payload passed to other handlers in parallel. Is is ok to 
concurrently read payload from several handlers but not to modify payload. The only point of payload modification is 
MergePoint.  
```java
@Reactored("myNiceHandler documentation")
CompletableFuture<HanlderResultType> myNiceHandler(String arg1, int arg2)` 
```

### Processor
Processor is a visual graph item that represent a handler invocation. Processor encapsulates coupled business logic 
that usually can be reused. Processors graph item could be reused between different business scenarios in different 
business flows.
 
```java
@Reactored("MyNiceService description")
class MyNiceService {
    //...
    @Reactored("myNiceHandler documentation")
    CompletableFuture<HanlderResultType> myNiceHandler(String arg1, int arg2) {/*...*/}
    //...
}
```

![Alt processor.png](docs/processor.png?raw=true "MergePoint")

### Merger
Merger is a synchronous function that takes Handlers computation result and uses it to update Payload. It is safe to 
modify payload within merger.
```java
@Reactored("myNiceMerger documentation")
Enum myNiceMerger(Paylaod paylaod, HanlderResultType handlerResult){
    paylaod.getResponse().setData(handlerResult.getData());
    return MyTransition.OK;
}
```
### MergePoint
MergePoint is a visual graph item that uses Merger method to make modification on Payload. And uses Merger result to 
select which outgoing transitions to activate and which to deactivate. 

![Alt merge-point.png](docs/merge-point.png?raw=true "MergePoint")

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

### Subgraph

Subgraph - is a Processor that execute Payload inside CompletableReactor. It have implicit Handler that takes Payload as argument and 
returns Payload as computation result. Subgraphs allows to reuse graphs.

![Alt subgraph.png](docs/subgraph.png?raw=true "Subgraph")


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

### Validation

During ReactorGraph building process CompletableReactorBuilder apply validation procedures on ReactorGraph instance. Validators checks 
that graph is consistent, have complete nodes and correct transitions etc. 

## Intellij Idea Plugin
https://plugins.jetbrains.com/plugin/9599-completable-reactor

Completable Reactor Intellij Idea plugin provides graph visualization and source code navigation within IDE.  
You can jump to code using double click on graph item or context menu.
![Alt idea-plugin-graph-example.png](docs/idea-plugin-graph-example.png?raw=true "Graph View")
 
## Examples

## Best Practice

Do not vertex templating via functions. use clone instead
//TODO explain best practice block here
```
val vertex = handler{}.withMerger{}
--- 
not: 
val vertex = foo(myState)
fun foo(){
    return handler{}.withMerger()
}
```  


# How to build this project
Intellij plugin located at `completable-reactor-plugin-idea` directory.  
In order to include plugin module into Intellij Idea you have to manually add line   
`<module fileurl="file://$PROJECT_DIR$/completable-reactor-plugin-idea/completable-reactor-plugin-idea.iml" filepath="$PROJECT_DIR$/completable-reactor-plugin-idea/completable-reactor-plugin-idea.iml" />`      
into `.idea/modules.xml` configuration file.  