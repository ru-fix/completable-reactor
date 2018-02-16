package ru.fix.completable.reactor.runtime

import mu.KotlinLogging
import ru.fix.completable.reactor.graph.Graph
import ru.fix.completable.reactor.graph.Vertex
import ru.fix.completable.reactor.graph.internal.GlGraph
import ru.fix.completable.reactor.graph.internal.InternalGlAccessor
import ru.fix.completable.reactor.graph.internal.ModelBuilder
import ru.fix.completable.reactor.model.validation.*

private val log = KotlinLogging.logger {}

class GraphBuilder {

    private val modelBuilder = ModelBuilder()

    fun buildGraph(graph: Graph<Any?>, dependencyInjector: DependencyInjector?): GlGraph {

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


        injectDependencies(graph, dependencyInjector)


        return glGraph
    }

    private fun injectDependencies(graph: Graph<Any?>, dependencyInjector: DependencyInjector?) {
        //No need to cache since graph registration is rare operation
        graph.javaClass.declaredFields
                .asSequence()
                .filter { Vertex::class.java.isAssignableFrom(it.type) }
                .forEach {


                    try {
                        if (!it.isAccessible) {
                            it.isAccessible = true
                        }

                        if (dependencyInjector == null) {
                            log.warn {
                                "Found candidate for dependency injection: ${it.name} of type ${it.type}" +
                                        ",  but dependencyInjector is not provided to Completable Reactor." +
                                        " Skip field."
                            }
                        } else {
                            // could be NULL
                            val dependency = dependencyInjector.resolve(it.name, it.type)
                            it.set(graph, dependency)
                        }

                    } catch (exc: Exception) {
                        log.error(exc) {
                            "Failed to resolve and set dependency ${it.name} of type ${it.type} due to exception." +
                                    " Skip field."
                        }
                    }
                }
    }
}