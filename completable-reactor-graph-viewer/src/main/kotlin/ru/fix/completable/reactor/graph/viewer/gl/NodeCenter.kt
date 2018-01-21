package ru.fix.completable.reactor.graph.viewer.gl;

import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.scene.Node;

/**
 * @author Kamil Asfandiyarov
 */
class NodeCenter(world: Node, node: Node) {

    val centerX = ReadOnlyDoubleWrapper()
    val centerY = ReadOnlyDoubleWrapper()

    init {
        var centrableNode: CentrableNode?
        if (node is CentrableNode) {
            centrableNode = node
        } else {
            centrableNode = null
        }

        node.boundsInParentProperty().addListener { observableValue, oldBounds, newBounds ->

            var pNode = node.getParent();
            var diffx = 0.0
            var diffy = 0.0

            while (pNode != null && pNode != world) {
                diffx += pNode.layoutX;
                diffy += pNode.layoutY;
                pNode = pNode.parent;
            }

            val nodeCenterX: Double
            val nodeCenterY: Double

            if (centrableNode != null) {
                nodeCenterX = centrableNode.centerX
                nodeCenterY = centrableNode.centerY
            } else {
                nodeCenterX = newBounds.minX + newBounds.width / 2;
                nodeCenterY = newBounds.minY + newBounds.height / 2;
            }

            centerX.set(nodeCenterX + diffx)
            centerY.set(nodeCenterY + diffy)
        }
    }
}
