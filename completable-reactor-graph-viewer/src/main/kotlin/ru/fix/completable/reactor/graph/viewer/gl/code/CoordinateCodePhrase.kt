package ru.fix.completable.reactor.graph.viewer.gl.code

sealed class CoordinateCodePhrase(
        val x: Int = 0,
        val y: Int = 0) {

    abstract class NamedCoordinateCodePhrase(val name: String, x: Int, y: Int) : CoordinateCodePhrase(x, y)

    class StartPoint(x: Int, y: Int) : CoordinateCodePhrase(x, y)
    class Handler(name: String, x: Int, y: Int) : NamedCoordinateCodePhrase(name, x, y)
    class Subgraph(name: String, x: Int, y: Int) : NamedCoordinateCodePhrase(name, x, y)
    class Router(name: String, x: Int, y: Int) : NamedCoordinateCodePhrase(name, x, y)
    class Merger(name: String, x: Int, y: Int) : NamedCoordinateCodePhrase(name, x, y)
    class EndPoint(name: String, x: Int, y: Int) : NamedCoordinateCodePhrase(name, x, y)
}
