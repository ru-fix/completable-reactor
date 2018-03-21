# Concept
To describe and idea behind CompletableReactor let's start with simple model and then evolve it step by step.

### Simple sequential model
This model consists of two base elements: *Payload* and *Vertex*.
Payload is a simple [POJO](https://en.wikipedia.org/wiki/Plain_old_Java_object).
Vertex is active element of model that takes Payload, execute business logic based on that Payload, 
then modify Vertex, stores computation result inside this Payload and pass this Payload to the next Vertex. All 
Vertices linked in chain one after another. Modification of Payload is optional for Vertex.

![Alt simple-sequential-model](res/simple-sequential-model.png?raw=true "simple-sequential-model")
 
In given example we have two vertices and payload object. Payload consists of two fields: x and result. 
MultiplyVertex reads x from payload, multiplies it by 2 and stores computation result in result field.
StdoutVertex does not modify incoming payload and simply prints result field to stdout.   

### Sequential asynchronous handler-merger model
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
Lets update MergePoint and allow it to have several outgoing transitions. When merger function inside MergePoint 
completes merging process it will send Payload through all of outgoing transitions in parallel.  
![Alt parallel-handler-merger-merge-point.png](res/parallel-handler-merger-merge-point.png?raw=true 
"parallel-handler-merger-merge-point")  
In given example origin Payload and processors Result goes from Processor to MergePoint. Then MergePoint modifies 
Payload and puts result inside it. Then MergePoint sends this Payload that contains result 42 to all outgoing 
transitions. 

Also we need another feature in MergePoint - an ability to join two incoming transitions into single outgoing. When 
MergePoint have two incoming transitions: one from Processor that carries Payload with handler Result and another 
with simply Payload, MergePoint will chose Result from first transition and will merge it to Payload that it received
 from second transition. For activation MergePoint has to wait for both incoming transitions.   
![Alt parallel-handler-merger-merge-point2.png](res/parallel-handler-merger-merge-point2.png?raw=true)
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
![Alt parallel-handler-merer-computation.png](res/parallel-handler-merer-computation.png?raw=true)
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
![Alt parallel-handler-merer-computation2.png](res/parallel-handler-merer-computation2.png?raw=true)
   
### Handler-merger model with conditional transitions
We almost thee. Fasten seat belts. Lets enrich our MergePoint with last feature: conditional transitions:    
![Alt merge-point.png](res/merge-point.png?raw=true "MergePoint")

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
