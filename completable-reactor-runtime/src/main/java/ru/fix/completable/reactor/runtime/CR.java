package ru.fix.completable.reactor.runtime;

import lombok.val;
import ru.fix.completable.reactor.runtime.internal.CRReactorGraph;
import ru.fix.completable.reactor.runtime.internal.ReactorReflector;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Kamil Asfandiyarov
 */
public class CR {
    public void write(ReactorGraph<?>... graphs) throws Exception {
        for (ReactorGraph<?> graph : graphs) {

            val model = graph.serialize();
            model.serializationPointSource = ReactorReflector.getMethodInvocationPoint().orElse(null);

            val content = ReactorMarshaller.marshall(model);

            val path = Paths.get(((CRReactorGraph)graph).getPayloadClass().getName() + ".rg");
            Files.write(path, content.getBytes(StandardCharsets.UTF_8));
        }
    }
}
