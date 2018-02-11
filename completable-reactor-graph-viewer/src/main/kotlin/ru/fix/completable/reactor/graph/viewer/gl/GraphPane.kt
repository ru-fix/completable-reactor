package ru.fix.completable.reactor.graph.viewer.gl

import javafx.scene.layout.Pane
import mu.KotlinLogging
import ru.fix.completable.reactor.model.DEFAULT_COORDINATES

private val log = KotlinLogging.logger {}

class GraphPane : Pane() {

    private val autoLayout = AutoLayout()

    var graphBordersInModelCoordinates: Rect = Rect(0, 0, 0, 0)

    init {
    }


    override fun layoutChildren() {
        super.layoutChildren()

        fun calcX(x: Int) = x - graphBordersInModelCoordinates.minX

        fun calcY(y: Int) = y - graphBordersInModelCoordinates.minY


        nodes().forEach { node ->
            val coordinate = node.figure.coordinates ?: DEFAULT_COORDINATES

            node.layoutX = calcX(coordinate.x).toDouble()
            node.layoutY = calcY(coordinate.y).toDouble()
        }


        children.asSequence()
                .mapNotNull { it as? StartPointNode }
                .first()
                .let {
                    autoLayout.layout(it)
                }


    }

    fun nodes() = children.asSequence().mapNotNull { it as? GraphNode }
}