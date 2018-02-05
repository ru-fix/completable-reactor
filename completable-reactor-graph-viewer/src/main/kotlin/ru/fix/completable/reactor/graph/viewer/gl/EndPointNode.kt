package ru.fix.completable.reactor.graph.viewer.gl

import javafx.scene.control.ContextMenu
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import javafx.scene.text.Font
import ru.fix.completable.reactor.model.Coordinates
import ru.fix.completable.reactor.model.DEFAULT_COORDINATES
import ru.fix.completable.reactor.model.EndPoint

/**
 * Created by swarmshine on 29.01.2017.
 */
class EndPointNode(
        private val translator: CoordinateTranslator,
        private val endPoint: EndPoint,
        private val actionListener: GraphViewer.ActionListener,
        private val positionListener: PositionListener) :
        GraphNode(endPoint) {

    init {

        this.styleClass.add("endPoint")

        val coordinates = endPoint.coordinates ?: DEFAULT_COORDINATES

        this.layoutX = translator.translateX(coordinates.x.toDouble())
        this.layoutY = translator.translateY(coordinates.y.toDouble())

        val nameLabel = Label("End")
        nameLabel.font = Font(16.0)

        this.children.add(nameLabel)

        this.setOnMouseClicked { mouseEvent ->
            if (mouseEvent.clickCount == 2) {
                endPoint.source?.let { actionListener.goToSource(it) }
            }
        }

        val dragger = NodeDragger.attach(this)

        dragger.addOnPositionChangedListener {
            endPoint.coordinates = Coordinates(
                    translator.reverseTranslateX(layoutX),
                    translator.reverseTranslateX(layoutY)
            )
            positionListener.positionChanged()
        }

        initializePopupMenu()

        this.setOnMouseClicked { mouseEvent ->
            if (mouseEvent.clickCount == 2) {
                endPoint.source?.let { actionListener.goToSource(it) }
                mouseEvent.consume()
            }
        }
    }

    private fun initializePopupMenu() {
        val contextMenu = ContextMenu()
        val menuItem = MenuItem("EndPoint")
        contextMenu.items.add(menuItem)

        menuItem.setOnAction { endPoint.source?.let { actionListener.goToSource(it) } }

        this.setOnContextMenuRequested { contextMenuEvent ->
            contextMenu.show(this, contextMenuEvent.screenX, contextMenuEvent.screenY)
            contextMenuEvent.consume()
        }
    }
}