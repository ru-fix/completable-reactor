package ru.fix.completable.reactor.parser.java

import ru.fix.completable.reactor.api.ReactorGraphModel
import sun.misc.Regexp
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

            skip(Token.PAYLOAD)
            skip(Token.BRACKETS)



            return result

        }catch (exc: ControlThrowable){
            position = startPosition
            return null
        }
    }

    enum class Token{
        PAYLOAD,
        DOT,

        BRACKETS
    }

    fun parse(regexp: Regexp):String{
        regexp.
    }

    fun findNext(token: Token): Boolean {
        TODO()
    }

    fun skip(token: Token): Boolean {
        TODO()
    }


}