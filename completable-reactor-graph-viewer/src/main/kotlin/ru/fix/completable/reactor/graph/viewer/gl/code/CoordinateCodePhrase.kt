package ru.fix.completable.reactor.graph.viewer.gl.code

sealed class CoordinateCodePhrase {

    interface NamedPhrase {
        val name: String
    }

    class StartPointPhrase(val x: Int, val y: Int) : CoordinateCodePhrase()

    class PlainVertexPhrase(override val name: String, val x: Int, val y: Int) :
            NamedPhrase, CoordinateCodePhrase()

    class ComplexVertexPhrase(override val name: String, val x: Int, val y: Int, val x2: Int, val y2: Int) :
            NamedPhrase, CoordinateCodePhrase()

    class EndPointPhrase(override val name: String, val x: Int, val y: Int):
            NamedPhrase, CoordinateCodePhrase()
}
