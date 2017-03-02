package ru.fix.completable.reactor.runtime;

import lombok.Data;
import ru.fix.completable.reactor.api.ReactorGraphModel;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Describe processor handing and merging methods
 *
 * @author Kamil Asfandiyarov
 */

@Data
public class GraphProcessorDescription<ProcessorType, PayloadType> {
    /**
     * Null if there is no merger provided.
     * In that case Processor does not modify payload.
     * {@code BiFunction<Payload, ProcessorResult, Enum>}
     */
    BiFunction merger;

    /**
     * {@code Function<Payload, Arg1>}
     */
    Function arg1;
    /**
     * {@code Function<Payload, Arg2>}
     */
    Function arg2;
    /**
     * {@code Function<Payload, Arg3>}
     */
    Function arg3;

    /**
     * {@code Function<Payload, Arg4>}
     */
    Function arg4;

    /**
     * {@code Function<Payload, Arg5>}
     */
    Function arg5;

    boolean isCopyArg1 = false;
    boolean isCopyArg2 = false;
    boolean isCopyArg3 = false;
    boolean isCopyArg4 = false;
    boolean isCopyArg5 = false;

    /**
     * {@code MethodReference0Args<Processor, CompletableFuture<ProcessorResult>>}
     */
    MethodReference0Args handler0;

    /**
     * {@code MethodReference1Arg<Processor, Arg1, CompletableFuture<ProcessorResult>>}
     */
    MethodReference1Arg handler1;

    /**
     * {@code MethodReference2Arg<Processor, Arg1, Arg2, CompletableFuture<ProcessorResult>>}
     */
    MethodReference2Args handler2;

    /**
     * {@code MethodReference3Arg<Processor, Arg1, Arg2, Arg3, CompletableFuture<ProcessorResult>>}
     */
    MethodReference3Args handler3;

    /**
     * {@code MethodReference4Arg<Processor, Arg1, Arg2, Arg3, Arg4, CompletableFuture<ProcessorResult>>}
     */
    MethodReference4Args handler4;

    /**
     * {@code MethodReference5Arg<Processor, Arg1, Arg2, Arg3, Arg4, Arg5, CompletableFuture<ProcessorResult>>}
     */
    MethodReference5Args handler5;

    /**
     * Source where {@code handleBy} method was used
     */
    ReactorGraphModel.Source handleBySource;

    /**
     * Source where {@code withMerger} or {@code withoutMerger}  method was used
     */
    ReactorGraphModel.Source mergeSource;
}
