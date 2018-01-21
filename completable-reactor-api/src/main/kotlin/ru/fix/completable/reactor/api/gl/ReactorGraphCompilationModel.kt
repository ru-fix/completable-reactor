package ru.fix.completable.reactor.api.gl.model

import ru.fix.completable.reactor.api.ReactorGraphModel
import java.util.*

data class Coordinates(
        @Volatile
        var x: Int = 0,

        @Volatile
        var y: Int = 0
)

data class Source(
        var fileName: String? = null,
        var line: Int? = null,
        var lineOffset: Int? = null
)

data class TransitionDocumentation(
        var mergeStatus: String,
        var docs: String
)

data class Transition(
        var mergeStatuses: List<String>? = null,
        var isOnAny: Boolean = false,
        var isComplete: Boolean = false,

        val transitionsDoc: List<TransitionDocumentation> = ArrayList(),

        var transitionOnAnySource: ReactorGraphModel.Source? = null,
        var transitionOnStatusSource: Map<String, ReactorGraphModel.Source>? = null,
        var mergeStatusSources: Map<String, ReactorGraphModel.Source>? = null) {

    lateinit var target: Figure
}

sealed class Figure(
        var coordinates: Coordinates = Coordinates(),
        var source: Source? = null
)

sealed class VertexFigure(var name: String) : Figure() {
    override fun toString() = "${javaClass.simpleName}($name)"
}

sealed class HandleableVertexFigure(name: String) : VertexFigure(name)

interface TransitionableFigure {
    val transitions: List<Transition>
}

class StartPoint : Figure() {
    var payloadType: String? = null
    val handleBy: MutableList<VertexFigure> = ArrayList()

    override fun toString() = "StartPoint"
}

class EndPoint(coordinates: Coordinates = Coordinates(), source: Source? = null) : Figure(coordinates, source) {
    override fun toString() = "EndPoint"
}

class Handler(name: String) : HandleableVertexFigure(name) {
    var title: String? = null
}

class Merger(name: String) : VertexFigure(name), TransitionableFigure {
    var title: String? = null
    override val transitions: ArrayList<Transition> = ArrayList()
}

class Router(name: String) : HandleableVertexFigure(name), TransitionableFigure {
    override val transitions: ArrayList<Transition> = ArrayList()
}

class Subgraph(
        name: String,
        var payloadClass: String
) : HandleableVertexFigure(name)

class ReactorGraphCompilationModel {
    val startPoint = StartPoint()
    val handlers = HashMap<String, Handler>()
    val subgraphs = HashMap<String, Subgraph>()
    val routers = HashMap<String, Router>()
    val mergers = HashMap<String, Merger>()
    val endpoints = HashMap<String, EndPoint>()

    inner class HandeableAccessor {
        operator fun get(name: String): HandleableVertexFigure? = handlers[name] ?: subgraphs[name] ?: routers[name]
    }

    /**
     * @return VertexFigure that can serve as a target of handleBy transition
     */
    val handleable = HandeableAccessor()


    inner class TransitionableAccessor {
        operator fun get(name: String): TransitionableFigure? = mergers[name] ?: routers[name]
    }

    val transitionable = TransitionableAccessor()
}