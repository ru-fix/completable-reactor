package ru.fix.completable.reactor.parser.java

import mu.KotlinLogging
import org.junit.Test
import ru.fix.completable.reactor.api.gl.model.*
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.fail

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
    fun `build visual model for example1`() {
        val body = readResource("/example1.java.txt")

        val startTime = Instant.now()

        val model = JavaSourceParser(object : JavaSourceParser.Listener {
            override fun error(msg: String) {
                log.error { msg }
                fail(msg)
            }
        }).parse(body)

        println("Parsing took ${Duration.between(startTime, Instant.now()).toMillis()}ms")


        fun vertexTransitions(name: String) = model.transitionable[name]!!.transitions.asIterable()

        assertEquals("PurchasePayload", model.startPoint.payloadType)

        assertEquals(
                listOf("userProfile", "serviceInfo"),
                model.startPoint.handleBy.map { it.name })

        vertexTransitions("userProfile")
                .apply {
                    assertEquals(2, count())

                    assertNotNull(find {
                        it.mergeStatuses == listOf("STOP")
                                && it.isComplete
                                && !it.isOnAny
                                && it.target.let {
                            it is EndPoint && it.coordinates == Coordinates(963, 258)
                        }
                    })

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

                    assertNotNull(find {
                        it.mergeStatuses == listOf("WITHDRAWAL")
                                && !it.isComplete
                                && !it.isOnAny
                                && it.target.let {
                            it is VertexFigure && it.name == "bank"
                        }
                    })

                    assertNotNull(find {
                        it.mergeStatuses == listOf("NO_WITHDRAWAL")
                                && !it.isComplete
                                && !it.isOnAny
                                && it.target.let {
                            it is VertexFigure && it.name == "userJournal"
                        }
                    })

                    assertNotNull(find {
                        it.mergeStatuses == listOf("NO_WITHDRAWAL")
                                && !it.isComplete
                                && !it.isOnAny
                                && it.target.let {
                            it is VertexFigure && it.name == "webNotification"
                        }
                    })

                    assertNotNull(find {
                        it.mergeStatuses == listOf("NO_WITHDRAWAL")
                                && !it.isComplete
                                && !it.isOnAny
                                && it.target.let {
                            it is VertexFigure && it.name == "smsNotification"
                        }
                    })

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

                    assertNotNull(find {
                        it.mergeStatuses == null
                                && !it.isComplete
                                && it.isOnAny
                                && it.target.let { it is VertexFigure && it.name == "marketingCampaign" }
                    })
                }

        assertEquals("checkWithdraw", model.mergers["bank"]!!.title)
        assertEquals("check profile state", model.mergers["userProfile"]!!.title)

        assertEquals("load user profile", model.handlers["userProfile"]!!.title)

        assertEquals(Coordinates(680, 60), model.startPoint.coordinates)
        assertEquals(Coordinates(410, 440), model.handleable["bank"]!!.coordinates)
        assertEquals(Coordinates(880, 430), model.handleable["webNotification"]!!.coordinates)
        assertEquals(Coordinates(850, 450), model.handleable["smsNotification"]!!.coordinates)
        assertEquals(Coordinates(480, 120), model.handleable["serviceInfo"]!!.coordinates)
        assertEquals(Coordinates(420, 650), model.handleable["txLog"]!!.coordinates)
        assertEquals(Coordinates(680, 820), model.handleable["userJournal"]!!.coordinates)
        assertEquals(Coordinates(770, 120), model.handleable["userProfile"]!!.coordinates)
        assertEquals(Coordinates(480, 550), model.mergers["bank"]!!.coordinates)
        assertEquals(Coordinates(640, 280), model.mergers["serviceInfo"]!!.coordinates)
        assertEquals(Coordinates(530, 770), model.mergers["txLog"]!!.coordinates)
        assertEquals(Coordinates(760, 930), model.mergers["userJournal"]!!.coordinates)
        assertEquals(Coordinates(806, 201), model.mergers["userProfile"]!!.coordinates)
        assertEquals(Coordinates(480, 310), model.endpoints["serviceInfo"]!!.coordinates)
        assertEquals(Coordinates(963, 258), model.endpoints["userProfile"]!!.coordinates)
    }
}

