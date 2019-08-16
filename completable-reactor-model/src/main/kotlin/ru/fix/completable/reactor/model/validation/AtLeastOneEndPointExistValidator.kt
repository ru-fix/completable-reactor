package ru.fix.completable.reactor.model.validation

import ru.fix.completable.reactor.model.CompileTimeGraph


class AtLeastOneEndPointExistValidator : Validator {

    override fun validate(graph: CompileTimeGraph): ValidationResult {

        return if (graph.transitionable
                        .values
                        .asSequence()
                        .flatMap { it.transitions.asSequence() }
                        .none { it.isComplete }) {

            ValidationFailed(
                    javaClass,
                    """
                    |There are no EndPoints in graph.
                    |Graph should contain at least one terminal transition.
                    |Add complete() step to at least one vertex.
                    """.trimMargin())

        } else {
            ValidationSucceed(javaClass)
        }
    }
}