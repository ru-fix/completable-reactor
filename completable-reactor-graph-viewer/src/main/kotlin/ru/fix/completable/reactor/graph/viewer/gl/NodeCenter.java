package ru.fix.completable.reactor.graph.viewer.gl;

import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.scene.Node;
import lombok.Getter;
import lombok.experimental.var;

import java.util.Optional;

/**
 * @author Kamil Asfandiyarov
 */
class NodeCenter {

    final Node world;
    final Node node;
    final Optional<CentrableNode> centrableNode;

    @Getter
    ReadOnlyDoubleWrapper centerX = new ReadOnlyDoubleWrapper();

    @Getter
    ReadOnlyDoubleWrapper centerY = new ReadOnlyDoubleWrapper();

    public NodeCenter(Node world, Node node) {
        this.world = world;
        this.node = node;

        if (node instanceof CentrableNode) {
            centrableNode = Optional.of((CentrableNode) node);
        } else {
            centrableNode = Optional.empty();
        }

        node.boundsInParentProperty().addListener((observableValue, oldBounds, newBounds) -> {

                    var pNode = node.getParent();
                    var diffx = 0.0;
                    var diffy = 0.0;

                    while (pNode != null && pNode != world) {
                        diffx += pNode.getLayoutX();
                        diffy += pNode.getLayoutY();
                        pNode = pNode.getParent();
                    }

                    double nodeCenterX;
                    double nodeCenterY;

                    if (centrableNode.isPresent()) {
                        nodeCenterX = centrableNode.get().getCenterX();
                        nodeCenterY = centrableNode.get().getCenterY();
                    } else {
                        nodeCenterX = newBounds.getMinX() + newBounds.getWidth() / 2;
                        nodeCenterY = newBounds.getMinY() + newBounds.getHeight() / 2;
                    }

                    centerX.set(nodeCenterX + diffx);
                    centerY.set(nodeCenterY + diffy);
                }
        );
    }
}
