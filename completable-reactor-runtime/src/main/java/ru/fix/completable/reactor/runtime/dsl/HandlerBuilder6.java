package ru.fix.completable.reactor.runtime.dsl;


/**
 * @author Kamil Asfandiyarov
 */
public interface HandlerBuilder6<ContextResult, PayloadType, Arg1, Arg2, Arg3, Arg4, Arg5, Arg6> {

    <ProcessorResult> ProcessorMergerBuilder<ContextResult, PayloadType, ProcessorResult> withHandler(
            Handler6Args<Arg1, Arg2, Arg3, Arg4, Arg5, Arg6, ProcessorResult> handler
    );
}
