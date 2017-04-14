package ru.fix.completable.reactor.runtime.dsl;

import java.util.function.Function;

/**
 * @author Kamil Asfandiyarov
 */
public interface HandlerBuilder3<PayloadType, Arg1, Arg2, Arg3> {

   <Arg4> HandlerBuilder4<PayloadType, Arg1, Arg2, Arg3, Arg4> passArg(
           Function<PayloadType, Arg4> arg
   );

   <Arg4> HandlerBuilder4<PayloadType, Arg1, Arg2, Arg3, Arg4> copyArg(
           Function<PayloadType, Arg4> arg
   );

   <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(
           Handler3Args<Arg1, Arg2, Arg3, ProcessorResult> handler
   );

   <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(
           String title,
           Handler3Args<Arg1, Arg2, Arg3, ProcessorResult> handler
   );

   <ProcessorResult> ProcessorMergerBuilder<PayloadType, ProcessorResult> withHandler(
           String title,
           String[] docs,
           Handler3Args<Arg1, Arg2, Arg3, ProcessorResult> handler
   );
}
