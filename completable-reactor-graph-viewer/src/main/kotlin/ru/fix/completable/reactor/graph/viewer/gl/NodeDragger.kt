package ru.fix.completable.reactor.graph.viewer.gl;

import javafx.geometry.Point2D
import javafx.scene.Cursor
import javafx.scene.Node
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

/**
 * @author Kamil Asfandiyarov
 */
class NodeDragger private constructor(val node: Node) {

    private val dragDoneHandlers = CopyOnWriteArrayList<PositionChangeListener>()

    companion object {
        fun attach(node: Node): NodeDragger {
            return NodeDragger(node)
        }
    }

    init {

        //Dragging

        val draggingState = AtomicBoolean();
        val startDragMousePoint = AtomicReference<Point2D>();
        val startDragNodePosition = AtomicReference<Point2D>();

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
                startDragMousePoint.set(Point2D(event.getSceneX(), event.getSceneY()))
                startDragNodePosition.set(Point2D(node.getLayoutX(), node.getLayoutY()))
            } else {
                node.cursor = Cursor.DEFAULT
                if (draggingState.compareAndSet(true, false)) {
                    firePositionChangedEvent()
                }
            }
        }

        node.setOnMouseReleased { event ->
            if (event.isControlDown) {
                if (draggingState.compareAndSet(true, false)) {
                    firePositionChangedEvent()
                }
                node.cursor = Cursor.OPEN_HAND
            } else {
                if (draggingState.compareAndSet(true, false)) {
                    firePositionChangedEvent()
                }
                node.cursor = Cursor.DEFAULT
            }
        }

        node.setOnMouseDragged { event ->
            if (event.isControlDown) {
                if (draggingState.get()) {
                    event.consume();

                    val nodePosition = startDragNodePosition.get()
                    val mousePosition = startDragMousePoint.get()

                    if (nodePosition == null || mousePosition == null || event == null) {
                        return@setOnMouseDragged
                    }

                    node.layoutX = (nodePosition.x
                            + event.sceneX
                            - mousePosition.x)

                    node.layoutY = (nodePosition.y
                            + event.sceneY
                            - mousePosition.y)

                } else {
                    draggingState.set(true)
                    node.cursor = Cursor.CLOSED_HAND;
                }
            } else {
                if (draggingState.compareAndSet(true, false)) {
                    firePositionChangedEvent()
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

    private fun firePositionChangedEvent() {
        dragDoneHandlers.forEach { it() }
    }


    fun addOnPositionChangedListener(positionChangedListener: ()-> Unit) {
        dragDoneHandlers.add(positionChangedListener)
    }
}
