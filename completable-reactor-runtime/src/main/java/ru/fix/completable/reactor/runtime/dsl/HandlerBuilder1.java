package ru.fix.completable.reactor.runtime.dsl;

import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public interface HandlerBuilder1<PayloadType, Arg1> {

    <Arg2> HandlerBuilder2<PayloadType, Arg1, Arg2> passArg(
            Function<PayloadType, Arg2> arg
    );

    <Arg2> HandlerBuilder2<PayloadType, Arg1, Arg2> copyArg(
            Function<PayloadType, Arg2> arg
    );

    <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(
            Handler1Arg<Arg1, ProcessorResult> handler
    );
}
