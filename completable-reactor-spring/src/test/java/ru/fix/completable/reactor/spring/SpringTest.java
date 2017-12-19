package ru.fix.completable.reactor.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.PostConstruct;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        SpringTest.ProjectServicesThatWeDependsOn.class,
        SpringTest.GraphConfig.class
})
public class SpringTest {


    static class Proc{
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
        A theA(){
            return new A();
        }

        @Bean
        B theB(){
            return new B();
        }
    }

    static Proc newProc(Function<A, Proc> f){
        throw new RuntimeException();
    }

    @Configuration
    public static class GraphConfig{

        @Bean
        Proc procA(A a){
            return new Proc(""+a);
        }

        @Bean
        Proc procAB(A a, B b) {
            return new Proc(""+a+b);
        }

        @Bean
        String graph105(
//                Proc procA,
                Proc procAB
        ){
            System.out.println("procA=" + procA);
            System.out.println("procAB=" + procAB);
            System.out.println("register graph 105");
            return "graph105";
        }

    }




    @Test
    public void example(){
        System.out.println("run test");

    }

}


class A{}
class B{}

class C {
    final A a;
    final B b;

    public C(A a, B b) {
        this.a = a;
        this.b = b;
    }
}