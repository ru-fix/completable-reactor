package ru.fix.completable.reactor.graph.viewer.gl

/**
 * Created by Kamil Asfandiyarov
 */

class CoordinateTranslator (var worldSize: Double) {

    fun translateX(x: Integer): Double {
        return worldSize / 2 + x
    }

    fun translateX(x: Double): Double {
        return worldSize / 2 + x
    }

    fun translateY(y: Integer): Double {
        return worldSize / 2 + y
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