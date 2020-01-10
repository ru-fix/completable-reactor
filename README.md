# CompletableReactor
[![Maven Central](https://img.shields.io/maven-central/v/ru.fix/completable-reactor-runtime.svg)](https://search.maven.org/search?q=g:ru.fix%20and%20a:completable-reactor-runtime)

CompletableReactor framework makes it easier to create business flows that have concurrently running parts
 and complex execution branching.

CompletableReactor provides DSL-like builder-style API to describe business flows and visualization plugins
that parse code and displays it as an execution graph. CompletableReactor follows code-first approach when developer
writes code and IDE visualizes it in plugin window and provides ability to navigate from graph to code and backward.

Framework built on top of Fork Join Pool and CompletableFuture API. Different JVM based languages use extension
to support more suitable DSL-like API.  

Visit CompletableReactor site for project documentation:   
https://ru-fix.github.io/completable-reactor/

MIT License