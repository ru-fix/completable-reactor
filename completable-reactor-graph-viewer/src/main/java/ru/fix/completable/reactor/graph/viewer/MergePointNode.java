package ru.fix.completable.reactor.graph.viewer;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import lombok.val;
import ru.fix.completable.reactor.api.ReactorGraphModel;

import java.util.List;

/**
 * Created by swarmshine on 29.01.2017.
 */
class MergePointNode extends VBox {

    final CoordinateTranslator coordinateTranslator;
    final ReactorGraphModel.MergePoint mergePoint;
    final GraphViewer.ActionListener actionListener;

    private Double radius = 20.0;

    public MergePointNode(
            CoordinateTranslator translator,
            ReactorGraphModel.MergePoint mergePoint,
            GraphViewer.ActionListener actionListener,
            List<GraphViewer.CoordinateItem> coordinateItemList) {

        this.coordinateTranslator = translator;
        this.mergePoint = mergePoint;
        this.actionListener = actionListener;

        this.getStyleClass().add("mergePoint");

        this.setLayoutX(translator.translateX(mergePoint.coordinates.x));
        this.setLayoutY(translator.translateY(mergePoint.coordinates.y));

        this.setPrefWidth(radius * 2);
        this.setPrefHeight(radius * 2);

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

        this.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getClickCount() == 2){
                actionListener.goToSource(mergePoint.coordinatesSource);
                mouseEvent.consume();
            }
        });

    }

    void initializePopupMenu(){
        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItem = new MenuItem("MergePoint");
        contextMenu.getItems().add(menuItem);

        menuItem.setOnAction(event -> actionListener.goToSource(mergePoint.coordinatesSource));

        this.setOnContextMenuRequested(contextMenuEvent -> {
            contextMenu.show(this, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
            contextMenuEvent.consume();
        });
    }
}