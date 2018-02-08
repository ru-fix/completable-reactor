package ru.fix.completable.reactor.graph.viewer.gl.code

import ru.fix.completable.reactor.graph.viewer.gl.code.CoordinateCodePhrase.*
import ru.fix.completable.reactor.model.GraphModel
import ru.fix.completable.reactor.model.Source

/**
 * @author Kamil Asfandiyarov
 */
class CodeUpdater {

    companion object {
        private val coordinatesRegex = """\\s*coordinates\s*\(\s*\)""".toRegex()
        private val replacementCodeRegex = """(\s*\.\s*(payload|handler|merger|complete)\s*\(\s*[^\)]+\))+;?""".toRegex()

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
                .sortedWith(object : Comparator<CoordinateCodePhrase> {

                    override fun compare(item1: CoordinateCodePhrase?, item2: CoordinateCodePhrase?): Int {
                        return if (item1 == null || item2 == null) {
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
                    }
                })
                .joinToString(separator = "\n$padding", prefix = padding, postfix = ";") { item ->
                    generateCoordinateBuilderCode(item)
                }
    }


    /**
     * @param codeBlock block of code, first line of which contains .coordinates() method
     */
    internal fun updateCoordinates(codeBlock: String, coordinates: List<CoordinateCodePhrase>): String {

        val out = StringBuilder()

        val coordinatesRange = coordinatesRegex.find(codeBlock)?.range ?: return codeBlock

        val padding = StringBuilder()
        for (i in 0 until coordinatesRange.start) {
            padding.append(' ')
        }

        val codeBlockEditPartStart = coordinatesRange.endInclusive + 1

        val replacementCodeRange = replacementCodeRegex.find(codeBlock, codeBlockEditPartStart)?.range
                ?: return codeBlock

        val codeBlockEditPartEnd = replacementCodeRange.endInclusive

        out.append(codeBlock.substring(0, codeBlockEditPartStart + 1))

        out.append(printCoordinates(coordinates, padding.toString()))
        out.append(padding.toString())
        out.append(codeBlock.substring(codeBlockEditPartEnd + 1, codeBlock.length))

        return out.toString()
    }


    private fun generateCoordinateBuilderCode(phrase: CoordinateCodePhrase): String {
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
                editor.insert(endOfBlock, printCoordinates(coordinates, ""))
            }
        }
    }

}
