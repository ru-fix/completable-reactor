package ru.fix.completable.reactor.model.validation

import ru.fix.completable.reactor.model.CompileTimeGraph


class MergerShouldHaveOutgoingOrCompleteTransitionValidator : Validator {

    override fun validate(graph: CompileTimeGraph): ValidationResult {

        val invalidMergers = graph.mergers.filter { entry ->
            entry.value.transitions.isEmpty()
        }

        return if (invalidMergers.isNotEmpty()) {
            ValidationFailed(
                    javaClass,
                    """
                    |Each merger should have at least single outgoing transition to another vertex or endpoint. 
                    |This mergers do not have such transitions: ${invalidMergers.keys.joinToString()}
                    """.trimMargin()
            )
        } else {
            ValidationSucceed(javaClass)
        }
    }
}