package ru.fix.completable.reactor.runtime;

import ru.fix.completable.reactor.runtime.function.Handler4Args;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public class HandlerBuilder4<ContextResult, PayloadType , Arg1, Arg2, Arg3, Arg4> extends BaseDescriberItem<ContextResult> {
    public HandlerBuilder4(ContextResult contextResult, GraphProcessorDescription processorDescription) {
        super(contextResult, processorDescription);
    }

    public <Arg5> HandlerBuilder5<ContextResult, PayloadType, Arg1, Arg2, Arg3, Arg4, Arg5> passArg(Function<PayloadType, Arg5> arg) {
        processorDescription.arg5 = arg;
        return new HandlerBuilder5<>(contextResult, processorDescription);
    }

    public <Arg5> HandlerBuilder5<ContextResult, PayloadType, Arg1, Arg2, Arg3, Arg4, Arg5> copyArg(Function<PayloadType, Arg5> arg) {
        processorDescription.arg5 = arg;
        processorDescription.isCopyArg4 = true;
        return new HandlerBuilder5<>(contextResult, processorDescription);
    }

    public <ProcessorResult> MergerBuilder<ContextResult, PayloadType, ProcessorResult> withHandler(Handler4Args<Arg1, Arg2, Arg3, Arg4, CompletableFuture<ProcessorResult>> handler) {
        processorDescription.handler4 = handler;
        ReactorReflector.getMethodInvocationPoint().ifPresent(source -> processorDescription.handleBySource = source);

        return new MergerBuilder<>(contextResult, mergerInfo -> {
            processorDescription.merger = mergerInfo.merger;
            processorDescription.mergeSource = mergerInfo.mergerSource;
            processorDescription.mergerDocs = mergerInfo.mergerDocs;
            processorDescription.mergerTitle = mergerInfo.mergerTitle;
        });
    }
}
