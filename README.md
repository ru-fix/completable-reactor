# CompletableReactor
CompletableReactor framework makes it easier to create business flows that have concurrently running parts and complex execution branching.

CompletableReactor provides DSL-like builder-style API to describe business flows.

Framework built on top of Fork Join Pool and CompletableFuture API.

## Motivation
[CompletableFuture API](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html) 
with [ForkJoinPool](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ForkJoinPool.html) 
provides ability to write asynchronous code. But sometimes business logic not as straight forward as we would like.
This leads to complex thenApply/thenCompose CompletableFuture chains that hard to read and maintain.
Complex business logic with lots of branching and concurrently executing parts hard to describe using regular coding approach
without proper visualization. And best way to represent that branching is graph with nodes.   
CompletableReactor API tries to keep API as simple and possible in same time provides ability to visualize 
code as graph of execution flows.
 
## Concept

### Simple sequential model
To better understanding of CompletableReactor lets start with simple sequential execution model and then evolve it step 
by step. This model consists of two base elements: *Payload* and *Processor*. Payload is a simple POJO. Processor is 
active element of model that takes Payload, execute business logic based on that Payload, then modify Payload, store 
computation result inside this Payload and pass this Payload to the next Processor. All Processors linked in chain one 
after another. Modification of Payload is optional for Processor.

![Alt sequential-model.png](docs/sequential-model.png?raw=true "sequential-model")
 
In given example we have two processors and payload object. Payload consists of two fields: x and result. 
MultiplyProcessor reads x from payload, multiplies it by 2 store computation result in result field. StdoutProcessor
does not modify incoming payload and simply prints result field.   

### Sequential asynchronous handler-merger model
Now lets make our processors work asynchronously. We will split computation logic of processors in two parts. First 
one, called *handler*, will read input data from payload and perform computation. Second one, called *merger*, will 
store computation result inside payload. Handler will be asynchronous function and merger will be synchronous.  
Here is an example how to split MultiplyProcessor logic to handler and merger functions: handler will read 
argument x from payload, then asynchronously perform multiplication and return CompletableFuture of this operation. 
Merger will get result of handler function as an argument and will store it inside payload in result filed.
```java
CompletableFuture<Integer> handler(Payload payload){
    return CompletableFuture.supplyAsync(()->{
        return payload.getX() * 2;
    });
}

void merger(Payload paylaod, int result){
    paylaod.setResult(result);
}
```
Visual representation:  
![Alt sequential-asynchronous-handler-merger-model.png](docs/sequential-asynchronous-handler-merger-model.png?raw=true "sequential-asynchronous-handler-merger-model")

Visual representation of such computation contains several items:
 - *Processor* bar that represent asynchronous computation of handler function
 - *MergePoint* circle that represent synchronous computation of merger function
 - *Transition* between processor and mergePoint that carries two objects: payload and computation result of handler 
 function.
 - *Transition* that goes into Processor and carries single object - Payload.
 - *Transition* that goes out of MergePoint and carries single object - Payload.  
 - *Paylaod* object that goes into Processors, then to MergePoint then out of MergerPoint.
 
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
 from second transition. 
![Alt parallel-handler-merger-merge-point2.png](docs/parallel-handler-merger-merge-point2.png?raw=true)
In given illustration there are two incoming transitions into MergePoint. First incoming transition carries Payload2 
and computation result of Processor2 - value 42. Second incoming transition  carries ony Payload1. MergePoint ignores
Payload2 from first transitions. It takes Payload1 from second transition and takes Result from First transition. 
Merges them together by merger function and the pass Payload1 to outgoing transition.
  
Now we are ready to make big step to parallel execution.  
As an example lets implement ...



### Payload
Payload is a plain old java object that encapsulate request, response and intermediate computation data required for request processing.
CompletableReactor receive payload as an argument. Execute business flows, modify payload during execution and returns it as a computation 
result.
```java
class MyPayload{
    static class Request {
        String data1;
        int data2;
    }
    static class Response {
        boolean result;
    }
    static class IntermediateData {
        String someData;
    }
    
    final Request request = new Request();
    final Response response = new Request();
    final IntermediateData intermediateData = new IntermediateData();
}
```
or simply
```java
class MyPayload{
    //request parameters
    String data1;
    int data2;
    
    //intermediate data
    String someData;
    
    //execution results
    boolean result;
}
```

![Alt payload.png](docs/payload.png?raw=true "Payload")

### Handler
Handler is an asynchronous method that takes information from Payload and returns computation result. Handler implements atomic business 
logic of the execution flow. It could be reused in different flows several time. Handler MUST NOT change Payload because same instance of 
Payload passed to other handlers in parallel. Is is ok to concurrently read payload from several handlers but not to modify payload. The 
only point of payload modification is MergePoint.
 
