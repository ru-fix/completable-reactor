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

    /**
     * This test is located in separate package because {@link ReactorReflector#getMethodInvocationPoint()}  skip call stack
     * with  ru.fix.completable.reactor.runtime.* classes.
     * @throws Exception
     */
    @Test
    public void getMethodInvocationPoint() throws Exception {
        val source = foo();
        Assert.assertEquals(ReactorReflectorTest.class.getName(), source.className);
        Assert.assertEquals(ReactorReflectorTest.class.getSimpleName() + ".java", source.fileName);
        Assert.assertEquals(25L, (long)source.fileNameLine);
    }
}