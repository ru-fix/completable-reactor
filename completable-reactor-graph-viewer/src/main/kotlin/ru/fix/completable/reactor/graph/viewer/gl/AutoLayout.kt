package ru.fix.completable.reactor.graph.viewer.gl

import mu.KotlinLogging
import ru.fix.completable.reactor.model.*

private val log = KotlinLogging.logger {}

class AutoLayout {
    fun layout(node: AutoLayoutable) {
        layout(listOf(node))
    }

    private tailrec fun layout(nodes: List<AutoLayoutable>) {
        if (nodes.isEmpty()) {
            return
        }

        for (node in nodes) {

            if (!node.isUserDefinedPosition) {

                val figure = node.figure

                when (figure) {
                    is StartPoint -> log.info { "Layout startPoint: ${figure}" }
                    is Handler -> log.info { "Layout handler: ${figure.name}" }
                    is Merger -> log.info { "Layout handler: ${figure.name}" }
                    is Router -> log.info { "Layout handler: ${figure.name}" }
                    is Subgraph -> log.info { "Layout handler: ${figure.name}" }
                    is EndPoint -> log.info { "Layout endpoint: ${figure}" }
                }
            }
        }

        layout(nodes.flatMap { it.graphChildren })
    }
}
