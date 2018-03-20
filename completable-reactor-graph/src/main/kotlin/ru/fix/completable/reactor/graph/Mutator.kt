package ru.fix.completable.reactor.graph

@FunctionalInterface
interface Mutator<Payload> {
    fun mutate(payload: Payload)
}
