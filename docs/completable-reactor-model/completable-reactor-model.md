#  Completable Reactor Model
It is essential to read 
[Handler-Merger execution model](../handler-merger-model/handler-merger-model.md)
first.

Completable Reactor Model enriches 
[Handler-Merger execution model](../handler-merger-model/handler-merger-model.md)
with new components and determinate its execution on JVM.

## Payload and StartPoint
Payload is a plain old java object that encapsulates request, response 
and intermediate computation data required for request processing. 
CompletableReactor receives payload as an argument. 
Executes business flows, modifies payload during execution and returns it as a result.
```java
class Purchase{
    //request parameters
    //provided by user during graph launching
    Long userId;
    Long productId;
    
    //intermediate data, used to pass information 
    //from one vertex to another vertex
    Boolean moneyReservationStatus;
    Boolean productReservationStatus;
    
    //results
    //that user receives when graph execution is complete
    Boolean purchaseStatus;
}
```
We can add little structure to payload to clarify purpose of each field
and make our graph code more readable: 
```java
class Purchase{
    Request request;
    Intermediate intermediate;
    Response response;
    
    Purchase(Long userId, Long productId){
        this.request = new Request(userId, productId);
    }
    
    static class Request{
        Long userId;
        Long productId;
        
        Request(Long userId, Long productId){
            this.userId = userId;
            this.productId = productId;
        }
    }
    static class Intermediate{
        Boolean moneyReservationStatus;
        Boolean productReservationStatus;
    }
    static class Response{
        Boolean purchaseStatus;
    }
}
```
To launch graph user should create Payload instance and submit it to reactor:
```java

//launch graph
Execution<Purchase> execution = completableReactor.submit(new Purchase(107, 42));

//wait for graph to complete
Purchase purchase = execution.getResultFuture().get();

//check result
System.out.print( purchase.getResponse().getPurchaseStatus() );
```
Start point visualization:    
![Alt start-point](res/start-point.png?raw=true "start-point")

To attach vertices to StartPoint you can use `payload()` builder method of graph.
```java

class PurchaseGraph extends Graph<Purchase>{

    Vertex reserveMoney = ...
    Vertex reserveProduct = ...
   
    {
        payload()
                .handleBy(reserveMoney)
                .handleBy(reserveProduct);
    }
}
``` 
![Alt start-point-handle-by](res/start-point-handle-by.png?raw=true "start-point-handle-by")
