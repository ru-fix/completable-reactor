package ru.fix.completable.reactor.runtime.gl


interface GlTransitionBuilder {

    fun complete(): Vertex<*>

    fun handleBy(vertex: Vertex<*>): Vertex<*>

    fun mergeBy(vertex: Vertex<*>): Vertex<*>
}