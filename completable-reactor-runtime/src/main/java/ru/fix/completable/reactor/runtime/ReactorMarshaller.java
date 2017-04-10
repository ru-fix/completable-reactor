package ru.fix.completable.reactor.runtime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ru.fix.completable.reactor.api.ReactorGraphModel;

/**
 * @author Kamil Asfandiyarov
 */
public class ReactorMarshaller {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public static String marshall(ReactorGraphModel graphModel) throws Exception {
        return mapper.writeValueAsString(graphModel);
    }
}
