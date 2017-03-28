package ru.fix.completable.reactor.runtime;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.matcher.ElementMatchers;
import org.apache.commons.lang3.exception.ExceptionUtils;
import ru.fix.completable.reactor.api.ProcessorDescription;
import ru.fix.completable.reactor.api.ReactorGraphModel;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * @author Kamil Asfandiyarov
 */
@Slf4j
public class ReactorReflector {

    @FunctionalInterface
    public interface MethodReference1 {
        Object apply(Object arg1);
    }

    @FunctionalInterface
    public interface MethodReference2 {
        Object apply(Object arg1, Object arg2);
    }

    @FunctionalInterface
    public interface MethodReference3 {
        Object apply(Object arg1, Object arg2, Object arg3);
    }

    @FunctionalInterface
    public interface MethodReference4 {
        Object apply(Object arg1, Object arg2, Object arg3, Object arg4);
    }

    @FunctionalInterface
    public interface MethodReference5 {
        Object apply(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5);
    }

    @FunctionalInterface
    public interface MethodReference6 {
        Object apply(Object arg1, Object arg2, Object arg3, Object arg4, Object arg5, Object arg6);
    }

    public Method getMethodFromMethodReference(Class processorClass, MethodReference1 handler) throws Exception {
        return extractMethodByProxyCall(processorClass, proxy -> handler.apply(proxy));
    }

    private static final Map<Class<?>, Object> defaultPrimitiveMockParameterValues = new HashMap<>();

    static {
        defaultPrimitiveMockParameterValues.put(String.class, "");
        defaultPrimitiveMockParameterValues.put(Boolean.class, false);
        defaultPrimitiveMockParameterValues.put(boolean.class, false);
        defaultPrimitiveMockParameterValues.put(Byte.class, false);
        defaultPrimitiveMockParameterValues.put(byte.class, false);
        defaultPrimitiveMockParameterValues.put(Short.class, 0);
        defaultPrimitiveMockParameterValues.put(short.class, 0);
        defaultPrimitiveMockParameterValues.put(Integer.class, 0);
        defaultPrimitiveMockParameterValues.put(int.class, 0);
        defaultPrimitiveMockParameterValues.put(Long.class, 0L);
        defaultPrimitiveMockParameterValues.put(long.class, 0L);
        defaultPrimitiveMockParameterValues.put(Double.class, 0d);
        defaultPrimitiveMockParameterValues.put(double.class, 0d);
        defaultPrimitiveMockParameterValues.put(Float.class, 0f);
        defaultPrimitiveMockParameterValues.put(float.class, 0f);
        defaultPrimitiveMockParameterValues.put(Character.class, '\0');
        defaultPrimitiveMockParameterValues.put(char.class, '\0');
        defaultPrimitiveMockParameterValues.put(Void.TYPE, null);
    }

    Object newMockParameterInstance(Class type) {
        if (type.isPrimitive()) {
            return defaultPrimitiveMockParameterValues.get(type);
        } else {
            return null;
        }
    }

    @Data
    static class MethodParameters {
        final Object[] parameters;

        @Override
        public boolean equals(Object obj) {
            MethodParameters that = (MethodParameters) obj;
            return Arrays.equals(parameters, that.parameters);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(parameters);
        }

    }

    Method tryToMatchAllPossibleMethodDeclarationOnGivenMethodReference(Class clazz, int parameterCount, BiConsumer<Object, Object[]> handlerInvoker) {
        Set<MethodParameters> paramsCombinations = Arrays.stream(clazz.getMethods())
                .filter(method -> method.getParameterCount() == parameterCount)
                .filter(method -> Modifier.isPublic(method.getModifiers()))
                .filter(method -> !Modifier.isStatic(method.getModifiers()))
                .map(Method::getParameterTypes)
                .map(paramTypes -> Arrays.stream(paramTypes).map(this::newMockParameterInstance).toArray(Object[]::new))
                .map(MethodParameters::new)
                .collect(Collectors.toSet());

        Collection<Exception> tryMatchExceptions = new ArrayList<>();
        for (MethodParameters parameters : paramsCombinations) {
            try {
                return extractMethodByProxyCall(clazz, proxy -> handlerInvoker.accept(proxy, parameters.getParameters()));

            } catch (ReactorGraphException reactorException) {
                throw reactorException;

            } catch (Exception exception) {
                tryMatchExceptions.add(new IllegalStateException(String.format("Failed to apply combination: %s", parameters),
                        exception));
                /**
                 * Workaround for avoiding NPE during autoboxing to primitive classes
                 * or ClassCast if we selected wrong method by reflection and this selected method
                 * signature does not mach real one that was provided by method reference.
                 */
                log.trace("Ignoring exception during function declaration mismatch", exception);
            }
        }
        String excString = tryMatchExceptions.stream().map(ExceptionUtils::getStackTrace).collect(Collectors.joining
                (System.lineSeparator(), "[", "]"));
        throw new IllegalStateException(String.format("Could not find suitable method declaration with %d args in " +
                        "class: %s, exceptions: %s, param combinations: %s",
                parameterCount,
                clazz, excString, paramsCombinations));
    }


    public Method getMethodFromMethodReference(Class clazz, MethodReference2 handler) throws Exception {
        return tryToMatchAllPossibleMethodDeclarationOnGivenMethodReference(clazz, 1, (proxy, args) -> handler.apply(proxy, args[0]));
    }

    public Method getMethodFromMethodReference(Class clazz, MethodReference3 handler) throws Exception {
        return tryToMatchAllPossibleMethodDeclarationOnGivenMethodReference(clazz, 2, (proxy, args) -> handler.apply(proxy, args[0], args[1]));
    }

