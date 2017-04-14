package ru.fix.completable.reactor.runtime.dsl;

import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public interface HandlerBuilder0<PayloadType> {

    <Arg1> HandlerBuilder1<PayloadType, Arg1> passArg(
            Function<PayloadType, Arg1> arg
    );

    <Arg1> HandlerBuilder1<PayloadType, Arg1> copyArg(
            Function<PayloadType, Arg1> arg
    );

    <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(
            Handler0Args<ProcessorResult> handler
    );

    <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(
            String title,
            Handler0Args<ProcessorResult> handler
    );

    <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(
            String title,
            String[] docs,
            Handler0Args<ProcessorResult> handler
    );
}
