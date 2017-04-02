package ru.fix.completable.reactor.runtime.internal.dsl;

import lombok.Data;
import ru.fix.completable.reactor.api.ReactorGraphModel;
import ru.fix.completable.reactor.runtime.dsl.*;

import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
@Data
public class CRProcessorDescription<PayloadType> implements ProcessorDescription<PayloadType> {
    /**
     * Null if there is no merger provided.
     * In that case Processor does not modify payload.
     * {@code BiFunction<Payload, ProcessorResult, Enum>}
     */
    ProcessorMerger merger;

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

    /**
     * {@code Function<Payload, Arg6>}
     */
    Function arg6;

    boolean isCopyArg1 = false;
    boolean isCopyArg2 = false;
    boolean isCopyArg3 = false;
    boolean isCopyArg4 = false;
    boolean isCopyArg5 = false;
    boolean isCopyArg6 = false;

    /**
     * {@code Handler0Args<Processor, ProcessorResult>}
     */
    Handler0Args handler0;

    /**
     * {@code Handler1Arg<Processor, Arg1, ProcessorResult>}
     */
    Handler1Arg handler1;

    /**
     * {@code MethodReference2Arg<Processor, Arg1, Arg2, ProcessorResult>}
     */
    Handler2Args handler2;

    /**
     * {@code MethodReference3Arg<Processor, Arg1, Arg2, Arg3, ProcessorResult>}
     */
    Handler3Args handler3;

    /**
     * {@code MethodReference4Arg<Processor, Arg1, Arg2, Arg3, Arg4, ProcessorResult>}
     */
    Handler4Args handler4;

    /**
     * {@code Handler<Arg1, Arg2, Arg3, Arg4, Arg5, ProcessorResult>}
     */
    Handler5Args handler5;

    /**
     * {@code Handler<Arg1, Arg2, Arg3, Arg4, Arg5, Arg6, ProcessorResult>}
     */
    Handler6Args handler6;

    /**
     * Source where {@code handleBy} method was used
     */
    ReactorGraphModel.Source handleBySource;

    /**
     * Source where {@code withMerger} or {@code withoutMerger}  method was used
     */
    ReactorGraphModel.Source mergeSource;

    /**
     * Merger documentation
     */
    String[] mergerDocs;

    /**
     * Merger title
     */
    String mergerTitle;

    /**
     * Handler class
     */
    Class processorType;

    String[] processorDoc;

    String handlerTitle;

    String[] handlerDocs;



    CRProcessorDescription() {
    }

    @Override
    public Processor<PayloadType> buildProcessor() {
        return new CRProcessor<PayloadType>( this);
    }
}
