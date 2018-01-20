package ru.fix.completable.reactor.graph.viewer.gl;

import javafx.scene.control.ContextMenu
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import ru.fix.completable.reactor.api.gl.model.Subgraph
import java.util.*

/**
 * Created by swarmshine on 29.01.2017.
 */
class SubgraphNode (
        val coordinateTranslator: CoordinateTranslator,
        val subgraph: Subgraph,
        val actionListener: GraphViewer.ActionListener,
        coordinateItems: List<GraphViewer.CoordinateItem>
) : VBox() {

    init {
        this.styleClass.add("subgraph")

        val x = subgraph.coordinates.x
        val y = subgraph.coordinates.y

        this.layoutX = coordinateTranslator.translateX(x)
        this.layoutY = coordinateTranslator.translateY(y)


        val nameLabel = Label(subgraph.name)
        nameLabel.font = Font(16.0)

        val payloadClassLabel = Label(subgraph.payloadClass)
        payloadClassLabel.font = Font(14.0)

        this.children.add(nameLabel)
        this.children.add(payloadClassLabel)

        this.setOnMouseClicked { event ->
            if (event.clickCount == 2) {
                actionListener.goToSubgraph(subgraph.payloadClass)
            }
        }

        GraphViewer.CoordinateItem coordinateItem = new GraphViewer.CoordinateItem(
                GraphViewer.CoordinateItem.Type.PROCESSOR,
                subgraph.getIdentity(),
                subgraph.coordinates.getX(),
                subgraph.coordinates.getY());
        coordinateItems.add(coordinateItem);

        val dragger = NodeDragger.attach(this);

        dragger.addOnPositionChangedListener(()->{

            coordinateItem.setX(translator.reverseTranslateX(getLayoutX()));
            coordinateItem.setY(translator.reverseTranslateY(getLayoutY()));

            actionListener.coordinatesChanged(coordinateItems);
        });


        initializePopupMenu();
    }

    void initializePopupMenu()
    {
        ContextMenu contextMenu = buildTooltipContent ();

        this.setOnContextMenuRequested(contextMenuEvent -> {
        contextMenu.show(this, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
        contextMenuEvent.consume();
    });
    }

    ContextMenu buildTooltipContent()
    {

        ContextMenu contextMenu = new ContextMenu();
        MenuItem processorMenuItem = new MenuItem();

        VBox processorContent = new VBox();
        processorMenuItem.setGraphic(processorContent);
        processorMenuItem.setOnAction(event -> actionListener.goToSource(this.subgraph.getBuildSource()));
        contextMenu.getItems().add(processorMenuItem);

        processorContent.getChildren().add(new Text (serialize(this.subgraph.getIdentity())));

        Optional.ofNullable(subgraph.subgraphDoc)
                .map(doc -> new Text(Arrays.stream(doc).collect(Collectors.joining("\n"))))
        .ifPresent(processorContent.getChildren()::add);

        return contextMenu;
    }

}