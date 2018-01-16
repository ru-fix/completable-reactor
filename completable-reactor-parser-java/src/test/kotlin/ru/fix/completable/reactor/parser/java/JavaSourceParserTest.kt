package ru.fix.completable.reactor.parser.java

import org.junit.Test
import ru.fix.completable.reactor.api.gl.ReactorGraphVisualModel
import ru.fix.completable.reactor.api.gl.ReactorGraphVisualModel.VertexFigure
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import kotlin.test.assertEquals

/**
 * @author Kamil Asfandiyarov
 */
class JavaSourceParserTest {

    fun readResource(resource: String): String {
        return JavaSourceParserTest::class.java.getResourceAsStream(resource)
                .reader(StandardCharsets.UTF_8)
                .use { it.readText() }
    }

    @Test
    fun build_visual_model_for_example1() {
        val body = readResource("/example1.java.txt")

        val startTime = Instant.now()

        val model = JavaSourceParser().parse(body)

        println("Parsing took ${Duration.between(startTime, Instant.now()).toMillis()}")

        assertEquals(
                listOf("userProfile", "serviceInfo"),
                model.startPoint.handleBy.map { it.name })

        fun vertexByName(name: String) = searchVertex(name, model.startPoint.handleBy)

        assertEquals(2, vertexByName("userProfile").transitions.size)


    }

    tailrec fun searchVertex(name: String, vertices: List<VertexFigure>): VertexFigure {

        vertices.find { it.name == name }?.let { return it }

        return searchVertex(name, vertices.flatMap { it.transitions }
                .mapNotNull {
                    if (it.target is VertexFigure) {
                        it.target as VertexFigure
                    } else {
                        null
                    }
                }.toList())
    }

}