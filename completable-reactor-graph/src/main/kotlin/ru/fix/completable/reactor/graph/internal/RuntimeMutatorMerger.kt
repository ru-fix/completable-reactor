package ru.fix.completable.reactor.graph.internal

import ru.fix.completable.reactor.graph.Mutator
import ru.fix.completable.reactor.graph.Router
import ru.fix.completable.reactor.graph.RoutingMerger

class RuntimeMutatorMerger(private val mutator: Mutator<Any?>) : RoutingMerger<Any?, Any?> {

    override fun merge(payload: Any?, handlerResult: Any?): Enum<out Enum<*>> {
        if (handlerResult != null) {
            throw IllegalStateException("""
                        Invalid runtime behavior.
                        Mutator received non null handling result.
                        """.trimIndent())
        }
        mutator.mutate(payload)
        return RuntimeEmptyMerger.EmptyMergerStatusEnum.EMPTY_MERGER_STATUS
    }
}