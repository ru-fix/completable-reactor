package ru.fix.completable.reactor.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ReflectionUtils;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        SpringTest.ProjectServicesThatWeDependsOn.class,
        SpringTest.GraphConfig.class
})
public class SpringTest {


    static class Proc {
        final String name;

        public Proc(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Proc{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    @Configuration
    public static class ProjectServicesThatWeDependsOn {
        @Bean
        A theA() {
            return new A();
        }

        @Bean
        A theOtherA() {
            return new A();
        }

        @Bean
        B theB() {
            return new B();
        }
    }

    static <T1> Proc proc(Function<T1, ProcDesc> f) {
        System.out.println("SpringTest.newProc");
        return null;
    }

    static <T1, T2> Proc proc(BiFunction<T1, T2, ProcDesc> f) {
        System.out.println("SpringTest.newProc");
        return null;
    }

    public static class ProcDesc {
        String val;

        public ProcDesc(String val) {
            this.val = val;
        }

        public Proc buildProc() {
            return new Proc("proc based on desc of " + val);
        }
    }


    static <A1, A2, R> void with(BiFunction<A1, A2, R> f){
        Class<?>[] types = Arrays.stream(f.getClass().getMethods()).filter(m -> m.getName().equals("apply")).findFirst()
                .get().getParameterTypes();

        System.out.println("SpringTest.with: " + Arrays.deepToString(types));
    }


    @Configuration
    public static class GraphConfig {
//
//
//        Proc procA1 = with(A.class).handle(pld, a -> {a.invokeSomeSmartMtehod(pld.arg1, pld.arg2, pld.arg3)})
//                                    .merge(pld, r -> {pld.setFoo(r)})
//                                    .build(::procA1))
//        Proc procA2 = procA1.clone();
//
//        Proc procA3 = procA1.clone();
//
//
//        @Bean
//        public Proc procA(A theA) {
//            System.out.println("GraphConfig.procA");
//            return new ProcDesc("desc of: " + theA).buildProc();
//        }
//
//

        Proc procAB;
        void procAB(A theOtherA, B b) {
            bindHandler(a -> sdfsldf)
            bindMerger(a -> sdfjsdlf)
            procAB = buildProc()
            procA1 = buildProc()


            procA3 = bindHandler()
                    .bindMerger()
                    .build();
            procA4 = buildProc();
            procA5 = buildProc();


        }



        @Bean
        String graph105() {

            with(this::procAB);


//            System.out.println("procA1=" + procA1);
//            System.out.println("procA2=" + procA2);
//            System.out.println("procAB=" + procAB);
//            System.out.println("register graph 105");
            return "graph105";
        }

    }




    @Test
    public void example() {
        System.out.println("run test");

    }

}


class A {
}

class B {
}

class C {
    final A a;
    final B b;

    public C(A a, B b) {
        this.a = a;
        this.b = b;
    }
}