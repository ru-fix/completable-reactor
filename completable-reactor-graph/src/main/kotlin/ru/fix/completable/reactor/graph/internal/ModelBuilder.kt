package ru.fix.completable.reactor.graph.internal

import ru.fix.completable.reactor.graph.runtime.RuntimeGraph
import ru.fix.completable.reactor.graph.runtime.RuntimeVertex
import ru.fix.completable.reactor.model.*

class ModelBuilder {

    fun buildCompileTimeFromRuntime(graph: RuntimeGraph): CompileTimeGraph {
        graph.vertices.forEach { vertex ->
            if (vertex.name == null) {
                throw IllegalArgumentException("Graph contains unnamed vertex.")
            }
        }

        val model = CompileTimeGraph()

        fun CompileTimeGraph.buildIfFigureIfAbsent(vx: RuntimeVertex): HandleableVertexFigure{
            return when (vx.type) {
                RuntimeVertex.Type.HandlerWithEmptyMerger,
                RuntimeVertex.Type.HandlerWithMerger,
                RuntimeVertex.Type.HandlerWithRoutingMerger ->{
                    Handler(vx.name!!).also{
                        this.handlers[it.name] = it
                        this.mergers[it.name] = Merger(it.name, vx.isImplicitMerger)
                    }
                }
                RuntimeVertex.Type.HandlerWithoutMerger -> {
                    Handler(vx.name!!).also{
                        this.handlers[it.name] = it
                    }
                }
                RuntimeVertex.Type.Mutator,
                RuntimeVertex.Type.Router -> {
                    Router(vx.name!!).also {
                        this.routers[it.name]  = it
                    }
                }
                RuntimeVertex.Type.SubgraphWithEmptyMerger,
                RuntimeVertex.Type.SubgraphWithMerger,
                RuntimeVertex.Type.SubgraphWithRoutingMerger,
                RuntimeVertex.Type.SubgraphWithoutMerger -> {
                    Subgraph(vx.name!!, vx.subgraphPayloadType!!.simpleName).also {
                        this.subgraphs[it.name] = it
                    }
                }
                null -> throw java.lang.IllegalArgumentException()
            }
        }

        graph.startPoint.forEach { vx ->
            model.startPoint.handleBy.add(model.buildIfFigureIfAbsent(vx))
        }

//        graph.vertices.forEach {vx ->
//            model.buildIfFigureIfAbsent(vx)
//            vx.
//        }





        //TODO build model
        return model

        //TODO add Runtime Validation Steps
        //TODO add Compilte time validation steps in tests

    }
}