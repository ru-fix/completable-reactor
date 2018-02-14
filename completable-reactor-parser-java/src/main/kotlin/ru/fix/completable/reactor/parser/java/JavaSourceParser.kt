package ru.fix.completable.reactor.parser.java

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.Token
import org.antlr.v4.runtime.tree.TerminalNode
import ru.fix.completable.reactor.model.*
import ru.fix.completable.reactor.parser.java.antlr.GraphConfigJava9Lexer
import ru.fix.completable.reactor.parser.java.antlr.GraphConfigJava9Parser
import ru.fix.completable.reactor.parser.java.antlr.GraphConfigJava9Parser.VertexBuilderContext

class JavaSourceParser(val listener: Listener) {

    interface Listener {
        fun error(msg: String)
    }

    fun parse(body: String, filePath: String): List<GraphModel> {
        val result = ArrayList<GraphModel>()

        val lexer = GraphConfigJava9Lexer(CharStreams.fromString(body))

        //Do not log into stderr in case of invalid tokens
        lexer.removeErrorListeners()

        val tokens = CommonTokenStream(lexer)
        val parser = GraphConfigJava9Parser(tokens)

        val sourceBlocks = parser.sourceFile().graphBlock()

        if (sourceBlocks.isEmpty()) {
            return emptyList()
        }

        val declarationBlockIndexes = sourceBlocks
                .asSequence()
                .mapIndexedNotNull { index, block -> block.graphDeclarationBlock()?.let { index } }
                .toMutableList()

        if (declarationBlockIndexes.isEmpty()) {
            return emptyList()
        }

        for (borderStart in 0 until declarationBlockIndexes.size) {

            val model = GraphModel()

            val graphBlocks = sourceBlocks.slice(
                    declarationBlockIndexes[borderStart]
                            until
                            if (borderStart + 1 < declarationBlockIndexes.size)
                                declarationBlockIndexes[borderStart + 1]
                            else
                                sourceBlocks.size)

            with(model) {

                //startPoint
                graphBlocks.asSequence()
                        .mapNotNull { it.graphDeclarationBlock() }
                        .forEach {
                            tokens.checkCommentsToLeft(it.start.tokenIndex)?.run {
                                startPoint.title = title
                                startPoint.doc = doc
                            }
                            graphClass = it.graphClass().text
                            startPoint.payloadType = it.payloadType()?.Identifier()?.text

                            graphDeclarationLocation = sourceFromToken(it.start, filePath)
                        }


                //vertex
                graphBlocks.asSequence()
                        .mapNotNull { it.vertexInitializationBlock() }
                        .forEach {
                            val vertexName = it.Identifier().text!!

                            createVertexFromVertexBuilder(
                                    tokens,
                                    model,
                                    vertexName,
                                    it.vertexInitializationStaticSection().vertexBuilder(),
                                    filePath)
                        }

                graphBlocks.asSequence()
                        .mapNotNull { it.vertexAssignmentBlock() }
                        .forEach {
                            val vertexName = it.vertexName().text!!

                            createVertexFromVertexBuilder(tokens, model, vertexName, it.vertexBuilder(), filePath)
                        }

                //start point handleBy transitions
                graphBlocks.asSequence()
                        .mapNotNull { it.payloadTransitionBlock() }
                        .also { startPoint.source = sourceFromToken(it.firstOrNull()?.start, filePath) }
                        .flatMap { it.handleBy().asSequence() }
                        .map { it.vertexName().Identifier().symbol }
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
                            val transitionSourceVertex = it.vertexName().text
                            val vertex = transitionable[transitionSourceVertex] ?: return@forEach listener.error(
                                    "Outgoing transition detected for vertex $transitionSourceVertex" +
                                            " at ${tokenPosition(it.start)}." +
                                            " But declaration of $transitionSourceVertex not found.")

                            it.vertexTransition().forEach {

                                val transition = Transition()

                                (it.vertexTransitionOnAny()?.run {
                                    transition.isOnAny = true
                                    transitionAction()
                                } ?: it.vertexTransitionOn().run {
                                    transition.mergeStatuses = setOf(transitionCondition().Identifier().last().text)
                                    transitionAction()
                                }).run {
                                    if (transitionActionComplete() != null) {
                                        transition.isComplete = true

                                        val endpoint = EndPoint(
                                                name = vertex.name,
                                                source = sourceFromToken(start, filePath))
                                        endpoints[vertex.name] = endpoint
                                        transition.target = endpoint

                                    } else {
                                        transitionActionHandleBy()?.vertexName()?.run {
                                            transition.target = handleable[text] ?: return@run listener.error(
                                                    "Transition target vertex $text at ${tokenPosition(start)}." +
                                                            " But declaration of $text not found.")

                                        } ?: transitionActionMergeBy()?.vertexName()?.run {
                                            transition.target = transitionable[text] as? Figure ?: return@run listener.error(
                                                    "Transition target vertex $text at ${tokenPosition(start)}." +
                                                            " But declaration of $text not found.")
                                        }
                                    }
                                }

                                mergeTransitions(transition, vertex.transitions)
                            }
                        }

                //coordinates
                graphBlocks.asSequence()
                        .mapNotNull { it.coordinatesBlock() }
                        .map {
                            coordinatesStart = sourceFromToken(it.start, filePath)
                            coordinatesEnd = sourceFromEndOfToken(it.stop, filePath)

                            it
                        }
                        .flatMap { it.coordinate().asSequence() }
                        .forEach {
                            it.coordinatePayload()?.run {
                                startPoint.coordinates = Coordinates(
                                        Coordinate()!!.first().text.toInt(),
                                        Coordinate()!!.last().text.toInt())

                            } ?: it.coordinateComplete()?.run {
                                val vertexName = Identifier().text
                                val endpoint = endpoints[vertexName] ?: return@run listener.error("" +
                                        "Failed to set coordinates." +
                                        " Coordinate of endpoint for $vertexName exist" +
                                        " at ${tokenPosition(this.start)}" +
                                        ", but vertex $vertexName does not have complete transition.")

                                endpoint.coordinates = Coordinates(
                                        Coordinate().first().text.toInt(),
                                        Coordinate().last().text.toInt())


                            } ?: it.coordinateHandler()?.run {

                                val vertexName = Identifier().text
                                val handler = handlers[vertexName] ?: return@run listener.error("" +
                                        "Failed to set coordinates." +
                                        " Handler $vertexName not found.")

                                handler.coordinates = Coordinates(
                                        Coordinate().first().text.toInt(),
                                        Coordinate().last().text.toInt())


                            } ?: it.coordinateSubgraph()?.run {
                                val vertexName = Identifier().text
                                val subgraph = subgraphs[vertexName] ?: return@run listener.error("" +
                                        "Failed to set coordinates." +
                                        " Subgraph $vertexName not found.")

                                subgraph.coordinates = Coordinates(
                                        Coordinate().first().text.toInt(),
                                        Coordinate().last().text.toInt())


                            } ?: it.coordinateRouter()?.run {
                                val vertexName = Identifier().text
                                val router = routers[vertexName] ?: return@run listener.error("" +
                                        "Failed to set coordinates." +
                                        " Router $vertexName not found.")

                                router.coordinates = Coordinates(
                                        Coordinate().first().text.toInt(),
                                        Coordinate().last().text.toInt())


                            } ?: it.coordinateMerger().run {
                                val vertexName = Identifier().text
                                val merger = mergers[vertexName] ?: return@run listener.error("" +
                                        "Failed to set coordinates." +
                                        " Merger $vertexName not found.")

                                merger.coordinates = Coordinates(
                                        Coordinate().first().text.toInt(),
                                        Coordinate().last().text.toInt())

                            }
                        }

                //end of last declaration block
                endOfLastCodeBlocksWithinGraph = graphBlocks
                        .asSequence()
                        .sortedBy { it.stop.line }
                        .last()
                        ?.let {
                            sourceFromEndOfToken(it.stop, filePath)
                        }
            }

            result.add(model)
        }

