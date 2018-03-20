package ru.fix.completable.reactor.graph

@FunctionalInterface
interface Router<Payload> {
    fun route(payload: Payload): Enum<*>
}
