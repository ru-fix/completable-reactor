package ru.fix.completable.reactor.graph.viewer.gl;

import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import lombok.val;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author Kamil Asfandiyarov
 */

public class NodeDragger {
    final Node node;
    final List<PositionChangeListener> dragDoneHandlers = new CopyOnWriteArrayList<>();

    public static NodeDragger attach(Node node) {
        return new NodeDragger(node);
    }

    private NodeDragger(Node node) {
        this.node = node;

        //Dragging

        val draggingState = new AtomicBoolean();
        val startDragMousePoint = new AtomicReference<Point2D>();
        val startDragNodePosition = new AtomicReference<Point2D>();

        node.setOnMouseMoved(event -> {
            if (event.isControlDown()) {
                node.setCursor(Cursor.OPEN_HAND);
            } else {
                node.setCursor(Cursor.DEFAULT);
            }
        });

        node.setOnMousePressed(event -> {
            if (event.isControlDown()) {
                node.setCursor(Cursor.CLOSED_HAND);
                draggingState.set(true);
                startDragMousePoint.set(new Point2D(event.getSceneX(), event.getSceneY()));
                startDragNodePosition.set(new Point2D(node.getLayoutX(), node.getLayoutY()));
            } else {
                node.setCursor(Cursor.DEFAULT);
                if(draggingState.compareAndSet(true, false)){
                    firePositionChangedEvent();
                }
            }
        });

        node.setOnMouseReleased(event -> {
            if (event.isControlDown()) {
                if(draggingState.compareAndSet(true, false)){
                    firePositionChangedEvent();
                }
                node.setCursor(Cursor.OPEN_HAND);
            } else {
                if(draggingState.compareAndSet(true, false)){
                    firePositionChangedEvent();
                }
                node.setCursor(Cursor.DEFAULT);
            }
        });

        node.setOnMouseDragged(event -> {
            if (event.isControlDown()) {
                if (draggingState.get()) {
                    event.consume();

                    Point2D nodePosition = startDragNodePosition.get();
                    Point2D mousePosition = startDragMousePoint.get();

                    if(nodePosition == null || mousePosition == null || event == null){
                        return;
                    }

                    node.setLayoutX(
                            nodePosition.getX()
                                    + event.getSceneX()
                                    - mousePosition.getX());
                    node.setLayoutY(
                            nodePosition.getY()
                                    + event.getSceneY()
                                    - mousePosition.getY());

                } else {
                    draggingState.set(true);
                    node.setCursor(Cursor.CLOSED_HAND);
                }
            } else {
                if(draggingState.compareAndSet(true, false)){
                    firePositionChangedEvent();
                }
                node.setCursor(Cursor.DEFAULT);
            }
        });

        node.setOnMouseEntered(event -> {
            if (event.isControlDown()) {
                node.setCursor(Cursor.OPEN_HAND);
            } else {
                node.setCursor(Cursor.DEFAULT);
            }
        });
    }

    void firePositionChangedEvent(){
        dragDoneHandlers.forEach(PositionChangeListener::positionChanged);
    }

    @FunctionalInterface
    interface PositionChangeListener {
        void positionChanged();
    }

    public NodeDragger addOnPositionChangedListener(PositionChangeListener dragDoneHandler) {
        dragDoneHandlers.add(dragDoneHandler);
        return this;
    }
}
