package ru.fix.completable.reactor.graph.viewer.gl

import javafx.application.Platform
import javafx.scene.layout.Pane
import mu.KotlinLogging
import ru.fix.completable.reactor.model.DEFAULT_COORDINATES

private val log = KotlinLogging.logger {}

class GraphPane : Pane() {

    private val autoLayout = AutoLayout()

    var graphBordersInModelCoordinates: Rect = Rect(0, 0, 0, 0)

    private var resizeRequested = false
    private lateinit var afterResizeCallback: () -> Unit


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

    private val GRAPH_PANE_MIN_BORDER_SIZE = 2048

    fun updatePaneSizeIfRequired() {

        if (!resizeRequested) {
            return
        } else {
            resizeRequested = false
        }

        val graphBorders = nodes()
                .map {
                    Rect(
                            it.nodeX,
                            it.nodeY,
                            it.nodeX + it.nodeWidth,
                            it.nodeY + it.nodeHeight)
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


            this.graphBordersInModelCoordinates = graphBorders
            this.prefWidth = targetWidth
            this.prefHeight = targetHeight

            //to refresh parent scroll pane sliders and ask parent to redraw itself and content
            Platform.runLater {
                this.requestParentLayout()
                this.afterResizeCallback()
            }
        }
    }



    /**
     * Request pane to resize on next children layout
     */
    fun requestResize(afterResize: ()->Unit) {
        resizeRequested = true
        this.afterResizeCallback = afterResize
    }
}