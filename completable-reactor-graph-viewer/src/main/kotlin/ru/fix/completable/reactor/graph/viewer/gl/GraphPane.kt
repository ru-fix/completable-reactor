package ru.fix.completable.reactor.graph.viewer.gl

import javafx.scene.layout.Pane
import mu.KotlinLogging
import ru.fix.completable.reactor.model.DEFAULT_COORDINATES

private val log = KotlinLogging.logger {}

class GraphPane : Pane() {

    private val autoLayout = AutoLayout()

    private var graphBordersInModelCoordinates: Rect = Rect(0, 0, 0, 0)


    override fun layoutChildren() {
        super.layoutChildren()

        children.asSequence()
                .mapNotNull { it as? StartPointNode }
                .first()
                .let {
                    autoLayout.layout(it)
                }

        updatePaneSizeIfRequired()


        fun calcX(x: Int) = x - graphBordersInModelCoordinates.minX

        fun calcY(y: Int) = y - graphBordersInModelCoordinates.minY


        nodes().forEach { node ->
            node.layoutX = calcX(node.nodeX).toDouble()
            node.layoutY = calcY(node.nodeY).toDouble()
        }
    }

    fun nodes() = children.asSequence().mapNotNull { it as? GraphNode }

    fun updatePaneSizeIfRequired() {

        val GRAPH_PANE_MIN_BORDER_SIZE = 2048


        val graphBorders = nodes()
                .map { it.figure }
                .map { it.coordinates ?: DEFAULT_COORDINATES }
                .map {
                    Rect(
                            it.x,
                            it.y,
                            it.x,
                            it.y)
                }
                .reduce { acc, rect ->
                    Rect(
                            Math.min(acc.minX, rect.minX),
                            Math.min(acc.minY, rect.minY),
                            Math.max(acc.maxX, rect.maxX),
                            Math.max(acc.maxY, rect.maxY)
                    )
                }
                .let {

                    val resizeWidth =
                            if (it.width * 2 < GRAPH_PANE_MIN_BORDER_SIZE) {
                                GRAPH_PANE_MIN_BORDER_SIZE - it.width
                            } else {
                                it.width
                            }
                    val resizeHeight =
                            if (it.height * 2 < GRAPH_PANE_MIN_BORDER_SIZE) {
                                GRAPH_PANE_MIN_BORDER_SIZE - it.height
                            } else {
                                it.height
                            }

                    Rect(
                            it.minX - resizeWidth / 2,
                            it.minY - resizeHeight / 2,
                            it.maxX + resizeWidth / 2,
                            it.maxY + resizeHeight / 2
                    )
                }

        val targetWidth = graphBorders.width.toDouble()
        val targetHeight = graphBorders.height.toDouble()

        if (this.prefWidth != targetWidth || this.prefHeight != targetHeight) {

            //TODO: fix scroll during resizing of the content

            log.info { "Update width and height" }

            this.graphBordersInModelCoordinates = graphBorders
            this.prefWidth = targetWidth
            this.prefHeight = targetHeight
        }
    }
}