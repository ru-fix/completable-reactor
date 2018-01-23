package ru.fix.completable.reactor.graph.viewer.gl

/**
 * Created by Kamil Asfandiyarov
 */

class CoordinateTranslator (var worldSize: Double) {

    fun translateX(x: Int): Double {
        return worldSize / 2 + x.toDouble()
    }

    fun translateX(x: Double): Double {
        return worldSize / 2 + x
    }

    fun translateY(y: Int): Double {
        return worldSize / 2 + y.toDouble()
    }

    fun translateY(y: Double): Double {
        return worldSize / 2 + y
    }

    fun reverseTranslateX(x: Double): Int {
        return (x - worldSize / 2).toInt()
    }

    fun reverseTranslateY(y: Double): Int {
        return (y - worldSize / 2).toInt()
    }
}