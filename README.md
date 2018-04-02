# CompletableReactor
CompletableReactor framework makes it easier to create business flows that have concurrently running parts
 and complex execution branching.

CompletableReactor provides DSL-like builder-style API to describe business flows and visualization plugins 
that parse code and displays it as an execution graph. CompletableReactor follows code-first approach when developer 
writes code and IDE visualizes it in plugin window and provides ability to navigate from graph to code and backward. 

Framework built on top of Fork Join Pool and CompletableFuture API. Different JVM based languages use extension 
to support more suitable DSL-like API. Currently supported DSLs are for `Java` and `Kotlin` languages.  

## Motivation
[CompletableFuture API](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/CompletableFuture.html) 
with [ForkJoinPool](https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ForkJoinPool.html) 
provides ability to write asynchronous code. But sometimes business logic not as straight forward as we would like.
This leads to complex `thenApply`/`thenCompose` CompletableFuture chains that hard to read and maintain. Kotlin 
[`suspend` methods and coroutines](https://kotlinlang.org/docs/reference/coroutines.html)
slightly simplify sequential chains, but they are still fail to clarify complexity of 
unobvious conditional branching with scenarios executed in parallel.  

Complex business logic with lots of branching and concurrently executing parts hard to describe using regular coding
 approach without proper visualization. One of ways to represent that branching is a graph with nodes and 
 transitions.  
CompletableReactor API tries to keep API as simple and possible, saves benefits of statically typed languages and in 
same time provides ability for fast code parsing and visualization as a graph of execution flows. 

## Getting Started

The first step is to include CompletableReactor into your project:
```
//Java Gradle
compile "ru.fix:completable-reactor-runtime:1.2.2"
compile "ru.fix:completable-reactor-graph:1.2.2"

//Kotlin Gradle
compile "ru.fix:completable-reactor-runtime:1.2.2"
compile "ru.fix:completable-reactor-graph-kotlin:1.2.2"
```
The second is to install plugin into your IDE
* [Intellij Idea Plugin](https://plugins.jetbrains.com/plugin/9599-completable-reactor)
* Eclipse Plugin (TODO)
* Standalone application `ru.fix:completable-reactor-graph-viewer:1.2.2`

The third step is to write the **Hello Graph** program:

## Handler-Merger execution model
Briefly describes concept behind CompletbleReactor.
Explains how we can decompose concurrent conditional execution into base graph components.

[Handler-Merger execution model](docs/handler-merger-model/handler-merger-model.md) 




## Completable Reactor model
It is essential to read 
[Handler-Merger execution model](docs/handler-merger-model/handler-merger-model.md)
first.

Describes how reactor components could be described in code and how they visualized. 
Adds new components and determinate execution of
[Handler-Merger execution model](docs/handler-merger-model/handler-merger-model.md)
on JVM.

[Completable Reactor model](docs/completable-reactor-model/completable-reactor-model.md)


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