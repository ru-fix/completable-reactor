package ru.fix.completable.reactor.runtime.gl

@FunctionalInterface
interface Router<Payload> {
    fun route(payload: Payload): Enum<*>
}
