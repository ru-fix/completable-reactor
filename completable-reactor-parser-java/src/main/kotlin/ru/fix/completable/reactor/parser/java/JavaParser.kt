package ru.fix.completable.reactor.parser.java

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import ru.fix.completable.reactor.api.ReactorGraphModel
import ru.fix.completable.reactor.parser.java.antlr.GraphConfigJava9Lexer
import ru.fix.completable.reactor.parser.java.antlr.GraphConfigJava9Parser

/**
 * @author Kamil Asfandiyarov
 */
class JavaParser {
    fun parse(javaCode: String): List<ReactorGraphModel> {

        val result = ArrayList<ReactorGraphModel>()

        val lexer = GraphConfigJava9Lexer(CharStreams.fromString(javaCode))
        val tokens = CommonTokenStream(lexer)
        val parser = GraphConfigJava9Parser(tokens)


        val graphBlocks = parser.sourceFile().graphBlock()


        val model = ReactorGraphModel()
        result.add(model)

        val vertices = graphBlocks.asIterable()
                .mapNotNull { it.vertexInitializationBlock() }
                .map { it.Identifier().text }
                .toList()

        println("vertices: $vertices")

        val payloadTransitions = graphBlocks.asIterable()
                .mapNotNull { it.payloadTransitionBlock() }
                .flatMap { it.handleBy() }
                .map { it.handleByVertex().Identifier().text }
                .toList()

        println("payloadTransitions: $payloadTransitions")


        return result
    }

}
