package ru.fix.completable.reactor.parser.java

import org.junit.Test
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

}