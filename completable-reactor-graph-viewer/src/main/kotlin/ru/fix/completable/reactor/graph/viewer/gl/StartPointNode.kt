package ru.fix.completable.reactor.graph.viewer.gl;

import javafx.scene.control.ContextMenu
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import javafx.scene.text.Text
import ru.fix.completable.reactor.model.StartPoint

/**
 * Created by swarmshine on 29.01.2017.
 */
class StartPointNode(
        val startPoint: StartPoint,
        val actionListener: GraphViewer.ActionListener
) : GraphNode(startPoint) {

    init {
        this.styleClass.add("startPoint")

        children.add(Label(startPoint.payloadType).apply {
            font = Font(16.0)
        })

        startPoint.title?.let {
            children.add(Label(it).apply { font = Font(14.0) })
        }

        this.setOnMouseClicked { event ->
            if (event.clickCount == 2) {
                fireGoToSourceEvent()
            }
        }

        initializePopupMenu()
    }

    fun initializePopupMenu() {
        val contextMenu = ContextMenu()
        val documentationMenuItem = MenuItem()
        documentationMenuItem.graphic = buildTooltipContent()
        contextMenu.items.add(documentationMenuItem)

        documentationMenuItem.setOnAction { event ->
            fireGoToSourceEvent()
        }

        this.setOnContextMenuRequested { contextMenuEvent ->
            contextMenu.show(this, contextMenuEvent.screenX, contextMenuEvent.screenY)
            contextMenuEvent.consume()
        }
    }

    private fun fireGoToSourceEvent() {
        startPoint.source?.let {
            actionListener.goToSource(it)
        }
    }

    private fun buildTooltipContent(): VBox {
        return VBox().apply {
            children.add(Text(startPoint.payloadType))
            startPoint.title?.let { children.add(Text(it)) }
            startPoint.doc?.let { children.add(Text(it)) }
        }
    }
}