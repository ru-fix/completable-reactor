package ru.fix.completable.reactor.graph.mapped;


/**
 *
 * @param <RequestT> type of Request, part of public api for graph invocation.
 * @param <ResponseT> type fo Response, part of public api for graph invocation.
 * @param <ContextT> Intermediate data, private part of Graph implementation.
 *                  Does not leak to public api.
 *                  Stores intermediate result to pass it between vertices.
 */
public abstract class Graph<RequestT, ResponseT, ContextT>
        extends ru.fix.completable.reactor.graph.Graph<Payload<RequestT, ResponseT, ContextT>> {
}
