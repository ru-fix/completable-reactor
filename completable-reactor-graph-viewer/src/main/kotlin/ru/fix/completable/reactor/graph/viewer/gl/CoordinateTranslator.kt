package ru.fix.completable.reactor.graph.viewer.gl

/**
 * Created by Kamil Asfandiyarov
 */

class CoordinateTranslator (val graphPane: GraphPane) {

    fun translateX(x: Int): Double {
        return graphPane.width / 2 + x.toDouble()
    }

    fun translateX(x: Double): Double {
        return graphPane.width / 2 + x
    }

    fun translateY(y: Int): Double {
        return graphPane.height / 2 + y.toDouble()
    }

    fun translateY(y: Double): Double {
        return graphPane.height / 2 + y
    }

    fun reverseTranslateX(x: Double): Int {
        return (x - graphPane.width / 2).toInt()
    }

    fun reverseTranslateY(y: Double): Int {
        return (y - graphPane.height / 2).toInt()
    }
}