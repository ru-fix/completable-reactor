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
        val translator: CoordinateTranslator,
        val startPoint: StartPoint,
        val actionListener: GraphViewer.ActionListener,
        val positionListener: PositionListener
) : VBox() {

    init {
        this.styleClass.add("startPoint")

        val x = startPoint.coordinates.x
        val y = startPoint.coordinates.y

        this.layoutX = translator.translateX(x)
        this.layoutY = translator.translateY(y)


        val nameLabel = Label(startPoint.payloadType)
        nameLabel.font = Font(16.0)

        this.children.add(nameLabel)

        this.setOnMouseClicked { event ->
            if (event.clickCount == 2) {
                fireGoToSourceEvent()
            }
        }

        val dragger = NodeDragger.attach(this);

        dragger.addOnPositionChangedListener {
            startPoint.coordinates.x = translator.reverseTranslateX(layoutX)
            startPoint.coordinates.y = translator.reverseTranslateX(layoutY)
            positionListener.positionChanged()
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

    fun fireGoToSourceEvent() {
        startPoint.source?.let {
            actionListener.goToSource(it)
        }
    }

    fun buildTooltipContent(): VBox {
        return VBox().apply {
            children.add(Text(startPoint.payloadType))
        }
    }
}