package ru.fix.completable.reactor.graph.internal

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import ru.fix.completable.reactor.example.PurchaseGraph
import ru.fix.completable.reactor.graph.runtime.RuntimeGraph
import ru.fix.completable.reactor.model.*
import ru.fix.completable.reactor.runtime.GraphBuilder

const val LOAD_USER_PROFILE = "loadUserProfile"
const val TX_LOG = "logTransaction"
const val TX_LOG2 = "logTransaction2"
const val USER_JOURNAL = "logActionToUserJournal"
const val WEB_NOTIFICATION = "sendWebNotification"
const val SMS_NOTIFICATION = "sendSmsNotification"
const val BANK = "withdrawMoneyWithMinus"
const val IS_PARTNER_SERVICE = "isPartnerService"
const val SERVICE_INFO = "loadServiceInfo"
const val MARKETING_CAMPAIGN = "checkBonuses"
const val BONUS_PURCHASE_SUBGRAPH = "bonusPurchaseSubgraph"

// TODO: Нужно ли делать полноценную compile time model из runtime?
// TODO: Если нет то нужно выдилть промежуточное звено, помимо model
// TOOD: Может RuntimeVertex базовый класс, а Handler, Router ... -> подтипы? по аналогии с View model
internal class ModelBuilderTest {

    @Test
    fun `create compile time graph model from runtime graph model`() {

        val graph = PurchaseGraph()

        val runtimeGraph = GraphBuilder().buildGraph(graph)

        val compileTimeGraph = ModelBuilder().buildCompileTimeFromRuntime(runtimeGraph)

        fun vertexTransitions(name: String) = compileTimeGraph.transitionable[name]!!.transitions.asSequence()

        assertTrue { 2 == compileTimeGraph.transitionable[LOAD_USER_PROFILE]!!.transitions.size }
        assertTrue { 1 == compileTimeGraph.transitionable[IS_PARTNER_SERVICE]!!.transitions.size }


        assertTrue { compileTimeGraph.handlers.containsKey(LOAD_USER_PROFILE) }
        assertTrue { compileTimeGraph.handlers.containsKey(SERVICE_INFO) }
        assertTrue { compileTimeGraph.handlers.containsKey(BANK) }
        assertTrue { compileTimeGraph.handlers.containsKey(WEB_NOTIFICATION) }

        assertTrue { compileTimeGraph.subgraphs.containsKey(BONUS_PURCHASE_SUBGRAPH) }

        assertTrue { compileTimeGraph.routers.containsKey(MARKETING_CAMPAIGN) }
        assertTrue { compileTimeGraph.routers.containsKey(IS_PARTNER_SERVICE) }

        assertTrue { compileTimeGraph.mergers.containsKey(LOAD_USER_PROFILE) }
        assertTrue { compileTimeGraph.mergers.containsKey(SERVICE_INFO) }
        assertTrue { compileTimeGraph.mergers.containsKey(BANK) }
        assertFalse { compileTimeGraph.mergers.containsKey(WEB_NOTIFICATION) }
        assertFalse { compileTimeGraph.mergers.containsKey(BONUS_PURCHASE_SUBGRAPH) }

        assertEquals(
                listOf(LOAD_USER_PROFILE, SERVICE_INFO),
                compileTimeGraph.startPoint.handleBy.map { it.name }
        )

        vertexTransitions(LOAD_USER_PROFILE)
                .apply {
                    assertEquals(2, count())

                    assertNotNull(
                            find {
                                it.mergeStatuses == setOf("STOP")
                                        && it.isComplete
                                        && !it.isOnAny
                                        && it.target is EndPoint
                            }
                    )

//                    assertEquals(
//                            Coordinates(897, 225),
//                            find { it.mergeStatuses == setOf("STOP") }?.target?.coordinates)

                    assertNotNull(find {
                        it.mergeStatuses == setOf("CONTINUE")
                                && !it.isComplete
                                && !it.isOnAny
                                && it.target.let {
                            it is Merger && it.name == SERVICE_INFO
                        }
                    })
                }
    }
}