package ru.fix.completable.reactor.graph.viewer.gl.code

import ru.fix.completable.reactor.graph.viewer.gl.code.CoordinateCodePhrase.*
import ru.fix.completable.reactor.model.GraphModel
import ru.fix.completable.reactor.model.Source

/**
 * @author Kamil Asfandiyarov
 */
class CodeUpdater {

    companion object {
        private val coordinateTypeOrder = listOf(
                StartPoint::class,
                Handler::class,
                Subgraph::class,
                Router::class,
                Merger::class,
                EndPoint::class)
                .mapIndexed { index, type -> Pair(type, index) }
                .toMap()
    }

    private fun coordinateCodePhraseFromModel(graphModel: GraphModel): List<CoordinateCodePhrase> {
        val result = ArrayList<CoordinateCodePhrase>()

        with(graphModel) {

            startPoint.coordinates?.run {
                result.add(StartPoint(x, y))
            }

            handlers.values.forEach {
                it.coordinates?.run {
                    result.add(Handler(it.name, x, y))
                }
            }

            subgraphs.values.forEach {
                it.coordinates?.run {
                    result.add(Subgraph(it.name, x, y))
                }
            }

            routers.values.forEach {
                it.coordinates?.run {
                    result.add(Router(it.name, x, y))
                }
            }

            mergers.values.forEach {
                it.coordinates?.run {
                    result.add(Merger(it.name, x, y))
                }
            }

            endpoints.values.forEach {
                it.coordinates?.run {
                    result.add(EndPoint(it.name, x, y))
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
                                && item1 is NamedCoordinateCodePhrase
                                && item2 is NamedCoordinateCodePhrase) {

                            //type are equals, compare processor
                            val cmpProc = item1.name.compareTo(item2.name)

                            if (cmpProc == 0) {
                                //processors are equals
                                val cmpx = Integer.compare(item1.x, item2.x)
                                if (cmpx == 0) {
                                    Integer.compare(item1.y, item2.y)
                                } else {
                                    cmpx
                                }
                            } else {
                                cmpProc
                            }
                        } else {
                            Integer.compare(coordinateTypeOrder[item1::class]!!, coordinateTypeOrder[item2::class]!!)
                        }
                    }
                })
                .joinToString(separator = "\n$padding", prefix = "coordinates()\n$padding", postfix = ";") { item ->
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
            is StartPoint -> ".payload(${phrase.x}, ${phrase.y})"
            is Handler -> ".handler(${phrase.name}, ${phrase.x}, ${phrase.y})"
            is Subgraph -> ".subgraph(${phrase.name}, ${phrase.x}, ${phrase.y})"
            is Merger -> ".merger(${phrase.name}, ${phrase.x}, ${phrase.y})"
            is Router -> ".router(${phrase.name}, ${phrase.x}, ${phrase.y})"
            is EndPoint -> ".complete(${phrase.name}, ${phrase.x}, ${phrase.y})"
            else -> throw IllegalArgumentException("Invalid type ${phrase::class} $phrase")
        }
    }

    interface Editor {
        fun getCodeBlock(coordinatesStart: Source, coordinatesEnd: Source): String
        fun replaceCodeBlock(start: Source, end: Source, newCodeBlock: String)
        fun insert(position: Source, newCodeBlock: String)
    }

    fun updateCoordinates(graphModel: GraphModel, editor: Editor) {

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