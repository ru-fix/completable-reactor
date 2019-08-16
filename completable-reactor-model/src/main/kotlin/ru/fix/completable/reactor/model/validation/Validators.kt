package ru.fix.completable.reactor.model.validation

class Validators {
    companion object {
        fun get() : List<Validator> = listOf(
                AtLeastOneEndPointExistValidator(),
                HandleableVerticesHaveIncomingFlowsValidator(),
                MergerableVerticesHaveOutgoingTransitionsValidator(),
                MergerShouldHaveOutgoingOrCompleteTransitionValidator()
        )
        //TODO: add RouterOrMutatorCouldNotParticipateInMergeByTransitionValidator, required resolving transition type in compileTimeModel
    }
}