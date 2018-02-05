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

                when (figure) {
                    is StartPoint -> log.info { "Layout startPoint: ${figure}" }
                    is Handler -> log.info { "Layout handler: ${figure.name}" }
                    is Merger -> log.info { "Layout merger: ${figure.name}" }
                    is Router -> log.info { "Layout router: ${figure.name}" }
                    is Subgraph -> log.info { "Layout subgraph: ${figure.name}" }
                    is EndPoint -> log.info { "Layout endpoint: ${figure}" }
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
