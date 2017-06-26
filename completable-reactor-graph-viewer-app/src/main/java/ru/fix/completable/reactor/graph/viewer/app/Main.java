package ru.fix.completable.reactor.graph.viewer.app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import ru.fix.completable.reactor.graph.viewer.GraphViewer;
import ru.fix.completable.reactor.parser.java.JavaParser;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Kamil Asfandiyarov
 */
@Slf4j
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        if (getParameters().getRaw().size() <= 0) {
            Platform.exit();
            return;
        }

        try {
            Path path = Paths.get(getParameters().getRaw().get(0));
            val viewer = new GraphViewer();

            if(path.endsWith(".rg")) {
                String graphModel = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
                viewer.openGraph(graphModel);

            } else if (path.endsWith(".java")){
                String javaModel = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
                viewer.openGraph(new JavaParser(null).parse(javaModel).get(0));
            } else {
                throw new IllegalArgumentException(String.format("Invalid model: {}", path.toAbsolutePath()));
            }

            stage.setScene(viewer.getScene());

            stage.show();
        } catch (Exception exc) {
            log.error(exc.getMessage(), exc);
            Platform.exit();
        }
    }
}
