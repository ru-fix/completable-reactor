package ru.fix.completable.reactor.graph.viewer

import javafx.event.EventHandler
import javafx.geometry.Point2D
import javafx.scene.Group
import javafx.scene.Node
import javafx.scene.control.ContextMenu
import javafx.scene.control.Label
import javafx.scene.control.MenuItem
import javafx.scene.input.ContextMenuEvent
import javafx.scene.layout.VBox
import javafx.scene.shape.Line
import javafx.scene.shape.Polygon
import javafx.scene.text.Text
import javafx.scene.transform.Rotate
import ru.fix.completable.reactor.model.Transition
import java.util.*

/**
 * @author Kamil Asfandiyarov
 */
class TransitionLine(
        val world: Node,
        val fromNode: Node,
        val toNode: Node,
        val transition: Transition?,
        val actionListener: GraphViewer.ActionListener
) : Group() {


    private val pointer: Polygon

    private val fromCenter: NodeCenter
    private val toCenter: NodeCenter
    private val line: Line
    private val labelsPane = VBox()
    private val menuArea: Polygon

    init {
        this.getStyleClass().add("transitionLine")

        line = Line()
        line.getStyleClass().add("pointerLine")

        fromCenter = NodeCenter(world, fromNode)
        toCenter = NodeCenter(world, toNode)

        fromCenter.centerX.addListener { observable, oldValue, newValue -> updateLine() }
        fromCenter.centerY.addListener { observable, oldValue, newValue -> updateLine() }
        toCenter.centerX.addListener { observable, oldValue, newValue -> updateLine() }
        toCenter.centerY.addListener { observable, oldValue, newValue -> updateLine() }

        pointer = Polygon()
        pointer.points.addAll(
                0.0, 0.0,
                5.0, -15.0,
                -5.0, -15.0
        );
        pointer.styleClass.add("pointer");
        this.children.add(pointer)

        this.children.add(line)

        menuArea = Polygon();
        menuArea.opacity = 0.0;
        this.children.add(menuArea)

        transition?.let { initializeTransition(it) }

        initializePopupMenu()
    }

    private fun initializeTransition(transitionItem: Transition) {
        if (transitionItem.isOnAny) {
            val anyStatusLabel = Label("<Any>")
            labelsPane.getChildren().add(anyStatusLabel)

            if (transitionItem.onAnySource != null) {
                anyStatusLabel.setOnMouseClicked { mouseEvent ->
                    if (mouseEvent.getClickCount() == 2) {
                        transitionItem.onAnySource?.let { actionListener.goToSource(it) }
                    }
                }
                menuArea.setOnMouseClicked { mouseEvent ->
                    if (mouseEvent.getClickCount() == 2) {
                        transitionItem.onAnySource?.let { actionListener.goToSource(it) }
                    }
                }
            }

        } else if (transitionItem.isOnElse) {
            val statusLabel = Label("<Else>")
            labelsPane.getChildren().add(statusLabel)

            if (transitionItem.onAnySource != null) {
                statusLabel.setOnMouseClicked { mouseEvent ->
                    if (mouseEvent.clickCount == 2) {
                        transitionItem.onAnySource?.let { actionListener.goToSource(it) }
                    }
                }
                menuArea.setOnMouseClicked { mouseEvent ->
                    if (mouseEvent.clickCount == 2) {
                        transitionItem.onAnySource?.let { actionListener.goToSource(it) }
                    }
                }
            }
        } else {
            transitionItem.mergeStatuses?.forEach { status ->

                val statusLabel = Label(status)
                labelsPane.getChildren().add(statusLabel)

                transitionItem.onStatusSource?.get(status)?.let { source ->

                    statusLabel.setOnMouseClicked { mouseEvent ->
                        if (mouseEvent.getClickCount() == 2) {

                            actionListener.goToSource(source)

                        }
                    }
                }
            }
        }

        line.layoutBoundsProperty().addListener { observable, oldValue, newValue ->
            val x = (newValue.getMinX() + newValue.getMaxX()) / 2;
            val y = (newValue.getMinY() + newValue.getMaxY()) / 2;

            labelsPane.setLayoutX(x);
            labelsPane.setLayoutY(y);
        }

        this.getChildren().add(labelsPane)
    }

    fun updateLine() {
        //Arrow
        val from = Point2D(
                fromCenter.centerX.doubleValue(),
                fromCenter.centerY.doubleValue())
        val to = Point2D(
                toCenter.centerX.doubleValue(),
                toCenter.centerY.doubleValue());

        var minx: Double
        var miny: Double
        var maxx: Double
        var maxy: Double

        if (toNode is BorderableNode) {
            minx = toNode.borderableX
            miny = toNode.borderableY;
            maxx = toNode.borderableX + toNode.borderableWidth;
            maxy = toNode.borderableY + toNode.borderableHeight;

        } else {
            minx = toNode.getLayoutX();
            miny = toNode.getLayoutY();
            maxx = toNode.getLayoutX() + toNode.getLayoutBounds().getWidth();
            maxy = toNode.getLayoutY() + toNode.getLayoutBounds().getHeight();
        }

        val toNodeBorders = arrayOf(
                arrayOf(Point2D(minx, miny), Point2D(maxx, miny)),
                arrayOf(Point2D(minx, miny), Point2D(minx, maxy)),
                arrayOf(Point2D(maxx, maxy), Point2D(maxx, miny)),
                arrayOf(Point2D(maxx, maxy), Point2D(minx, maxy))
        )

        var toNodeBorderIntersection = Point2D(toCenter.centerX.get(), toCenter.centerY.get())

        for (i in 0..3) {
            val intersectionPoint = intersection2(
                    from,
                    to,
                    toNodeBorders[i][0],
                    toNodeBorders[i][1])
            if (intersectionPoint != null) {
                toNodeBorderIntersection = intersectionPoint
                break
            }
        }

        val x0 = toNodeBorderIntersection.getX();
        val y0 = toNodeBorderIntersection.getY();


        val tan = (to.getX() - from.getX()) / (to.getY() - from.getY());
        val angleRad = Math.atan(tan);
        val angleDeg = Math.toDegrees(angleRad);

        var rotationDeg = (360 - angleDeg) % 360;

        if (to.getY() - from.getY() < 0) {
            rotationDeg = (rotationDeg + 180) % 360;
        }

        pointer.getTransforms().clear()
        pointer.getTransforms().add(Rotate(rotationDeg, 0.0, 0.0))

        pointer.setLayoutX(x0)
        pointer.setLayoutY(y0)


        //Line
        line.startX = fromCenter.centerX.get()
        line.startY = fromCenter.centerY.get()
        line.endX = toCenter.centerX.get()
        line.endY = toCenter.centerY.get()

        //menu area
        menuArea.getPoints().clear()
        val menuAreaWidth = 30.0

        val menu_dx = menuAreaWidth / 2 * Math.cos(angleRad)
        val menu_dy = menuAreaWidth / 2 * Math.sin(angleRad)

        menuArea.getPoints().addAll(
                line.getStartX() + menu_dx, line.getStartY() - menu_dy,
                line.getStartX() - menu_dx, line.getStartY() + menu_dy,
                line.getEndX() - menu_dx, line.getEndY() + menu_dy,
                line.getEndX() + menu_dx, line.getEndY() - menu_dy
        )
    }


    fun intersection2(firstStart: Point2D, firstEnd: Point2D, secondStart: Point2D, secondEnd: Point2D): Point2D? {
        val p0_x = firstStart.x
        val p0_y = firstStart.y
        val p1_x = firstEnd.x
        val p1_y = firstEnd.y
        val p2_x = secondStart.x
        val p2_y = secondStart.y
        val p3_x = secondEnd.x
        val p3_y = secondEnd.y

        val s10_x = p1_x - p0_x
        val s10_y = p1_y - p0_y
        val s32_x = p3_x - p2_x
        val s32_y = p3_y - p2_y

        val denom = s10_x * s32_y - s32_x * s10_y

        if (denom.toRawBits() == 0L) {
            return null // Collinear
        }

        val denomPositive = denom > 0;

        val s02_x = p0_x - p2_x;
        val s02_y = p0_y - p2_y;
        val s_numer = s10_x * s02_y - s10_y * s02_x;
        if ((s_numer < 0) == denomPositive) {
            return null // No collision
        }

        val t_numer = s32_x * s02_y - s32_y * s02_x;
        if ((t_numer < 0) == denomPositive) {
            return null // No collision
        }

        if (((s_numer > denom) == denomPositive) || ((t_numer > denom) == denomPositive)) {
            return null // No collision
        }

        // Collision detected
        val i_x: Double
        val i_y: Double

        val t = t_numer / denom;
        i_x = p0_x + (t * s10_x)

        i_y = p0_y + (t * s10_y)

        return Point2D(i_x, i_y)
    }

    fun initializePopupMenu() {

        val contextMenu = buildTooltipContent()

        val contextMenuHandler = EventHandler<ContextMenuEvent> { contextMenuEvent ->
            contextMenu.show(line, contextMenuEvent.screenX, contextMenuEvent.screenY)
            contextMenuEvent.consume()
        }

        labelsPane.onContextMenuRequested = contextMenuHandler
        pointer.onContextMenuRequested = contextMenuHandler
        line.onContextMenuRequested = contextMenuHandler
        menuArea.onContextMenuRequested = contextMenuHandler
    }

    fun buildTooltipContent(): ContextMenu {

        //menu controls
        val contextMenu = ContextMenu()

        if (transition != null) {

            //documentation
            val mergeStatusDocumentation = HashMap<String, String>()

            transition.mergeStatuses.forEach { status -> mergeStatusDocumentation[status] = "" }
            transition.transitionDocs.forEach { doc -> mergeStatusDocumentation[doc.mergeStatus] = doc.docs }


            for ((status, statusDoc) in mergeStatusDocumentation) {

                val content = VBox()
                val documentationMenuItem = MenuItem().apply {
                    graphic = content
                }

                contextMenu.items.add(documentationMenuItem)

                transition.onStatusSource?.get(status)?.let { source ->
                    documentationMenuItem.setOnAction { _ -> actionListener.goToSource(source) }
                }

                //menu item content
                content.children.add(Text(status))
                content.children.add(Text(statusDoc))
            }

            if (transition.isOnElse) {
                val onEnyMenuItem = MenuItem()

                val content = VBox()
                onEnyMenuItem.graphic = content
                content.children.add(Text("<Else>"))
                content.children.add(Text("Transition is activated if other are not"))
                contextMenu.items.add(onEnyMenuItem)

                val source = transition.onAnySource
                if (source != null) {
                    onEnyMenuItem.setOnAction { _ -> actionListener.goToSource(source) }
                }
            }

            if (transition.isOnAny) {
                val onAnyMenuItem = MenuItem()

                val content = VBox()
                onAnyMenuItem.graphic = content
                content.children.add(Text("<Any>"))
                content.children.add(Text("Transition activated by any status."))
                contextMenu.items.add(onAnyMenuItem)

                val source = transition.onAnySource
                if (source != null) {
                    onAnyMenuItem.setOnAction { _ -> actionListener.goToSource(source) }
                }
            }
        }

        return contextMenu
    }
}
