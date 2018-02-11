package ru.fix.completable.reactor.graph.viewer.gl;

import javafx.geometry.Point2D
import javafx.scene.Cursor
import mu.KotlinLogging
import ru.fix.completable.reactor.model.Coordinates
import ru.fix.completable.reactor.model.DEFAULT_COORDINATES
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference


interface NodeDraggerListener {
    fun modelChanging()
    fun modelChanged()
}

private val log = KotlinLogging.logger { }

/**
 * @author Kamil Asfandiyarov
 */
class NodeDragger private constructor(val node: GraphNode, val draggerListener: NodeDraggerListener) {

    companion object {
        @JvmStatic
        fun attach(node: GraphNode, draggerListener: NodeDraggerListener) {
            NodeDragger(node, draggerListener)
        }
    }

    init {

        //Dragging

        val draggingState = AtomicBoolean()
        val startDragMousePoint = AtomicReference<Point2D>()
        val startDragNodePosition = AtomicReference<Point2D>()

        node.setOnMouseMoved { event ->
            if (event.isControlDown()) {
                node.setCursor(Cursor.OPEN_HAND);
            } else {
                node.setCursor(Cursor.DEFAULT);
            }
        }

        node.setOnMousePressed { event ->
            if (event.isControlDown()) {
                node.cursor = Cursor.CLOSED_HAND
                draggingState.set(true)
                startDragMousePoint.set(Point2D(event.sceneX, event.sceneY))
                startDragNodePosition.set(Point2D(node.layoutX, node.layoutY))
            } else {
                node.cursor = Cursor.DEFAULT
                if (draggingState.compareAndSet(true, false)) {
                    fireModelChanged()
                }
            }
        }

        node.setOnMouseReleased { event ->
            if (event.isControlDown) {
                if (draggingState.compareAndSet(true, false)) {
                    fireModelChanged()
                }
                node.cursor = Cursor.OPEN_HAND
            } else {
                if (draggingState.compareAndSet(true, false)) {
                    fireModelChanged()
                }
                node.cursor = Cursor.DEFAULT
            }
        }

        node.setOnMouseDragged { event ->
            if (event.isControlDown) {
                if (draggingState.get()) {
                    event.consume()

                    val nodePosition = startDragNodePosition.get()
                    val mousePosition = startDragMousePoint.get()

                    if (nodePosition == null || mousePosition == null) {
                        return@setOnMouseDragged
                    }


                    val newCoordinateX = (nodePosition.x + event.sceneX - mousePosition.x)

                    log.info { "${nodePosition.x} + ${event.sceneX} - ${mousePosition.x} = $newCoordinateX" }

                    val newCoordinateY = (nodePosition.y + event.sceneY - mousePosition.y)


                    node.figure.coordinates = Coordinates(newCoordinateX.toInt(), newCoordinateY.toInt())
                    fireModelChanging()

                } else {
                    draggingState.set(true)
                    node.cursor = Cursor.CLOSED_HAND;
                }
            } else {
                if (draggingState.compareAndSet(true, false)) {
                    fireModelChanged()
                }
                node.cursor = Cursor.DEFAULT
            }
        }

        node.setOnMouseEntered { event ->
            if (event.isControlDown) {
                node.cursor = Cursor.OPEN_HAND
            } else {
                node.cursor = Cursor.DEFAULT
            }
        }
    }

    private fun fireModelChanged() {
        draggerListener.modelChanged()
    }

    private fun fireModelChanging() {
        draggerListener.modelChanging()
    }


}
