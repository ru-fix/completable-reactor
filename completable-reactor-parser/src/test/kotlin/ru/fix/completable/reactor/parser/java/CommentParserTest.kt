package ru.fix.completable.reactor.parser.java

import org.junit.Test
import kotlin.test.assertEquals

class CommentParserTest {

    val parser = CommentParser()

    @Test
    fun `title inside multiline comment`() {
        assertEquals(
                Comment("Title here", null),
                parser.parseComment(
                        "/* \n" +
                                "   #Title here\n" +
                                "*/"))
    }

    @Test
    fun `title and single line doc inside multiline comment`() {
        assertEquals(
                Comment("Title here", "And documentation"),
                parser.parseComment(
                        "/*  # Title here\n" +
                                "   And documentation" +
                                "*/"))
    }

    @Test
    fun `title inside single line comment`() {
        assertEquals(
                Comment("Title here", null),
                parser.parseComment("//# Title here  \n"))
    }

    @Test
    fun `title and multiline doc inside single line comment`() {
        assertEquals(
                Comment("Title here", "And documentation\nmultiline"),
                parser.parseComment(
                        "// #    Title here\n" +
                                "       //     And documentation\n" +
                                "       //     multiline"))
    }

    @Test
    fun `multiline doc without comment prefix`() {
        assertEquals(
                Comment(null, "Documentation line 1\nDocumentation line 2"),
                parser.parseComment(
                        """
                        Documentation line 1
                        Documentation line 2
                        """.trimIndent()))
    }
    @Test
    fun `single doc inside multiline comments with prefix`() {
        assertEquals(
                Comment(null, "Doc here."),
                parser.parseComment(
                        """
                         /**
                          * Doc here.
                          */
                        """.trimIndent()))
    }

    @Test
    fun `multiline title inside comments without prefix`() {
        assertEquals(
                Comment("Multiline title\nwith second line", null),
                parser.parseComment("" +
                        " #   Multiline title   \n" +
                        "  #with second line  " +
                        "")
        )
    }

    @Test
    fun `multiline title with sigle line doc inside comments without prefix`(){
        assertEquals(
                Comment("Multiline title\nwith second line", "and documentation"),
                parser.parseComment("" +
                        " #   Multiline title   \n" +
                        "  #with second line  \n" +
                        " and documentation")
        )
    }

    @Test
    fun `multiline title with multiline doc inside comments without prefix`(){
        assertEquals(
                Comment("Multiline title\nwith second line", "and documentation\nwith second doc line"),
                parser.parseComment("" +
                        " #   Multiline title   \n" +
                        "  #with second line  \n" +
                        " and documentation\n" +
                        "  with second doc line\n")
        )
    }

    @Test
    fun `multiline title inside single comments with prefix`() {
        assertEquals(
                Comment("Multiline title\nwith second line", null),
                parser.parseComment("" +
                        " //# Multiline title   \n" +
                        " //# with second line  " +
                        "")
        )
    }


}