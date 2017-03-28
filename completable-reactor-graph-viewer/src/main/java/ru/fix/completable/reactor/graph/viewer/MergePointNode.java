package ru.fix.completable.reactor.graph.viewer;

import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import lombok.val;
import ru.fix.completable.reactor.api.ReactorGraphModel;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by swarmshine on 29.01.2017.
 */
class MergePointNode extends VBox  implements CentrableNode, BorderableNode{

    final CoordinateTranslator coordinateTranslator;
    final ReactorGraphModel.MergePoint mergePoint;
    final GraphViewer.ActionListener actionListener;

    final Pane mergePointCircle = new Pane();

    private Double radius = 20.0;

    public MergePointNode(
            CoordinateTranslator translator,
            ReactorGraphModel.MergePoint mergePoint,
            GraphViewer.ActionListener actionListener,
            List<GraphViewer.CoordinateItem> coordinateItemList) {

        this.coordinateTranslator = translator;
        this.mergePoint = mergePoint;
        this.actionListener = actionListener;

        this.getChildren().add(mergePointCircle);
        this.mergePointCircle.getStyleClass().add("mergePoint");

        this.setLayoutX(translator.translateX(mergePoint.coordinates.x));
        this.setLayoutY(translator.translateY(mergePoint.coordinates.y));


        this.mergePointCircle.setPrefWidth(radius * 2);
        this.mergePointCircle.setMaxWidth(radius * 2);
        this.mergePointCircle.setPrefHeight(radius * 2);
        this.mergePointCircle.setMaxHeight(radius * 2);
        this.setAlignment(Pos.CENTER);

        this.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                actionListener.goToSource(mergePoint.mergeSource);
            }
        });


        GraphViewer.CoordinateItem coordinateItem = new GraphViewer.CoordinateItem(
                GraphViewer.CoordinateItem.Type.MERGE_POINT,
                mergePoint.processor,
                mergePoint.coordinates.getX(),
                mergePoint.coordinates.getY());

        coordinateItemList.add(coordinateItem);

        val dragger = NodeDragger.attach(this);

        dragger.addOnPositionChangedListener(() -> {
            coordinateItem.setX(translator.reverseTranslateX(getLayoutX()));
            coordinateItem.setY(translator.reverseTranslateY(getLayoutY()));
            actionListener.coordinatesChanged(coordinateItemList);
        });

        initializePopupMenu();

        if (mergePoint.mergeSource != null) {
            this.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getClickCount() == 2) {
                    actionListener.goToSource(mergePoint.mergeSource);
                    mouseEvent.consume();
                }
            });
        }

        Label title = new Label(Optional.ofNullable(mergePoint.mergerTitle).orElse(""));
        this.getChildren().add(title);
    }

    void initializePopupMenu() {
        ContextMenu contextMenu = new ContextMenu();

        StringBuilder textBuilder = new StringBuilder();

        if(mergePoint.mergerTitle != null){
            textBuilder.append(mergePoint.mergerTitle).append("\n");
        }

        Optional.ofNullable(mergePoint.mergerDocs)
                .map(Arrays::stream)
                .map(stream -> stream.collect(Collectors.joining("\n")))
                .ifPresent(textBuilder::append);

        MenuItem menuItem = new MenuItem(textBuilder.length() > 0 ? textBuilder.toString() : "MergePoint");
        contextMenu.getItems().add(menuItem);

        if (mergePoint.mergeSource != null) {
            menuItem.setOnAction(event -> actionListener.goToSource(mergePoint.mergeSource));
        }

        this.setOnContextMenuRequested(contextMenuEvent -> {
            contextMenu.show(this, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
            contextMenuEvent.consume();
        });
    }

    @Override
    public double getCenterX() {
        Bounds bounds = this.mergePointCircle.getBoundsInParent();
        return this.getLayoutX() + bounds.getMinX() + bounds.getWidth() / 2;
    }

    @Override
    public double getCenterY() {
        Bounds bounds = this.mergePointCircle.getBoundsInParent();
        return this.getLayoutY() + bounds.getMinY() + bounds.getHeight() / 2;
    }

    @Override
    public double getBorderableX() {
        return this.getLayoutX() + mergePointCircle.getLayoutX();
    }

    @Override
    public double getBorderableY() {
        return this.getLayoutY() + mergePointCircle.getLayoutY();
    }

    @Override
    public double getBorderableWidth() {
        return mergePointCircle.getPrefWidth();
    }

    @Override
    public double getBorderableHeight() {
        return mergePointCircle.getPrefHeight();
    }
}