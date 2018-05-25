# Best Practice and Code Convention

- [Best Practice and Code Convention](#best-practice-and-code-convention)
  * [Give your payloads and graphs similar names](#give-your-payloads-and-graphs-similar-names)
  * [Treat vertex declaration as a function](#treat-vertex-declaration-as-a-function)
  * [Give vertex a name as if it is a function](#give-vertex-a-name-as-if-it-is-a-function)
  * [In Spring project declare your graph as a Bean](#in-spring-project-declare-your-graph-as-a-bean)

## Give your payloads and graphs similar names
It is good to follow same naming convention in the project. 
This way it would be easy to find payloads that could be submitted to reactor 
and graphs that implements their execution flows.  
We can use `ActionNamePayload` and `ActionNameGraph` template for names. 

Bad:
```java
class BuyData {...}
class PurchaseConfiguration extends Graph<BuyData> {...}

class SubscriptonAction {...}
class SubscriptonGraphDeclaration extends Graph<SubscriptonAction> {...}
```
Good:
```java
class PurchasePayload {...}
class PurchaseGraph extends Graph<PurchasePayload> {...}

class SubscriptonPayload {...}
class SubscriptonGraph extends Graph<SubscriptonPayload.> {...}
```
## Give your payloads and graphs names that represent actions that graph implies

It is better to give graph a name that represent action. 

Bad:
```java
class SmsSenderPayload {...} 
```
Good: 
```java
class SendSmsPayload {...}
```

## Treat vertex declaration as a function
Do not wrap your logic into function if the only purpose of the function is to
be invoked inside handler/merger block of a Vertex.
If you are not reusing this function in other places - simply put code inside
handler/merger lambda directly and give the Vertex same name as you would give
to the function.

Bad:
```java
public class PurchaseGraph extends Graph<PurchasePayload> {

    Vertex writeUserLog =
        handler(
            //# log ussd purchase
            payload -> writeUserLog(payload)

        ).withMerger((payload, result) -> {
              ...
        });

    Vertex fireSubscribeAction =
        handler(
              //# fire purchase statistic event
              payload -> fireSubscribeAction(payload)

        ).withEmptyMerger();

    private CompletableFuture<Reullt> writeUserLog(PurchasePayload p) {
        <purchase logging code>
    }

    private CompletableFuture<Void> fireSubscribeAction(PurchasePayload payload) {
            <statistc event firing code>
    }

}

```
Good:
```java
public class PurchaseGraph extends Graph<PurchasePayload> {
  Vertex writeUserLog =
      handler(
          //# log ussd purchase
          payload -> {
              <purchase logging code>
          }

      ).withMerger((payload, result) -> {
            ...
      });

  Vertex fireSubscribeAction =
      handler(
            //# fire purchase statistic event
            payload -> {
                <statistc event firing code>
            }

      ).withEmptyMerger();

}

```
## Give vertex a name as if it is a function
Vertex usually implies business logic that should be executed as a part of 
use case scenario. 
It would be better to give vertex a name 
that describes what action this vertex implements.     


Bad:
```java
Vertex userLogWriter = ...;
Vertex statisticProcessor = ...;
```
Good:
```java
Vertex writePurchaseToUserLog = ...;
Vertex firePurchaseEventToStatistics = ...;
```
## In Spring project declare your graph as a Bean
You can use different approaches in registering graphs in you application. 
Main thing is to be consistent about it. 
We suggest to use an approach when you declare graph as a Bean

```java
public class MyGraph extends Graph<MyPayload>{
    @Autowired
    MyService myService;

    Vertex doStuff =
        handler(
            paylaod -> myService.doSomethingUsefull()

        ).withMerger(...);
}

@Configuration
public class ApplicationConfiguration{

    @Bean
    public MyGraph muGraph(){
        return new MyGraph();
    }
    ...

    @Autowired
    Collection<Graph> applicationGraphs;

    @Bean
    public CompletableReactor completableReactor(){
      CompletableReactor completableReactor = new CompletableReactor()
      applicationGraphs.forEach(completableReactor::registerGraph)
      return completableReactor;
    }
}
```

## Optional, lateinit vars for Graph context variable

Nullable types `T?` or `Optional<T>` force us to check value for null before we can use it.
This is very handy for function arguments, class states, etc.
But lets discuss situation with graphs.
We usually desing graphs in a way so upper vertices initialize data and bottom vertices uses them.
Upper vertex1 initialize `data` with value and vertex2 reads `data`. 
In that case our graph design imply that data will be ready for reading in vertex2. 
Suppose that data is not ready : vertex2 from our example have only one case: if `data` is null - rise an exception. 
But Kotlin lateinit or Nullable `data` will rise NPE for this case for us.
So if our graph design imply that data should be ready before reading by bottom vertices - we should use nullable or lateinit vars.
 
What if our login have two cases: in one we use data, and in another - do not. 
Best way to solve this case is to try to implement it thought graph design and have two branches of vertices.
This way we can continue to use lateinit vars/nullable values. 
But some times it is better not to increase complexity by adding new branches and instead to use
lateinit/nullable data in graph payload and implement `if(x==null) A else B` logic inside vertex without any exceptions.





<!--
```
//TODO explain best practice block here
Use Vertex clone instead of function reusing for similar logic
Do not vertex templating via functions. use clone instead

val vertex = handler{}.withMerger{}
---
not:
val vertex = foo(myState)
fun foo(){
    return handler{}.withMerger()
}
```
-->
