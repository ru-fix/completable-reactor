package ru.fix.completable.reactor.model.validation

import ru.fix.completable.reactor.model.CompileTimeGraph


sealed class ValidationResult(val validatorClass: Class<Validator>)

class ValidationSucceed(validator: Class<Validator>) : ValidationResult(validator)

class ValidationFailed(validator: Class<Validator>, val message: String) : ValidationResult(validator)

interface Validator {
    fun validate(graph: CompileTimeGraph): ValidationResult
}