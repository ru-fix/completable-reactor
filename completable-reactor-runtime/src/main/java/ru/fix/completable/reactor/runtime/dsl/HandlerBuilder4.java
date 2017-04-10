package ru.fix.completable.reactor.runtime.dsl;

import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public interface HandlerBuilder4<PayloadType , Arg1, Arg2, Arg3, Arg4> {

    <Arg5> HandlerBuilder5<PayloadType, Arg1, Arg2, Arg3, Arg4, Arg5> passArg(
            Function<PayloadType, Arg5> arg
    );

    <Arg5> HandlerBuilder5<PayloadType, Arg1, Arg2, Arg3, Arg4, Arg5> copyArg(
            Function<PayloadType, Arg5> arg
    );

    <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(
            Handler4Args<Arg1, Arg2, Arg3, Arg4, ProcessorResult> handler
    );
}