    public Method getMethodFromMethodReference(Class clazz, MethodReference4 handler) throws Exception {
        return tryToMatchAllPossibleMethodDeclarationOnGivenMethodReference(clazz, 3, (proxy, args) -> handler.apply(proxy, args[0], args[1], args[2]));
    }

    public Method getMethodFromMethodReference(Class clazz, MethodReference5 handler) throws Exception {
        return tryToMatchAllPossibleMethodDeclarationOnGivenMethodReference(clazz, 4, (proxy, args) -> handler.apply(proxy, args[0], args[1], args[2], args[3]));
    }

    public Method getMethodFromMethodReference(Class clazz, MethodReference6 handler) throws Exception {
        return tryToMatchAllPossibleMethodDeclarationOnGivenMethodReference(clazz, 5, (proxy, args) -> handler.apply(proxy, args[0], args[1], args[2], args[3], args[4]));
    }

    @FunctionalInterface
    interface ProxyCall {
        void invokeMethodOfProxy(Object proxy);
    }


    public static class MethodInterceptor {
        AtomicReference<Method> methodAcceptor = new AtomicReference<>();

        @RuntimeType
        public Object intercept(@Origin Method method) {
            methodAcceptor.set(method);
            return null;
        }

        Method getMethod() {
            return methodAcceptor.get();
        }
    }

    Method extractMethodByProxyCall(Class processorClazz, ProxyCall proxyCall) throws Exception {

        MethodInterceptor methodInterceptor = new MethodInterceptor();

        Class proxyClass;

        Class<?> identityClass = lookupProcessorIdentityClass(processorClazz);

        if (identityClass.isInterface()) {
            proxyClass = new ByteBuddy()
                    .subclass(Object.class)
                    .implement(identityClass)
                    .method(ElementMatchers.any())
                    .intercept(MethodDelegation.to(methodInterceptor))
                    .make()
                    .load(processorClazz.getClassLoader())
                    .getLoaded();
        } else {
            proxyClass = new ByteBuddy()
                    .subclass(identityClass)
                    .method(ElementMatchers.any())
                    .intercept(MethodDelegation.to(methodInterceptor))
                    .make()
                    .load(processorClazz.getClassLoader())
                    .getLoaded();
        }

        Constructor ctor = Arrays.stream(proxyClass.getConstructors())
                .sorted(Comparator.comparingInt(Constructor::getParameterCount))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Can not find constructor in proxy for class %s", processorClazz)));

        Object proxy = ctor.newInstance(new Object[ctor.getParameterCount()]);

        proxyCall.invokeMethodOfProxy(proxy);

        return methodInterceptor.getMethod();
    }

    private static final String[] methodInvocationClassNamePrefixSkipList = Arrays.stream(
            new Class[]{
                    ReactorGraphBuilder.class,
                    ArgMethodMerger.class,
                    ArgMethodHandler0.class,
                    ArgMethodHandler1.class,
                    ArgMethodHandler2.class,
                    ArgMethodHandler3.class,
                    ArgMethodHandler4.class,
                    ArgMethodHandler5.class,
                    MergePointArgMethodMerger.class
            })
            .map(Class::getName).toArray(String[]::new);

    /**
     * @return location where method was called in format class_name:line_number
     */
    public static Optional<ReactorGraphModel.Source> getMethodInvocationPoint() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();


        stackTrace:
        for (int i = 3; i < trace.length; i++) {
            //skip package
            for (String skipPackage : methodInvocationClassNamePrefixSkipList) {
                if (trace[i].getClassName().startsWith(skipPackage)) {
                    continue stackTrace;
                }
            }

            StackTraceElement traceElement = trace[i];

            ReactorGraphModel.Source source = new ReactorGraphModel.Source();
            source.fileName = traceElement.getFileName();
            source.fileNameLine = traceElement.getLineNumber();
            source.className = traceElement.getClassName();
            return Optional.of(source);
        }

        return Optional.empty();
    }

    /**
     * Due to mocking framework we have to search for real identity of processor.
     * Look for interface or class that have  {@link ProcessorDescription} annotation
     * If no such interface or class is found - return current class instance.
     *
     * @param processorClass
     * @return
     */
    public static Class<?> lookupProcessorIdentityClass(Class<?> processorClass) throws ReactorGraphException {

        Class<?> clazz = processorClass;
        do {
            Class<?> result = lookupProcessorIdentityClassInImplementationHierarchy(clazz);

            if (result != null) {
                return result;
            }

            // will be set to null when we reach top of hierarchy
            clazz = clazz.getSuperclass();

        } while (clazz != null);

        throw new ReactorGraphException(String.format("Processor of class %s can not be used in CompletableReactor." +
                        " Processor's class or one of subclasses or one of interfaces that processor implements should be annotated" +
                        " by %s annotation.",
                processorClass, ProcessorDescription.class));
    }

    private static Class<?> lookupProcessorIdentityClassInImplementationHierarchy(Class<?> clazz) {
        if (clazz.getDeclaredAnnotation(ProcessorDescription.class) != null) {
            return clazz;
        }

        return Arrays.stream(clazz.getInterfaces())
                .map(ReactorReflector::lookupProcessorIdentityClassInImplementationHierarchy)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    public static ProcessorDescription lookupProcessorDescription(Class<?> processorClass) throws ReactorGraphException {
        Class<?> identityClass = lookupProcessorIdentityClass(processorClass);
        return identityClass.getAnnotation(ProcessorDescription.class);
    }
}
