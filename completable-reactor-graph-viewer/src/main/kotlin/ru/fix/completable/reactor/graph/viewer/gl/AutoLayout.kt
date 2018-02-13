package ru.fix.completable.reactor.graph.viewer.gl

import mu.KotlinLogging
import ru.fix.completable.reactor.model.*

private val log = KotlinLogging.logger {}

class AutoLayout {

    val deltaX = 200.0
    val deltaY = 100.0

    fun layout(node: AutoLayoutable) {

        recursiveFixCoordinates(node, node.nodeCenterX(), node.nodeCenterY(), 200, 100)

    }



    private tailrec fun layout(nodes: List<AutoLayoutable>, visitedNodes: MutableSet<AutoLayoutable>) {

        val children = ArrayList<AutoLayoutable>()

        for (node in nodes) {

            if (visitedNodes.contains(node)) {
                continue
            }

            visitedNodes.add(node)

            if (!node.isUserDefinedCoordinates) {

                val figure = node.figure

                fun position(node: AutoLayoutable) =
                        "x: ${node.nodeX}, y: ${node.nodeY}, w: ${node.nodeWidth}, h: ${node.nodeHeight}"

                when (figure) {
                    is StartPoint -> log.trace { "Layout startPoint: $figure, ${position(node)}" }
                    is Handler -> log.trace { "Layout handler: ${figure.name}, ${position(node)}" }
                    is Merger -> log.trace { "Layout merger: ${figure.name}, ${position(node)}" }
                    is Router -> log.trace { "Layout router: ${figure.name}, ${position(node)}" }
                    is Subgraph -> log.trace { "Layout subgraph: ${figure.name}, ${position(node)}" }
                    is EndPoint -> log.trace { "Layout endpoint: ${figure}, ${position(node)}" }
                }
            }

            children.addAll(node.graphChildren)
        }

        if (children.isEmpty()) {
            return
        }

        layout(children, visitedNodes)
    }


    private fun recursiveFixCoordinates(
            parentNode: AutoLayoutable,
            parentX: Int,
            parentY: Int,
            deltaX: Int,
            deltaY: Int) {

        var index = -1 * (parentNode.graphChildren.size / 2)

        for (node in parentNode.graphChildren) {

            when (node.figure) {
                is EndPoint -> {
                    if(!node.isUserDefinedCoordinates) {
                        node.nodeX = parentX
                        node.nodeY = (deltaY + parentY)
                    }
                }
                else -> {
                    if (!node.isUserDefinedCoordinates) {
                        node.nodeX = (deltaX * index++ + parentX)
                        node.nodeY = (deltaY + parentY)
                    }
                    recursiveFixCoordinates(node, node.nodeCenterX(), node.nodeCenterY(), deltaX, deltaY)
                }
            }
        }
    }
}
