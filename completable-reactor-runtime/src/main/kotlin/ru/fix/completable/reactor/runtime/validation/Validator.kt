package ru.fix.completable.reactor.runtime.validation

import ru.fix.completable.reactor.runtime.internal.gl.GlReactorGraph

class ValidationException(message: String) : Exception(message)

interface Validator {
    fun validate(graph: GlReactorGraph<*>)
}