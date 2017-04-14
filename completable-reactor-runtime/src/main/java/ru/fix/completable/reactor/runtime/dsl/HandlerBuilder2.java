package ru.fix.completable.reactor.runtime.dsl;

import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public interface HandlerBuilder2<PayloadType, Arg1, Arg2> {

    <Arg3> HandlerBuilder3<PayloadType, Arg1, Arg2, Arg3> passArg(
            Function<PayloadType, Arg3> arg
    );

    <Arg3> HandlerBuilder3<PayloadType, Arg1, Arg2, Arg3> copyArg(
            Function<PayloadType, Arg3> arg
    );

    <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(
            Handler2Args<Arg1, Arg2, ProcessorResult> handler
    );

    <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(
            String title,
            Handler2Args<Arg1, Arg2, ProcessorResult> handler
    );

    <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(
            String title,
            String[] docs,
            Handler2Args<Arg1, Arg2, ProcessorResult> handler
    );
}
