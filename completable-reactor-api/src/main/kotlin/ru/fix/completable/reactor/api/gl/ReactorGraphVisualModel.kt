package ru.fix.completable.reactor.api.gl

import ru.fix.completable.reactor.api.ReactorGraphModel
import java.util.*

class ReactorGraphVisualModel {

    data class Coordinates(val x: Int, val y: Int)

    class Source {
        var fileName: String? = null
        var fileLine: Int? = null
        var fileOffset: Int? = null
    }

    data class TransitionDocumentation(
            var mergeStatus: String,
            var docs: String
    )

    data class Transition(
            var mergeStatuses: List<String>? = null,
            var isOnAny: Boolean = false,
            var isComplete: Boolean = false,
            var target: Figure? = null,

            val transitionsDoc: List<TransitionDocumentation> = ArrayList(),

            var transitionOnAnySource: ReactorGraphModel.Source? = null,
            var transitionOnStatusSource: Map<String, ReactorGraphModel.Source>? = null,
            var mergeStatusSources: Map<String, ReactorGraphModel.Source>? = null
    )

    abstract class Figure {
        var coordinates: Coordinates? = null
        var source: Source? = null
    }

    class StartPoint : Figure() {
        val handleBy: MutableList<VertexFigure> = ArrayList()
    }

    class EndPoint : Figure()

    abstract class VertexFigure(
            var name: String,
            val transitions: ArrayList<Transition> = ArrayList()
    ) : Figure()

    class Handler(name: String) : VertexFigure(name)
    class Merger(name: String) : VertexFigure(name){
        var title: String? = null
    }
    class Router(name: String) : VertexFigure(name)
    class Subgraph(name: String) : VertexFigure(name)

    val startPoint = StartPoint()
}