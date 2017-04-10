package ru.fix.completable.reactor.runtime.execution;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

/**
 * @author Kamil Asfandiyarov
 */
@Data
@Builder
@Accessors(chain = true)
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
    private final Collection<ru.fix.completable.reactor.runtime.execution.ReactorGraphExecutionBuilder.ProcessingVertex> debugProcessingVertexGraphState;
}
