/**
 * @author Kamil Asfandiyarov
 */
class Context(val parent: Context?){

    val children = ArrayList<Context>()

    init {
        parent?.addChild(this)
    }

    fun addChild(context: Context) {
        children.add(context)
    }

    fun resolveSymbol(symbol: String) {

    }

    fun registerSymbol(symbol: String) {

    }
}