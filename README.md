# CompletableReactor
CompletableReactor framework makes it easier to create business flows that have concurrently running parts
 and complex execution branching.

CompletableReactor provides DSL-like builder-style API to describe business flows and visualization plugins
that parse code and displays it as an execution graph. CompletableReactor follows code-first approach when developer
writes code and IDE visualizes it in plugin window and provides ability to navigate from graph to code and backward.

Framework built on top of Fork Join Pool and CompletableFuture API. Different JVM based languages use extension
to support more suitable DSL-like API. Currently supported DSLs are for `Java` and `Kotlin` languages.  

## Table of contents
- [CompletableReactor](#completablereactor)
  * [Table of contents](#table-of-contents)
  * [Motivation](#motivation)
  * [Getting Started](#getting-started)
  * [Handler-Merger model](#handler-merger-model)
  * [Completable Reactor model](#completable-reactor-model)
  * [Validation](#validation)
  * [Intellij Idea Plugin](#intellij-idea-plugin)
  * [Examples](#examples)
  * [Best Practice and Code Convention](#best-practice-and-code-convention)


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

## Handler-Merger model
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


## Validation

During ReactorGraph building process CompletableReactorBuilder apply validation procedures on ReactorGraph instance. Validators checks
that graph is consistent, have complete nodes and correct transitions etc.

## Intellij Idea Plugin
https://plugins.jetbrains.com/plugin/9599-completable-reactor

Completable Reactor Intellij Idea plugin provides graph visualization and source code navigation within IDE.  
You can jump to code using double click on graph item or context menu.
![Alt idea-plugin-graph-example.png](docs/idea-plugin-graph-example.png?raw=true "Graph View")

## Examples

## Best Practice and Code Convention

Paragraph explains handy rules that will keep your code clear

[Best Practice and Code Convention](docs/best-practice-code-convention/best-practice-code-convention.md)  


# How to build this project
Intellij plugin located at `completable-reactor-plugin-idea` directory.  
In order to include plugin module into Intellij Idea you have to manually add line   
`<module fileurl="file://$PROJECT_DIR$/completable-reactor-plugin-idea/completable-reactor-plugin-idea.iml" filepath="$PROJECT_DIR$/completable-reactor-plugin-idea/completable-reactor-plugin-idea.iml" />`      
into `.idea/modules.xml` configuration file.  
