package ru.fix.completable.reactor.runtime.dsl;

import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public interface HandlerBuilder2<ContextResult, PayloadType, Arg1, Arg2> {

    <Arg3> HandlerBuilder3<ContextResult, PayloadType, Arg1, Arg2, Arg3> passArg(
            Function<PayloadType, Arg3> arg
    );

    <Arg3> HandlerBuilder3<ContextResult, PayloadType, Arg1, Arg2, Arg3> copyArg(
            Function<PayloadType, Arg3> arg
    );

    <ProcessorResult> ProcessorMergerBuilder<ContextResult, PayloadType, ProcessorResult> withHandler(
            Handler2Args<Arg1, Arg2, ProcessorResult> handler
    );
}
