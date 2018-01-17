package ru.fix.completable.reactor.parser.java

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.Token
import ru.fix.completable.reactor.api.gl.ReactorGraphVisualModel
import ru.fix.completable.reactor.api.gl.ReactorGraphVisualModel.*
import ru.fix.completable.reactor.parser.java.antlr.GraphConfigJava9Lexer
import ru.fix.completable.reactor.parser.java.antlr.GraphConfigJava9Parser

class JavaSourceParser(val listener: Listener) {

    interface Listener {
        fun error(msg: String)
    }

    fun parse(body: String): ReactorGraphVisualModel {
        val model = ReactorGraphVisualModel()

        val lexer = GraphConfigJava9Lexer(CharStreams.fromString(body))
        val tokens = CommonTokenStream(lexer)
        val parser = GraphConfigJava9Parser(tokens)

        val graphBlocks = parser.sourceFile().graphBlock()

        val handlers = HashMap<String, Handler>()
        val mergers = HashMap<String, Merger>()
        val routers = HashMap<String, Router>()
        val subgraphs = HashMap<String, Subgraph>()
        val endpoints = HashMap<String, EndPoint>()

        fun vertexByName(name: String) = handlers[name] ?: subgraphs[name] ?: routers[name]!!

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
                .map { it.handleByVertex().Identifier().text }
                .forEach {
                    model.startPoint.handleBy.add(vertexByName(it))

                }

        //vertex handleBy and mergeBy transitions
        graphBlocks.asIterable()
                .mapNotNull { it.vertexTransitionBlock() }
                .forEach {
                    val vertex = vertexByName(it.Identifier().text)

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

                                val endpoint = EndPoint()
                                endpoints[vertex.name] = endpoint
                                transition.target = endpoint

                            } else {
                                transitionActionHandleBy()?.Identifier()?.run {
                                    transition.target = vertexByName(text)
                                } ?: transitionActionMergeBy()?.Identifier()?.run {
                                    transition.target = mergers[text]
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
                        model.startPoint.coordinates = Coordinates(
                                IntegerLiteral()!!.first().text.toInt(),
                                IntegerLiteral()!!.last().text.toInt())

                    } ?: it.coordinateComplete()?.run {
                        val vertexName = Identifier().text
                        val endpoint = endpoints[vertexName] ?: return@run listener.error(
                                "Coordinate of endpoint for $vertexName exist" +
                                        ", but vertex $vertexName does not have complete transition." +
                                        "\n${this.start.line}")
                        TODO("line number")


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

        return model
    }
}
