package ru.fix.completable.reactor.graph.viewer.gl.code

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import ru.fix.completable.reactor.graph.viewer.gl.code.CoordinateCodePhrase.*

/**
 * @author Kamil Asfandiyarov
 */
class CodeUpdaterTest {

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
                StartPointPhrase(500, -200),
                PlainVertexPhrase("plainVertex", 910, 901),
                ComplexVertexPhrase("complexVertex", 1170, 1270, 123, 213),
                EndPointPhrase("endPoint", 1500, 511))
    }

    private fun resourceAsText(resource: String): String {
        return javaClass.getResource(resource).readText()
    }

    @Test
    fun update_code_block_java() {
        val input = resourceAsText("/gl-code-block/code-block-1.txt")
        val expectedOutput = resourceAsText("/gl-code-block/code-block-1-result.txt")

        val output = CodeUpdater(CodeUpdater.CodeType.JAVA).updateCoordinates(input, codeCoordinates)
        Assert.assertEquals(normalize(expectedOutput), normalize(output))
    }

}