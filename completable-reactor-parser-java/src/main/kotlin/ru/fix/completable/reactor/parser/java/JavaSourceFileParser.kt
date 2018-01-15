package ru.fix.completable.reactor.parser.java

import com.sun.scenario.effect.Merge
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import ru.fix.completable.reactor.api.gl.ReactorGraphVisualModel
import ru.fix.completable.reactor.api.gl.ReactorGraphVisualModel.*
import ru.fix.completable.reactor.parser.java.antlr.GraphConfigJava9Lexer
import ru.fix.completable.reactor.parser.java.antlr.GraphConfigJava9Parser

class JavaSourceFileParser {
    fun parse(body: String): ReactorGraphVisualModel {
        val result = ReactorGraphVisualModel()

        val lexer = GraphConfigJava9Lexer(CharStreams.fromString(body))
        val tokens = CommonTokenStream(lexer)
        val parser = GraphConfigJava9Parser(tokens)

        val graphBlocks = parser.sourceFile().graphBlock()

        val handlers = HashMap<String, Handler>()
        val mergers = HashMap<String, Merger>()
        val routers = HashMap<String, Router>()
        val subgraphs = HashMap<String, Subgraph>()

        fun vertexByName(name: String) = handlers[name] ?: subgraphs[name] ?: routers[name]!!

        //named figures
        graphBlocks.asIterable()
                .mapNotNull { it.vertexInitializationBlock() }
                .forEach {
                    val vertexName = it.Identifier().text!!

                    it.vertexInitializationStaticSection().vertexBuilder().let {

                        it.builderHandler()?.apply {
                            //handler
                            handlers[vertexName] = Handler(vertexName)

                            builderMerger().builderWithMerger()?.apply {
                                mergers[vertexName] = Merger(vertexName).apply {
                                    title = mergerTitle()?.text
                                }
                            }

                        } ?: it.buliderSubgraph().apply {
                            //subgraph
                            subgraphs[vertexName] = Subgraph(vertexName)

                            builderMerger().builderWithMerger()?.apply {
                                mergers[vertexName] = Merger(vertexName).apply {
                                    title = mergerTitle()?.text
                                }
                            }
                        }
                    }
                }

        //start point handleBy transitions
        graphBlocks.asIterable()
                .mapNotNull { it.payloadTransitionBlock() }
                .flatMap { it.handleBy() }
                .map { it.handleByVertex().Identifier().text }
                .forEach {
                    result.startPoint.handleBy.add(vertexByName(it))

                }

        //other transitions
        graphBlocks.asIterable()
                .mapNotNull { it.vertexTransitionBlock() }
                .forEach {
                    val vertex = vertexByName(it.Identifier().text)

                    it.vertexTransition().forEach {

                        val transition = Transition()
                        vertex.transitions.add(transition)


                        it.vertexTransitionOnAny()?.apply {
                            transition.isOnAny = true
                            //TODO process
                            transitionAction()
                        } ?: it.vertexTransitionOn().apply {
                            transition.mergeStatuses = transitionCondition().Identifier().map { text }.toList()
                            transitionAction()
                        }

                    }

                }

        return result
    }

}
