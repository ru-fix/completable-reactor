package ru.fix.completable.reactor.graph.internal

import ru.fix.completable.reactor.graph.runtime.GlVertex

class GlTransition(

        var mergeStatuses: Set<Enum<*>> = emptySet(),

        var isOnAny: Boolean = false,
        var isOrElse: Boolean = false,
        var isComplete: Boolean = false,

        var mergeBy: GlVertex? = null,
        var handleBy: GlVertex? = null) {


    override fun toString(): String {

        return when {
            isOnAny -> "{onAny}"
            isOrElse -> "{orElse}"
            mergeStatuses.isNotEmpty() -> mergeStatuses.joinToString(",", "{", "}") { it.name }
            else -> "{INVALID}"
        }
    }
}