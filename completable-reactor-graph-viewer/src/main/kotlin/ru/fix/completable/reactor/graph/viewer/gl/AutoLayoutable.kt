package ru.fix.completable.reactor.graph.viewer.gl

import ru.fix.completable.reactor.model.Figure

interface AutoLayoutable {
    val graphChildren: List<AutoLayoutable>

    /**
     * User could specify graph vertices coordinate in graph configuration source class.
     * This coordinates is stored in GraphModel.
     * This Coordinates are logical. They being translated to JavaFx position (layoutX and layoutY property of
     * javaFx Nodes) during graph visualization.
     * If user coordinates is defined for particular node, AutoLayout should skip this node and not change
     * it's coordinates
     * Otherwise if user coordinate is not defined in graph source, AutoLayout could take place and initialize
     * coordinates.
     */
    val isUserDefinedCoordinates: Boolean

    /**
     * x position of the node.
     */
    var nodeX: Int
    /**
     * y position of the node.
     */

    var nodeY: Int

    fun nodeCenterX() = nodeX + nodeWidth / 2

    fun nodeCenterY() = nodeY + nodeHeight / 2

    val nodeHeight: Int
    val nodeWidth: Int

    val figure: Figure
}