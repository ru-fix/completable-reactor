package ru.fix.completable.reactor.runtime;

import ru.fix.completable.reactor.runtime.function.Handler0Args;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public class ArgMethodHandler0<ContextResult, PayloadType, ProcessorType> extends BaseDescriberItem<ContextResult> {

    public ArgMethodHandler0(ContextResult contextResult, GraphProcessorDescription processorDescription) {
        super(contextResult, processorDescription);
    }

    public <ProcessorResult> ArgMethodMerger<ContextResult, PayloadType, ProcessorResult> withHandler(
            Handler0Args<CompletableFuture<ProcessorResult>> handler) {

        processorDescription.handler0 = handler;
        ReactorReflector.getMethodInvocationPoint().ifPresent(source -> processorDescription.handleBySource = source);

        LambdaReflector.methodReference(handler).ifPresent(method -> {
            method.getName()
        });

        return new ArgMethodMerger<>(contextResult, mergerInfo -> {
            processorDescription.merger = mergerInfo.merger;
            processorDescription.mergeSource = mergerInfo.mergerSource;
            processorDescription.mergerDocs = mergerInfo.mergerDocs;
            processorDescription.mergerTitle = mergerInfo.mergerTitle;
        });
    }


    public <Arg1> ArgMethodHandler1<ContextResult, PayloadType, ProcessorType, Arg1> passArg(Function<PayloadType, Arg1> arg) {
        processorDescription.arg1 = arg;
        return new ArgMethodHandler1<>(contextResult, processorDescription);
    }

    public <Arg1> ArgMethodHandler1<ContextResult, PayloadType, ProcessorType, Arg1> copyArg(Function<PayloadType, Arg1> arg) {
        processorDescription.arg1 = arg;
        processorDescription.isCopyArg1 = true;
        return new ArgMethodHandler1<>(contextResult, processorDescription);
    }
}
