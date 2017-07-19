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

    fun parse(regexp: Regexp): String{
    }

    fun findNext(token: Token): Boolean {
        TODO()
    }

    fun skip(token: Token): Boolean {
        TODO()
    }

    fun skipIrrelevant(){
        while ( skipWhitespace() || skipLineComments());
    }

    fun skipWhitespace(): Boolean{
        val start = position
        while (input[position].isWhitespace()){
            position++
        }
        return start == position
    }
    fun skipLineComments(): Boolean {
        if(!have(2)){
            return false
        }
        val start = position
        if(check('/') && check(1) == '/'){
            skip(2)
            while (check() != '\n'){
                position++
            }
        }
    }

    fun isEnd(): Boolean = position == input.length
    fun have(symbolsCount: Int) = position + symbolsCount <= input.length
    fun skip(symbolsCount: Int) {
        position += symbolsCount
    }
    fun check(offset: Int = 0, symbol: Char): Boolean {
        if(!have(offset + 1)){
            return false
        }
        return input[position + offset] == symbol
    }

    fun check(sample: String) {
        if(!have(sample.length)){

        }
    }

}