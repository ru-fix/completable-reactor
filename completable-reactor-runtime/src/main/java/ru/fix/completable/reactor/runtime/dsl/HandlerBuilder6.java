package ru.fix.completable.reactor.runtime.dsl;


/**
 * @author Kamil Asfandiyarov
 */
public interface HandlerBuilder6<PayloadType, Arg1, Arg2, Arg3, Arg4, Arg5, Arg6> {

    <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(
            Handler6Args<Arg1, Arg2, Arg3, Arg4, Arg5, Arg6, ProcessorResult> handler
    );
}
