#  Handler-Merger concurrent execution concept
To describe and idea behind CompletableReactor let's start with simple model and then evolve it step by step.

## Simple sequential model
This model consists of two base elements: *Payload* and *Vertex*.
Payload is a simple [POJO](https://en.wikipedia.org/wiki/Plain_old_Java_object).
Vertex is active element of model that takes Payload, execute business logic based on that Payload, 
then modify Vertex, stores computation result inside this Payload and pass this Payload to the next Vertex. All 
Vertices linked in chain one after another. Modification of Payload is optional for Vertex.

![Alt simple-sequential-model](res/simple-sequential-model.png?raw=true "simple-sequential-model")
 
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
![Alt sequential-async-handler-merger-model](res/sequential-async-handler-merger-model.png?raw=true 
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
![Alt sequential-async-handler-merger-model-simplified.png](res/sequential-async-handler-merger-model-simplified.png?raw=true
 "sequential-asynchronous-handler-merger-model2")


### Parallel handler-merger model
Lets update Merger and allow it to have several outgoing transitions. When merger function 
completes merging process it will send same Payload through all of outgoing transitions in parallel.  
![Alt parallel-handler-merger-multiple-outgoing](res/parallel-handler-merger-multiple-outgoing.png?raw=true 
"parallel-handler-merger-merge-point")  
In given example origin Payload and vertex Result goes from Handler to Merger. Then Merger modifies 
Payload and puts result inside it. Then Merger sends this Payload instance that contains result 20 to all outgoing 
transitions. 

Also we need another feature in Merger - an ability to join two incoming transitions into single outgoing. When 
Merger have two incoming transitions: one from Vertex that carries Payload with handler Result and another 
with Payload only, Merger will chose Result from first transition and will merge it to Payload that it received
 from second transition. For activation Merger has to wait for both incoming transitions.   
![Alt parallel-handler-merger-join-flows.png](res/parallel-handler-merger-join-flows.png?raw=true)

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
![Alt parallel-handler-merger-computation-example](res/parallel-handler-merger-computation-example.png?raw=true)
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
![Alt parallel-handler-merger-computation-example-simplified](res/parallel-handler-merger-computation-example-simplified.png?raw=true)


## Handler-merger model with conditional transitions
We almost thee. Fasten seat belts. Lets enrich our Merger with last feature: conditional transitions:    
![Alt merger-conditional-outgoing-flows](res/merger-conditional-outgoing-flows.png?raw=true "MergePoint")

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
![Alt merger-conditional-outgoing-example](res/merger-conditional-outgoing-example.png?raw=true "MergePoint")   

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
![Alt start-point-and-end-point](res/start-point-and-end-point.png?raw=true "MergePoint")

In given illustration graph execution starts at StartPoint.
* Then Payload goes to Vertex1 and Vertex2 in parallel.
* Then Merger1 executes.
* Then Merger2 executes.
* Then if Merger2 returns FIRST  graph execution stops at left EndPoint
* Otherwise if Merger2 returns SECOND execution continues
* Vertex3 with Merger3 runs sequentially and after that graph execution stops ant bottom EndPoint