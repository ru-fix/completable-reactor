package ru.fix.completable.reactor.parser.java

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.junit.Ignore
import org.junit.Test
import ru.fix.completable.reactor.parser.java.antlr.Java9BaseListener
import ru.fix.completable.reactor.parser.java.antlr.Java9BaseVisitor
import ru.fix.completable.reactor.parser.java.antlr.Java9Lexer
import ru.fix.completable.reactor.parser.java.antlr.Java9Parser
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import kotlin.test.assertEquals

/**
 * @author Kamil Asfandiyarov
 */
class JavaParserTest {

    @Ignore
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



        val startTime = Instant.now()

        val lexer = Java9Lexer(CharStreams.fromString(body))
        val tokens = CommonTokenStream(lexer)
        val parser = Java9Parser(tokens)

//        parser.addParseListener(L())

        val res = parser.compilationUnit()
//        V().visitCompilationUnit(parser.compilationUnit())


        println("parsed for ${Duration.between(startTime, Instant.now()).seconds}")

    }

    class V : Java9BaseVisitor<Void?>() {
        override fun visitFieldDeclaration(ctx: Java9Parser.FieldDeclarationContext?): Void? {

            print("V: $ctx")
            return null
        }
    }

    class L : Java9BaseListener() {
        override fun enterFieldDeclaration(ctx: Java9Parser.FieldDeclarationContext?) {

            println("L: ${ctx?.fieldModifier()}")
        }

        override fun exitFieldDeclaration(ctx: Java9Parser.FieldDeclarationContext?) {
            println("L: exit $ctx")
        }
    }




}