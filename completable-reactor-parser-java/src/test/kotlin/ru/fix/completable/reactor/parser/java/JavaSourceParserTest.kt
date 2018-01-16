package ru.fix.completable.reactor.parser.java

import org.junit.Test
import ru.fix.completable.reactor.api.gl.ReactorGraphVisualModel
import ru.fix.completable.reactor.api.gl.ReactorGraphVisualModel.VertexFigure
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

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

        fun vertexTransitions(name: String) = vertexByName(name).transitions.asIterable()

        vertexTransitions("userProfile")
                .apply {
                    assertEquals(2, count())
                }
                .apply {
                    assertNotNull(find {
                        it.mergeStatuses == listOf("STOP")
                                && it.isComplete
                                && !it.isOnAny
                                && it.target.run {
                            this is ReactorGraphVisualModel.EndPoint &&
                                    coordinates == ReactorGraphVisualModel.Coordinates(963, 258)
                        }
                    })
                }
                .apply {
                    assertNotNull(find {
                        it.mergeStatuses == listOf("CONTINUE")
                        && !it.isComplete
                        && !it.isOnAny
                        && it.target.run {
                            this is VertexFigure &&
                                    this.name == "serviceInfo"
                        }
                    })
                }
    }


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

