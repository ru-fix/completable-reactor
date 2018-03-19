package ru.fix.completable.reactor.graph.internal

import ru.fix.completable.reactor.graph.RoutingMerger

class GlEmptyMerger : RoutingMerger<Any?, Any?> {

    enum class EmptyMergerStatusEnum{
        EMPTY_MERGER_STATUS
    }

    override fun merge(payload: Any?, handlerResult: Any?): Enum<out Enum<*>> {
        return EmptyMergerStatusEnum.EMPTY_MERGER_STATUS
    }
}