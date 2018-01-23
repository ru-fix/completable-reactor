package ru.fix.completable.reactor.graph.viewer.gl.code

import ru.fix.completable.reactor.graph.viewer.gl.CoordinateItem
import ru.fix.completable.reactor.graph.viewer.gl.CoordinateItem.Type

/**
 * @author Kamil Asfandiyarov
 */
class CodeUpdater {

    companion object {
        private val COORDINATES = ".coordinates()"
        private val BUILD_GRAPH = ".buildGraph()"

        private val coordinateTypeOrder = listOf(
                Type.START_POINT,
                Type.HANDLER,
                Type.SUBGRAPH,
                Type.ROUTER,
                Type.MERGER,
                Type.END_POINT)
                .mapIndexed { index, type -> Pair(type, index) }
                .toMap()
    }


    /**
     * @param codeBlock block of code that contains .coordinates() and .buildGraph() methods
     * @return
     */
    fun updateCoordinates(codeBlock: String, coordinates: List<CoordinateItem>): String {

        val out = StringBuilder()

        var start = codeBlock.indexOf(COORDINATES)
        if (start < 0) {
            return codeBlock
        }

        val padding = StringBuilder()
        for (i in 0 until start) {
            padding.append(' ')
        }

        start += COORDINATES.length

        val end = codeBlock.indexOf(BUILD_GRAPH)
        if (end < 0) {
            return codeBlock
        }

        out.append(codeBlock.substring(0, start + 1))

        coordinates.stream()
                .sorted { item1, item2 ->

                    //compare Type
                    if (item1.type == item2.type) {

                        //type are equals, compare processor
                        val cmpProc = (item1.name ?: "").compareTo(item2.name ?: "")

                        if (cmpProc == 0) {
                            //processors are equals
                            val cmpx = Integer.compare(item1.x, item2.x)
                            if (cmpx == 0) {
                                return@sorted Integer.compare(item1.y, item2.y)
                            } else {
                                return@sorted cmpx
                            }
                        } else {
                            return@sorted cmpProc
                        }
                    } else {
                        Integer.compare(coordinateTypeOrder[item1.type]!!, coordinateTypeOrder[item2.type]!!)
                    }
                }
                .forEach { item ->
                    out.append(padding.toString())
                    out.append(serialize(item))
                    out.append("\n")
                }

        out.append("\n")
        out.append(padding.toString())
        out.append(codeBlock.substring(end, codeBlock.length))

        return out.toString()
    }


    private fun serialize(item: CoordinateItem): String {
        return when (item.type) {
            Type.START_POINT -> String.format(".payload(%d, %d)", item.x, item.y)
            Type.HANDLER -> String.format(".handler(%s, %d, %d)", item.name, item.x, item.y)
            Type.SUBGRAPH -> String.format(".subgraph(%s, %d, %d)", item.name, item.x, item.y)
            Type.MERGER -> String.format(".merger(%s, %d, %d)", item.name, item.x, item.y)
            Type.ROUTER -> String.format(".router(%s, %d, %d)", item.name, item.x, item.y)
            Type.END_POINT -> String.format(".complete(%s, %d, %d)", item.name, item.x, item.y)
        }
    }

}
