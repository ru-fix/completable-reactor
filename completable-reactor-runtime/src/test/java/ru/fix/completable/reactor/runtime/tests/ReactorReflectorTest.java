package ru.fix.completable.reactor.runtime.tests;

import org.junit.Before;
import org.junit.Test;
import ru.fix.completable.reactor.api.Reactored;
import ru.fix.completable.reactor.runtime.internal.ReactorReflector;

import java.lang.reflect.Method;
import java.util.function.BiFunction;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;

/**
 * @author Kamil Asfandiyarov
 */
public class ReactorReflectorTest {

    ReactorReflector reflector;

    @Before
    public void init(){
        reflector = new ReactorReflector();
    }


    @Reactored("none")
    static class ProcessorClassWithMethods {

        public Object noArgMethod(){
            throw new UnsupportedOperationException();
        }

        public Object oneArgMethod(Long arg){
            throw new UnsupportedOperationException();
        }
    }

    @Reactored("none")
    public interface ProcessorInterfaceWithMethod {
        Object noArgMethod();
    }

    static interface ProcessorInterfaceExtendsInterface extends ProcessorInterfaceWithMethod {
    }

    static class ProcessorClassImplementsInterface implements ProcessorInterfaceExtendsInterface {
        @Override
        public Object noArgMethod() {
            throw new UnsupportedOperationException();
        }
    }


    @Test
    public void get_method_name_from_method_reference_without_args() throws Exception {
        ReactorReflector reflector = new ReactorReflector();

        Function function = (Function<ProcessorClassWithMethods, Object>) ProcessorClassWithMethods::noArgMethod;

        Method method = reflector.getMethodFromMethodReference(ProcessorClassWithMethods.class, function::apply);

        assertEquals("noArgMethod", method.getName());

    }

    @Test
    public void get_method_name_from_method_reference_with_arg() throws Exception {
        ReactorReflector reflector = new ReactorReflector();

        BiFunction function = (BiFunction<ProcessorClassWithMethods, Long, Object>) ProcessorClassWithMethods::oneArgMethod;

        Method method = reflector.getMethodFromMethodReference(ProcessorClassWithMethods.class, function::apply);

        assertEquals("oneArgMethod", method.getName());

    }

    @Test
    public void get_method_name_from_interface_method_reference_without_args() throws Exception {
        ReactorReflector reflector = new ReactorReflector();

        Function function = (Function<ProcessorInterfaceWithMethod, Object>) ProcessorInterfaceWithMethod::noArgMethod;

        Method method = reflector.getMethodFromMethodReference(ProcessorClassImplementsInterface.class, function::apply);

        assertEquals("noArgMethod", method.getName());

    }
}