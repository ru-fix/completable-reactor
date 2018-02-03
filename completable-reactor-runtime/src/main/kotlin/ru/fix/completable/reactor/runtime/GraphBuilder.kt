package ru.fix.completable.reactor.runtime

import ru.fix.completable.reactor.graph.internal.GlGraph
import ru.fix.completable.reactor.graph.internal.ModelBuilder
import ru.fix.completable.reactor.model.validation.AtLeastOneEndPointExistValidator
import ru.fix.completable.reactor.model.validation.ValidationFailed
import ru.fix.completable.reactor.model.validation.ValidationSucceed

class GraphBuilder {
    private val validators = listOf(AtLeastOneEndPointExistValidator())
    private val modelBuilder = ModelBuilder()

    fun buildGraph(graph: GlGraph) {

        val graphModel = modelBuilder.build(graph)

        validators.forEach {
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
    }
}