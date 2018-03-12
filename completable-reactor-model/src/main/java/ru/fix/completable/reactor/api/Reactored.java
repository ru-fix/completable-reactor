package ru.fix.completable.reactor.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Documentation for payload.<br>
 * Documentation for referenced methods and classes.<br>
 *
 * <h1>Using &#064;Reactored for payload documentation.</h1>
 * <pre><code>
 * &#064;Reactored({"Description of paylaod class "})
 * class MyPayload { }
 * //somewhere in graph description:
 * graphBuilder.payload(MyPayload.class)
 * </code></pre>
 *
 *
 * <h1>Using &#064;Reactored for handling method documentation.</h1>
 * <pre><code>
 * &#064;Reactored({"Description of class with handling method"})
 * class ClassWithHandlingMethod {
 *     &#064;Reactored({
 *         "Description of handling method"
 *     })
 *     CompletableFuture&lt;Result&gt; handlingMethod(ArgType1 arg1, ArgType2 arg2, ...){}
 * }
 * //somewhere in graph description:
 * .withMerger(ClassWithHandlingMethod::handlingMethod)
 * </code></pre>
 *
 *
 * <h1>Using &#064;Reactored for merging method documentation.</h1>
 * <pre><code>
 * &#064;Reactored({"Description of class with merging method"})
 * class ClassWithMergingMethod {
 *     &#064;Reactored({
 *         "Description of merging method"
 *     })
 *     Enum mergingMethod(PayloadType paylaod, ResultType result){}
 * }
 * //somewhere in graph description:
 * .withMerger(ClassWithMergingMethod::mergingMethod)
 * </code></pre>
 *
 * @deprecated will be removed. Use simple javadoc instead
 *
 * @author Kamil Asfandiyarov
 */
@Deprecated()
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.FIELD})
public @interface Reactored {
    /**
     * Documentation.<br>
     * Each string from array will be displayed in new line.
     */
    String[] value() default {};
}
