package ru.fix.completable.reactor.runtime.dsl;

import ru.fix.completable.reactor.runtime.HandlerBuilder1;
import ru.fix.completable.reactor.runtime.MergerBuilder;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public interface HandlerBuilder0<PayloadType> {

    <ProcessorResult> MergerBuilder<ContextResult, PayloadType, ProcessorResult> withHandler(
            Handler0Args<CompletableFuture<ProcessorResult>> handler) {

    public <Arg1> HandlerBuilder1<ContextResult, PayloadType, Arg1> passArg(Function<PayloadType, Arg1> arg) {
}
