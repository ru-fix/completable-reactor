package ru.fix.completable.reactor.graph.viewer.gl

import mu.KotlinLogging
import ru.fix.completable.reactor.model.*

private val log = KotlinLogging.logger {}

class AutoLayout {
    fun layout(node: AutoLayoutable) {
        layout(listOf(node), HashSet())
    }


    private tailrec fun layout(nodes: List<AutoLayoutable>, visitedNodes: MutableSet<AutoLayoutable>) {

        val children = ArrayList<AutoLayoutable>()

        for (node in nodes) {

            if (visitedNodes.contains(node)) {
                continue
            }

            visitedNodes.add(node)

            if (!node.isUserDefinedPosition) {

                val figure = node.figure

                fun position(node: AutoLayoutable) =
                        "x: ${node.nodeX}, y: ${node.nodeY}, w: ${node.nodeWidth}, h: ${node.nodeHeight}"

                when (figure) {
                    is StartPoint -> log.info { "Layout startPoint: $figure, ${position(node)}" }
                    is Handler -> log.info { "Layout handler: ${figure.name}, ${position(node)}" }
                    is Merger -> log.info { "Layout merger: ${figure.name}, ${position(node)}" }
                    is Router -> log.info { "Layout router: ${figure.name}, ${position(node)}" }
                    is Subgraph -> log.info { "Layout subgraph: ${figure.name}, ${position(node)}" }
                    is EndPoint -> log.info { "Layout endpoint: ${figure}, ${position(node)}" }
                }
            }

            children.addAll(node.graphChildren)
        }

        if (children.isEmpty()) {
            return
        }

        layout(children, visitedNodes)
    }
}