        return result
    }

    private fun mergeTransitions(transition: Transition, transitions: MutableList<Transition>) {
        if (transitions.isNotEmpty()) {
            val existingTransition = transitions.firstOrNull {
                it.target == transition.target &&
                        it.mergeStatuses.isNotEmpty()
            }

            if (existingTransition != null && transition.mergeStatuses.isNotEmpty()) {
                existingTransition.mergeStatuses = existingTransition.mergeStatuses + transition.mergeStatuses

            } else {
                transitions.add(transition)
            }

        } else {
            transitions.add(transition)
        }
    }

    private fun CommonTokenStream.checkCommentsToRight(tokenIndex: Int): Comment? =
            this.getHiddenTokensToRight(tokenIndex)
                    ?.takeIf { it.size > 0 }
                    ?.first()
                    ?.run {
                        parseComment(text)
                    }

    private fun CommonTokenStream.checkCommentsToLeft(tokenIndex: Int): Comment? =
            this.getHiddenTokensToLeft(tokenIndex)
                    ?.takeIf { it.size > 0 }
                    ?.first()
                    ?.run {
                        parseComment(text)
                    }

    private fun createVertexFromVertexBuilder(
            tokens: CommonTokenStream,
            model: GraphModel,
            vertexName: String,
            vertexBuilder: VertexBuilderContext,
            fileName: String) = with(model) {

        vertexBuilder.builderHandler()?.apply {
            //handler
            handlers[vertexName] = Handler(vertexName).also {
                it.source = sourceFromToken(start, fileName)

                tokens.checkCommentsToRight(this.LPAREN().symbol.tokenIndex)?.run {
                    it.title = title
                    it.doc = doc
                }
            }

            builderMerger().builderWithMerger()?.apply {
                mergers[vertexName] = Merger(vertexName).also {
                    it.source = sourceFromToken(start, fileName)

                    tokens.checkCommentsToRight(this.LPAREN().symbol.tokenIndex)?.run {
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
                it.source = sourceFromToken(start, fileName)

                tokens.checkCommentsToRight(this.LPAREN().symbol.tokenIndex)?.run {
                    it.title = title
                    it.doc = doc
                }
            }

            builderMerger().builderWithMerger()?.apply {
                //merger
                mergers[vertexName] = Merger(vertexName).also {
                    it.source = sourceFromToken(start, fileName)

                    tokens.checkCommentsToRight(this.LPAREN().symbol.tokenIndex)?.run {
                        it.title = title
                        it.doc = doc
                    }

                }
            }
        } ?: vertexBuilder.builderRouter()?.apply {
            //router
            routers[vertexName] = Router(vertexName).also {
                it.source = sourceFromToken(start, fileName)

                tokens.checkCommentsToRight(this.LPAREN().symbol.tokenIndex)?.run {
                    it.title = title
                    it.doc = doc
                }
            }
        }
    }

    private fun tokenPosition(token: Token) = "${token.line}:${token.charPositionInLine}"

    private fun sourceFromToken(token: Token?, filePath: String): Source? {
        if (token == null) {
            return null
        }

        return Source(
                filePath = filePath,
                line = token.line,
                column = 1 + token.charPositionInLine,
                offset = token.startIndex
        )
    }

    private fun sourceFromEndOfToken(token: Token?, filePath: String): Source? {
        if (token == null) {
            return null
        }

        return Source(
                filePath = filePath,
                line = token.line,
                column = 1 + token.charPositionInLine + token.text.length,
                offset = token.stopIndex + token.text.length
        )
    }

    data class Comment(val title: String?, val doc: String?)


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
