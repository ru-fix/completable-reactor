package ru.fix.completable.reactor.runtime

import mu.KotlinLogging
import ru.fix.completable.reactor.graph.Graphable
import ru.fix.completable.reactor.graph.internal.InternalGlAccessor
import ru.fix.completable.reactor.graph.internal.ModelBuilder
import ru.fix.completable.reactor.graph.runtime.GlGraph
import ru.fix.completable.reactor.model.validation.ValidationFailed
import ru.fix.completable.reactor.model.validation.ValidationSucceed
import ru.fix.completable.reactor.model.validation.Validators

private val log = KotlinLogging.logger {}

class GraphBuilder {

    private val modelBuilder = ModelBuilder()

    fun buildGraph(graph: Graphable): GlGraph {

        val glGraph = InternalGlAccessor.graph(graph)

        if (glGraph.vertices.any { it.name == null }) {
            throw IllegalArgumentException("Detected illegal vertex that does not have name.")
        }

        glGraph.vertices
                .asSequence()
                .filter {
                    it.handler == null
                            && it.subgraphPayloadBuilder == null
                            && it.router == null
                }
                .map { it.name }
                .toList()
                .takeIf { it.isNotEmpty() }
                ?.let {
                    throw IllegalArgumentException("" +
                            "Detected illegal vertex that does not have" +
                            " handler, router or subgraph:" +
                            " $it")
                }

        val graphModel = modelBuilder.build(glGraph)

        Validators.get().forEach {
            val result = it.validate(graphModel)
            when (result) {
                is ValidationFailed -> IllegalArgumentException(
                        """
                        Graph validation failed.
                        ${result.message}
                        """
                )
                is ValidationSucceed -> Unit
            }
        }


        return glGraph
    }
}