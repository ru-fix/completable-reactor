package ru.fix.completable.reactor.graph.viewer.gl

import mu.KotlinLogging
import ru.fix.completable.reactor.model.*

private val log = KotlinLogging.logger {}

class AutoLayout {

    private val deltaX = 200
    private val deltaY = 60

    fun layout(node: AutoLayoutable) {

        recursiveFixCoordinates(listOf(node))

    }

    private tailrec fun recursiveFixCoordinates(nodes: List<AutoLayoutable>) {

        val nodesToProcess = ArrayList<AutoLayoutable>()

        for (parentNode in nodes) {

            val parentX = parentNode.nodeCenterX()
            val parentY = parentNode.nodeCenterY()

            var index = -1 * (parentNode.graphChildren.size / 2)


            for (node in parentNode.graphChildren) {

                when (node.figure) {
                    is EndPoint -> {
                        if (!node.isUserDefinedCoordinates) {
                            node.nodeX = parentX
                            node.nodeY = (deltaY + parentY)
                        }
                    }
                    else -> {
                        if (!node.isUserDefinedCoordinates) {
                            node.nodeX = (deltaX * index++ + parentX)
                            node.nodeY = (deltaY + parentY)
                        }
                        nodesToProcess.add(node)
                    }
                }
            }
        }

        if(!nodesToProcess.isEmpty()) {
            recursiveFixCoordinates(nodesToProcess)
        }
    }
}
