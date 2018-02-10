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
}
