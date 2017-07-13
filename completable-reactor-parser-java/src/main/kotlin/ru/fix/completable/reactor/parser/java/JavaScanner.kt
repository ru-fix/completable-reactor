package ru.fix.completable.reactor.parser.java

import ru.fix.completable.reactor.api.ReactorGraphModel
import java.util.*

/**
 * @author Kamil Asfandiyarov
 */
class JavaScanner(val input: String) {

    class ControlThrowable : Throwable(null, null, false, false)
    val STOP = ControlThrowable()


    var position = 0

    fun scan(): List<ReactorGraphModel> {
        val result = ArrayList<ReactorGraphModel>()

        while(findNext(Token.PAYLOAD)){
            tryParseGraph()?.let(result::add)
        }

        return result
    }

    private fun tryParseGraph(): ReactorGraphModel? {
        var startPosition = position
        try {
            val result = ReactorGraphModel()

            skipDot()
            skip



            return result

        }catch (exc: ControlThrowable){
            position = startPosition
            return null
        }
    }

    enum class Token{
        PAYLOAD,
        DOT
    }

    fun findNext(token: Token): Boolean {
        TODO()
    }

    fun skip(Token): Boolean {
        TODO()
    }


}