package ru.fix.completable.reactor.example

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import ru.fix.completable.reactor.graph.kotlin.Graph
import ru.fix.completable.reactor.runtime.CompletableReactor
import java.util.concurrent.CompletableFuture
import javax.annotation.PostConstruct

//TODO: тесты на все возможные кейсы с onElse
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
            handler {
                println("First vertex, real: $flow")
                response.result = OrElsePayload.Flow.FIRST
                CompletableFuture.completedFuture(OrElsePayload.Flow.FIRST)
            }.withRoutingMerger { resut ->
                return@withRoutingMerger resut
            }

    private val second =
            handler {
                println("Second vertex, real: $flow")
                response.result = OrElsePayload.Flow.SECOND
                CompletableFuture.completedFuture(OrElsePayload.Flow.SECOND)
            }.withRoutingMerger { result ->
                return@withRoutingMerger result
            }

    private val orElse =
            handler {
                println("Or else vertex, real: $flow")
                response.result = flow
                return@handler CompletableFuture.completedFuture(flow)
            }.withRoutingMerger { result ->
                return@withRoutingMerger result
            }

    private val endVertex =
            handler {
                println("this is the end...")
                CompletableFuture.completedFuture(response.result)
            }.withoutMerger()

    init {
        payload()
                .handleBy(resolveFlow)

        resolveFlow
                .on(OrElsePayload.Flow.FIRST).handleBy(first)
                .on(OrElsePayload.Flow.SECOND).handleBy(second)
                .onElse().handleBy(orElse)

        first
                .onAny().handleBy(endVertex)

        second
                .onAny().handleBy(endVertex)

        orElse
                .onAny().handleBy(endVertex)

        endVertex
                .onAny().complete()

                coordinates()
                    .vx(endVertex, 44, 568)
                    .vx(orElse, 237, 302)
                    .vx(second, -186, 293)
                    .vx(first, 44, 310, 51, 450)
                    .vx(resolveFlow, 10, 90, 45, 177);
    }
}