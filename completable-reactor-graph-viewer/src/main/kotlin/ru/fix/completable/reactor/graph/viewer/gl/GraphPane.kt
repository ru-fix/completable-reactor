package ru.fix.completable.reactor.graph.viewer.gl

import javafx.scene.layout.Pane
import mu.KotlinLogging

private val log = KotlinLogging.logger {}

class GraphPane : Pane() {

    private val autoLayout = AutoLayout()

    init {
    }

    data class Rect(
            val minX: Double,
            val minY: Double,
            val maxX: Double,
            val maxY: Double)

    override fun layoutChildren() {
        super.layoutChildren()

        log.info { "layout children" }

        children.asSequence()
                .mapNotNull { it as? StartPointNode }
                .first()
                .let {
                    autoLayout.layout(it)
                }

        val graphRect = nodes()
                .map {
                    Rect(
                            it.layoutX,
                            it.layoutY,
                            it.layoutX + it.width,
                            it.layoutY + it.height)
                }
                .reduce { acc, rect ->
                    Rect(
                            Math.min(acc.minX, rect.minX),
                            Math.min(acc.minY, rect.minY),
                            Math.max(acc.maxX, rect.maxX),
                            Math.max(acc.maxY, rect.maxY)
                    )
                }

        log.info { "update prefHeight $prefHeight to ${(graphRect.maxX - graphRect.minX) * 2}" }

        this.prefWidth = (graphRect.maxX - graphRect.minX) * 2
        this.prefHeight = (graphRect.maxY - graphRect.minY) * 2

        nodes().forEach {

        }

    }

    fun nodes() = children.asSequence().mapNotNull { it as? GraphNode }
}