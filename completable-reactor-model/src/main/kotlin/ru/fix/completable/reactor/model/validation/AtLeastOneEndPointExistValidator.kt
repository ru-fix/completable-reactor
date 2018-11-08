package ru.fix.completable.reactor.model.validation

import ru.fix.completable.reactor.model.CompileTimeGraphModel


class AtLeastOneEndPointExistValidator : Validator {

    override fun validate(graph: CompileTimeGraphModel): ValidationResult {

        return if (graph.transitionable
                        .values
                        .asSequence()
                        .flatMap { it.transitions.asSequence() }
                        .none { it.isComplete }) {

            ValidationFailed(
                    """
                    There are no EndPoints in graph.
                    Graph should contain at least one terminal transition.
                    Add complete step to at least one vertex.
                    """.trimIndent())

        } else {
            ValidationSucceed()
        }

    }
}