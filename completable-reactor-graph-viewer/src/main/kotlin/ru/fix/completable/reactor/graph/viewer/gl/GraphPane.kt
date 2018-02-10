package ru.fix.completable.reactor.graph.viewer.gl

import javafx.scene.layout.Pane
import mu.KotlinLogging
import ru.fix.completable.reactor.model.DEFAULT_COORDINATES

private val log = KotlinLogging.logger {}

class GraphPane : Pane() {

    private val autoLayout = AutoLayout()

    init {
    }



    override fun layoutChildren() {
        super.layoutChildren()

        log.info { "layout children" }

        fun calcX(x: Int) = width / 2 + x

        fun calcY(y: Int) = height / 2 + y


        nodes().forEach { node ->
            val coordinate = node.figure.coordinates ?: DEFAULT_COORDINATES

            node.layoutX = calcX(coordinate.x)
            node.layoutY = calcY(coordinate.y)
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