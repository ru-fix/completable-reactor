package ru.fix.completable.reactor.runtime.dsl;

import ru.fix.completable.reactor.runtime.internal.MergerBuilder;
import ru.fix.completable.reactor.runtime.internal.HandlerBuilder4;

import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public interface HandlerBuilder3<ContextResult, PayloadType, Arg1, Arg2, Arg3> {

   <Arg4> ru.fix.completable.reactor.runtime.internal.HandlerBuilder4<ContextResult, PayloadType, Arg1, Arg2, Arg3, Arg4> passArg(
           Function<PayloadType, Arg4> arg
   );

   <Arg4> HandlerBuilder4<ContextResult, PayloadType, Arg1, Arg2, Arg3, Arg4> copyArg(
           Function<PayloadType, Arg4> arg
   );

   <ProcessorResult> MergerBuilder<ContextResult, PayloadType, ProcessorResult> withHandler(
           Handler3Args<Arg1, Arg2, Arg3, ProcessorResult> handler
   );
}
