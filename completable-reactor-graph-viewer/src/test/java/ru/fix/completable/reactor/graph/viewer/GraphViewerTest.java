package ru.fix.completable.reactor.graph.viewer;

import javafx.application.Application;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import lombok.val;
import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;
import ru.fix.completable.reactor.api.ReactorGraphModel;
import ru.fix.completable.reactor.graph.viewer.code.CodeUpdater;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by swarmshine on 29.01.2017.
 */

public class GraphViewerTest extends Application {

    private static String payload;

    public GraphViewerTest() {
    }

    static class LogActionListener implements GraphViewer.ActionListener {
        @Override
        public void goToSource(ReactorGraphModel.Source source) {
            System.out.println("goToSource: " + source);
        }

        @Override
        public void goToSubgraph(String subgraphPayloadClass) {
            System.out.println("goToSubgraph: " + subgraphPayloadClass);
        }

        @Override
        public void coordinatesChanged(List<GraphViewer.CoordinateItem> coordinateItems) {
            try {
                System.out.println("changeCoordinates:\n" +
                        coordinateItems.stream().map(Object::toString).collect(Collectors.joining("\n")));

                val codeUpdater = new CodeUpdater();
                try (val resource = getClass().getResourceAsStream("/viewer-code-block.txt")) {
                    System.out.println("Code:\n" +
                            codeUpdater.updateCoordinates(IOUtils.toString(resource, StandardCharsets.UTF_8), coordinateItems));
                }
            } catch (Exception exc) {
                exc.printStackTrace();
            }
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        if (stage == null) {
            throw new IllegalArgumentException("stage is null");
        }


        String graphModel;

        try (val resource = getClass().getResourceAsStream("/"  + GraphViewerTest.payload)) {

            graphModel = IOUtils.toString(resource, StandardCharsets.UTF_8);
        }

        val viewer = new GraphViewer();
        viewer.registerListener(new LogActionListener());

        viewer.setShortcut(ShortcutType.GOTO_SERIALIZATION_POINT, new Shortcut(true, KeyCode.B));

        viewer.openGraph(graphModel);
        stage.setScene(viewer.getScene());

        stage.show();
    }

    /**
     * Manual UI Test
     */
    @Ignore
    @Test
    public void open_pane() throws Exception {
        GraphViewerTest.payload = "PurchasePayload.rg";
        Application.launch();
    }


    @Ignore
    @Test
    public void open_grpah_with_subgraph() throws Exception{
        GraphViewerTest.payload = "SubscribePayload.rg";
        Application.launch();

    }

}