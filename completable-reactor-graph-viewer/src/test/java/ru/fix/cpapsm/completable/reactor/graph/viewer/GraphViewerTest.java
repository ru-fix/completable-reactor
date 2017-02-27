package ru.fix.cpapsm.completable.reactor.graph.viewer;

import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import lombok.val;
import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;
import ru.fix.cpapsm.completable.reactor.api.ReactorGraphModel;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by swarmshine on 29.01.2017.
 */

public class GraphViewerTest extends Application {
    public GraphViewerTest() {
    }

    static class LogActionListener implements GraphViewer.ActionListener {
        @Override
        public void goToSource(ReactorGraphModel.Source source) {
            System.out.println("goToSource: " + source);
        }

        @Override
        public void coordinatesChanged(List<GraphViewer.CoordinateItem> coordinateItems){
            System.out.println("changeCoordinates: " + coordinateItems.stream().map(Object::toString).collect(Collectors.joining("\n")));
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        if (stage == null) {
            throw new IllegalArgumentException("stage is null");
        }


        String graphModel;

        try (val resource = getClass().getResourceAsStream("/example-graph.rg")) {
            graphModel = IOUtils.toString(resource, StandardCharsets.UTF_8);
        }

        val viewer = new GraphViewer();
        viewer.registerListener(new LogActionListener());

        viewer.setShortcut(ShortcutType.GOTO_SERIALIZATION_POINT, new Shortcut(true, KeyCode.B));

        viewer.openGraph(graphModel);
        stage.setScene(viewer.scene);

        stage.show();
    }

    /**
     * Manual UI Test
     */
    @Ignore
    @Test
    public void open_pane() throws Exception {
        Application.launch();
    }


}