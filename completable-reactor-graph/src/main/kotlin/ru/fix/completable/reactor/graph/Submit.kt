package ru.fix.completable.reactor.graph
//TODO doc with example
@FunctionalInterface
interface Submit <Request, Response>{
    fun submit(request: Request): Execution<Response>
}