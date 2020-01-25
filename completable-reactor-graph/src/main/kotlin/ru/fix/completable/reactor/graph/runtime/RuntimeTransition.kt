package ru.fix.completable.reactor.graph.runtime

// TODO: Может разбить на 3 перехода?
class RuntimeTransition(

        var mergeStatuses: Set<Enum<*>> = emptySet(),

        var isOnAny: Boolean = false,
        var isOnElse: Boolean = false,
        var isComplete: Boolean = false,

        var mergeBy: RuntimeVertex? = null,
        var handleBy: RuntimeVertex? = null) {


    override fun toString(): String {

        return when {
            isOnAny -> "{onAny}"
            isOnElse -> "{onElse}"
            mergeStatuses.isNotEmpty() -> mergeStatuses.joinToString(",", "{", "}") { it.name }
            else -> "{INVALID}"
        }
    }
}