```java
@Reactored("myNiceHandler documentation")
CompletableFuture<HanlderResultType> myNiceHandler(String arg1, int arg2)` 
```

### Processor
Processor is a service that encapsulates coupled business logic. Usually Processor contains several Handlers. Processors could be reused
 between different business scenarios in different business flows. They encapsulate reusable logic.
 
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
Merger is a synchronous method that takes Handlers computation result and uses it to update Payload. Merger is being invoked by 
framework sequentially. That is why it is safe to modify payload within merger.
```java
@Reactored("myNiceMerger documentation")
Enum myNiceMerger(Paylaod paylaod, HanlderResultType handlerResult){
    paylaod.getResponse().setData(handlerResult.getData());
    return MyTransition.OK;
}
```
### MergePoint
MergePoint is a graph processing item that uses Merger method to make modification on Payload.

![Alt merge-point.png](docs/merge-point.png?raw=true "MergePoint")

### Transition
Transition is a Enum instance that represent transition during flow execution. MergePoint merger returns instance of Enum. 
Outgoing transition will be activated according to this value. If mergere returns status PLAN_B, then all outgoing transitions with 
condition status PLAN_B will be activated and all transitions without PLAN_B status will be marked as dead. For unconditional transitions 
there is no distinct status. That transitions  will be always activated regardless of the merger result.

```java
enum MyTransitions{
    @Reactored("OneWay transition description")
    ONE_WAY,
    @Reactored("AnotherWay transition description")
    ANOTHER_WAY
}
```
### EndPoint
EndPoint is a graph item that indicates end of flow execution. When graph execution reaches EndPoint then all transitions marked as dead
 and CompletableReactor immediately returns graph result. The only exception is detached processors or subgraphs. They will continue to 
 exectue.   

![Alt end-point.png](docs/end-point.png?raw=true "EndPoint")


### Processor with MergePoint

![Alt processor-with-merge-point.png](docs/processor-with-merge-point.png?raw=true "Processor with MergePoint")

Processor uses Handler to make asynchronous computation, MergePoint uses Merger to apply Handler computation result on Payload.  
Origin payload is passed to Processors handler, then after handler computation is done origin payload is passed together with handler 
result to merge point. Inside merge point origin payload is modified and became Payload*. Outgoing transitions from merge point pass 
this new Payload* to next nodes.  


### MergePoint decision

If all of outgoing transitions from MergePoint have distinct statuses then flow will continue to execute only by one of transitions.

![Alt merge-point-decision.png](docs/merge-point-decision.png?raw=true "MergePoint decision")

If MergePoint merger will return FAIL status then execution completes immediately. In case of FIRST status flow will continue and 
Processor2 will be invoked. In case of SECOND status - Processor3
In given example there could be three options:  
* Payload -> Processor1 -> End (if merger returns FAIL status)
* Payload -> Processor1 -> Processor2 -> End (if merger returns FIRST status)
* Payload -> Processor1 -> Processor3 -> End (if merger returns SECOND status)

First payload goes to Processor1 and passed to Processor1 handler. Then framework waits when CompletableFuture returned by Processor1 
handler completes. After that MergePoint merger will be invoked and Payload with Processor1 handler result will be passed to this merger.
Merger will check Processor1 handler result, modify Payload if needed and returns one of statuses: FAIL, FIRST, SECOND.  After that 
execution will continue along with one of transitions (FAIL leads to END, FIRST - to Processor1, SECOND - to Processor3).

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

### Implicit MergeGroups

MergePoints is the only place where it is allowed to modify Payload. During execution of parallel flows CompletableReactor protects 
Payload from concurrent reading and modification. In given example there are two processors: Processor2 and Processor3 that start in 
parallel. It is possible that Processor3 finishes first and triggers execution of MergePoint of Processor3. In that case there are could 
be the risk of parallel reading Payload by Processor2 in Payload modification by Processor3. To prevent that CompletableReactor joins two 
MergePoints into implicit MergeGroup. This MergeGroup prevents its MergePoint from execution until all incoming transitions to this 
MergePoint is activated or marked as dead. This way even in Processor3 completes before Processor2, MergePoint of Processor3 will stay 
inactive. And only after second incoming transitions to MergeGroup (transition from Processor2 to MergePoint of Processor2) is activated 
MergeGroup will launch execution of MergePoint of Processor3.    

![Alt merge-group.png](docs/merge-group.png?raw=true "MergeGroups")

MergeGroups generated by CompletableReactorBuilder automatically during graph building process. CompletableReactorBuilder analyzes graph,
adds implicit MergeGroups where needed and then build ReactorGraph instance.

### Validation

During ReactorGraph building process CompletableReactorBuilder apply validation procedures on ReactorGraph instance. Validators checks 
that graph is consistent, have complete nodes and correct transitions etc. 

## Intellij Idea Plugin
https://plugins.jetbrains.com/plugin/9599-completable-reactor

Completable Reactor Intellij Idea plugin provides graph visualization and source code navigation within IDE.  
You can jump to code using double click on graph item or context menu.
![Alt cr-idea-plugin-graph-example.png](docs/cr-idea-plugin-graph-example.png?raw=true "Graph View")
 
## Examples

