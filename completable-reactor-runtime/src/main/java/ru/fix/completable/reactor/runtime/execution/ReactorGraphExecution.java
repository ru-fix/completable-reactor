package ru.fix.completable.reactor.runtime.execution;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

/**
 * @author Kamil Asfandiyarov
 */
public class ReactorGraphExecution<PayloadType > {

    /**
     * Completes when all processes within given execution of single request is complete
     */
    final CompletableFuture<Void> chainExecutionFuture;

    /**
     * Should be completed with Payload that about to be send to processor chain
     */
    final CompletableFuture<PayloadType> submitFuture;

    /**
     * Will be completed with payload when terminal graph state would be reached.
     */
    final CompletableFuture<PayloadType> resultFuture;

    /**
     * Debug purpose field that allows to check internal execution graph state
     */
    private final Collection debugProcessingVertexGraphState;


    public ReactorGraphExecution(
            CompletableFuture<PayloadType> submitFuture,
            CompletableFuture<PayloadType> resultFuture,
            CompletableFuture<Void> chainExecutionFuture,
            Collection debugProcessingVertexGraphState) {
        this.chainExecutionFuture = chainExecutionFuture;
        this.submitFuture = submitFuture;
        this.resultFuture = resultFuture;
        this.debugProcessingVertexGraphState = debugProcessingVertexGraphState;
    }

    public CompletableFuture<Void> getChainExecutionFuture() {
        return chainExecutionFuture;
    }

    public CompletableFuture<PayloadType> getSubmitFuture() {
        return submitFuture;
    }

    public CompletableFuture<PayloadType> getResultFuture() {
        return resultFuture;
    }

    public Collection getDebugProcessingVertexGraphState() {
        return debugProcessingVertexGraphState;
    }
}
