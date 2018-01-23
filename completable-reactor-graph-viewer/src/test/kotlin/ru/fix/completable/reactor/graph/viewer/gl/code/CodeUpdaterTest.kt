package ru.fix.completable.reactor.graph.viewer.gl.code

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ru.fix.completable.reactor.graph.viewer.gl.CoordinateItem
import ru.fix.completable.reactor.graph.viewer.gl.CoordinateItem.Type
import java.util.*

/**
 * @author Kamil Asfandiyarov
 */
class CodeUpdaterTest {

    val updater = CodeUpdater()

    val coordinateItems: MutableList<CoordinateItem> = ArrayList()

    fun normalize(value: String): String {
        return value
                .replace("\r", "\n")
                .replace("\n\n", "\n")
                .replace("\n\n", "\n")
    }

    @Before
    fun create_test_coordiate_items() {
        coordinateItems.add(CoordinateItem(
                CoordinateItem.Type.START_POINT,
                null,
                500,
                -200))

        coordinateItems.add(CoordinateItem(
                Type.HANDLER,
                "smsRequestConstructProcessor4",
                910,
                901))

        coordinateItems.add(CoordinateItem(
                Type.HANDLER,
                "smsRequestConstructProcessor5",
                1170,
                1270))

        coordinateItems.add(CoordinateItem(
                Type.SUBGRAPH,
                "userProfileSubgraph",
                320,
                1141))

        coordinateItems.add(CoordinateItem(
                Type.MERGER,
                "serviceFetchProcessor5",
                1210,
                401))

        coordinateItems.add(CoordinateItem(
                Type.END_POINT,
                "smsRequestConstructProcessor6",
                1500,
                511))
    }

    private fun resourceAsText(resource: String): String {
        return javaClass.getResource(resource).readText()
    }

    @Test
    fun update_code_block() {
        val input = resourceAsText("/gl-code-block/code-block-1.txt")
        val expectedOutput = resourceAsText("/gl-code-block/code-block-1-result.txt")

        val output = updater.updateCoordinates(input, coordinateItems)
        Assert.assertEquals(normalize(expectedOutput), normalize(output))
    }

    @Test
    fun code_after_code_Block_should_not_be_changed() {
        val input = resourceAsText("/gl-code-block/code-block-2.txt")
        val expectedOutput = resourceAsText("/gl-code-block/code-block-2-result.txt")

        val output = updater.updateCoordinates(input, coordinateItems)
        Assert.assertEquals(normalize(expectedOutput), normalize(output))
    }

    @Test
    fun update_existing_code_bloc_what_it_smaller_that_new_one() {
        val input = resourceAsText("/gl-code-block/code-block-3.txt")
        val expectedOutput = resourceAsText("/gl-code-block/code-block-3-result.txt")

        val output = updater.updateCoordinates(input, coordinateItems)
        Assert.assertEquals(normalize(expectedOutput), normalize(output))
    }

}