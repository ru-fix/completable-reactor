package ru.fix.completable.reactor.model

import java.util.*

data class Coordinates(
        val x: Int,
        val y: Int
)

val DEFAULT_COORDINATES = Coordinates(0, 0)

data class Source(
        var filePath: String,
        /**
         * 1..n
         */
        var line: Int,
        /**
         * 1..n
         */
        var column: Int,
        var offset: Int
)

data class TransitionDocumentation(
        var mergeStatus: String,
        var docs: String
)

data class Transition(
        var mergeStatuses: Set<String> = emptySet(),
        var isOnAny: Boolean = false,
        var isComplete: Boolean = false,

        val transitionsDoc: List<TransitionDocumentation> = ArrayList(),

        var transitionOnAnySource: Source? = null,
        var transitionOnStatusSource: Map<String, Source>? = null,
        var mergeStatusSources: Map<String, Source>? = null) {

    lateinit var target: Figure
}

sealed class Figure(
        /**
         * User defined coordinates stored in graph source
         * Null if no coordinates defined in source
         */
        @Volatile
        var coordinates: Coordinates? = null,
        var source: Source? = null
)

sealed class VertexFigure(var name: String) : Figure() {
    var title: String? = null
    var doc: String? = null

    override fun toString() = "${javaClass.simpleName}($name)"
}

sealed class HandleableVertexFigure(name: String) : VertexFigure(name)

interface TransitionableFigure {
    val name: String
    val transitions: MutableList<Transition>
}

class StartPoint : Figure() {
    var payloadType: String? = null
    val handleBy: MutableList<HandleableVertexFigure> = ArrayList()

    var title: String? = null
    var doc: String? = null

    override fun toString() = "StartPoint"
}

//EndPoint requires name for easy code generation for coordinates section
class EndPoint(var name: String,
               coordinates: Coordinates? = null,
               source: Source? = null) : Figure(coordinates, source) {
    override fun toString() = "EndPoint"
}

class Handler(name: String) : HandleableVertexFigure(name)

class Merger(name: String) : VertexFigure(name), TransitionableFigure {
    override val transitions: ArrayList<Transition> = ArrayList()
}

class Router(name: String) : HandleableVertexFigure(name), TransitionableFigure {
    override val transitions: ArrayList<Transition> = ArrayList()
}

class Subgraph(
        name: String,
        var payloadClass: String
) : HandleableVertexFigure(name)


class GraphModel {
    val startPoint = StartPoint()
    val handlers = HashMap<String, Handler>()
    val subgraphs = HashMap<String, Subgraph>()
    val routers = HashMap<String, Router>()
    val mergers = HashMap<String, Merger>()
    val endpoints = HashMap<String, EndPoint>()

    lateinit var graphClass: String

    var graphDeclarationLocation: Source? = null


    /**
     * If coordinates block exists, points to beginging of 'coordinates()' method invocation
     */
    var coordinatesStart: Source? = null
    /**
     * If coordinates block exists, points to end of coordinates building section
     */
    var coordinatesEnd: Source? = null
    /**
     * Point to end of last code block within graph.
     * Coordinates could be injected at that point if they does not exist
     */
    var endOfLastCodeBlocksWithinGraph: Source? = null

    inner class HandeableAccessor {
        operator fun get(name: String): HandleableVertexFigure? = handlers[name] ?: subgraphs[name] ?: routers[name]
        val values: List<HandleableVertexFigure>
            get() = handlers.values + subgraphs.values + routers.values
    }

    /**
     * @return VertexFigure that can serve as a target of handleBy transition
     */
    val handleable = HandeableAccessor()


    inner class TransitionableAccessor {
        operator fun get(name: String): TransitionableFigure? = mergers[name] ?: routers[name]
        val values: List<TransitionableFigure>
            get() = mergers.values + routers.values
    }

    val transitionable = TransitionableAccessor()
}