package ru.fix.completable.reactor.model.validation

class Validators {
    companion object {
        fun get() = listOf(
                AtLeastOneEndPointExistValidator(),
                HandleableVerticesHaveIncomingFlowsValidator(),
                MergerableVerticesHaveOutgoingTransitionsValidator()
        )
        //TODO: add RouterOrMutatorCouldNotParticipateInMergeByTransitionValidator, required resolving transition type in compileTimeModel
    }
}