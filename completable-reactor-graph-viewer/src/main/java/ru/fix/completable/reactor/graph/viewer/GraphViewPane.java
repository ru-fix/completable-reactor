package ru.fix.completable.reactor.graph.viewer;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import lombok.experimental.var;
import lombok.val;
import ru.fix.completable.reactor.api.ReactorGraphModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by Kamil Asfandiyarov
 */
public class GraphViewPane extends ScrollPane {

    GraphViewer.ActionListener actionListener;


    private Double WORLD_SIZE = 10000.0;
    private Double ZOOM_CHANGE_FACTOR = 1.15;

    private Double worldSize = WORLD_SIZE;

    private Pane pane = new Pane();

    private CoordinateTranslator translator = new CoordinateTranslator(WORLD_SIZE);

    private HashMap<String, ProcessorNode> processors = new HashMap<>();
    private HashMap<String, MergePointNode> mergePoints = new HashMap<>();
    private ArrayList<MergeGroupNode> mergeGroups = new ArrayList<>();

    List<GraphViewer.CoordinateItem> coordinateItems = new ArrayList<>();
    private ReactorGraphModel graphModel;

    private final Function<ShortcutType, Optional<Shortcut>> shortcutProvider;


    public GraphViewPane(GraphViewer.ActionListener actionListener, Function<ShortcutType, Optional<Shortcut>> shortcutProvider) {
        this.actionListener = actionListener;
        this.shortcutProvider = shortcutProvider;

        pane.getStyleClass().add("graphViewer");

        this.setPannable(true);

        this.setContent(pane);

        pane.setPrefSize(this.worldSize, this.worldSize);
        this.setVvalue(0.5);
        this.setHvalue(0.5);


        pane.setOnScroll(scrollEvent ->
                {
                    if (!scrollEvent.isControlDown()) {
                        scrollEvent.consume();

                        Double zoomChangeFactor;
                        if (scrollEvent.getDeltaY() > 0) {
                            zoomChangeFactor = ZOOM_CHANGE_FACTOR;
                        } else {
                            zoomChangeFactor = 1 / ZOOM_CHANGE_FACTOR;
                        }

                        pane.setScaleX(pane.getScaleX() * zoomChangeFactor);
                        pane.setScaleY(pane.getScaleY() * zoomChangeFactor);
                    }
                }
        );

        initializePopupMenu();
    }

    void initializePopupMenu() {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem buildGraphMenuItem = new MenuItem("Graph build location");
        buildGraphMenuItem.setOnAction(event -> actionListener.goToSource(this.graphModel.buildGraphSource));
        contextMenu.getItems().add(buildGraphMenuItem);

        MenuItem coordinatesMenuItem = new MenuItem("Coordinates location");
        coordinatesMenuItem.setOnAction(event -> actionListener.goToSource(this.graphModel.coordinatesSource));
        contextMenu.getItems().add(coordinatesMenuItem);

        MenuItem serializationMenuItem = new MenuItem();

        serializationMenuItem.setOnAction(event -> actionListener.goToSource(this.graphModel.serializationPointSource));
        contextMenu.getItems().add(serializationMenuItem);

        pane.setOnContextMenuRequested(contextMenuEvent -> {

            var serializationMenuText = new StringBuilder("Graph serialization location");
            shortcutProvider.apply(ShortcutType.GOTO_SERIALIZATION_POINT).ifPresent(
                    shortcut -> serializationMenuText.append(" (" + shortcut.getTitle() + ")"));
            serializationMenuItem.setText(serializationMenuText.toString());


            contextMenu.show(pane, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
            contextMenuEvent.consume();
        });

        this.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            contextMenu.hide();
        });
    }

    ReactorGraphModel getGraph() {
        return this.graphModel;
    }

    GraphViewPane setGraph(ReactorGraphModel graphModel) {
        this.graphModel = graphModel;

        pane.getChildren().removeAll();
        coordinateItems.clear();

        graphModel.processors.forEach((processorName, processorInfo) -> {
            val processorNode = new ProcessorNode(translator, processorName, processorInfo, actionListener, coordinateItems);
            processors.put(processorName, processorNode);
            pane.getChildren().add(processorNode);

        });

        for (val mergeGroup : graphModel.mergeGroups) {

            List<MergePointNode> groupMergePoints = new ArrayList<>();

            for (val mergePoint : mergeGroup.mergePoints) {
                val mergePointNode = new MergePointNode(translator, mergePoint, actionListener, coordinateItems);
                mergePoints.put(mergePoint.processor, mergePointNode);
                pane.getChildren().add(mergePointNode);
                groupMergePoints.add(mergePointNode);
            }

            if (groupMergePoints.size() > 1) {
                val mergeGroupNode = new MergeGroupNode(translator, mergeGroup, groupMergePoints, actionListener);
                mergeGroupNode.toBack();
                mergeGroups.add(mergeGroupNode);
                pane.getChildren().add(mergeGroupNode);
            }
        }

        // Draw transition lines

        mergePoints.forEach((processorName, mergePointNode) -> {

            val mergePoint = mergePointNode.mergePoint;

            if (mergePoint.transitions != null) {
                for (val transition : mergePoint.transitions) {
                    if (transition.isComplete) {

                        val endPointNode = new EndPointNode(translator, mergePoint, transition, actionListener, coordinateItems);

                        pane.getChildren().addAll(endPointNode);

                        val line = new TransitionLine(pane, mergePointNode, endPointNode, Optional.of(transition));
                        pane.getChildren().add(line);
                        line.toBack();

                    } else if (transition.isOnAny) {
                        if (transition.handleByProcessor != null) {
                            val line = new TransitionLine(pane, mergePointNode, processors.get(transition.handleByProcessor), Optional.of(transition));
                            pane.getChildren().add(line);
                            line.toBack();
                        } else if (transition.mergeProcessor != null) {
                            val line = new TransitionLine(pane, mergePointNode, mergePoints.get(transition.mergeProcessor), Optional.of(transition));
                            pane.getChildren().add(line);
                            line.toBack();
                        }
                    } else if (transition.handleByProcessor != null) {
                        val line = new TransitionLine(pane, mergePointNode, processors.get(transition.handleByProcessor), Optional.of(transition));
                        pane.getChildren().add(line);
                        line.toBack();
                    } else if (transition.mergeProcessor != null) {
                        val line = new TransitionLine(pane, mergePointNode, mergePoints.get(transition.mergeProcessor), Optional.of(transition));
                        pane.getChildren().add(line);
                        line.toBack();
                    }
                }
            }
        });

        processors.forEach((processorName, processorNode) -> {
            val mergePointNode = mergePoints.get(processorName);
            if (mergePointNode == null) {
                return;
            }

            val line = new TransitionLine(pane, processorNode, mergePointNode, Optional.empty());
            pane.getChildren().add(line);
            line.toBack();
        });

        val startPointNode = new StartPointNode(translator, graphModel, actionListener, coordinateItems);
        pane.getChildren().add(startPointNode);

        graphModel.startPoint.processors.forEach(processorName -> {
            val transition = new TransitionLine(pane, startPointNode, processors.get(processorName), Optional.empty());
            pane.getChildren().add(transition);
            transition.toBack();
        });


        for (val mergePointNode : mergePoints.values()) {
            mergePointNode.toFront();
        }

        return this;
    }

}