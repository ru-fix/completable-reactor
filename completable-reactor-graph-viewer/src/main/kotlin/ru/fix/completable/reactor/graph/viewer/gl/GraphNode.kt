package ru.fix.completable.reactor.graph.viewer.gl

import javafx.scene.layout.VBox
import ru.fix.completable.reactor.model.DEFAULT_COORDINATES
import ru.fix.completable.reactor.model.Figure

open class GraphNode(override val figure: Figure) : VBox(), AutoLayoutable {

    override val graphChildren = ArrayList<GraphNode>()

    var autoLayoutX: Int? = null
    var autoLayoutY: Int? = null

    override var nodeX: Int
        get() =
            if (isUserDefinedCoordinates) {
                (figure.coordinates ?: DEFAULT_COORDINATES).x
            } else {
                autoLayoutX ?: DEFAULT_COORDINATES.x
            }
        set(value) {
            if(!isUserDefinedCoordinates) {
                autoLayoutX = value
            } else{
                throw IllegalStateException("Auto layout tries to override user defined coordinates")
            }
        }

    override var nodeY: Int
        get() =
            if (isUserDefinedCoordinates) {
                (figure.coordinates ?: DEFAULT_COORDINATES).y
            } else {
                autoLayoutY ?: DEFAULT_COORDINATES.y
            }
        set(value) {
            if(!isUserDefinedCoordinates) {
                autoLayoutY = value
            } else{
                throw IllegalStateException("Auto layout tries to override user defined coordinates")
            }
        }

    override val isUserDefinedCoordinates: Boolean
        get() = figure.coordinates != null

    override val nodeHeight: Int
        get() = super.getHeight().toInt()

    override val nodeWidth: Int
        get() = super.getWidth().toInt()
}