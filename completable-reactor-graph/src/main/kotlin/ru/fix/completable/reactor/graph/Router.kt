package ru.fix.completable.reactor.graph

@FunctionalInterface
interface Router<Payload> {
    //TODO: add documentation to all public interfaces
    fun route(payload: Payload): Enum<*>
}
