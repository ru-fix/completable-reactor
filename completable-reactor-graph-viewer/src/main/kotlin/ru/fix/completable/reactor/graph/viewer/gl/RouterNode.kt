package ru.fix.completable.reactor.graph.viewer.gl;

import javafx.geometry.Pos
import javafx.scene.control.ContextMenu
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import javafx.scene.layout.Pane
import javafx.scene.layout.VBox
import ru.fix.completable.reactor.api.gl.model.Router

/**
 * Created by swarmshine on 29.01.2017.
 */
class RouterNode(
        val translator: CoordinateTranslator,
        val router: Router,
        val actionListener: GraphViewer.ActionListener,
        val positionListener: PositionListener
) : VBox(), CentrableNode, BorderableNode {

    val routerNodeShape = Pane()

    var radius: Double = 0.0

    init {

        children.add(routerNodeShape)

        this.styleClass.add("routerNode");
        this.routerNodeShape.getStyleClass().add("router")
        this.radius = 13.0


        this.layoutX = translator.translateX(router.coordinates.x.toDouble())
        this.layoutY = translator.translateY(router.coordinates.y.toDouble())


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

        val dragger = NodeDragger.attach(this)

        dragger.addOnPositionChangedListener {
            router.coordinates.x = translator.reverseTranslateX(layoutX)
            router.coordinates.y = translator.reverseTranslateY(layoutY)
            positionListener.positionChanged()
        }

        initializePopupMenu()

        this.setOnMouseClicked { mouseEvent ->
            if (mouseEvent.clickCount == 2) {
                router.source?.let { actionListener.goToSource(it); }
            }
            mouseEvent.consume()
        }

        router.title?.let {
            val title = Label(it)
            this.children.add(title)
        }


        router.title?.let {
            val title = Label(it)
            this.getChildren().add(title)
        }
    }

    fun initializePopupMenu() {
        val contextMenu = ContextMenu()

        val textBuilder = StringBuilder()

        router.title?.let {
            textBuilder.append(it).append("\n");
        }

        val menuItem = MenuItem(
                if (textBuilder.isNotEmpty()) textBuilder.toString() else "MergePoint"
        )
        contextMenu.getItems().add(menuItem)


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