package ru.fix.completable.reactor.graph.viewer.gl

class CoordinateItem {

    enum class Type {
        HANDLER,
        MERGER,
        ROUTER,
        START_POINT,
        END_POINT
    }

    val type: Type? = null

    @Volatile
    internal var x: Int = 0

    @Volatile
    internal var y: Int = 0

    }


interface PositionListener {
    fun positionChanged()
}