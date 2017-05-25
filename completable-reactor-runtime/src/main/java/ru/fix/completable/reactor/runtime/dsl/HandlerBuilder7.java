package ru.fix.completable.reactor.runtime.dsl;


/**
 * @author Kamil Asfandiyarov
 */
public interface HandlerBuilder7<PayloadType, Arg1, Arg2, Arg3, Arg4, Arg5, Arg6, Arg7> {

    <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(
            Handler7Args<Arg1, Arg2, Arg3, Arg4, Arg5, Arg6, Arg7, ProcessorResult> handler
    );

    <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(
            String title,
            Handler7Args<Arg1, Arg2, Arg3, Arg4, Arg5, Arg6, Arg7, ProcessorResult> handler
    );

    <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(
            String title,
            String[] docs,
            Handler7Args<Arg1, Arg2, Arg3, Arg4, Arg5, Arg6, Arg7, ProcessorResult> handler
    );
}
