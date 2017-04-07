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
class ProcessorNode extends VBox {

    final CoordinateTranslator coordinateTranslator;
    final ReactorGraphModel.Processor processor;
    final GraphViewer.ActionListener actionListener;

    public ProcessorNode(
            CoordinateTranslator translator,
            ReactorGraphModel.Processor processor,
            GraphViewer.ActionListener actionListener,
            List<GraphViewer.CoordinateItem> coordinateItems) {

        this.coordinateTranslator = translator;
        this.processor = processor;
        this.actionListener = actionListener;

        this.getStyleClass().add("processor");

        val x = processor.coordinates.x;
        val y = processor.coordinates.y;

        this.setLayoutX(translator.translateX(x));
        this.setLayoutY(translator.translateY(y));


        val nameLabel = new Label(processor.id);
        nameLabel.setFont(new Font(16.0));

        val methodNameLabel = new Label(processor.handlerTitle);
        methodNameLabel.setFont(new Font(14.0));

        this.getChildren().add(nameLabel);
        this.getChildren().add(methodNameLabel);

        this.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                actionListener.goToSource(processor.withHandlerSource);
            }
        });

        GraphViewer.CoordinateItem coordinateItem = new GraphViewer.CoordinateItem(
                GraphViewer.CoordinateItem.Type.PROCESSOR,
                processor.id,
                processor.coordinates.getX(),
                processor.coordinates.getY());
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
        processorMenuItem.setOnAction(event -> actionListener.goToSource(this.processor.withHandlerSource));
        contextMenu.getItems().add(processorMenuItem);

        processorContent.getChildren().add(new Text(this.processor.id));

        Optional.ofNullable(processor.processorDoc)
                .map(doc -> new Text(Arrays.stream(doc).collect(Collectors.joining("\n"))))
                .ifPresent(processorContent.getChildren()::add);


        val handlerMenuItem = new MenuItem();
        val handlerConent = new VBox();
        handlerMenuItem.setGraphic(handlerConent);
        handlerMenuItem.setOnAction(event -> actionListener.goToSource(this.processor.withHandlerSource));

        contextMenu.getItems().addAll(handlerMenuItem);

        Optional.ofNullable(processor.handlerTitle)
                .map(Text::new)
                .ifPresent(handlerConent.getChildren()::add);

        Optional.ofNullable(processor.handlerDoc)
                .map(doc -> new Text(Arrays.stream(doc).collect(Collectors.joining("\n"))))
                .ifPresent(handlerConent.getChildren()::add);

        return contextMenu;
    }

}