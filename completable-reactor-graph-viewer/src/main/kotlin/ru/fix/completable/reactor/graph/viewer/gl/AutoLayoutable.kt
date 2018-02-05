package ru.fix.completable.reactor.graph.viewer.gl

import ru.fix.completable.reactor.model.Figure

interface AutoLayoutable {
    val graphChildren: List<AutoLayoutable>

    /**
     * User could specify graph vertices coordinate in graph configuration source class.
     * This coordinates is stored in GraphModel.
     * This Coordinates are logical. They being translated to JavaFx position (layoutX and layoutY property of
     * javaFx Nodes) during graph visualization.
     * If user coordinates is defined for particular node, AutoLayout should skip it and not change it's position
     * (javafx layoutX and layoutY).
     * Otherwise if user coordinate is not defined in graph source, AutoLayout could take place and change node
     * position.
     */
    val isUserDefinedPosition: Boolean

    /**
     * Position of the node (javafx layoutX).
     * Position of the node (javafx layoutY).
     */
    var positionX: Double
    var positionY: Double

    val figure: Figure
}