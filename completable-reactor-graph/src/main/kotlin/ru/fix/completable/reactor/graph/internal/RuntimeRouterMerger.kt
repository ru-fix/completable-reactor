package ru.fix.completable.reactor.graph.internal

import ru.fix.completable.reactor.graph.Router
import ru.fix.completable.reactor.graph.RoutingMerger

class RuntimeRouterMerger(private val router: Router<Any?>) : RoutingMerger<Any?, Any?> {

    override fun merge(payload: Any?, handlerResult: Any?): Enum<out Enum<*>> {
        if (handlerResult != null) {
            throw IllegalStateException("""
                        Invalid runtime behavior.
                        Router received non null handling result.
                        """.trimIndent())
        }
        return router.route(payload)
    }
}