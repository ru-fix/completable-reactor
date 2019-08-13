package ru.fix.completable.reactor.graph.mapped;

/**
 * Payload that can be mapped to interface with single abstract method that receives RequestT argument and returns ResponseT
 * @param <RequestT> Request, public part of graph invocation.
 * @param <ResponseT> Response, public part of graph invocation.
 * @param <ContextT> private part of Graph implementation. Stores intermediate result to pass it between vertices.
 */
public class Payload<RequestT, ResponseT, ContextT> {
    public RequestT request;
    public ResponseT response;
    public ContextT context;
}
