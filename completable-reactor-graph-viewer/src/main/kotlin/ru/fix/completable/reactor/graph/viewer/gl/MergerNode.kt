package ru.fix.completable.reactor.graph.viewer.gl;

import javafx.geometry.Pos
import javafx.scene.control.ContextMenu
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import javafx.scene.layout.Pane
import ru.fix.completable.reactor.model.Merger

/**
 * Created by swarmshine on 29.01.2017.
 */
class MergerNode(
        val merger: Merger,
        val actionListener: GraphViewer.ActionListener
) : GraphNode(merger), CentrableNode, BorderableNode {

    val mergePointShape = Pane()

    var radius: Double = 0.0

    init {
        children.add(mergePointShape)

        styleClass.add("mergePointNode")
        mergePointShape.styleClass.add("merger")
        this.radius = 14.0


        this.mergePointShape.prefWidth = radius * 2;
        this.mergePointShape.maxWidth = radius * 2;
        this.mergePointShape.prefHeight = radius * 2;
        this.mergePointShape.maxHeight = radius * 2;
        this.alignment = Pos.CENTER

        this.setOnMouseClicked { mouseEvent ->
            if (mouseEvent.getClickCount() == 2) {
                merger.source?.let { actionListener.goToSource(it) }
            }
        }


        initializePopupMenu()

        this.setOnMouseClicked { mouseEvent ->
            if (mouseEvent.clickCount == 2) {
                merger.source?.let { actionListener.goToSource(it); }
            }
            mouseEvent.consume()
        }

        merger.title?.let {
            this.children.add(Label(it))
        }
    }

    fun initializePopupMenu(): ContextMenu {
        val contextMenu = ContextMenu()
        val textBuilder = StringBuilder();

        if (merger.title != null) {
            textBuilder.append(merger.title).append("\n")
        }

        val menuItem = MenuItem(
                if (textBuilder.isNotEmpty()) textBuilder.toString() else "Merger"
        )

        contextMenu.items.add(menuItem)

        menuItem.setOnAction { event -> merger.source?.let { actionListener.goToSource(it) } }

        this.setOnContextMenuRequested { contextMenuEvent ->
            contextMenu.show(this, contextMenuEvent.screenX, contextMenuEvent.screenY)
            contextMenuEvent.consume()
        }

        return contextMenu
    }


    override val centerX: Double
        get() {
            val bounds = this.mergePointShape.boundsInParent
            return this.layoutX + bounds.minX + bounds.width / 2
        }

    override val centerY: Double
        get() {
            val bounds = this.mergePointShape.getBoundsInParent()
            return this.getLayoutY() + bounds.getMinY() + bounds.getHeight() / 2
        }

    override val borderableX: Double
        get() {
            return this.getLayoutX() + mergePointShape.getLayoutX();
        }

    override val borderableY: Double
        get() {
            return this.getLayoutY() + mergePointShape.getLayoutY();
        }

    override val borderableWidth: Double
        get() {
            return mergePointShape.getPrefWidth()
        }

    override val borderableHeight: Double
        get() {
            return mergePointShape.getPrefHeight()
        }
}