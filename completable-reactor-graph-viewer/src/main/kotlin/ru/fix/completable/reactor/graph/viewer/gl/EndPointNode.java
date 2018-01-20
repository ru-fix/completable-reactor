package ru.fix.completable.reactor.graph.viewer.gl;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import lombok.val;
import ru.fix.completable.reactor.api.ReactorGraphModel;

import java.util.List;

/**
 * Created by swarmshine on 29.01.2017.
 */
public class EndPointNode extends VBox {
    CoordinateTranslator coordinateTranslator;
    ReactorGraphModel.Transition endPointTransition;
    GraphViewer.ActionListener actionListener;
    ReactorGraphModel.MergePoint mergePoint;


    public EndPointNode(
            CoordinateTranslator translator,
            ReactorGraphModel.MergePoint mergePoint,
            ReactorGraphModel.Transition endPointTransition,
            GraphViewer.ActionListener actionListener,
            List<GraphViewer.CoordinateItem> coordinateItems) {

        this.coordinateTranslator = translator;
        this.mergePoint = mergePoint;
        this.endPointTransition = endPointTransition;
        this.actionListener = actionListener;

        this.getStyleClass().add("endPoint");

        val x = endPointTransition.completeCoordinates.x;
        val y = endPointTransition.completeCoordinates.y;

        this.setLayoutX(translator.translateX(x));
        this.setLayoutY(translator.translateY(y));

        val nameLabel = new Label("End");
        nameLabel.setFont(new Font(16.0));

        this.getChildren().add(nameLabel);

        this.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getClickCount() == 2) {
                actionListener.goToSource(this.endPointTransition.completeSource);
            }
        });

        GraphViewer.CoordinateItem coordinateItem = new GraphViewer.CoordinateItem(
                GraphViewer.CoordinateItem.Type.END_POINT,
                mergePoint.identity,
                endPointTransition.completeCoordinates.getX(),
                endPointTransition.completeCoordinates.getY());
        coordinateItems.add(coordinateItem);

        NodeDragger.attach(this);

        val dragger = NodeDragger.attach(this);

        dragger.addOnPositionChangedListener(() -> {
            coordinateItem.setX(translator.reverseTranslateX(getLayoutX()));
            coordinateItem.setY(translator.reverseTranslateX(getLayoutY()));
            actionListener.coordinatesChanged(coordinateItems);
        });

        initializePopupMenu();

        this.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getClickCount() == 2){
                actionListener.goToSource(endPointTransition.completeSource);
                mouseEvent.consume();
            }
        });
    }

    void initializePopupMenu(){
        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItem = new MenuItem("EndPoint");
        contextMenu.getItems().add(menuItem);

        menuItem.setOnAction(event -> actionListener.goToSource(endPointTransition.completeSource));

        this.setOnContextMenuRequested(contextMenuEvent -> {
            contextMenu.show(this, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
            contextMenuEvent.consume();
        });
    }

}