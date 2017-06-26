package ru.fix.completable.reactor.parser.java

/**
 * @author Kamil Asfandiyarov
 */
interface SymbolResolver {
    fun resolveFullClassNameByShortName(shortClassName: String) : String
}