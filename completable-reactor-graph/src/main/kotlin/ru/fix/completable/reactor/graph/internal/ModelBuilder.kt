package ru.fix.completable.reactor.graph.internal

import ru.fix.completable.reactor.graph.runtime.RuntimeGraph
import ru.fix.completable.reactor.model.CompileTimeGraphModel

class ModelBuilder{

    fun build(graph: RuntimeGraph): CompileTimeGraphModel {
        graph.vertices.forEach { vertex ->
            if (vertex.name == null) {
                throw IllegalArgumentException("Graph contains unnamed vertex.")
            }
        }

        //TODO build model
        return CompileTimeGraphModel()

        //TODO add Runtime Validation Steps
        //TODO add Compilte time validation steps in tests

    }
}