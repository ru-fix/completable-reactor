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

### Terms

**Payload** - plain old java object that encapsulate request, response and intermediate computation data required for request processing.  
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

**Processor** - service that encapsulates common logic. Usually Processor contains several Handlers.  
```java
@Reactored("MyNiceService description")
class MyNiceService {
    //...
    @Reactored("myNiceHandler documentation")
    CompletableFuture<HanlderResultType> myNiceHandler(String arg1, int arg2) {/*...*/}
    //...
}
```

**Handler** - asynchronous method that takes information from Payload and returns computation result. 
```java
@Reactored("myNiceHandler documentation")
CompletableFuture<HanlderResultType> myNiceHandler(String arg1, int arg2)` 
```

![Alt processor.png](docs/processor.png?raw=true "MergePoint")

**Merger** - synchronous method that takes Handlers computation result and uses it to update Payload
```java
@Reactored("myNiceMerger documentation")
Enum myNiceMerger(Paylaod paylaod, HanlderResultType handlerResult){
    paylaod.getResponse().setData(handlerResult.getData());
    return MyTransition.OK;
}
```
**MergePoint** - graph processing item that uses Merger method to make modification on Payload.
![Alt merge-point.png](docs/merge-point.png?raw=true "MergePoint")

**Transition** - Enum instance that represent transition during flow execution. MergePoint merger returns instance of Enum. 
Outgoing transition will be selected according to this value. 
```java
enum MyTransitions{
    @Reactored("OneWay transition description")
    ONE_WAY,
    @Reactored("AnotherWay transition description")
    ANOTHER_WAY
}
```

Processor uses Handler to make asynchronous computation, MergePoint uses Merger to apply Handler computation result on Payload.  
Origin payload is passed to Processors handler, then after handler computation is done origin payload is passed together with handler 
result to merge point. Inside merge point origin payload is modified and became Payload*. Outgoing transitions from merge point pass 
this new Payload* to next nodes.  
![Alt processor-with-merge-point.png](docs/processor-with-merge-point.png?raw=true "Processor with MergePoint")


## Dive into details 

## Intellij Idea Plugin
https://plugins.jetbrains.com/plugin/9599-completable-reactor

Completable Reactor Intellij Idea plugin provides graph visualization and source code navigation within IDE.  
You can jump to code using double click on graph item or context menu.
![Alt cr-idea-plugin-graph-example.png](docs/cr-idea-plugin-graph-example.png?raw=true "Graph View")
 
