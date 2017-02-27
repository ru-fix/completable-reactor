package ru.fix.cpapsm.completable.reactor.graph.viewer;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lombok.val;
import ru.fix.cpapsm.completable.reactor.api.ReactorGraphModel;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by swarmshine on 29.01.2017.
 */
class StartPointNode extends VBox {

    CoordinateTranslator coordinateTranslator;
    ReactorGraphModel model;
    GraphViewer.ActionListener actionListener;

    public StartPointNode(CoordinateTranslator translator,
                          ReactorGraphModel model,
                          GraphViewer.ActionListener actionListener,
                          List<GraphViewer.CoordinateItem> coordinateItems) {
        this.coordinateTranslator = translator;
        this.model = model;
        this.actionListener = actionListener;

        this.getStyleClass().add("startPoint");

        val x = model.startPoint.coordinates.x;
        val y = model.startPoint.coordinates.y;

        this.setLayoutX(translator.translateX(x));
        this.setLayoutY(translator.translateY(y));


        val nameLabel = new Label(model.payload.payloadName);
        nameLabel.setFont(new Font(16.0));

        this.getChildren().add(nameLabel);

        this.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
//                actionListener.goToSource(model.payload.payload)
            }
        });

        GraphViewer.CoordinateItem coordinateItem = new GraphViewer.CoordinateItem(
                GraphViewer.CoordinateItem.Type.START_POINT,
                null,
                model.startPoint.coordinates.x,
                model.startPoint.coordinates.y);
        coordinateItems.add(coordinateItem);

        val dragger = NodeDragger.attach(this);

        dragger.addOnPositionChangedListener(() -> {
            coordinateItem.setX(translator.reverseTranslateX(getLayoutX()));
            coordinateItem.setY(translator.reverseTranslateX(getLayoutY()));

            actionListener.coordinatesChanged(coordinateItems);
        });
        initializePopupMenu();
    }

    void initializePopupMenu(){
        ContextMenu contextMenu = new ContextMenu();
        MenuItem documentationMenuItem = new MenuItem();
        documentationMenuItem.setGraphic(buildTooltipContent());
        contextMenu.getItems().add(documentationMenuItem);

        this.setOnContextMenuRequested(contextMenuEvent -> {
            contextMenu.show(this, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
            contextMenuEvent.consume();
        });
    }

    VBox buildTooltipContent(){
        VBox content = new VBox();

        content.getChildren().add(new Text(this.model.payload.payloadName));
        content.getChildren().add(new Text(this.model.payload.payloadClass));

        Optional.ofNullable(this.model.payload.payloadDoc)
                .map(doc -> new Text(Arrays.stream(doc).collect(Collectors.joining("\n"))))
                .ifPresent(content.getChildren()::add);

        return content;
    }

}