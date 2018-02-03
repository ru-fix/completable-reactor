package ru.fix.completable.reactor.parser.java

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.Token
import org.antlr.v4.runtime.tree.TerminalNode
import ru.fix.completable.reactor.model.*
import ru.fix.completable.reactor.parser.java.antlr.GraphConfigJava9Lexer
import ru.fix.completable.reactor.parser.java.antlr.GraphConfigJava9Parser

class JavaSourceParser(val listener: Listener) {

    interface Listener {
        fun error(msg: String)
    }

    fun parse(body: String): GraphModel {
        val model = GraphModel()

        val lexer = GraphConfigJava9Lexer(CharStreams.fromString(body))
        val tokens = CommonTokenStream(lexer)
        val parser = GraphConfigJava9Parser(tokens)

        val graphBlocks = parser.sourceFile().graphBlock()

        fun checkCommentsToRight(tokenIndex: Int): Comment? =
                tokens.getHiddenTokensToRight(tokenIndex)
                        ?.takeIf { it.size > 0 }
                        ?.first()
                        ?.run {
                            parseComment(text)
                        }

        fun checkCommentsToLeft(tokenIndex: Int): Comment? =
                tokens.getHiddenTokensToLeft(tokenIndex)
                        ?.takeIf { it.size > 0 }
                        ?.first()
                        ?.run {
                            parseComment(text)
                        }


        with(model) {

            //startPoint
            graphBlocks.asSequence()
                    .mapNotNull { it.graphDeclarationBlock() }
                    .forEach {
                        checkCommentsToLeft(it.start.tokenIndex)?.run {
                            startPoint.title = title
                            startPoint.doc = doc
                        }
                        startPoint.payloadType = it.payloadType()?.Identifier()?.text
                    }

            fun createVertexFromVertexBuilder(vertexName: String,
                                              vertexBuilder: GraphConfigJava9Parser.VertexBuilderContext) {

                vertexBuilder.builderHandler()?.apply {
                    //handler
                    handlers[vertexName] = Handler(vertexName).also {
                        it.source = sourceFromToken(start)

                        checkCommentsToRight(this.LPAREN().symbol.tokenIndex)?.run {
                            it.title = title
                            it.doc = doc
                        }
                    }

                    builderMerger().builderWithMerger()?.apply {
                        mergers[vertexName] = Merger(vertexName).also {
                            it.source = sourceFromToken(start)

                            checkCommentsToRight(this.LPAREN().symbol.tokenIndex)?.run {
                                it.title = title
                                it.doc = doc
                            }
                        }
                    }

                } ?: vertexBuilder.builderSubgraph()?.apply {
                    //subgraph
                    subgraphs[vertexName] = Subgraph(
                            name = vertexName,
                            payloadClass = subgraphPayloadClass().text
                    ).also {
                        it.source = sourceFromToken(start)

                        checkCommentsToRight(this.LPAREN().symbol.tokenIndex)?.run {
                            it.title = title
                            it.doc = doc
                        }
                    }

                    builderMerger().builderWithMerger()?.apply {
                        //merger
                        mergers[vertexName] = Merger(vertexName).also {
                            it.source = sourceFromToken(start)

                            checkCommentsToRight(this.LPAREN().symbol.tokenIndex)?.run {
                                it.title = title
                                it.doc = doc
                            }

                        }
                    }
                } ?: vertexBuilder.builderRouter()?.apply {
                    //router
                    routers[vertexName] = Router(vertexName).also {
                        it.source = sourceFromToken(start)

                        checkCommentsToRight(this.LPAREN().symbol.tokenIndex)?.run {
                            it.title = title
                            it.doc = doc
                        }
                    }
                }
            }

            //vertex
            graphBlocks.asSequence()
                    .mapNotNull { it.vertexInitializationBlock() }
                    .forEach {
                        val vertexName = it.Identifier().text!!

                        createVertexFromVertexBuilder(vertexName, it.vertexInitializationStaticSection().vertexBuilder())
                    }

            graphBlocks.asSequence()
                    .mapNotNull { it.vertexAssignmentBlock() }
                    .forEach {
                        val vertexName = it.vertexAssignmentName().text!!

                        createVertexFromVertexBuilder(vertexName, it.vertexBuilder())
                    }

            //start point handleBy transitions
            graphBlocks.asSequence()
                    .mapNotNull { it.payloadTransitionBlock() }
                    .also { startPoint.source = sourceFromToken(it.firstOrNull()?.start) }
                    .flatMap { it.handleBy().asSequence() }
                    .map { it.handleByVertex().Identifier().symbol }
                    .forEach {
                        startPoint.handleBy.add(handleable[it.text] ?: return@forEach listener.error(
                                "Outgoing transition detected from payload to vertex $it" +
                                        " at ${tokenPosition(it)}" +
                                        " But declaration of ${it.text} not found."
                        ))
                    }

            //vertex handleBy and mergeBy transitions
            graphBlocks.asSequence()
                    .mapNotNull { it.vertexTransitionBlock() }
                    .forEach {
                        val transitionSourceVertex = it.Identifier().text
                        val vertex = transitionable[transitionSourceVertex] ?: return@forEach listener.error(
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

                                    val endpoint = EndPoint(
                                            name = vertex.name,
                                            source = sourceFromToken(start))
                                    endpoints[vertex.name] = endpoint
                                    transition.target = endpoint

                                } else {
                                    transitionActionHandleBy()?.Identifier()?.run {
                                        transition.target = handleable[text] ?: return@run listener.error(
                                                "Transition target vertex $text at ${tokenPosition(symbol)}." +
                                                        " But declaration of $text not found.")

                                    } ?: transitionActionMergeBy()?.Identifier()?.run {
                                        transition.target = transitionable[text] as? Figure ?: return@run listener.error(
                                                "Transition target vertex $text at ${tokenPosition(symbol)}." +
                                                        " But declaration of $text not found.")
                                    }
                                }
                            }
                        }
                    }

