package ru.fix.completable.reactor.model.validation

import ru.fix.completable.reactor.model.CompileTimeGraph
import ru.fix.completable.reactor.model.HandleableVertexFigure

class MergerableVerticesHaveOutgoingTransitionsValidator : Validator {

    override fun validate(graph: CompileTimeGraph): ValidationResult {
        return graph.transitionable.values.asSequence()
                .filter { it.transitions.isEmpty() }
                .map { it.name }
                .toSet()
                .takeIf { it.isNotEmpty() }
                ?.joinToString()
                ?.let {
                    ValidationFailed(javaClass, "Mergerable items $it do not have any outgoing transitions.")
                }
                ?: ValidationSucceed(javaClass)
    }
}