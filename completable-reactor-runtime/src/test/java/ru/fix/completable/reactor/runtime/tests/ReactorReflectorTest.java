package ru.fix.completable.reactor.runtime.tests;

import lombok.val;
import org.junit.Assert;
import org.junit.Test;
import ru.fix.completable.reactor.api.ReactorGraphModel;
import ru.fix.completable.reactor.runtime.internal.ReactorReflector;

/**
 * @author Kamil Asfandiyarov
 */
public class ReactorReflectorTest {

    public ReactorGraphModel.Source foo(){
        return ReactorReflector.getMethodInvocationPoint().get();
    }

    @Test
    public void getMethodInvocationPoint() throws Exception {
        val source = foo();
        Assert.assertEquals(ReactorReflectorTest.class.getName(), source.className);
        Assert.assertEquals(ReactorReflectorTest.class.getSimpleName(), source.fileName);
        Assert.assertEquals(15L, (long)source.fileNameLine);
    }
}