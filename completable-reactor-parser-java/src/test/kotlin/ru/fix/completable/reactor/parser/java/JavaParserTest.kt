package ru.fix.completable.reactor.parser.java

import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.ErrorNode
import org.antlr.v4.runtime.tree.ParseTreeListener
import org.antlr.v4.runtime.tree.TerminalNode
import org.junit.Ignore
import org.junit.Test
import ru.fix.completable.reactor.parser.java.antlr.*
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import kotlin.test.assertEquals

/**
 * @author Kamil Asfandiyarov
 */
class JavaParserTest {

    fun readResource(resource: String): String {
        return JavaParserTest::class.java.getResourceAsStream(resource)
                .reader(StandardCharsets.UTF_8)
                .use { it.readText() }
    }

    @Ignore
    @Test
    fun parse() {
        val body = readResource("/example1.java.txt")

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
    fun `Parse graph config for java 9`(){
        val body = readResource("/example1.java.txt")

        val startTime = Instant.now()

        val lexer = GraphConfigJava9Lexer(CharStreams.fromString(body))
        val tokens = CommonTokenStream(lexer)
        val parser = GraphConfigJava9Parser(tokens)


        val result = parser.sourceFile().graphBlock()

        println("parsed for ${Duration.between(startTime, Instant.now()).toMillis()}")

        val vertices = result.asIterable()
                .mapNotNull { it.vertexInitializationBlock() }
                .map { it.Identifier().text }
                .toList()

        println("vertices: $vertices")

        val payloadTransitions = result.asIterable()
                .mapNotNull { it.payloadTransitionBlock() }
                .flatMap { it.handleBy() }
                .map { it.handleByVertex().Identifier().text }
                .toList()

        println("payloadTransitions: $payloadTransitions")

    }


    @Test
    fun `simple regexp approach`(){
        val body = readResource("/example1.java.txt")

        val startTime = Instant.now()


        val verticesRegexp = """(?<id>\w+)\s*=\s*new\s+Vertex\s*\(\s*\)\s*"""
                .toRegex()
        val vertices = verticesRegexp.findAll(body).map {
            it.groups["id"]!!.value
        }.toList()

        val oneOfVertexRegexp = vertices.joinToString("|", "(", ")").toRegex()
        val payloadRegexp = """\s*payload\s*\(\s*\)\s*(\.\s*handleBy\s*\(\s*(?<vx>$oneOfVertexRegexp)\s*\)\s*)+"""
                .toRegex()


        val handleBys = payloadRegexp.findAll(body).map {
            it.groups["vx"]!!
        }.toList()



        println("parsed for ${Duration.between(startTime, Instant.now()).toMillis()}")
    }




}