package ru.fix.completable.reactor.parser.java

import mu.KotlinLogging
import org.junit.Test
import ru.fix.completable.reactor.model.Coordinates
import ru.fix.completable.reactor.model.EndPoint
import ru.fix.completable.reactor.model.Source
import ru.fix.completable.reactor.model.VertexFigure
import java.nio.charset.StandardCharsets
import java.time.Duration
import java.time.Instant
import kotlin.test.*

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
    fun `build compilation model for example1`() {
        val body = readResource("/example1.java.txt")

        val startTime = Instant.now()

        val models = JavaSourceParser(object : JavaSourceParser.Listener {
            override fun error(msg: String) {
                log.error { msg }
                fail(msg)
            }
        }).parse(body)

        println("Parsing took ${Duration.between(startTime, Instant.now()).toMillis()}ms")

        assertEquals(1, models.size)

        val model = models[0]

        fun vertexTransitions(name: String) = model.transitionable[name]!!.transitions.asSequence()


        assertTrue(model.handlers.containsKey("userProfile"))
        assertTrue(model.handlers.containsKey("txLog"))
        assertTrue(model.handlers.containsKey("userJournal"))
        assertTrue(model.handlers.containsKey("webNotification"))
        assertTrue(model.handlers.containsKey("smsNotification"))
        assertTrue(model.handlers.containsKey("bank"))
        assertTrue(model.handlers.containsKey("serviceInfo"))
        assertTrue(model.handlers.containsKey("marketingCampaign"))
        assertTrue(model.subgraphs.containsKey("bonusPurchaseSubgraph"))
        assertTrue(model.routers.containsKey("isPartnerService"))


        assertEquals("PurchasePayload", model.startPoint.payloadType)
        assertEquals("Define purchase process when user buys good in the shop.", model.startPoint.doc)

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
                                && it.target.let { it is VertexFigure && it.name == "isPartnerService" }
                    })
                }

        vertexTransitions("isPartnerService")
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


        assertEquals("CheckWithdraw", model.mergers["bank"]!!.title)
        assertEquals("check profile state", model.mergers["userProfile"]!!.title)
        assertEquals("load user profile", model.handlers["userProfile"]!!.title)

        assertNull(model.routers["isPartnerService"]!!.title)
        assertTrue {
            model.routers["isPartnerService"]!!
                    .doc!!
                    .startsWith("Check if given service is provided by a partner.")
        }


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

        assertEquals(Source(null, 196, 8), model.startPoint.source)

        assertEquals(Source(null, 85, 12), model.handlers["bank"]!!.source)
        assertEquals(Source(null, 93, 14), model.mergers["bank"]!!.source)
        assertEquals(Source(null, 73, 12), model.handlers["webNotification"]!!.source)
    }


    @Test
    fun `parse various comments`() {
        val parser = JavaSourceParser(object : JavaSourceParser.Listener {
            override fun error(msg: String) {
                fail(msg)
            }
        })

        assertEquals(
                JavaSourceParser.Comment("Title here", null),
                parser.parseComment(
                        "/* \n" +
                                "   #Title here\n" +
                                "*/"))

        assertEquals(
                JavaSourceParser.Comment("Title here", "And documentation"),
                parser.parseComment(
                        "/*  # Title here\n" +
                                "   And documentation" +
                                "*/"))

        assertEquals(
                JavaSourceParser.Comment("Title here", null),
                parser.parseComment("//# Title here  \n"))

        assertEquals(
                JavaSourceParser.Comment("Title here", "And documentation\nmultiline"),
                parser.parseComment(
                        "// #    Title here\n" +
                                "       //     And documentation\n" +
                                "       //     multiline"))


        assertEquals(
                JavaSourceParser.Comment(null, "Documentation line 1\nDocumentation line 2"),
                parser.parseComment(
                        """
                        Documentation line 1
                        Documentation line 2
                        """.trimIndent()))

        assertEquals(
                JavaSourceParser.Comment(null, "Doc here."),
                parser.parseComment(
                        """
                         /**
                          * Doc here.
                          */
                        """.trimIndent()))
    }

}

