package ru.fix.completable.reactor.graph.viewer.gl

/**
 * Created by Kamil Asfandiyarov
 */

class CoordinateTranslator (val graphPane: GraphPane) {

    fun paneLayoutToModelX(x: Double): Int {
        return (graphPane.graphBordersInModelCoordinates.minX.toDouble() + x).toInt()
    }


    fun paneLayoutToModelY(y: Double): Int {
        return (graphPane.graphBordersInModelCoordinates.minY.toDouble() + y).toInt()
    }
}