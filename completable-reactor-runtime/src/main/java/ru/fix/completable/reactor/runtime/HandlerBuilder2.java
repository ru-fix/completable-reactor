package ru.fix.completable.reactor.runtime;

import ru.fix.completable.reactor.runtime.dsl.Handler2Args;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public class HandlerBuilder2<ContextResult, PayloadType, Arg1, Arg2> extends BaseDescriberItem<ContextResult> {

    HandlerBuilder2(ContextResult contextResult, GraphProcessorDescription processorDescription) {
        super(contextResult, processorDescription);
    }

    public <Arg3> HandlerBuilder3<ContextResult, PayloadType, Arg1, Arg2, Arg3> passArg(Function<PayloadType, Arg3> arg) {
        processorDescription.arg3 = arg;
        return new HandlerBuilder3<>(contextResult, processorDescription);
    }

    public <Arg3> HandlerBuilder3<ContextResult, PayloadType, Arg1, Arg2, Arg3> copyArg(Function<PayloadType, Arg3> arg) {
        processorDescription.arg3 = arg;
        processorDescription.isCopyArg3 = true;
        return new HandlerBuilder3<>(contextResult, processorDescription);
    }

    public <ProcessorResult> MergerBuilder<ContextResult, PayloadType, ProcessorResult> withHandler(Handler2Args<Arg1, Arg2, CompletableFuture<ProcessorResult>> handler) {
        processorDescription.handler2 = handler;
        ReactorReflector.getMethodInvocationPoint().ifPresent(source -> processorDescription.handleBySource = source);

        return new MergerBuilder<>(contextResult, mergerInfo -> {
            processorDescription.merger = mergerInfo.merger;
            processorDescription.mergeSource = mergerInfo.mergerSource;
            processorDescription.mergerDocs = mergerInfo.mergerDocs;
            processorDescription.mergerTitle = mergerInfo.mergerTitle;
        });
    }
}
