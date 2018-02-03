package ru.fix.completable.reactor.model.validation

import ru.fix.completable.reactor.model.GraphModel
import ru.fix.completable.reactor.model.HandleableVertexFigure

class HandleableVerticesHaveIncomingFlowsValidator : Validator {

    override fun validate(graph: GraphModel): ValidationResult {

        val verticesWithIncomingTransitions = (graph.transitionable
                //collect all transitions targets
                .values
                .asSequence()
                .flatMap { it.transitions.asSequence() }
                .mapNotNull { it.target as? HandleableVertexFigure }
                +
                //add start point transitions
                graph.startPoint.handleBy.asSequence()
                )
                .map { it.name }
                .toHashSet()


        val verticesWithoutIncomingFlows = graph.handleable
                .values
                .asSequence()
                .map { it.name }
                .filter { verticesWithIncomingTransitions.contains(it) }
                .toList()

        return if (verticesWithoutIncomingFlows.isNotEmpty()) {
            ValidationFailed("ProcessingItem %s does not have incoming transitions.")
        } else {
            ValidationSucceed()
        }
    }
}