package ru.fix.completable.reactor.parser.java

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.junit.Test
import ru.fix.completable.reactor.parser.java.antlr.Java8BaseListener
import ru.fix.completable.reactor.parser.java.antlr.Java8BaseVisitor
import ru.fix.completable.reactor.parser.java.antlr.Java8Lexer
import ru.fix.completable.reactor.parser.java.antlr.Java8Parser
import java.nio.charset.StandardCharsets
import kotlin.test.assertEquals

/**
 * @author Kamil Asfandiyarov
 */
class JavaParserTest {

    @Test
    fun parse() {
        val body = JavaParserTest::class.java.getResourceAsStream("/example1.java.txt")
                .reader(StandardCharsets.UTF_8)
                .use { it.readText() }

        val symbolsResolver: SymbolResolver = object: SymbolResolver {
            override fun resolveFullClassNameByShortName(shortClassName: String): String {
                TODO("not implemented")
            }
        }

        val models = JavaParser(symbolsResolver).parse(body)

        assertEquals(2, models.size)

        assertEquals("ru.fix.completable.reactor.example.chain.PurchasePayload", models[0].payload.payloadClass)
        assertEquals("ru.fix.completable.reactor.example.chain.SubscribePayload", models[1].payload.payloadClass)
    }


    @Test
    fun antlrTest(){
        val body = JavaParserTest::class.java.getResourceAsStream("/example1.java.txt")
                .reader(StandardCharsets.UTF_8)
                .use { it.readText() }



        val lexer = Java8Lexer(CharStreams.fromString(body))
        val tokens = CommonTokenStream(lexer)
        val parser = Java8Parser(tokens)

        parser.addParseListener(L())

        parser.compilationUnit()
//        V().visitCompilationUnit(parser.compilationUnit())


    }

    class V : Java8BaseVisitor<Void?>() {
        override fun visitFieldDeclaration(ctx: Java8Parser.FieldDeclarationContext?): Void? {

            print("V: $ctx")
            return null
        }
    }

    class L : Java8BaseListener() {
        override fun enterFieldDeclaration(ctx: Java8Parser.FieldDeclarationContext?) {

            println("L: ${ctx?.fieldModifier()}")
        }

        override fun exitFieldDeclaration(ctx: Java8Parser.FieldDeclarationContext?) {
            println("L: exit $ctx")
        }
    }




}