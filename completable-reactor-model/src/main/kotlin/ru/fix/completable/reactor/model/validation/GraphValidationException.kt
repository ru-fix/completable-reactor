package ru.fix.completable.reactor.model.validation

import java.lang.Exception

class GraphValidationException(
        message: String,
        val validatorClass: Class<Validator>) : Exception(message)