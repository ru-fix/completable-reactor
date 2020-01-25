package ru.fix.completable.reactor.graph.internal

import ru.fix.completable.reactor.graph.runtime.RuntimeGraph
import ru.fix.completable.reactor.graph.runtime.RuntimeTransition
import ru.fix.completable.reactor.graph.runtime.RuntimeVertex
import ru.fix.completable.reactor.model.*

class ModelBuilder {

    fun buildCompileTimeFromRuntime(graph: RuntimeGraph): CompileTimeGraph {
        graph.vertices.forEach { vertex ->
            requireNotNull(vertex.name) { "Graph contains unnamed vertex." }
        }

        val model = CompileTimeGraph()


        fun CompileTimeGraph.buildIfFigureIfAbsent(vx: RuntimeVertex): HandleableVertexFigure {
            return when (vx.type) {
                RuntimeVertex.Type.HandlerWithEmptyMerger,
                RuntimeVertex.Type.HandlerWithMerger,
                RuntimeVertex.Type.HandlerWithRoutingMerger ->{
                    Handler(vx.name!!).also {
                        this.handlers[it.name] = it
                        val merger = Merger(it.name, vx.isImplicitMerger)

                        val transitions = vx.transitions.map { runtimeTransition ->
                            val t = Transition(
                                    mergeStatuses = runtimeTransition.mergeStatuses.map { it.name }.toSet(),
                                    isOnAny = runtimeTransition.isOnAny,
                                    isOnElse = runtimeTransition.isOnElse,
                                    isComplete = runtimeTransition.isComplete
                            )

                            if (runtimeTransition.isComplete) {
                                t.target = EndPoint(it.name)
                            } else if (runtimeTransition.mergeBy != null) {
                                val target = buildIfFigureIfAbsent(runtimeTransition.mergeBy!!)
                                t.target = target
                            } else {
                                t.target = Merger(it.name)
                            }

                            return@map t
                        }.toList()

                        merger.transitions.addAll(transitions)

                        this.mergers[it.name] = merger
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
                        val transitions = vx.transitions.map { runtimeTransition ->
                            val t = Transition(
                                    mergeStatuses = runtimeTransition.mergeStatuses.map { it.name }.toSet(),
                                    isOnAny = runtimeTransition.isOnAny,
                                    isOnElse = runtimeTransition.isOnElse,
                                    isComplete = runtimeTransition.isComplete
                            )
                            t.target = Router(it.name)

                            return@map t
                        }.toList()

                        it.transitions.addAll(transitions)

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

        graph.vertices.forEach { vx ->
            model.buildIfFigureIfAbsent(vx)
        }





        //TODO build model
        return model

        //TODO add Runtime Validation Steps
        //TODO add Compilte time validation steps in tests

    }
}