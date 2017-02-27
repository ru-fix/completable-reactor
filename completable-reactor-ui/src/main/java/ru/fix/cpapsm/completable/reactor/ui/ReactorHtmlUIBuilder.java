package ru.fix.cpapsm.completable.reactor.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ru.fix.cpapsm.completable.reactor.api.ReactorGraphModel;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

/**
 * @author Kamil Asfandiyarov
 */
public class ReactorHtmlUIBuilder {

    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public static String buildHtmlPage(List<ReactorGraphModel> graphModels) throws Exception{
        String jsonDescription =  mapper.writeValueAsString(graphModels);
        return UIBuilder.buildHtmlPage(jsonDescription);
    }

    public static Path write(ReactorGraphModel graphModel) throws Exception{
        return write(Arrays.asList(graphModel), Paths.get(graphModel.payload.payloadClass + ".rg.html"));
    }

    public static Path write(ReactorGraphModel graphModel, Path path) throws Exception{
        return write(Arrays.asList(graphModel), path);
    }
    public static Path write(List<ReactorGraphModel> graphModels, Path path) throws Exception{
        String html = buildHtmlPage(graphModels);
        return Files.write(path, html.getBytes(StandardCharsets.UTF_8));
    }
}
