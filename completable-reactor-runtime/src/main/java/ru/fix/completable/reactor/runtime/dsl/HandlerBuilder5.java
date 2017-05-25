package ru.fix.completable.reactor.runtime.dsl;

import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public interface HandlerBuilder5<PayloadType , Arg1, Arg2, Arg3, Arg4, Arg5> {

    <Arg6> HandlerBuilder6<PayloadType, Arg1, Arg2, Arg3, Arg4, Arg5, Arg6> passArg(
            Function<PayloadType, Arg6> arg
    );

    <Arg6> HandlerBuilder6<PayloadType, Arg1, Arg2, Arg3, Arg4, Arg5, Arg6> copyArg(
            Function<PayloadType, Arg6> arg
    );

    <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(
            Handler5Args<Arg1, Arg2, Arg3, Arg4, Arg5, ProcessorResult> handler
    );

    <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(
            String title,
            Handler5Args<Arg1, Arg2, Arg3, Arg4, Arg5, ProcessorResult> handler
    );

    <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(
            String title,
            String[] docs,
            Handler5Args<Arg1, Arg2, Arg3, Arg4, Arg5, ProcessorResult> handler
    );
}
