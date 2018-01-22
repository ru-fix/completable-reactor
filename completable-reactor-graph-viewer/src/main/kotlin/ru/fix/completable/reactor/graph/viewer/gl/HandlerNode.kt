package ru.fix.completable.reactor.graph.viewer.gl;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import ru.fix.completable.reactor.api.gl.model.*

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by swarmshine on 29.01.2017.
 */
class HandlerNode(
        val translator: CoordinateTranslator,
        val handler: Handler,
        val actionListener: GraphViewer.ActionListener,
        val positionListener: PositionListener) : VBox() {

    init {

        this.styleClass.add("handler")

        val x = handler.coordinates.x
        val y = handler.coordinates.y

        layoutX = translator.translateX(x.toDouble())
        layoutY = translator.translateY(y.toDouble())

        val nameLabel = Label(handler.name)
        nameLabel.font = Font(16.0)

        val methodNameLabel = Label(handler.title)
        methodNameLabel.setFont(Font(14.0))

        this.getChildren().add(nameLabel)
        this.getChildren().add(methodNameLabel)

        this.setOnMouseClicked { event ->
            if (event.clickCount == 2) {
                actionListener.goToSource(handler.source)
            }
        }

        val dragger = NodeDragger.attach(this)

        dragger.addOnPositionChangedListener {
            handler.coordinates.x = translator.reverseTranslateX(getLayoutX())
            handler.coordinates.y = translator.reverseTranslateY(getLayoutY())

            positionListener.positionChanged()
        }

        initializePopupMenu()
    }

    fun initializePopupMenu() {
        val contextMenu = buildTooltipContent()

        this.setOnContextMenuRequested { contextMenuEvent ->
            contextMenu.show(this, contextMenuEvent.screenX, contextMenuEvent.screenY)
            contextMenuEvent.consume()
        }
    }

    fun buildTooltipContent(): ContextMenu {

        val contextMenu = ContextMenu()
        val processorMenuItem = MenuItem()

        val processorContent = VBox()
        processorMenuItem.setGraphic(processorContent);
        processorMenuItem.setOnAction { event ->
            handler.source?.let { actionListener.goToSource(it) }
        }
        contextMenu.items.add(processorMenuItem)

        processorContent.children.apply{
            add(Text(handler.name))
        }

        val handlerMenuItem = MenuItem()
        val handlerConent = VBox ()
        handlerMenuItem.setGraphic(handlerConent);
        handlerMenuItem.setOnAction { event -> actionListener.goToSource(handler.source) }

        contextMenu.getItems().addAll(handlerMenuItem)

        handler.title?.let{handlerConent.children.add(Text(it))}
        return contextMenu
    }
}