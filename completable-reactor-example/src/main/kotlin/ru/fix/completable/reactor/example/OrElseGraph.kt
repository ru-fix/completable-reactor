package ru.fix.completable.reactor.example

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import ru.fix.completable.reactor.graph.kotlin.Graph
import ru.fix.completable.reactor.runtime.CompletableReactor
import java.util.concurrent.CompletableFuture
import javax.annotation.PostConstruct

@Configuration
open class OrElseGraph : Graph<OrElsePayload>() {

    @Autowired
    private lateinit var completableReactor: CompletableReactor

    @PostConstruct
    fun initialize() {
        completableReactor.registerGraphIfAbsent(this)
    }

    private val resolveFlow =
            handler {
                return@handler CompletableFuture.completedFuture(flow)
            }.withRoutingMerger {
                flow
            }

    private val first =
            mutator {
                response.result = OrElsePayload.Flow.FIRST
                println("First vertex, real: $flow")
            }

    private val second =
            mutator {
                response.result = OrElsePayload.Flow.SECOND
                println("Second vertex, real: $flow")
            }

    private val orElse =
            mutator {
                response.result = flow
                println("Or else vertex, real: $flow")
            }

    init {
        payload()
                .handleBy(resolveFlow)

        resolveFlow
                .on(OrElsePayload.Flow.FIRST).handleBy(first)
                .on(OrElsePayload.Flow.SECOND).handleBy(second)
                .orElse().handleBy(orElse)

        first
                .onAny().complete()

        second
                .onAny().complete()

                coordinates()
                    .vx(first, 1, 262)
                    .vx(resolveFlow, 10, 90, 45, 177)
                    .ct(first, 51, 362)

        orElse
                .onAny().complete()
    }
}