package ru.fix.completable.reactor.runtime.gl

class GlTransition(

        var mergeStatuses: Set<Enum<*>> = emptySet(),

        var isOnAny: Boolean = false,
        var isComplete: Boolean = false,

        var mergeBy: Vertex? = null,
        var handleBy: Vertex? = null) {


    override fun toString(): String {

        return when {
            isOnAny -> "{onAny}"
            mergeStatuses.isNotEmpty() -> mergeStatuses.joinToString(",", "{", "}") { it.name }
            else -> "{INVALID}"
        }
    }
}