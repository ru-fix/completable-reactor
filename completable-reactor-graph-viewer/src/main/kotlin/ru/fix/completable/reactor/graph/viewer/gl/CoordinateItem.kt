package ru.fix.completable.reactor.graph.viewer.gl

data class CoordinateItem(
        val type: Type,
        var x: Int = 0,
        var y: Int = 0
) {

    enum class Type {
        HANDLER,
        MERGER,
        ROUTER,
        START_POINT,
        END_POINT
    }
}


interface PositionListener {
    fun positionChanged()
}