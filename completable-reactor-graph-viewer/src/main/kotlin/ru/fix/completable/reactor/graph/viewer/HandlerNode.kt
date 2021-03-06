package ru.fix.completable.reactor.graph.viewer;

import javafx.scene.control.ContextMenu
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.Text
import ru.fix.completable.reactor.model.Handler

/**
 * Created by swarmshine on 29.01.2017.
 */
class HandlerNode(
        val handler: Handler,
        val actionListener: GraphViewer.ActionListener) :
        GraphNode(handler) {

    init {

        this.styleClass.add("handler")

        val nameLabel = Label(handler.name)
        nameLabel.font = Font(16.0)

        val methodNameLabel = Label(handler.title)
        methodNameLabel.setFont(Font(14.0))

        this.getChildren().add(nameLabel)
        this.getChildren().add(methodNameLabel)

        this.setOnMouseClicked { event ->
            if (event.clickCount == 2) {
                handler.source?.let { actionListener.goToSource(it) }
            }
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

        processorContent.children.apply {
            add(Text(handler.name))
            handler.title?.let{add(Text(it))}
            handler.doc?.let{add(Text(it))}
        }

        return contextMenu
    }
}