package ru.fix.completable.reactor.graph.viewer;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lombok.val;
import ru.fix.completable.reactor.api.ReactorGraphModel;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by swarmshine on 29.01.2017.
 */
class SubgraphNode extends VBox {

    final CoordinateTranslator coordinateTranslator;
    final ReactorGraphModel.Subgraph subgraph;
    final GraphViewer.ActionListener actionListener;

    public SubgraphNode(
            CoordinateTranslator translator,
            ReactorGraphModel.Subgraph subgraph,
            GraphViewer.ActionListener actionListener,
            List<GraphViewer.CoordinateItem> coordinateItems) {

        this.coordinateTranslator = translator;
        this.subgraph = subgraph;
        this.actionListener = actionListener;

        this.getStyleClass().add("subgraph");

        val x = subgraph.coordinates.x;
        val y = subgraph.coordinates.y;

        this.setLayoutX(translator.translateX(x));
        this.setLayoutY(translator.translateY(y));


        val nameLabel = new Label(serialize(subgraph.getIdentity()));
        nameLabel.setFont(new Font(16.0));

        val methodNameLabel = new Label(subgraph.getSubgraphTitle());
        methodNameLabel.setFont(new Font(14.0));

        this.getChildren().add(nameLabel);
        this.getChildren().add(methodNameLabel);

        this.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                actionListener.goToSubgraph(subgraph.getIdentity());
            }
        });

        GraphViewer.CoordinateItem coordinateItem = new GraphViewer.CoordinateItem(
                GraphViewer.CoordinateItem.Type.PROCESSOR,
                subgraph.getIdentity(),
                subgraph.coordinates.getX(),
                subgraph.coordinates.getY());
        coordinateItems.add(coordinateItem);

        val dragger = NodeDragger.attach(this);

        dragger.addOnPositionChangedListener(()->{

            coordinateItem.setX( translator.reverseTranslateX(getLayoutX()) );
            coordinateItem.setY( translator.reverseTranslateY(getLayoutY()) );

            actionListener.coordinatesChanged(coordinateItems);
        });


        initializePopupMenu();
    }

    void initializePopupMenu(){
        ContextMenu contextMenu = buildTooltipContent();

        this.setOnContextMenuRequested(contextMenuEvent -> {
            contextMenu.show(this, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
            contextMenuEvent.consume();
        });
    }

    ContextMenu buildTooltipContent(){

        ContextMenu contextMenu = new ContextMenu();
        MenuItem processorMenuItem = new MenuItem();

        VBox processorContent = new VBox();
        processorMenuItem.setGraphic(processorContent);
        processorMenuItem.setOnAction(event -> actionListener.goToSource(this.subgraph.getBuildSource()));
        contextMenu.getItems().add(processorMenuItem);

        processorContent.getChildren().add(new Text(serialize(this.subgraph.getIdentity())));

        Optional.ofNullable(subgraph.subgraphDoc)
                .map(doc -> new Text(Arrays.stream(doc).collect(Collectors.joining("\n"))))
                .ifPresent(processorContent.getChildren()::add);

        return contextMenu;
    }

    String serialize(ReactorGraphModel.Identity identity){
        return identity.getClassName() + "@" + identity.getId();
    }
}