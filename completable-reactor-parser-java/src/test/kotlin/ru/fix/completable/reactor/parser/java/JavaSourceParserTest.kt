package ru.fix.completable.reactor.parser.java

import mu.KotlinLogging
import org.junit.Test
import ru.fix.completable.reactor.api.gl.ReactorGraphVisualModel.EndPoint
import ru.fix.completable.reactor.api.gl.ReactorGraphVisualModel.VertexFigure
import ru.fix.completable.reactor.api.gl.ReactorGraphVisualModel.Coordinates
import ru.fix.completable.reactor.api.gl.ReactorGraphVisualModel.Transition
import ru.fix.completable.reactor.api.gl.ReactorGraphVisualModel.Figure
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

private val log = KotlinLogging.logger {}

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

        val model = JavaSourceParser(object : JavaSourceParser.Listener {
            override fun error(msg: String) {
                log.error { msg }
                //TODO fix fail
//                fail(msg)
            }
        }).parse(body)

        println("Parsing took ${Duration.between(startTime, Instant.now()).toMillis()}")

        fun vertexByName(name: String) = searchVertex(name, model.startPoint.handleBy)

        fun vertexTransitions(name: String) = vertexByName(name).transitions.asIterable()

        assertEquals(
                listOf("userProfile", "serviceInfo"),
                model.startPoint.handleBy.map { it.name })

        vertexTransitions("userProfile")
                .apply {
                    assertEquals(2, count())
                }
                .apply {
                    assertNotNull(find {
                        it.mergeStatuses == listOf("STOP")
                                && it.isComplete
                                && !it.isOnAny
                                && it.target.let {
                            it is EndPoint && it.coordinates == Coordinates(963, 258)
                        }
                    })
                }
                .apply {
                    assertNotNull(find {
                        it.mergeStatuses == listOf("CONTINUE")
                                && !it.isComplete
                                && !it.isOnAny
                                && it.target.let {
                            it is VertexFigure && it.name == "serviceInfo"
                        }
                    })
                }

        vertexTransitions("serviceInfo")
                .apply {
                    assertEquals(5, count())
                }
                .apply {
                    assertNotNull(find {
                        it.mergeStatuses == listOf("WITHDRAWAL")
                                && !it.isComplete
                                && !it.isOnAny
                                && it.target.let {
                            it is VertexFigure && it.name == "bank"
                        }
                    })
                }
                .apply {
                    assertNotNull(find {
                        it.mergeStatuses == listOf("NO_WITHDRAWAL")
                                && !it.isComplete
                                && !it.isOnAny
                                && it.target.let {
                            it is VertexFigure && it.name == "userJournal"
                        }
                    })
                }
                .apply {
                    assertNotNull(find {
                        it.mergeStatuses == listOf("NO_WITHDRAWAL")
                                && !it.isComplete
                                && !it.isOnAny
                                && it.target.let {
                            it is VertexFigure && it.name == "webNotification"
                        }
                    })
                }
                .apply {
                    assertNotNull(find {
                        it.mergeStatuses == listOf("NO_WITHDRAWAL")
                                && !it.isComplete
                                && !it.isOnAny
                                && it.target.let {
                            it is VertexFigure && it.name == "smsNotification"
                        }
                    })
                }
                .apply {
                    assertNotNull(find {
                        it.mergeStatuses == listOf("STOP")
                                && it.isComplete
                                && !it.isOnAny
                                && it.target.let {
                            it is EndPoint && it.coordinates == Coordinates(480, 310)
                        }
                    })
                }

        vertexTransitions("bank")
                .apply {
                    assertEquals(1, count())
                }
                .apply {
                    assertNotNull(find {
                        it.mergeStatuses == null
                                && !it.isComplete
                                && it.isOnAny
                                && it.target.let { it is VertexFigure && it.name == "txLog" }
                    })
                }

        vertexTransitions("txLog")
                .apply {
                    assertEquals(1, count())
                }
                .apply {
                    assertNotNull(find {
                        it.mergeStatuses == null
                                && !it.isComplete
                                && it.isOnAny
                                && it.target.let { it is VertexFigure && it.name == "userJournal" }
                    })
                }

        vertexTransitions("userJournal")
                .apply {
                    assertEquals(1, count())
                }
                .apply {
                    assertNotNull(find {
                        it.mergeStatuses == null
                                && !it.isComplete
                                && it.isOnAny
                                && it.target.let { it is VertexFigure && it.name == "marketingCampaign" }
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

