package ru.fix.completable.reactor.runtime;

import org.junit.Test;
import ru.fix.completable.reactor.runtime.internal.LambdaReflector;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * @author Kamil Asfandiyarov
 */
public class LambdaReflectorTest {

    @FunctionalInterface
    interface Function1<R, A1> extends Serializable {
        R method(A1 a);
    }

    @FunctionalInterface
    interface Function2<R, A1, A2> extends Serializable {
        R method(A1 a1, A2 a2);
    }

    @FunctionalInterface
    interface Function3<R, A1, A2, A3> extends Serializable {
        R method(A1 a1, A2 a2, A3 a3);
    }

    static String myStaticMethod(String value) {
        return value + "-static";
    }

    String myInstanceMethod(String value) {
        return value + "-instance";
    }

    static String staticTwinMethod(int value) {
        return "static-twin1-" + value;
    }

    static String staticTwinMethod(short value) {
        return "static-twin2-" + value;
    }

    static class Data {
    }

    String instanceTwinMethod(Data value1, Object value2, int[] value3) {
        return "instance-twin1-" + value2 + value3.length;
    }

    String instanceTwinMethod(Data value1, Object value2, short[] value3) {
        return "instance-twin2-" + value2 + value3.length;
    }

    static String signature1(Data value1, Object value2, int[] value3) {
        throw new UnsupportedOperationException();
    }

    static void signature2(int value1, Data[] value3) {
        throw new UnsupportedOperationException();
    }

    static int signature3(Integer value1) {
        throw new UnsupportedOperationException();
    }

    static Method getMethodByName(String name){
        return Arrays.stream(LambdaReflectorTest.class.getDeclaredMethods())
                .filter(method -> method.getName().equals(name))
                .findAny().get();
    }

    @Test
    public void signature_check() throws Exception {
        assertEquals(
                "(Lru/fix/completable/reactor/runtime/LambdaReflectorTest$Data;Ljava/lang/Object;[I)Ljava/lang/String;",
                LambdaReflector.signature(getMethodByName("signature1")));
        assertEquals(
                "(I[Lru/fix/completable/reactor/runtime/LambdaReflectorTest$Data;)V",
                LambdaReflector.signature(getMethodByName("signature2")));
        assertEquals(
                "(Ljava/lang/Integer;)I",
                LambdaReflector.signature(getMethodByName("signature3")));
    }

    @Test
    public void methodReference() throws Exception {

        Function1<String, String> staticMethodLambda = LambdaReflectorTest::myStaticMethod;
        Function2<String, LambdaReflectorTest, String> instanceMethodLambda = LambdaReflectorTest::myInstanceMethod;
        Function1<String, String> dynamicLambda = value -> value;

        assertEquals("value-static", LambdaReflector.methodReference(staticMethodLambda).get().invoke(null, "value"));

        assertEquals("value-instance", LambdaReflector.methodReference(instanceMethodLambda).get().invoke(this, "value"));

        //should not throw exception
        LambdaReflector.methodReference(dynamicLambda);
    }

    @Test
    public void staticTwins() throws Exception {
        Function1<String, Integer> twin1 = LambdaReflectorTest::staticTwinMethod;
        Function1<String, Short> twin2 = LambdaReflectorTest::staticTwinMethod;

        assertEquals("static-twin1-1", LambdaReflector.methodReference(twin1).get().invoke(null, 1));
        assertEquals("static-twin2-2", LambdaReflector.methodReference(twin2).get().invoke(null, (short) 2));
    }


    @Test
    public void instanceTwins() throws Exception {
        Function3<String, Data, Object, int[]> twin1 = this::instanceTwinMethod;
        Function3<String, Data, Object, short[]> twin2 = this::instanceTwinMethod;

        assertEquals("instance-twin1-o12", LambdaReflector.methodReference(twin1).get().invoke(
                this,
                new Data(),
                "o1",
                new int[]{1, 2}));
        assertEquals("instance-twin2-o23", LambdaReflector.methodReference(twin2).get().invoke(
                this,
                new Data(),
                "o2",
                new short[]{1, 2, 3}));
    }

}