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

    private fun readResource(resource: String): String {
        return JavaSourceParserTest::class.java.getResourceAsStream(resource)
                .reader(StandardCharsets.UTF_8)
                .use { it.readText() }
    }

    @Test
    fun build_compilation_model_for_single_purchase_graph() {
        val sourceFilePath = "/single-purchase-graph.java.txt"

        val body = readResource(sourceFilePath)

        val startTime = Instant.now()

        val models = JavaSourceParser(object : JavaSourceParser.Listener {
            override fun error(msg: String) {
                log.error { msg }
                fail(msg)
            }
        }).parse(body, sourceFilePath)

        log.info { "Parsing took ${Duration.between(startTime, Instant.now()).toMillis()}ms" }

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
        assertEquals("PurchaseGraph", model.graphClass)
        assertEquals("Defines purchase process when user buys good in the shop.", model.startPoint.doc)

        assertEquals(
                listOf("userProfile", "serviceInfo"),
                model.startPoint.handleBy.map { it.name })

        vertexTransitions("userProfile")
                .apply {
                    assertEquals(2, count())

                    assertNotNull(find {
                        it.mergeStatuses == setOf("STOP")
                                && it.isComplete
                                && !it.isOnAny
                                && it.target is EndPoint
                    })

                    assertEquals(
                            Coordinates(897, 225),
                            find { it.mergeStatuses == setOf("STOP") }?.target?.coordinates)

                    assertNotNull(find {
                        it.mergeStatuses == setOf("CONTINUE")
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
                        it.mergeStatuses == setOf("WITHDRAWAL")
                                && !it.isComplete
                                && !it.isOnAny
                                && it.target.let {
                            it is VertexFigure && it.name == "bank"
                        }
                    })

                    assertNotNull(find {
                        it.mergeStatuses == setOf("NO_WITHDRAWAL")
                                && !it.isComplete
                                && !it.isOnAny
                                && it.target.let {
                            it is VertexFigure && it.name == "txLog2"
                        }
                    })

                    assertNotNull(find {
                        it.mergeStatuses == setOf("NO_WITHDRAWAL")
                                && !it.isComplete
                                && !it.isOnAny
                                && it.target.let {
                            it is VertexFigure && it.name == "webNotification"
                        }
                    })

                    assertNotNull(find {
                        it.mergeStatuses == setOf("NO_WITHDRAWAL")
                                && !it.isComplete
                                && !it.isOnAny
                                && it.target.let {
                            it is VertexFigure && it.name == "smsNotification"
                        }
                    })

                    assertNotNull(find {
                        it.mergeStatuses == setOf("STOP")
                                && it.isComplete
                                && !it.isOnAny
                                && it.target is EndPoint
                    })

                    assertEquals(
                            Coordinates(497, 293),
                            find { it.mergeStatuses == setOf("STOP") }?.target?.coordinates)
                }

        vertexTransitions("bank")
                .apply {
                    assertEquals(1, count())

                    assertNotNull(find {
                        it.mergeStatuses.isEmpty()
                                && !it.isComplete
                                && it.isOnAny
                                && it.target.let { it is VertexFigure && it.name == "isPartnerService" }
                    })
                }

        vertexTransitions("isPartnerService")
                .apply {
                    assertEquals(1, count())

                    assertNotNull(find {
                        it.mergeStatuses.isEmpty()
                                && !it.isComplete
                                && it.isOnAny
                                && it.target.let { it is VertexFigure && it.name == "txLog" }
                    })
                }

        vertexTransitions("txLog")
                .apply {
                    assertEquals(1, count())

                    assertNotNull(find {
                        it.mergeStatuses.isEmpty()
                                && !it.isComplete
                                && it.isOnAny
                                && it.target.let { it is VertexFigure && it.name == "userJournal" }
                    })
                }

        vertexTransitions("txLog2")
                .apply {
                    assertEquals(1, count())

                    assertNotNull(find {
                        it.mergeStatuses.isEmpty()
                                && !it.isComplete
                                && it.isOnAny
                                && it.target.let { it is VertexFigure && it.name == "userJournal" }
                    })
                }

        vertexTransitions("userJournal")
                .apply {
                    assertEquals(1, count())

                    assertNotNull(find {
                        it.mergeStatuses.isEmpty()
                                && !it.isComplete
                                && it.isOnAny
                                && it.target.let { it is VertexFigure && it.name == "marketingCampaign" }
                    })
                }

        vertexTransitions("marketingCampaign")
                .apply {
                    assertEquals(2, count())

                    assertNotNull(find {
                        it.mergeStatuses.singleOrNull() == "BONUS_EXIST"
                                && !it.isComplete
                                && !it.isOnAny
                                && it.target.let { it is VertexFigure && it.name == "bonusPurchaseSubgraph" }
                    }?.also {
                        val docs = it.transitionDocs.singleOrNull()
                        assertNotNull(docs)
                        assertEquals("BONUS_EXIST", docs!!.mergeStatus)
                        assertEquals("User can claim bonus purchase", docs.docs)
                    })

                    assertNotNull(find {
                        it.mergeStatuses.singleOrNull() == "NO_BONUS"
                                && it.isComplete
                                && !it.isOnAny
                                && it.target.let { it is EndPoint }
                    }?.also {
                        val docs = it.transitionDocs.singleOrNull()
                        assertNotNull(docs)
                        assertEquals("NO_BONUS", docs!!.mergeStatus)
                        assertEquals("User does not have any bonuses", docs.docs)
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


        assertEquals(Coordinates(627, 46), model.startPoint.coordinates)
        assertEquals(Coordinates(349, 377), model.handleable["bank"]!!.coordinates)
        assertEquals(Coordinates(926, 333), model.handleable["webNotification"]!!.coordinates)
        assertEquals(Coordinates( 378, 441), model.mergers["bank"]!!.coordinates)
        assertEquals(Coordinates(497, 293), model.endpoints["serviceInfo"]!!.coordinates)

        assertEquals(Source(sourceFilePath, 212, 9, 7712), model.startPoint.source)

        assertEquals(Source(sourceFilePath, 94, 13, 2757), model.handlers["bank"]!!.source)

    }

    @Test
    fun two_test_graphs_in_single_source() {
        val sourceFilePath = "/two-test-graphs-in-one-source.java.txt"
        val body = readResource(sourceFilePath)

        val startTime = Instant.now()

        val models = JavaSourceParser(object : JavaSourceParser.Listener {
            override fun error(msg: String) {
                log.error { msg }
                fail(msg)
            }
        }).parse(body, sourceFilePath)

        log.info { "Parsing took ${Duration.between(startTime, Instant.now()).toMillis()}ms" }

        assertEquals(2, models.size)

        val singleProcessorGraph = models
                .find { it.graphClass == "SingleProcessorGraph" }
                ?: fail()

        assertNotNull(singleProcessorGraph.handlers["idProcessor1"])

        assertEquals(-98, singleProcessorGraph.startPoint.coordinates!!.y)

        assertEquals(Source(sourceFilePath, 43, 13, 1169), singleProcessorGraph.coordinatesStart)
        assertEquals(Source(sourceFilePath, 47, 55, 1381), singleProcessorGraph.coordinatesEnd)
        assertEquals(Source(sourceFilePath, 47, 55, 1381), singleProcessorGraph.endOfLastCodeBlocksWithinGraph)

        val twoProcesssorSequenceGraph = models
                .find { it.graphClass == "TwoProcessorSequentialMergeGraph" }
                ?: fail()

        assertNotNull(twoProcesssorSequenceGraph.handlers["idProcessor1"])
        assertNotNull(twoProcesssorSequenceGraph.handlers["idProcessor2"])

        assertNull(twoProcesssorSequenceGraph.coordinatesStart)
        assertNull(twoProcesssorSequenceGraph.coordinatesEnd)
        assertEquals(Source(sourceFilePath, 87, 45, 2624), twoProcesssorSequenceGraph.endOfLastCodeBlocksWithinGraph)
    }


    @Test
    fun parse_various_comments() {
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

