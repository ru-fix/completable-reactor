package ru.fix.completable.reactor.runtime;

import ru.fix.completable.reactor.runtime.function.Handler4Args;
import ru.fix.completable.reactor.runtime.function.Handler5Args;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public class HandlerBuilder5<ContextResult, PayloadType , Arg1, Arg2, Arg3, Arg4, Arg5> extends BaseDescriberItem<ContextResult> {
    public HandlerBuilder5(ContextResult contextResult, GraphProcessorDescription processorDescription) {
        super(contextResult, processorDescription);
    }

    public <Arg6> HandlerBuilder6<ContextResult, PayloadType, Arg1, Arg2, Arg3, Arg4, Arg5, Arg6> passArg(Function<PayloadType, Arg5> arg) {
        processorDescription.arg6 = arg;
        return new HandlerBuilder6<>(contextResult, processorDescription);
    }

    public <Arg6> HandlerBuilder6<ContextResult, PayloadType, Arg1, Arg2, Arg3, Arg4, Arg5, Arg6> copyArg(Function<PayloadType, Arg5> arg) {
        processorDescription.arg6 = arg;
        processorDescription.isCopyArg6 = true;
        return new HandlerBuilder6<>(contextResult, processorDescription);
    }

    public <ProcessorResult> MergerBuilder<ContextResult, PayloadType, ProcessorResult> withHandler(Handler5Args<Arg1, Arg2, Arg3, Arg4, Arg5, CompletableFuture<ProcessorResult>> handler) {
        processorDescription.handler5 = handler;
        ReactorReflector.getMethodInvocationPoint().ifPresent(source -> processorDescription.handleBySource = source);

        return new MergerBuilder<>(contextResult, mergerInfo -> {
            processorDescription.merger = mergerInfo.merger;
            processorDescription.mergeSource = mergerInfo.mergerSource;
            processorDescription.mergerDocs = mergerInfo.mergerDocs;
            processorDescription.mergerTitle = mergerInfo.mergerTitle;
        });
    }
}
