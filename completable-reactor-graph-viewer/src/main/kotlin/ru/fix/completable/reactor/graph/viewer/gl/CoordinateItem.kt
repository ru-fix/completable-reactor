package ru.fix.completable.reactor.graph.viewer.gl

data class CoordinateItem(
        val type: Type,
        val name: String?,
        var x: Int = 0,
        var y: Int = 0
) {

    enum class Type {
        START_POINT,
        HANDLER,
        SUBGRAPH,
        ROUTER,
        MERGER,
        END_POINT
    }
}


interface PositionListener {
    fun positionChanged()
}