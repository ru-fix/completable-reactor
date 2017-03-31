package ru.fix.completable.reactor.runtime;

import ru.fix.completable.reactor.runtime.dsl.Handler3Args;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public class HandlerBuilder3<ContextResult, PayloadType, Arg1, Arg2, Arg3> extends BaseDescriberItem<ContextResult> {

    HandlerBuilder3(ContextResult contextResult, GraphProcessorDescription processorDescription) {
        super(contextResult, processorDescription);
    }

    public <Arg4> HandlerBuilder4<ContextResult, PayloadType, Arg1, Arg2, Arg3, Arg4> passArg(Function<PayloadType, Arg4> arg) {
        processorDescription.arg4 = arg;
        return new HandlerBuilder4<>(contextResult, processorDescription);
    }

    public <Arg4> HandlerBuilder4<ContextResult, PayloadType, Arg1, Arg2, Arg3, Arg4> copyArg(Function<PayloadType, Arg4> arg) {
        processorDescription.arg4 = arg;
        processorDescription.isCopyArg3 = true;
        return new HandlerBuilder4<>(contextResult, processorDescription);
    }

    public <ProcessorResult> MergerBuilder<ContextResult, PayloadType, ProcessorResult> withHandler(Handler3Args<Arg1, Arg2, Arg3, CompletableFuture<ProcessorResult>> handler) {
        processorDescription.handler3 = handler;
        ReactorReflector.getMethodInvocationPoint().ifPresent(source -> processorDescription.handleBySource = source);

        return new MergerBuilder<>(contextResult, mergerInfo -> {
            processorDescription.merger = mergerInfo.merger;
            processorDescription.mergeSource = mergerInfo.mergerSource;
            processorDescription.mergerDocs = mergerInfo.mergerDocs;
            processorDescription.mergerTitle = mergerInfo.mergerTitle;
        });
    }
}
