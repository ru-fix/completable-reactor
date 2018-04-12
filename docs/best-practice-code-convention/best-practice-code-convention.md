# Best Practice and Code Convention

## Give your payloads and graphs similar names
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
## In Spring project declare your graph as Bean
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