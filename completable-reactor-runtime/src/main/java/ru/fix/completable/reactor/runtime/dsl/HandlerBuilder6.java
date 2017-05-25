package ru.fix.completable.reactor.runtime.dsl;


import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public interface HandlerBuilder6<PayloadType, Arg1, Arg2, Arg3, Arg4, Arg5, Arg6> {

    <Arg7> HandlerBuilder7<PayloadType, Arg1, Arg2, Arg3, Arg4, Arg5, Arg6, Arg7> passArg(
            Function<PayloadType, Arg7> arg
    );

    <Arg7> HandlerBuilder7<PayloadType, Arg1, Arg2, Arg3, Arg4, Arg5, Arg6, Arg7> copyArg(
            Function<PayloadType, Arg7> arg
    );

    <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(
            Handler6Args<Arg1, Arg2, Arg3, Arg4, Arg5, Arg6, ProcessorResult> handler
    );

    <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(
            String title,
            Handler6Args<Arg1, Arg2, Arg3, Arg4, Arg5, Arg6, ProcessorResult> handler
    );

    <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(
            String title,
            String[] docs,
            Handler6Args<Arg1, Arg2, Arg3, Arg4, Arg5, Arg6, ProcessorResult> handler
    );

}
