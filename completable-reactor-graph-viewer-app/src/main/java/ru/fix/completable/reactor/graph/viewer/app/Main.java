package ru.fix.completable.reactor.graph.viewer.app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import lombok.val;
import ru.fix.completable.reactor.graph.viewer.GraphViewer;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Kamil Asfandiyarov
 */
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

            val graphModel = new String(Files.readAllBytes(Paths.get(getParameters().getRaw().get(0))), StandardCharsets.UTF_8);

            val viewer = new GraphViewer();

            viewer.openGraph(graphModel);
            stage.setScene(viewer.getScene());

            stage.show();
        } catch (Exception exc) {
            exc.printStackTrace();
            Platform.exit();
        }
    }
}
