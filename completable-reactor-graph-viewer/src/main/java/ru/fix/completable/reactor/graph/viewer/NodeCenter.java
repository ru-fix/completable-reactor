package ru.fix.completable.reactor.graph.viewer;

import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.scene.Node;
import lombok.Getter;
import lombok.experimental.var;

/**
 * @author Kamil Asfandiyarov
 */
class NodeCenter {

    final Node world;
    final Node node;

    @Getter
    ReadOnlyDoubleWrapper centerX = new ReadOnlyDoubleWrapper();

    @Getter
    ReadOnlyDoubleWrapper centerY = new ReadOnlyDoubleWrapper();

    public NodeCenter(Node world, Node node) {
        this.world = world;
        this.node = node;

        node.boundsInParentProperty().addListener(
                (observableValue, oldBounds, newBounds)
                        ->
                {

                    var pNode = node.getParent();
                    var diffx = 0.0;
                    var diffy = 0.0;

                    while (pNode != null && pNode != world) {
                        diffx += pNode.getLayoutX();
                        diffy += pNode.getLayoutY();
                        pNode = pNode.getParent();
                    }

                    centerX.set(newBounds.getMinX() + newBounds.getWidth() / 2 + diffx);
                    centerY.set(newBounds.getMinY() + newBounds.getHeight() / 2 + diffy);
                }
        );
    }
}
