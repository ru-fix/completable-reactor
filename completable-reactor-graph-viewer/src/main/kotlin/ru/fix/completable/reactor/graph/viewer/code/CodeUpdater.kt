package ru.fix.completable.reactor.graph.viewer.code

import ru.fix.completable.reactor.graph.viewer.code.CoordinateCodePhrase.*
import ru.fix.completable.reactor.model.CompileTimeGraph
import ru.fix.completable.reactor.model.Source

/**
 * @author Kamil Asfandiyarov
 */
class CodeUpdater(private val codeType: CodeType) {

    enum class CodeType { JAVA, KOTLIN }

    companion object {
        private val coordinateTypeOrder = listOf(
                StartPointPhrase::class,
                PlainVertexPhrase::class,
                ComplexVertexPhrase::class,
                EndPointPhrase::class)
                .mapIndexed { index, type -> Pair(type, index) }
                .toMap()
    }

    private fun coordinateCodePhraseFromModel(graphModel: CompileTimeGraph): List<CoordinateCodePhrase> {
        val result = ArrayList<CoordinateCodePhrase>()

        with(graphModel) {

            startPoint.coordinates?.run {
                result.add(StartPointPhrase(x, y))
            }

            handlers.values.forEach { handler ->

                val handlerCoordinates = handler.coordinates
                val mergerCoordinates = mergers[handler.name]?.coordinates

                if (handlerCoordinates != null) {
                    if (mergerCoordinates != null) {
                        result.add(ComplexVertexPhrase(
                                handler.name,
                                handlerCoordinates.x,
                                handlerCoordinates.y,
                                mergerCoordinates.x,
                                mergerCoordinates.y))
                    } else {
                        result.add(PlainVertexPhrase(
                                handler.name,
                                handlerCoordinates.x,
                                handlerCoordinates.y
                        ))
                    }
                }
            }

            subgraphs.values.forEach { subgraph ->

                val subgraphCoordinates = subgraph.coordinates
                val mergerCoordinates = mergers[subgraph.name]?.coordinates

                if (subgraphCoordinates != null) {
                    if (mergerCoordinates != null) {
                        result.add(ComplexVertexPhrase(
                                subgraph.name,
                                subgraphCoordinates.x,
                                subgraphCoordinates.y,
                                mergerCoordinates.x,
                                mergerCoordinates.y))
                    } else {
                        result.add(PlainVertexPhrase(
                                subgraph.name,
                                subgraphCoordinates.x,
                                subgraphCoordinates.y
                        ))
                    }
                }
            }

            routers.values.forEach { router ->
                val routerCoordinates = router.coordinates
                if (routerCoordinates != null) {
                    result.add(PlainVertexPhrase(
                            router.name,
                            routerCoordinates.x,
                            routerCoordinates.y
                    ))
                }
            }

            endpoints.values.forEach { endpoint ->
                endpoint.coordinates?.run {
                    result.add(EndPointPhrase(endpoint.name, x, y))
                }
            }
        }
        return result
    }

    private fun printCoordinates(coordinates: List<CoordinateCodePhrase>, padding: String): String {
        return coordinates.asSequence()
                .sortedWith(Comparator { item1, item2 ->
                    if (item1 == null || item2 == null) {
                        0
                    } else {

                        //compare Type
                        if (item1::class == item2::class
                                && item1 is NamedPhrase
                                && item2 is NamedPhrase) {

                            //type are equals, compare processor
                            return@Comparator item1.name.compareTo(item2.name)


                        } else {
                            Integer.compare(coordinateTypeOrder[item1::class]!!, coordinateTypeOrder[item2::class]!!)
                        }
                    }
                })
                .joinToString(
                        separator = "\n$padding",
                        prefix = "coordinates()\n$padding",
                        postfix = if (codeType == CodeType.JAVA) ";" else "") { item ->

                    generateCoordinateBuilderMemberCode(item)
                }
    }


    /**
     * @param codeBlock block of code, that starts with coordinates() method end ends with ;
     */
    internal fun updateCoordinates(codeBlock: String, coordinates: List<CoordinateCodePhrase>): String {

        val padding = codeBlock.lineSequence().last()
                .let {
                    it.substring(0, it.indexOfFirst { !it.isWhitespace() })
                }

        return printCoordinates(coordinates, padding)
    }


    private fun generateCoordinateBuilderMemberCode(phrase: CoordinateCodePhrase): String {
        return when (phrase) {
            is StartPointPhrase -> ".pd(${phrase.x}, ${phrase.y})"
            is PlainVertexPhrase -> ".vx(${phrase.name}, ${phrase.x}, ${phrase.y})"
            is ComplexVertexPhrase -> ".vx(${phrase.name}, ${phrase.x}, ${phrase.y}, ${phrase.x2}, ${phrase.y2})"
            is EndPointPhrase -> ".ct(${phrase.name}, ${phrase.x}, ${phrase.y})"
        }
    }

    interface Editor {
        fun getCodeBlock(coordinatesStart: Source, coordinatesEnd: Source): String
        fun replaceCodeBlock(start: Source, end: Source, newCodeBlock: String)
        fun insert(position: Source, newCodeBlock: String)
    }


    fun updateCoordinates(graphModel: CompileTimeGraph, editor: Editor) {

        val coordinates = coordinateCodePhraseFromModel(graphModel)

        val start = graphModel.coordinatesStart
        val end = graphModel.coordinatesEnd

        if (start != null && end != null) {
            val codeBlock = editor.getCodeBlock(graphModel.coordinatesStart!!, graphModel.coordinatesEnd!!)

            val newCodeBlock = updateCoordinates(codeBlock, coordinates)
            editor.replaceCodeBlock(start, end, newCodeBlock)

        } else {
            val endOfBlock = graphModel.endOfLastCodeBlocksWithinGraph

            if (endOfBlock != null) {

                val startOfLine = Source(
                        filePath = endOfBlock.filePath,
                        line = endOfBlock.line,
                        column = 1,
                        offset = endOfBlock.offset - endOfBlock.column + 1
                )

                val padding = editor.getCodeBlock(startOfLine, endOfBlock).let {
                    it.substring(0, it.indexOfFirst { !it.isWhitespace() })
                }

                editor.insert(endOfBlock, "\n\n$padding${printCoordinates(coordinates, padding + "    ")}")
            }
        }
    }
}