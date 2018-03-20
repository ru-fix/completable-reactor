package ru.fix.completable.reactor.graph

@FunctionalInterface
interface RoutingMerger<Payload, HandlerResult> {
    fun merge(payload: Payload, handlerResult: HandlerResult): Enum<*>
}
//TODO replace Enum with Any?
//TODO move GL graph API to separate package
//TODO create scala API proposal
