package ru.fix.cpapsm.completable.reactor.runtime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ru.fix.cpapsm.completable.reactor.api.ReactorGraphModel;

import java.util.List;

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

    public static String marshall(List<ReactorGraphModel> graphModels) throws Exception {
        return mapper.writeValueAsString(graphModels);
    }
}
