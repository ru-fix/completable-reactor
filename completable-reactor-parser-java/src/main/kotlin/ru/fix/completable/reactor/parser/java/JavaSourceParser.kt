package ru.fix.completable.reactor.parser.java

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.Token
import ru.fix.completable.reactor.api.gl.model.*
import ru.fix.completable.reactor.parser.java.antlr.GraphConfigJava9Lexer
import ru.fix.completable.reactor.parser.java.antlr.GraphConfigJava9Parser

class JavaSourceParser(val listener: Listener) {

    interface Listener {
        fun error(msg: String)
    }

    fun parse(body: String): ReactorGraphCompilationModel {
        val model = ReactorGraphCompilationModel()

        val lexer = GraphConfigJava9Lexer(CharStreams.fromString(body))
        val tokens = CommonTokenStream(lexer)
        val parser = GraphConfigJava9Parser(tokens)

        val graphBlocks = parser.sourceFile().graphBlock()

        with(model) {

            fun createVertexFromVertexBuilder(vertexName: String,
                                              vertexBuilder: GraphConfigJava9Parser.VertexBuilderContext) {

                vertexBuilder.builderHandler()?.apply {
                    //handler
                    handlers[vertexName] = Handler(vertexName)

                    builderMerger().builderWithMerger()?.apply {
                        mergers[vertexName] = Merger(vertexName).apply {
                            title = mergerTitle()?.text
                        }
                    }

                } ?: vertexBuilder.buliderSubgraph().apply {
                    //subgraph
                    subgraphs[vertexName] = Subgraph(vertexName)

                    builderMerger().builderWithMerger()?.apply {
                        mergers[vertexName] = Merger(vertexName).apply {
                            title = mergerTitle()?.text
                        }
                    }
                }
            }

            //vertex
            graphBlocks.asIterable()
                    .mapNotNull { it.vertexInitializationBlock() }
                    .forEach {
                        val vertexName = it.Identifier().text!!

                        createVertexFromVertexBuilder(vertexName, it.vertexInitializationStaticSection().vertexBuilder())
                    }

            graphBlocks.asIterable()
                    .mapNotNull { it.vertexAssignmentBlock() }
                    .forEach {
                        val vertexName = it.Identifier().text!!

                        createVertexFromVertexBuilder(vertexName, it.vertexBuilder())
                    }

            //start point handleBy transitions
            graphBlocks.asIterable()
                    .mapNotNull { it.payloadTransitionBlock() }
                    .flatMap { it.handleBy() }
                    .map { it.handleByVertex().Identifier().symbol }
                    .forEach {
                        startPoint.handleBy.add(handleable[it.text] ?: return@forEach listener.error(
                                "Outgoing transition detected from payload to vertex $it" +
                                        " at ${tokenPosition(it)}" +
                                        " But declaration of ${it.text} not found."
                        ))
                    }

            //vertex handleBy and mergeBy transitions
            graphBlocks.asIterable()
                    .mapNotNull { it.vertexTransitionBlock() }
                    .forEach {
                        val transitionSourceVertex = it.Identifier().text
                        val vertex = mergers[transitionSourceVertex] ?: return@forEach listener.error(
                                "Outgoing transition detected for vertex $transitionSourceVertex" +
                                        " at ${tokenPosition(it.start)}." +
                                        " But declaration of $transitionSourceVertex not found.")

                        it.vertexTransition().forEach {

                            val transition = Transition()
                            vertex.transitions.add(transition)

                            (it.vertexTransitionOnAny()?.run {
                                transition.isOnAny = true
                                transitionAction()
                            } ?: it.vertexTransitionOn().run {
                                transition.mergeStatuses = listOf(transitionCondition().Identifier().last().text)
                                transitionAction()
                            }).run {
                                if (transitionActionComplete() != null) {
                                    transition.isComplete = true

                                    val endpoint = EndPoint(source = sourceFromToken(start))
                                    endpoints[vertex.name] = endpoint
                                    transition.target = endpoint

                                } else {
                                    transitionActionHandleBy()?.Identifier()?.run {
                                        transition.target = handleable[text] ?: return@run listener.error(
                                                "Transition target vertex $text at ${tokenPosition(symbol)}." +
                                                        " But declaration of $text not found.")

                                    } ?: transitionActionMergeBy()?.Identifier()?.run {
                                        transition.target = mergers[text] ?: return@run listener.error(
                                                "Transition target vertex $text at ${tokenPosition(symbol)}." +
                                                        " But declaration of $text not found.")
                                    }
                                }
                            }
                        }
                    }

            //coordinates
            graphBlocks.asIterable()
                    .mapNotNull { it.coordinatesBlock() }
                    .flatMap { it.coordinate() }
                    .forEach {
                        it.coordinatePayload()?.run {
                            startPoint.coordinates = Coordinates(
                                    IntegerLiteral()!!.first().text.toInt(),
                                    IntegerLiteral()!!.last().text.toInt())

                        } ?: it.coordinateComplete()?.run {
                            val vertexName = Identifier().text
                            val endpoint = endpoints[vertexName] ?: return@run listener.error(
                                    "Coordinate of endpoint for $vertexName exist at ${tokenPosition(this.start)}" +
                                            ", but vertex $vertexName does not have complete transition.")

                            endpoint.coordinates = Coordinates(
                                    IntegerLiteral().first().text.toInt(),
                                    IntegerLiteral().last().text.toInt())


                        } ?: it.coordinateHandler()?.run {
                            handlers[Identifier().text]!!.coordinates = Coordinates(
                                    IntegerLiteral().first().text.toInt(),
                                    IntegerLiteral().last().text.toInt())
                        } ?: it.coordinateMerger().run {
                            mergers[Identifier().text]!!.coordinates = Coordinates(
                                    IntegerLiteral().first().text.toInt(),
                                    IntegerLiteral().last().text.toInt())
                        }
                    }
        }

        return model
    }

    private fun tokenPosition(token: Token) = "${token.line}:${token.charPositionInLine}"

    private fun sourceFromToken(token: Token?): Source? {
        if (token == null) {
            return null
        }
        return Source(
                line = token.line,
                lineOffset = token.charPositionInLine
        )
    }
}
