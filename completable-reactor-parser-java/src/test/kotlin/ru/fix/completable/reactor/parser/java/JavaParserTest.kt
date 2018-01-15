package ru.fix.completable.reactor.parser.java

import org.junit.Ignore
import org.junit.Test
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

        val startTime = Instant.now()

        val models = JavaParser().parse(body)

        println("Parsing took ${Duration.between(startTime, Instant.now()).toMillis()}")

        assertEquals(1, models.size)
    }

}