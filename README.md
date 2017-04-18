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

Payload - plain old java object that encapsulate request, response and intermediate computation data required for request processing.  
```
class MyPayload{
    String data1;
    int data2;
}
```
Processor - service that encapsulates common logic. Usually Processor contains several Handlers.  
```
@Reactored("MyNiceService description")
class MyNiceService {
    ...
    @Reactored("myNiceHandler documentation")
    CompletableFuture<HanlderResultType> myNiceHandler(String arg1, int arg2)`
    ...
}
```
Handler - asynchronous method that takes information from Payload and returns computation result. 
```
@Reactored("myNiceHandler documentation")
CompletableFuture<HanlderResultType> myNiceHandler(String arg1, int arg2)` 
```
Merger - synchronous method that takes Handlers computation result and uses it to update Payload
```
@Reactored("myNiceMerger documentation")
Enum myNiceMerger(Paylaod paylaod, HanlderResultType handlerResult)`
```
MergePoint - graph processing item that uses Merger method to make modification on Payload.

Visual representation of Processor and MergePoint:       
Processor uses Handler to make asynchronous computation, MergePoint uses Merger to apply Handler computation result on Payload.    
![Alt processor-with-mergePoint.png](docs/processor-with-mergePoint.png?raw=true "Processor with MergePoint")


## Dive into details 

## Intellij Idea Plugin
https://plugins.jetbrains.com/plugin/9526-completable-reactor

Completable Reactor Intellij Idea plugin provides graph visualization and source code navigation within IDE.  
You can jump to code using double click on graph item or context menu.
![Alt cr-idea-plugin-graph-example.png](docs/cr-idea-plugin-graph-example.png?raw=true "Graph View")
 
