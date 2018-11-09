package ru.fix.completable.reactor.runtime

import mu.KotlinLogging
import ru.fix.completable.reactor.graph.Graphable
import ru.fix.completable.reactor.graph.internal.BuilderContext
import ru.fix.completable.reactor.graph.internal.InternalDslAccessor
import ru.fix.completable.reactor.graph.internal.ModelBuilder
import ru.fix.completable.reactor.graph.runtime.RuntimeGraph
import ru.fix.completable.reactor.model.validation.ValidationFailed
import ru.fix.completable.reactor.model.validation.ValidationSucceed
import ru.fix.completable.reactor.model.validation.Validators

private val log = KotlinLogging.logger {}

class GraphBuilder {

    private val modelBuilder = ModelBuilder()

    fun buildGraph(graph: Graphable): RuntimeGraph {

        val glGraph = InternalDslAccessor.graph(graph)

        glGraph.vertices.filter { it.name == null }.takeIf { it.isNotEmpty() }?.let { unusedVertices ->

            val contex = BuilderContext().apply { setGraph(graph, glGraph) }
            val verticesNames = unusedVertices.map { contex.resolveVertexName(it.sourceVertex) }.joinToString()

            throw IllegalArgumentException("" +
                    "Detected illegal vertices that does not have names and probably was not properly initialized." +
                    " Could you please check if this vertices participates in any transitions of the graph:" +
                    " $verticesNames")
        }

        glGraph.vertices.filter { it.type == null }.takeIf { it.isNotEmpty() }?.let { untypedVertices ->

            val contex = BuilderContext().apply { setGraph(graph, glGraph) }
            val verticesNames = untypedVertices.map { contex.resolveVertexName(it.sourceVertex) }.joinToString()

            throw IllegalStateException("Failed to resolve type for vertices: $verticesNames")
        }

        glGraph.vertices
                .asSequence()
                .filter {
                    it.handler == null
                            && it.subgraphPayloadBuilder == null
                }
                .map { it.name }
                .toList()
                .takeIf { it.isNotEmpty() }
                ?.let {
                    throw IllegalArgumentException("" +
                            "Detected illegal vertex that does not have handler or subgraph: $it")
                }

        val graphModel = modelBuilder.build(glGraph)

        Validators.get().forEach {
            val result = it.validate(graphModel)
            when (result) {
                is ValidationFailed -> IllegalArgumentException(
                        """
                        Graph validation failed.
                        ${result.message}
                        """.trimIndent()
                )
                is ValidationSucceed -> Unit
            }
        }


        return glGraph
    }
}