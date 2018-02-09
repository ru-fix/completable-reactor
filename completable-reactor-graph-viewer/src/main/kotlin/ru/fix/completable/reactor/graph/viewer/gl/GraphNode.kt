package ru.fix.completable.reactor.graph.viewer.gl

import javafx.scene.layout.VBox
import ru.fix.completable.reactor.model.Figure

open class GraphNode(override val figure: Figure) : VBox(), AutoLayoutable {

    override val graphChildren = ArrayList<GraphNode>()

    override var nodeX: Double
        get() = layoutX
        set(value) {
            layoutY = value
        }

    override var nodeY: Double
        get() = layoutY
        set(value) {
            layoutY = value
        }
    override val isUserDefinedPosition: Boolean
        get() = figure.coordinates != null

    override val nodeHeight: Double
        get() = super.getHeight()

    override val nodeWidth: Double
        get() = super.getWidth()
}