            //coordinates
            graphBlocks.asSequence()
                    .mapNotNull { it.coordinatesBlock() }
                    .flatMap { it.coordinate().asSequence() }
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

            //buildGraph()
            graphBlocks.asSequence()
                    .mapNotNull { it.buildGraphBlock() }
                    .forEach {
                        if (buildGraphSource == null) {
                            buildGraphSource = sourceFromToken(it.start)
                        } else {
                            listener.error("Found second buildGraph() invocation at ${tokenPosition(it.start)}." +
                                    " Expected single buildGraph() per source file.")
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

    private fun textFromStringLiteral(token: TerminalNode): String {
        val text = token.text
        if (text.startsWith("\"") && text.endsWith("\"")) {
            return text.substring(1, text.length - 1)
        }
        return text
    }

    data class Comment(val title: String?, val doc: String?)

    val lineCommentRegex = """^\s*//""".toRegex(RegexOption.MULTILINE)

    fun parseComment(commentText: String): Comment {
        val lines = commentText.split('\n')
                .asSequence()
                .map { it.trim() }
                .map {
                    when {
                        it.startsWith("//") -> it.substring("//".length).trim()
                        it.startsWith("/**") -> it.substring("/**".length).trim()
                        it.startsWith("/*") -> it.substring("/*".length).trim()
                        it.endsWith("*/") -> it.substring(0, it.length - "*/".length).trim()
                        it.startsWith("*") -> it.substring("*".length).trim()
                        else -> it
                    }
                }
                .dropWhile { it.isBlank() }
                .toList()
                .dropLastWhile { it.isBlank() }

        if (lines.isEmpty()) {
            return Comment(null, null)
        }

        val title: String?
        val doc: String

        if (lines[0].startsWith('#')) {
            title = lines[0].substring(1).trim()
            doc = lines.drop(1).joinToString("\n")
        } else {
            title = null
            doc = lines.joinToString("\n")
        }

        return Comment(title, doc.takeIf { it.isNotBlank() })
    }
}
