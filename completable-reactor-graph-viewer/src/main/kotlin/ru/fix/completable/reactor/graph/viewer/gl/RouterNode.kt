package ru.fix.completable.reactor.graph.viewer.gl;

import javafx.geometry.Pos
import javafx.scene.control.ContextMenu
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import javafx.scene.layout.Pane
import javafx.scene.text.Font
import ru.fix.completable.reactor.model.Router

/**
 * Created by swarmshine on 29.01.2017.
 */
class RouterNode(
        val router: Router,
        val actionListener: GraphViewer.ActionListener
) : GraphNode(router), CentrableNode, BorderableNode {

    val routerNodeShape = Pane()

    var radius: Double = 18.0

    init {

        children.add(routerNodeShape)

        this.styleClass.add("routerNode")
        this.routerNodeShape.getStyleClass().add("router")



        this.routerNodeShape.prefWidth = radius * 2;
        this.routerNodeShape.maxWidth = radius * 2;
        this.routerNodeShape.prefHeight = radius * 2;
        this.routerNodeShape.maxHeight = radius * 2;
        this.alignment = Pos.CENTER

        this.setOnMouseClicked { mouseEvent ->
            if (mouseEvent.getClickCount() == 2) {
                router.source?.let { actionListener.goToSource(it) }
            }
        }

        initializePopupMenu()

        this.setOnMouseClicked { mouseEvent ->
            if (mouseEvent.clickCount == 2) {
                router.source?.let { actionListener.goToSource(it); }
            }
            mouseEvent.consume()
        }

        this.children.add(Label(router.name).apply {
            font = Font(16.0)
        })

        router.title?.let {
            this.children.add(Label(it).apply {
                font = Font(14.0)
            })
        }
    }

    fun initializePopupMenu() {
        val contextMenu = ContextMenu()

        val textBuilder = StringBuilder()

        (router.title ?: router.name).let {
            textBuilder.appendln(it)
        }

        router.doc?.let {
            textBuilder.appendln(it)
        }

        val menuItem = MenuItem(textBuilder.toString())

        contextMenu.items.add(menuItem)


        menuItem.setOnAction { event -> router.source?.let { actionListener.goToSource(it) } }


        this.setOnContextMenuRequested { contextMenuEvent ->
            contextMenu.show(this, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
            contextMenuEvent.consume()
        }
    }

    override val centerX: Double
        get() {
            val bounds = this.routerNodeShape.boundsInParent
            return this.layoutX + bounds.minX + bounds.width / 2
        }

    override val centerY: Double
        get() {
            val bounds = this.routerNodeShape.getBoundsInParent()
            return this.getLayoutY() + bounds.getMinY() + bounds.getHeight() / 2
        }

    override val borderableX: Double
        get() {
            return this.getLayoutX() + routerNodeShape.getLayoutX();
        }

    override val borderableY: Double
        get() {
            return this.getLayoutY() + routerNodeShape.getLayoutY();
        }

    override val borderableWidth: Double
        get() {
            return routerNodeShape.getPrefWidth()
        }

    override val borderableHeight: Double
        get() {
            return routerNodeShape.getPrefHeight()
        }
}