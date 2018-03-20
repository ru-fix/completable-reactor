package ru.fix.completable.reactor.graph.internal

import ru.fix.completable.reactor.graph.runtime.GlGraph
import ru.fix.completable.reactor.model.GraphModel

class ModelBuilder{

    fun build(graph: GlGraph): GraphModel {
        graph.vertices.forEach { vertex ->
            if (vertex.name == null) {
                throw IllegalArgumentException("Graph contains unnamed vertex.")
            }
        }

        //TODO build model
        return GraphModel()

        //TODO add Runtime Validation Steps
        //TODO add Compilte time validation steps in tests

    }
}