package ru.fix.completable.reactor.runtime.gl

class GlTransition (

    var mergeStatuses: Set<Enum<*>>? = null,

    var isOnAny: Boolean = false,
    var isComplete: Boolean = false,

    var mergeBy: Vertex? = null,
    var handleBy: Vertex? = null

)