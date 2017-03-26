package ru.fix.completable.reactor.runtime;

import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;

/**
 * @author Kamil Asfandiyarov
 */
public class LambdaReflectorTest {

    @FunctionalInterface
    interface Function1<R,A1> extends Serializable {
        R method(A1 a);
    }
    @FunctionalInterface
    interface Function2<R,A1,A2> extends Serializable{
        R method(A1 a1, A2 a2);
    }


    static String myStaticMethod(String value){
        return value + "-static";
    }

    String myInstanceMethod(String value){
        return value + "-instance";
    }

    @Test
    public void methodReference() throws Exception {

        Function1<String, String> staticMethodLambda = LambdaReflectorTest::myStaticMethod;
        Function2<String, LambdaReflectorTest, String> instanceMethodLambda = LambdaReflectorTest::myInstanceMethod;
        Function1<String, String> dynamicLambda = value -> value;

        Assert.assertEquals("value-static", LambdaReflector.methodReference(staticMethodLambda).get().invoke(null, "value"));

        Assert.assertEquals("value-instance", LambdaReflector.methodReference(instanceMethodLambda).get().invoke(this, "value"));

        //should not throw exception
        LambdaReflector.methodReference(dynamicLambda);


    }

}