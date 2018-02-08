package ru.fix.completable.reactor.graph.viewer.gl.code

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ru.fix.completable.reactor.graph.viewer.gl.code.CoordinateCodePhrase.*

/**
 * @author Kamil Asfandiyarov
 */
class CodeUpdaterTest {

    val updater = CodeUpdater()

    lateinit var codeCoordinates: List<CoordinateCodePhrase>

    fun normalize(value: String): String {
        return value
                .replace("\r", "\n")
                .replace("\n\n", "\n")
                .replace("\n\n", "\n")
    }

    @Before
    fun create_test_coordiate_items() {

        codeCoordinates = listOf(
                StartPoint(500, -200),
                Handler("smsRequestConstructProcessor4", 910, 901),
                Handler("smsRequestConstructProcessor5", 1170, 1270),
                Subgraph("userProfileSubgraph", 320, 1141),
                Merger("serviceFetchProcessor5", 1210, 401),
                EndPoint("smsRequestConstructProcessor6", 1500, 511))
    }

    private fun resourceAsText(resource: String): String {
        return javaClass.getResource(resource).readText()
    }

    @Test
    fun update_code_block() {
        val input = resourceAsText("/gl-code-block/code-block-1.txt")
        val expectedOutput = resourceAsText("/gl-code-block/code-block-1-result.txt")

        val output = updater.updateCoordinates(input, codeCoordinates)
        Assert.assertEquals(normalize(expectedOutput), normalize(output))
    }

}